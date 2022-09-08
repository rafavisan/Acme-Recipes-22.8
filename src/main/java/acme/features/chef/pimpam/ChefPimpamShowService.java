package acme.features.chef.pimpam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pimpam.Pimpam;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefPimpamShowService implements AbstractShowService<Chef, Pimpam>{
	
	@Autowired
	protected ChefPimpamRepository repository;

	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request!=null;
		return true;
	}

	@Override
	public Pimpam findOne(final Request<Pimpam> request) {
		assert request!=null;
		int id;
		Pimpam result;
		
		id= request.getModel().getInteger("id");
		result= this.repository.findOnePimpamById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "code","instantiationMoment","title","description","initialDate","finishDate","budget","link","artifact");
		model.setAttribute("artifactId", entity.getArtifact().getId());
	}

}
