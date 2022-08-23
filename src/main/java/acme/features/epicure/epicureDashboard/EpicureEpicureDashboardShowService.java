package acme.features.epicure.epicureDashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public void unbind( Request<EpicureDashboard> request,  EpicureDashboard entity,  Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalFineDish", "averageFineDishBudget", "deviationFineDishBudget", "maximumFineDishBudget", "minimumFineDishBudget");
		model.setAttribute("fineDishStatuses", StatusType.values());

	}

	@Override
	public EpicureDashboard findOne( Request<EpicureDashboard> request) {
		assert request != null;
		
		 EpicureDashboard result = new EpicureDashboard();
		
//		Map<StatusType, Integer> totalFineDish = new HashMap<StatusType, Integer>();
//		for(StatusType status: StatusType.values()) {
//			Integer total = this.repository.countFineDishByStatus(status);
//			totalFineDish.put(status, total != null ? total : 0);
//		}
		
		 Map<StatusType, Double> averageFineDishBudget = new HashMap<StatusType, Double>();
		for( StatusType status: StatusType.values()) {
			 Double averageBudget = this.repository.calcAverageFineDishBudgetByStatus(status);
			 Double averageBudgetFormat = this.formatDouble(averageBudget);
			averageFineDishBudget.put(status, averageBudgetFormat != null ? averageBudgetFormat : 0);
		}
		
		 Map<StatusType, Double> deviationFineDishBudget = new HashMap<StatusType, Double>();
		for( StatusType status: StatusType.values()) {
			 Double deviationBudget = this.repository.calcDeviationFineDishBudgetByStatus(status);
			 Double deviationBudgetFormat = this.formatDouble(deviationBudget);
			deviationFineDishBudget.put(status, deviationBudgetFormat != null ? deviationBudgetFormat : 0);
		}
		
		 Map<StatusType, Double> maximumFineDishBudget = new HashMap<StatusType, Double>();
		for( StatusType status: StatusType.values()) {
			 Double maximumBudget = this.repository.calcMaximumFineDishBudgetByStatus(status);
			 Double maximumBudgetFormat = this.formatDouble(maximumBudget);
			maximumFineDishBudget.put(status, maximumBudgetFormat != null ? maximumBudgetFormat : 0);
		}
		
		 Map<StatusType, Double> minimumFineDishBudget = new HashMap<StatusType, Double>();
		for( StatusType status: StatusType.values()) {
			 Double minimumBudget = this.repository.calcMinimumFineDishBudgetByStatus(status);
			 Double minimumBudgetFormat = this.formatDouble(minimumBudget);
			minimumFineDishBudget.put(status, minimumBudgetFormat != null ? minimumBudgetFormat : 0);
		}
		
		//result.setTotalFineDish(totalFineDish);
		result.setAverageFineDishBudget(averageFineDishBudget);
		result.setDeviationFineDishBudget(deviationFineDishBudget);
		result.setMaximumFineDishBudget(maximumFineDishBudget);
		result.setMinimumFineDishBudget(minimumFineDishBudget);
		
		return result; 
	}
	
	protected Double formatDouble( Double number) {
		return number != null ? new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
	}
	
	

}
