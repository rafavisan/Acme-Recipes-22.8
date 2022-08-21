package acme.features.chef.artifact;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.artifact.Artifact;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;

@Controller
public class ChefArtifactController extends AbstractController<Chef, Artifact> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefIngredientListService	listIngredientService;
	
	@Autowired
	protected ChefUtensilListService	listUtensilService;
	
	@Autowired
	protected ChefArtifactShowService showArtifactService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-ingredient","list", this.listIngredientService);
		super.addCommand("list-utensil","list", this.listUtensilService);
		super.addCommand("show", this.showArtifactService);
	}

}
