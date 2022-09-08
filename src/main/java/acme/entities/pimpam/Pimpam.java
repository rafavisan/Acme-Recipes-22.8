package acme.entities.pimpam;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.artifact.Artifact;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pimpam extends AbstractEntity{

	@NotBlank
	@Pattern(regexp = "^[0-9]{2}/[0-9]{2}/[0-9]{2}$")
	@Column(unique=true)
	protected String			code;	
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date instantiationMoment;
	
	@NotBlank
	@Length(min=1,max=100)
	protected String title;
	
	
	@NotBlank
	@Length(min=1,max=255)
	protected String description;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date initialDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date finishDate;
	
	@NotNull
	protected Money budget;
	
	@URL
	protected String link;
	
	@NotNull
	@OneToOne(optional=false)
	protected Artifact artifact;
	
	
	
}
