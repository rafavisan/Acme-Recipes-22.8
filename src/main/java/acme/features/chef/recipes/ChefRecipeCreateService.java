package acme.features.chef.recipes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.Recipe;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class ChefRecipeCreateService implements AbstractCreateService<Chef, Recipe> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefRecipeRepository repository;

	// AbstractCreateService<Chef, Recipe> interface --------------


	@Override
	public boolean authorise(final Request<Recipe> request) {
		assert request != null;

		return true;
	}

	@Override
	public Recipe instantiate(final Request<Recipe> request) {
		assert request != null;

		Recipe result;
		Chef chf;

		int i =request.getPrincipal().getActiveRoleId();
		chf = this.repository.findOneInventorByInventorId(i);
		result = new Recipe();
		
		result.setPublished(false);
		result.setChef(chf);
		
		return result;
	}

	@Override
	public void bind(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "heading", "description", "preparationNotes", "link");
		
	}

	@Override
	public void validate(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		//---Heading
		if (!errors.hasErrors("heading")) {
			errors.state(request, entity.getHeading().length()<101, "heading", "chef.recipe.error.heading.a-lot-of-words");
			String[] text = entity.getHeading().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "heading", "chef.recipe.error.heading.spam-threshold");
		}
		//---Code
		if(!errors.hasErrors("code")) {
			Optional<Recipe> existingCode = this.repository.findRecipeByCode(entity.getCode());
			errors.state(request, existingCode.isPresent() || existingCode.get().getId() == entity.getId(), "code", "chef.recipe.error.code.repeated");
		}
		//---description
		if(!errors.hasErrors("description")) {
			errors.state(request, entity.getDescription().length()<256, "description", "chef.recipe.error.description.a-lot-of-words");
			String[] text = entity.getDescription().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "description", "chef.recipe.error.description.spam-threshold");
		}
		//---preparationNotes
		if(!errors.hasErrors("preparationNotes")) {
			errors.state(request, entity.getPreparationNotes().length()<256, "preparationNotes", "chef.recipe.error.preparationNotes.a-lot-of-words");
			String[] text = entity.getPreparationNotes().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "preparationNotes", "chef.recipe.error.preparationNotes.spam-threshold");
		}
		
	}

	@Override
	public void unbind(final Request<Recipe> request, final Recipe entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "heading", "description", "preparationNotes", "link");
		model.setAttribute("isNew", true);
	}

	@Override
	public void create(final Request<Recipe> request, final Recipe entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
	
	public Double checkSpam(String[] text) {
		//--Initial data
		SystemSettings ss = this.repository.findAllSpanTuples();
		String[] strongWords = ss.getSpamTuples().toLowerCase().replaceAll("\\(", "").replaceAll(", ", ",").split("\\),");;
		Integer nWord = 0;
		Double spamValue= 0.;
		nWord = text.length;
		//--Method
		for(int i = 0; i<strongWords.length;i++) {
			String[] spamTuple = strongWords[i].toLowerCase().split(",");
			String[] spamTerm = spamTuple[0].split(" ");
			for(int o = 0; o<nWord; o++) {
				boolean b = false;
				int aux = nWord-spamTerm.length+1;
				for(int p = 0; p<spamTerm.length; p++) {
					b = true;
					
					if(!spamTerm[p].equals(text[o+p])) {
						b=false;
						break;
					}
				}
				if(b) {
					Double spamTermWeight = Double.parseDouble(spamTuple[1]);
					spamValue += spamTermWeight/aux;
				}
			}
		}
		return spamValue;
	}

}