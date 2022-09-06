package acme.features.chef.artifact;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifact.Artifact;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefArtifactUpdateService implements AbstractUpdateService<Chef, Artifact> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefArtifactRepository repository;


	@Override
	public boolean authorise(final Request<Artifact> request) {
		assert request != null;

		boolean result = true;
		int masterId;
		Optional<AbstractEntity> art;
		Chef chef;

		masterId = request.getModel().getInteger("id");
		art = this.repository.findById(masterId);
		if(!art.isPresent()) {
			return false;
		}
		chef = ((Artifact) art.get()).getChef();
		result = request.isPrincipal(chef);
		
		return result;
	}

	@Override
	public void validate(final Request<Artifact> request, final Artifact entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final SystemSettings s = this.repository.findSystemSetting();
		
		final Artifact artifact = this.repository.findByCodeAndDifferentId(entity.getCode(), entity.getId());
		if(entity.getRetailPrice()!=null) {
		errors.state(request, s.getAcceptedCurrencies().contains(entity.getRetailPrice().getCurrency()),
			  "retailPrice", "chef.artifact.retailPrice.not-able-currency");
		errors.state(request, entity.getRetailPrice().getAmount() > 0, "retailPrice", "chef.artifact.code.repeated.retailPrice.non-negative");
		}
		errors.state(request, artifact == null, "code", "chef.artifact.code.repeated");
		if(!errors.hasErrors("name")) {
			final String[] text = entity.getName().toLowerCase().replace("\n", " ").split("\\s+");
			final Double spamValue = this.checkSpam(text);
			final Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "name", "chef.artifact.error.name.spam-threshold");
		}
		if(!errors.hasErrors("description")) {
			final String[] text = entity.getDescription().toLowerCase().replace("\n", " ").split("\\s+");
			final Double spamValue = this.checkSpam(text);
			final Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "description", "chef.artifact.error.description.spam-threshold");
		}
	}

	@Override
	public void bind(final Request<Artifact> request, final Artifact entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "name", "code", "description", "retailPrice", "link");
		
	}

	@Override
	public void unbind(final Request<Artifact> request, final Artifact entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "description", "retailPrice", "link");
		model.setAttribute("isNew", true);
		model.setAttribute("isIngredient", true);
	}

	@Override
	public Artifact findOne(final Request<Artifact> request) {
		assert request != null;
		
		Artifact result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneArtifactById(id);

		return result;
	}

	@Override
	public void update(final Request<Artifact> request, final Artifact entity) {
		assert request != null;
		assert entity != null;
		
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
				if(aux>0) {
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
		}
		return spamValue;
	}
	
}