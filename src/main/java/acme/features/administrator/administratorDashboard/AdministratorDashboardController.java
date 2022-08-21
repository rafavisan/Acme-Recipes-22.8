package acme.features.administrator.administratorDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.administratorDashboard.AdministratorDashboard;
import acme.entities.artifact.Artifact;
import acme.features.any.artifact.AnyArtifactShowService;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Administrator;
import acme.framework.roles.Any;

@Controller
public class AdministratorDashboardController extends AbstractController<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardShowService	showAdministratorDashboardShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showAdministratorDashboardShowService);
	}
}
