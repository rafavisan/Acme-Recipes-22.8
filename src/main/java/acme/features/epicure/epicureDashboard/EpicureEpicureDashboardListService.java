package acme.features.epicure.epicureDashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.controllers.Request;
import acme.entities.artifact.Artifact;
import acme.entities.epicureDashboard.EpicureDashboard;
import acme.framework.services.AbstractListService;
import acme.framework.components.models.Model;
import acme.roles.Epicure;

@Service
public class EpicureEpicureDashboardListService implements AbstractListService<Epicure, EpicureDashboard>{
	
	@Autowired
	protected EpicureEpicureDashBoardRepository repository;
	
	@Override
	public boolean authorise(final Request<EpicureDashboard> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<EpicureDashboard> findMany(final Request<EpicureDashboard> request) {
		assert request != null;

		Collection<EpicureDashboard> result;

		result = this.repository.findByEpicure(request.getModel().get("epicure"));

		return result;
	}
	
	@Override
	public void unbind(final Request<EpicureDashboard> request, final EpicureDashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalFineDish", "averageFineDishRetailPrice", "deviationFineDishRetailPrice", "maximumFineDishRetailPrice", "minimumUtensilRetailPrice");
	}

}
