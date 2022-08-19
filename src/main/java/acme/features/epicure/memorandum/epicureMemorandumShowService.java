package acme.features.epicure.memorandum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.memorandum.Memorandum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Epicure;

@Service
public class epicureMemorandumShowService implements AbstractShowService<Epicure, Memorandum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected epicureMemorandumRepository repository;

	// AbstractShowService<Anonymous, Memorandum> interface --------------------------

	@Override
	public boolean authorise(final Request<Memorandum> request) {
		assert request != null;

		final boolean result= request.getPrincipal().hasRole(Epicure.class);
		
		return result;
	}

	@Override
	public void unbind(final Request<Memorandum> request, final Memorandum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "report");
	}

	@Override
	public Memorandum findOne(final Request<Memorandum> request) {
		assert request != null;

		Memorandum result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneMemorandumById(id);

		return result;
	}
}