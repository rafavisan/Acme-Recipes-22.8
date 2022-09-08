package acme.testing.chef.pimpam;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefPimpamUpdateTest extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/pimpam/create.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex, final String title, final String description,
		final String startPeriod, final String finishPeriod , final String budget, final String link) {
		super.signIn("chef1", "chef1");
		super.clickOnMenu("Chef", "Pimpam");
		super.checkListingExists();
		
		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("finishPeriod", finishPeriod);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");


		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/pimpam/update.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void update(final int recordIndex, final String title, final String description,
		final String startPeriod, final String finishPeriod, final String budget, final String link) {
		super.signIn("chef1", "chef1");
		super.clickOnMenu("Chef", "Pimpam");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.signOut();
	}
	
	

}
