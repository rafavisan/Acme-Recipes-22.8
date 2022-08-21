package acme.features.epicure.epicureDashboard;

import java.util.Optional;

import org.springframework.stereotype.Service;

import acme.entities.epicureDashboard.EpicureDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.framework.services.AbstractShowService;
import acme.roles.Epicure;

@Service
public class EpicureEpicureDashboardShowService implements AbstractShowService<Epicure, EpicureDashboard>{
	
	protected EpicureEpicureDashBoardRepository repository;
	
	@Override
	public boolean authorise(final Request<EpicureDashboard> request) {
		assert request != null;
		
		final Integer id = request.getModel().getInteger("id");
		final Optional<AbstractEntity> result =  this.repository.findById(id);

		return result.isPresent();
	}

	@Override
	public void unbind(final Request<EpicureDashboard> request, final EpicureDashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalFineDish", "averageFineDishRetailPrice", "deviationFineDishRetailPrice", "maximumFineDishRetailPrice", "minimumUtensilRetailPrice");
	}

	@Override
	public EpicureDashboard findOne(final Request<EpicureDashboard> request) {
		assert request != null;
		
		final EpicureDashboard result;
		int id;

		id = request.getModel().getInteger("id");
		//result = (EpicureDashboard) this.repository.findById(id).get();

		//assert result != null;
		
		return null;
	}

}
