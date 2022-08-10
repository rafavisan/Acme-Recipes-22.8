package acme.entities.partOf;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.entities.recipe.Recipe;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PartOf extends AbstractEntity {
	
	protected static final long	serialVersionUID	= 1L;
	
	@NotNull
	@Min(1)
	protected Integer quantity;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Recipe recipe;

}
