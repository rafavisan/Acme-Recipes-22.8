package acme.features.administrator.systemSetting;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorSystemSettingsUpdateService implements AbstractUpdateService<Administrator, SystemSettings> {
	
	@Autowired
	protected AdministratorSystemSettingsRepository repository;

	@Override
	public void bind(final Request<SystemSettings> request, final SystemSettings entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "spamThreshold", "defaultCurrency", "acceptedCurrencies", "spamTuples");
		
	}

	@Override
	public void unbind(final Request<SystemSettings> request, final SystemSettings entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "spamThreshold", "defaultCurrency", "acceptedCurrencies", "spamTuples");
		
	}

	@Override
	public void validate(final Request<SystemSettings> request, final SystemSettings entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("defaultCurrency")) {
			final String defaultCurrency = entity.getDefaultCurrency();
			final String acceptedCurrencies = entity.getAcceptedCurrencies();
			errors.state(request, !acceptedCurrencies.contains(defaultCurrency), defaultCurrency, acceptedCurrencies, "administrator.system-settings.error.default-currency-not-accepted");
		}
		
	}

	@Override
	public void update(final Request<SystemSettings> request, final SystemSettings entity) {
		assert request != null;
		assert entity != null;
		
		entity.setAcceptedCurrencies(request.getModel().getAttribute("acceptedCurrencies", String.class));
		entity.setDefaultCurrency(request.getModel().getAttribute("defaultCurrency", String.class));
		entity.setSpamThreshold(request.getModel().getAttribute("spamThreshold", Double.class));
		entity.setSpamTuples(request.getModel().getAttribute("spamTuples", String.class));
		
		this.repository.save(entity);
		
	}

	@Override
	public boolean authorise(final Request<SystemSettings> request) {
		assert request != null;
		return true;
	}

	@Override
	public SystemSettings findOne(final Request<SystemSettings> request) {
		assert request != null;
		
		SystemSettings result = null;
		
		final Optional<SystemSettings> optResult = this.repository.findOne();
		
		if(optResult.isPresent()) {
			result = optResult.get();
		}
		
		assert result != null;
		
		return result;
	}

}
