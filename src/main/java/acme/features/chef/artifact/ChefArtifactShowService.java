package acme.features.chef.artifact;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifact.Artifact;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefArtifactShowService implements AbstractShowService<Chef, Artifact> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefArtifactRepository repository;

	// AbstractShowService<Chef, Artifact> interface --------------------------

	@Override
	public boolean authorise(final Request<Artifact> request) {
		assert request != null;
		
		final Integer id = request.getModel().getInteger("id");
		final Optional<AbstractEntity> result = this.repository.findById(id);
		final Principal principal = request.getPrincipal();
		
		return result.isPresent() && ((Artifact)result.get()).getChef().getId() == principal.getActiveRoleId();
	}

	@Override
	public void unbind(final Request<Artifact> request, final Artifact entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "code", "description", "retailPrice", "link");
	}

	@Override
	public Artifact findOne(final Request<Artifact> request) {
		assert request != null;
		
		Artifact result=null;
		int id;

		id = request.getModel().getInteger("id");
		final Optional<AbstractEntity> optResult = this.repository.findById(id);
		if (optResult.isPresent()) {
			result = (Artifact) optResult.get();
		}

		assert result != null;
		
		return result;
	}
}
