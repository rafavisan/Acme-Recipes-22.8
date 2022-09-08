package acme.features.chef.pimpam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifact.Artifact;
import acme.entities.pimpam.Pimpam;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class ChefPimpamCreateService implements AbstractCreateService<Chef, Pimpam>{
	
	@Autowired
	protected ChefPimpamRepository repository;

	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request!=null;
		return true;
	}

	@Override
	public void bind(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request!=null;
		assert entity!=null;
		assert errors!=null;
		
		
		
		
		Model model;
		Artifact selectedArtifact;
		model = request.getModel();
		selectedArtifact = this.repository.findArtifactById(Integer.parseInt(model.getString("artifacts")));
		
		
		request.bind(entity, errors,"title","description","initialDate","finishDate","budget","link");
		
		entity.setArtifact(selectedArtifact);
		
		
	}

	@Override
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		List<Artifact> artifacts;
		artifacts=this.repository.findAllArtifacts();
		
		request.unbind(entity, model,"title","description","initialDate","finishDate","budget","link");
		model.setAttribute("artifacts", artifacts);
		model.setAttribute("isNew", true);
	}

	@Override
	public Pimpam instantiate(final Request<Pimpam> request) {
		assert request!=null;
		
		Pimpam result;
		result=new Pimpam();
		
		final LocalDate localDate = LocalDate.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		final String formattedString = localDate.format(formatter);
		result.setCode(formattedString);
		
		result.setInstantiationMoment(Calendar.getInstance().getTime());
		
		
		
		return result;
	}

	@Override
	public void validate(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Calendar d=Calendar.getInstance();
		d.setTime(entity.getInstantiationMoment());
		d.add(Calendar.MONTH, 1);
		
		
		if (!errors.hasErrors("initialDate")) {


			errors.state(request, entity.getInitialDate().after(d.getTime()), "initialDate",
					"chef.pimpam.error.month.initialDate");
		}
		
		final Calendar ds=Calendar.getInstance();
		if(entity.getInitialDate()!=null ) {
		ds.setTime(entity.getInitialDate());
		}
		ds.add(Calendar.DAY_OF_YEAR, 7);
		
		
		if (!errors.hasErrors("finishDate")) {


			errors.state(request, entity.getFinishDate().after(ds.getTime()), "finishDate",
					"chef.pimpam.error.week.finishDate");
		}
		
		
		
		

		
		final Money money=entity.getBudget();
		final SystemSettings c = this.repository.findConfiguration();
		if (!errors.hasErrors("budget")) {


			errors.state(request, money.getAmount()>=0., "budget",
					"chef.pimpam.error.budget");
			
			errors.state(request, c.getAcceptedCurrencies().contains(money.getCurrency()) ,
					  "budget", "chef.pimpam.not-able-currency");
		}
	}

	@Override
	public void create(final Request<Pimpam> request, final Pimpam entity) {
		assert request!=null;
		assert entity!=null;
		
		this.repository.save(entity);
	}

}
