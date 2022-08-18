
package acme.features.administrator.administratorDashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import acme.entities.administratorDashboard.AdministratorDashboard;
import acme.entities.artifact.Artifact;
import acme.entities.artifact.ArtifactType;
import acme.features.any.artifact.AnyArtifactRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.roles.Any;
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
				averageArtifactRetailPrice.put(Pair.of(type, currency), this.repository.calcAverageArtifactRetailPriceByTypeAndCurrency(type, currency));
			}
		}
		result.setTotalArtifact(totalArtifact);
		result.setAverageArtifactRetailPrice(averageArtifactRetailPrice);
		return result;
	}

	@Override
	public void unbind(Request<AdministratorDashboard> request, AdministratorDashboard entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalArtifact", "averageArtifactRetailPrice");
		
		model.setAttribute("acceptedCurrencies", this.repository.findAcceptedCurrencies().split(","));
		model.setAttribute("artifactTypes", ArtifactType.values());
	}
}
