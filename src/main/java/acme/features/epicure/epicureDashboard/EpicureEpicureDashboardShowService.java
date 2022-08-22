package acme.features.epicure.epicureDashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import acme.entities.epicureDashboard.EpicureDashboard;
import acme.entities.fineDish.StatusType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Epicure;

@Service
public class EpicureEpicureDashboardShowService implements AbstractShowService<Epicure, EpicureDashboard>{
	
	protected EpicureEpicureDashBoardRepository repository;
	
	@Override
	public boolean authorise(final Request<EpicureDashboard> request) {
		assert request != null;
		return true;
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
		
		EpicureDashboard result = new EpicureDashboard();
		
		final Map<StatusType, Integer> totalFineDish = new HashMap<StatusType, Integer>();
		for(final StatusType status: StatusType.values()) {
			totalFineDish.put(status, this.repository.countFineDishByStatus(status));
		}
		
		final Map<StatusType, Double> averageFineDishRetailPrice = new HashMap<StatusType, Double>();
		for(final StatusType status: StatusType.values()) {
			averageFineDishRetailPrice.put(status, this.repository.calcAverageFineDishRetailPriceByStatus(status));
		}
		
		final Map<StatusType, Double> deviationFineDishRetailPrice = new HashMap<StatusType, Double>();
		for(final StatusType status: StatusType.values()) {
			deviationFineDishRetailPrice.put(status, this.repository.calcDeviationFineDishRetailPriceByStatus(status));
		}
		
		final Map<StatusType, Double> maximumFineDishRetailPrice = new HashMap<StatusType, Double>();
		for(final StatusType status: StatusType.values()) {
			maximumFineDishRetailPrice.put(status, this.repository.calcMaximumFineDishRetailPriceByStatus(status));
		}
		
		final Map<StatusType, Double> minimumUtensilRetailPrice = new HashMap<StatusType, Double>();
		for(final StatusType status: StatusType.values()) {
			minimumUtensilRetailPrice.put(status, this.repository.calcMinimumUtensilRetailPrice());
		}
		
		result.setTotalFineDish(totalFineDish);
		result.setAverageFineDishRetailPrice(averageFineDishRetailPrice);
		result.setDeviationFineDishRetailPrice(deviationFineDishRetailPrice);
		result.setMaximumFineDishRetailPrice(maximumFineDishRetailPrice);
		result.setMinimumUtensilRetailPrice(minimumUtensilRetailPrice);
		
		return result;
	}

}
