package acme.features.authenticated.systemConfiguration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifact.Artifact;
import acme.entities.systemSetting.SystemSettings;
import acme.features.any.artifact.AnyArtifactRepository;
import acme.framework.entities.AbstractEntity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedSystemConfigurationShowService extends AbstractShowService{Authenticated, SystemSettings} {
	// Internal state ---------------------------------------------------------

		@Autowired
		protected AuthenticatedSystemConfigurationRepository repository;

		// AbstractShowService<Authenticated, SystemSettings> interface --------------------------

		@Override
		public boolean authorise(final Request<SystemSettings> request) {
			assert request != null;
			
			Optional<AbstractEntity> result =  this.repository.findOne();

			return result.isPresent();
		}

		@Override
		public void unbind(final Request<SystemSettings> request, final SystemSettings entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "spamThreshold", "defaultCurrency", "acceptedCurrencies", "spamTuples");
		}

		@Override
		public SystemSettings findOne(final Request<SystemSettings> request) {
			assert request != null;
			
			SystemSettings result;

			result = (SystemSettings) this.repository.findOne().get();

			assert result != null;
			
			return result;
		}
	}

}
