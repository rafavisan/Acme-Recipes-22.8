package acme.features.epicure.fineDish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.fineDish.FineDish;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Epicure;


@Service
public class FineDishPublishService implements AbstractUpdateService<Epicure, FineDish>{
	
	@Autowired
	protected FineDishEpicureRepository repository;
	
	@Override
	public boolean authorise(Request<FineDish> request) {
		assert request != null;

		boolean result = true;
		
		
		return result;
	}

	@Override
	public void bind(Request<FineDish> request, FineDish entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors,  "status", "code", "request", "budget", "initialDate", "finishDate", "url");
		
	}

	@Override
	public void unbind(Request<FineDish> request, FineDish entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "status", "code", "request", "budget", "initialDate", "finishDate", "url", "isPublish");
		
	}

	@Override
	public FineDish findOne(Request<FineDish> request) {
		assert request != null;

		FineDish result;
		int id;

		id = request.getModel().getInteger("id");
		result = repository.findOneFineDishById(id);

		return result;
	}

	@Override
	public void validate(Request<FineDish> request, FineDish entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void update(Request<FineDish> request, FineDish entity) {
		assert request != null;
		assert entity != null;
		
		entity.setPublish(true);
		this.repository.save(entity);
		
	}

}
