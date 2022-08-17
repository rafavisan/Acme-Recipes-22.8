package acme.features.epicure.fineDish;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.fineDish.FineDish;
import acme.framework.controllers.AbstractController;
import acme.roles.Epicure;


@Controller
public class FineDishEpicureController extends AbstractController<Epicure, FineDish> {
	
	// Internal state ---------------------------------------------------------

			@Autowired
			protected FineDishEpicureListService	listService;

			@Autowired
			protected FineDishEpicureShowService	showService;
			

			// Constructors -----------------------------------------------------------


			@PostConstruct
			protected void initialise() {
				super.addCommand("list", this.listService);
				super.addCommand("show", this.showService);
			}

}
