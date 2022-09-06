package acme.features.chef.recipes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.Recipe;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefRecipePublishService implements AbstractUpdateService<Chef, Recipe> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefRecipeRepository repository;


	@Override
	public boolean authorise(final Request<Recipe> request) {
		assert request != null;

		final Integer id = request.getModel().getInteger("id");
		final Optional<AbstractEntity> result = this.repository.findById(id);
		final Principal principal = request.getPrincipal();
		
		return result.isPresent() && ((Recipe)result.get()).getChef().getId() == principal.getActiveRoleId();
	}

	@Override
	public void validate(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//---Heading
		if (!errors.hasErrors("heading")) {
			errors.state(request, entity.getHeading().length()<101, "heading", "chef.recipe.error.heading.a-lot-of-words");
			final String[] text = entity.getHeading().toLowerCase().replace("\n", " ").split("\\s+");
			final Double spamValue = this.checkSpam(text);
			final Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "heading", "chef.recipe.error.heading.spam-threshold");
		}
		//---Code
		if(!errors.hasErrors("code")) {
			final Optional<Recipe> existingCode = this.repository.findRecipeByCode(entity.getCode());
			if(existingCode.isPresent())
				errors.state(request, existingCode.get().getId() == entity.getId(), "code", "chef.recipe.error.code.repeated");
		}
		//---description
		if(!errors.hasErrors("description")) {
			errors.state(request, entity.getDescription().length()<256, "description", "chef.recipe.error.description.a-lot-of-words");
			final String[] text = entity.getDescription().toLowerCase().replace("\n", " ").split("\\s+");
			final Double spamValue = this.checkSpam(text);
			final Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "description", "chef.recipe.error.description.spam-threshold");
		}
		//---preparationNotes
		if(!errors.hasErrors("preparationNotes")) {
			errors.state(request, entity.getPreparationNotes().length()<256, "preparationNotes", "chef.recipe.error.preparationNotes.a-lot-of-words");
			final String[] text = entity.getPreparationNotes().toLowerCase().replace("\n", " ").split("\\s+");
			final Double spamValue = this.checkSpam(text);
			final Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "preparationNotes", "chef.recipe.error.preparationNotes.spam-threshold");
		}
		
	}

	@Override
	public void bind(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "heading", "code", "description", "preparationNotes", "link");
		
	}

	@Override
	public void unbind(final Request<Recipe> request, final Recipe entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "heading", "code", "description", "preparationNotes", "link", "isPublished");
	}

	@Override
	public Recipe findOne(final Request<Recipe> request) {
		assert request != null;
		
		Recipe result=null;
		int id;

		id = request.getModel().getInteger("id");
		final Optional<AbstractEntity> optResult = this.repository.findById(id);
		if (optResult.isPresent()) {
			result = (Recipe) optResult.get();
		}

		assert result != null;
		
		return result;
	}

	@Override
	public void update(final Request<Recipe> request, final Recipe entity) {
		assert request != null;
		assert entity != null;
		
		entity.setPublished(true);
		this.repository.save(entity);
	}
	
	public Double checkSpam(final String[] text) {
		//--Initial data
		final SystemSettings ss = this.repository.findAllSpanTuples();
		final String[] strongWords = ss.getSpamTuples().toLowerCase().replace("\\(", "").replace(", ", ",").split("\\),");;
		Integer nWord = 0;
		Double spamValue= 0.;
		nWord = text.length;
		//--Method
		for(int i = 0; i<strongWords.length;i++) {
			final String[] spamTuple = strongWords[i].toLowerCase().split(",");
			final String[] spamTerm = spamTuple[0].split(" ");
			for(int o = 0; o<nWord; o++) {
				boolean b = false;
				final int aux = nWord-spamTerm.length+1;
				for(int p = 0; p<spamTerm.length; p++) {
					b = true;
					
					if(!spamTerm[p].equals(text[o+p])) {
						b=false;
						break;
					}
				}
				if(b) {
					final Double spamTermWeight = Double.parseDouble(spamTuple[1]);
					spamValue += spamTermWeight/aux;
				}
			}
		}
		return spamValue;
	}

}