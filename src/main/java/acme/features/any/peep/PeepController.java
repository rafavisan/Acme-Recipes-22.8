package acme.features.any.peep;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.peep.Peep;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;


@Controller
public class PeepController extends AbstractController<Any, Peep>{
	
	@Autowired
	protected PeepListService	listService;

	
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
	}

}
