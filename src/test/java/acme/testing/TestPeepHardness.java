package acme.testing;

import org.hibernate.internal.util.StringHelper;

import acme.framework.testing.AbstractTest;

public class TestPeepHardness extends AbstractTest{
	
	protected void Peep() {
		super.navigateHome();
		super.clickOnMenu("Anonymous", "Peeps");
	}

	
}
