package acme.entities.systemSetting;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemSettings extends AbstractEntity{

	
	 //Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;
				
		
		
		//Attributes
		protected double spamThreshold=0.10;
		
		@NotBlank
		protected String defaultCurrency;
		
		@NotBlank
		protected String acceptedCurrencies;
		
		@NotBlank
		protected String spamTuples;
}
