
package acme.features.administrator.administratorDashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.entities.administratorDashboard.AdministratorDashboard;
import acme.entities.artifact.ArtifactType;
import acme.entities.fineDish.StatusType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, AdministratorDashboard> interface --------------------------
	
	@Override
	public boolean authorise(Request<AdministratorDashboard> request) {
		assert request != null;
		return true;
	}

	@Override
	public AdministratorDashboard findOne(Request<AdministratorDashboard> request) {
		AdministratorDashboard result = new AdministratorDashboard();
		String[] arrayCurrencies = this.repository.findAcceptedCurrencies().split(",");
		List<String> acceptedCurrencies = Arrays.asList(arrayCurrencies);
		
		Map<ArtifactType,Integer> totalArtifact = new HashMap<>();
		for(ArtifactType type : ArtifactType.values()) {
			totalArtifact.put(type, this.repository.countArtifactByType(type));
		}
		
		Map<Pair<ArtifactType, String>, Double>	averageArtifactRetailPrice = new HashMap<>();
		for(ArtifactType type : ArtifactType.values()) {
			for(String currency : acceptedCurrencies) {
				Double averagePrice = this.repository.calcAverageArtifactRetailPriceByTypeAndCurrency(type, currency);
				Double averagePriceFormat = formatDouble(averagePrice);
				averageArtifactRetailPrice.put(Pair.of(type, currency),  averagePriceFormat);
			}
		}
		
		Map<Pair<ArtifactType, String>, Double>	deviationArtifactRetailPrice = new HashMap<>();
		for(ArtifactType type : ArtifactType.values()) {
			for(String currency : acceptedCurrencies) {
				Double deviationPrice = this.repository.calcDeviationArtifactRetailPriceByTypeAndCurrency(type, currency);
				Double deviationPriceFormat = formatDouble(deviationPrice);
				deviationArtifactRetailPrice.put(Pair.of(type, currency),  deviationPriceFormat);
			}
		}
		
		Map<Pair<ArtifactType, String>, Double>	minimumArtifactRetailPrice = new HashMap<>();
		for(ArtifactType type : ArtifactType.values()) {
			for(String currency : acceptedCurrencies) {
				Double minimumPrice = this.repository.calcMinimumArtifactRetailPriceByTypeAndCurrency(type, currency);
				Double minimumPriceFormat = formatDouble(minimumPrice);
				minimumArtifactRetailPrice.put(Pair.of(type, currency),  minimumPriceFormat);
			}
		}
		
		Map<Pair<ArtifactType, String>, Double>	maximumArtifactRetailPrice = new HashMap<>();
		for(ArtifactType type : ArtifactType.values()) {
			for(String currency : acceptedCurrencies) {
				Double maximumPrice = this.repository.calcMaximumArtifactRetailPriceByTypeAndCurrency(type, currency);
				Double maximumPriceFormat = formatDouble(maximumPrice);
				maximumArtifactRetailPrice.put(Pair.of(type, currency),  maximumPriceFormat);
			}
		}
		
		Map<StatusType,Integer> totalFineDish = new HashMap<>();
		for(StatusType status : StatusType.values()) {
			Integer total = this.repository.countFineDishByStatus(status);
			totalFineDish.put(status, total != null ? total : 0);
		}
		
		Map<StatusType,Double> averageFineDishBudget = new HashMap<>();
		for(StatusType status : StatusType.values()) {
			Double averageBudget = this.repository.calcAverageFineDishBudgetByStatus(status);
			Double averageBudgetFormat = formatDouble(averageBudget);
			averageFineDishBudget.put(status, averageBudgetFormat != null ? averageBudgetFormat : 0);
		}
		
		Map<StatusType,Double> deviationFineDishBudget = new HashMap<>();
		for(StatusType status : StatusType.values()) {
			Double deviationBudget = this.repository.calcDeviationFineDishBudgetByStatus(status);
			Double deviationBudgetFormat = formatDouble(deviationBudget);
			deviationFineDishBudget.put(status, deviationBudgetFormat != null ? deviationBudgetFormat : 0);
		}
		
		Map<StatusType,Double> maximumFineDishBudget = new HashMap<>();
		for(StatusType status : StatusType.values()) {
			Double maximumBudget = this.repository.calcMaximumFineDishBudgetByStatus(status);
			Double maximumBudgetFormat = formatDouble(maximumBudget);
			maximumFineDishBudget.put(status, maximumBudgetFormat != null ? maximumBudgetFormat : 0);
		}
		
		Map<StatusType,Double> minimumFineDishBudget = new HashMap<>();
		for(StatusType status : StatusType.values()) {
			Double minimumBudget = this.repository.calcMinimumFineDishBudgetByStatus(status);
			Double minimumBudgetFormat = formatDouble(minimumBudget);
			minimumFineDishBudget.put(status, minimumBudgetFormat != null ? minimumBudgetFormat : 0);
		}
		
		result.setTotalArtifact(totalArtifact);
		result.setAverageArtifactRetailPrice(averageArtifactRetailPrice);
		result.setDeviationArtifactRetailPrice(deviationArtifactRetailPrice);
		result.setMinimumArtifactRetailPrice(minimumArtifactRetailPrice);
		result.setMaximumArtifactRetailPrice(maximumArtifactRetailPrice);
		result.setTotalFineDish(totalFineDish);
		result.setAverageFineDishBudget(averageFineDishBudget);
		result.setDeviationFineDishBudget(deviationFineDishBudget);
		result.setMaximumFineDishBudget(maximumFineDishBudget);
		result.setMinimumFineDishBudget(minimumFineDishBudget);
		return result;
	}
	
	protected Double formatDouble(Double number) {
		return number != null ? new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
	}

	@Override
	public void unbind(Request<AdministratorDashboard> request, AdministratorDashboard entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalArtifact", "averageArtifactRetailPrice", "deviationArtifactRetailPrice"
			, "minimumArtifactRetailPrice", "maximumArtifactRetailPrice", "totalFineDish", "averageFineDishBudget",
			"deviationFineDishBudget", "maximumFineDishBudget", "minimumFineDishBudget");
		
		model.setAttribute("acceptedCurrencies", this.repository.findAcceptedCurrencies().split(","));
		model.setAttribute("artifactTypes", ArtifactType.values());
		model.setAttribute("fineDishStatuses", StatusType.values());
	}
}
