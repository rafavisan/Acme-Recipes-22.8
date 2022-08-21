package acme.features.chef.partOf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.partOf.PartOf;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;

@Controller
public class ChefPartOfController extends AbstractController<Chef, PartOf> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefPartOfRecipeListService	listRecipesService;
	
	@Autowired
	protected ChefPartOfArtifactListService	listArtifactService;
	
	@Autowired
	protected ChefPartOfShowService showPartOfService;
	
	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-recipes", "list", this.listRecipesService);
		super.addCommand("list-artifacts", "list", this.listArtifactService);
		super.addCommand("show", this.showPartOfService);
	}

}
