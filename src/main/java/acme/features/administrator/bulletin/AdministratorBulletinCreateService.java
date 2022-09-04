package acme.features.administrator.bulletin;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.entities.systemSetting.SystemSettings;
import acme.features.authenticated.bulletin.AuthenticatedBulletinRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorBulletinCreateService implements AbstractCreateService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinRepository repository;
	

	// AbstractCreateService<Administrator, Chirp> interface --------------


	@Override
	public boolean authorise(final Request<Bulletin> request) {
		assert request != null;

		return true;
	}

	@Override
	public Bulletin instantiate(final Request<Bulletin> request) {
		assert request != null;

		final Bulletin result = new Bulletin();

		return result;
	}

	@Override
	public void bind(final Request<Bulletin> request, final Bulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final Date moment= new Date(System.currentTimeMillis() - 1);
    
		request.bind(entity, errors, "heading", "text", "link");
		entity.setInstantiationMoment(moment);
		entity.setFlag(true);
	}

	@Override
	public void validate(final Request<Bulletin> request, final Bulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		boolean confirmation;

		confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "administrator.bulletin.form.label.confirmation.message");
		
		if(!errors.hasErrors("heading")) {
			String[] text = entity.getHeading().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "heading", "chef.bulletin.error.heading.spam-threshold");
		}
		if(!errors.hasErrors("text")) {
			String[] text = entity.getText().toLowerCase().replaceAll("\n", " ").split("\\s+");
			Double spamValue = checkSpam(text);
			Double threshold = this.repository.findAllSpanTuples().getSpamThreshold();
			errors.state(request, spamValue<threshold, "text", "chef.bulletin.error.text.spam-threshold");
		}
	}

	@Override
	public void unbind(final Request<Bulletin> request, final Bulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "heading", "text", "link");
		model.setAttribute("confirmation", false);
	}

	@Override
	public void create(final Request<Bulletin> request, final Bulletin entity) {
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
				if(aux>0) {
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
		}
		return spamValue;
	}
	
}