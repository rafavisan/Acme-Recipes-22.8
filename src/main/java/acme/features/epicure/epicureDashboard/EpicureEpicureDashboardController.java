package acme.features.epicure.epicureDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.roles.Epicure;
import acme.entities.epicureDashboard.EpicureDashboard;
import acme.features.any.artifact.AnyArtifactShowService;
import acme.features.any.artifact.AnyUtensilListService;

@Controller
public class EpicureEpicureDashboardController extends AbstractController<Epicure, EpicureDashboard> {
	
	@Autowired
	protected EpicureEpicureDashboardListService listService;
	
	@Autowired
	protected EpicureEpicureDashboardShowService showService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
	}

}
