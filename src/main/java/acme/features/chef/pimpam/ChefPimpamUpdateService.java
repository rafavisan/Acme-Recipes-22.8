package acme.features.chef.pimpam;

import org.springframework.stereotype.Service;

import acme.entities.pimpam.Pimpam;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefPimpamUpdateService implements AbstractUpdateService<Chef, Pimpam>{

	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request!=null;
		return true;
	}

	@Override
	public void bind(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request!=null;
		assert entity!=null;
		assert errors!=null;
		
		//request.bind(entity, errors, "code");
		
	}

	@Override
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pimpam findOne(final Request<Pimpam> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(final Request<Pimpam> request, final Pimpam entity) {
		// TODO Auto-generated method stub
		
	}

}
