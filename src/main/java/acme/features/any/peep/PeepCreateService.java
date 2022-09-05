package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;

@Service
public class PeepCreateService implements AbstractCreateService<Any, Peep>{
	
	@Autowired
	protected PeepRepository repository;

	// AbstractCreateService<Administrator, Shout> interface --------------


	@Override
	public boolean authorise(final Request<Peep> request) {
		assert request != null;

		return true;
	}

	@Override
	public Peep instantiate(final Request<Peep> request) {
		assert request != null;

		Peep result;

		result = new Peep();

		return result;
	}

	@Override
	public void bind(final Request<Peep> request, final Peep entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Date instantiationMoment;

		instantiationMoment = new Date(System.currentTimeMillis() - 1);
	
		request.bind(entity, errors, "heading", "writer", "text", "email");
		entity.setInstantiationMoment(instantiationMoment);
		
	}

	@Override
	public void validate(final Request<Peep> request, final Peep entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		
		
		final Boolean isConfirmed = request.getModel().getBoolean("confirm");
		errors.state(request, isConfirmed, "confirm", "any.peep.form.error.must-confirm");
		
		if(!errors.hasErrors("heading")) {
			String[] text = entity.getHeading().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "heading", "any.peep.error.heading.spam-threshold");
		}

		if(!errors.hasErrors("writer")) {
			String[] text = entity.getWriter().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "writer", "any.peep.error.writer.spam-threshold");
		}
		
		if(!errors.hasErrors("text")) {
			String[] text = entity.getText().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "text", "any.peep.error.text.spam-threshold");
		}
	}

	@Override
	public void unbind(final Request<Peep> request, final Peep entity, final Model model) {
		assert request != null; 
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "heading", "writer", "text", "email");
		model.setAttribute("confirm", "false");
		model.setAttribute("isNew", true);
	}

	@Override
	public void create(final Request<Peep> request, final Peep entity) {
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
