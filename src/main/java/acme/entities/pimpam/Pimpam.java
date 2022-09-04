package acme.entities.pimpam;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pimpam extends AbstractEntity{
	
	// Serialisation identifier -----------------------------------------------

		protected static final long		serialVersionUID	= 1L;
		
		//Attributes PIMPAM
		
		@Column(unique=false)
		@Pattern(regexp = "^\\d{2}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$",  message = "default.error.conversion")
		protected  String code;
		//code = yy/mm/dd
		
		@NotNull
		@Past
		@Temporal(TemporalType.TIMESTAMP)
		protected Date instantiationmoment;
		
		@NotBlank
		@Length(max=101)
		protected String title;
		
		@NotBlank
		@Length(max=256)
		protected String description;
		
		
		@NotNull
		@Temporal(TemporalType.TIMESTAMP)
		protected Date startPeriod;
		
		@NotNull
		@Temporal(TemporalType.TIMESTAMP)
		protected Date endPeriod;
		
		@NotBlank
		protected Money budget;
		
		@URL
		protected String link;

}

