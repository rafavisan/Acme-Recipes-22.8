package acme.testing.epicure.epicureDashboard;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class EpicureOnEpicureDashboard extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/sign-in/rol.csv", encoding = "utf-8", numLinesToSkip = 2)
	@Order(10)
	public void positiveEpicureTest(final String username, final String password) {
		super.signIn(username, password);
		super.clickOnMenu("Epicure", "EpicureDashboard");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
	}

}
