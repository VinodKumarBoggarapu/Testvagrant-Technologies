package Tests;

import BrowserLaunch.TestHarness;
import Pages.NdtvHomePage;
import apicalls.OpenWeatherMap;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

public class UiapiAutomation extends TestHarness {

    NdtvHomePage ui;
    HashMap<String, String> map;
    OpenWeatherMap weather;

	/*@Test(priority = 1, alwaysRun = true)
	public void reachtheweather() throws InterruptedException {
		ui = new NdtvHomePage(driver);
		ui.reachtheweather();
	}*/

    @Test(priority = 2, alwaysRun = true)
    public void searchpincity() {
        ui = new NdtvHomePage(driver);
        ui.pinyourcity("Bengaluru");

    }

    @Test(priority = 3, alwaysRun = true)
    public void validateTheCityOnthemap() {
        ui = new NdtvHomePage(driver);
        ui.validateTheCityOnthemap();
    }

    @Test(priority = 4, alwaysRun = true)
    public void validateWeatherdetails() {
        ui = new NdtvHomePage(driver);
        ui.validateweatherdetails();
    }


    @Test(priority = 5, alwaysRun = true)
    public void compareUiandapi() {
        weather = new OpenWeatherMap();
        map = new HashMap<String, String>(weather.writeReviewQueryParam("Bengaluru", "7fe67bf08c80ded756e598d6f8fedaea"));
        Response res = weather.wetherinfo(map);
        ui.compareUiandService();
        System.out.println("print response in test class---" + res.asString());

    }


}
