package Pages;

import BrowserLaunch.TestHarness;
import BrowserLaunch.WebDriverInStance;
import apicalls.OpenWeatherMap;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.bytebuddy.implementation.bind.annotation.Super;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NdtvHomePage extends TestHarness {

    WebDriver driver;

    Map<String, String> map;
    OpenWeatherMap weather;
    ArrayList<String> al = new ArrayList<String>();
    List<String> addedvalues = al;

    public NdtvHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='noti_btnwrap']/a[@class='notnow']")
    private WebElement notificationNotnow;

    @FindBy(xpath = "//a[@id='h_sub_menu']")
    private WebElement departmentExtention;

    @FindBy(xpath = "//a[text()='WEATHER']")
    private WebElement weatherLink;

    @FindBy(id = "searchBox")
    private WebElement searchText;

    @FindBy(xpath = "//input[@class='defaultChecked'][@id='Bengaluru']")
    private WebElement defaultCheckBoxChecked;

    @FindBy(xpath = "//input[@class='defaultChecked'][@id='Bengaluru'][@checked='checked']")
    private WebElement verifyCheckBox;

    @FindBy(xpath = "//div[@class='leaflet-pane leaflet-marker-pane']/div//div[@title]")
    private List<WebElement> verifyselectedarea;

    @FindBy(xpath = "//div[@title='Bengaluru']/div/span[@class='tempRedText']")
    private WebElement tempInBlackColor;

    @FindBy(xpath = "//div[@title='Bengaluru']/div/span[@class='tempWhiteText']")
    private WebElement tempInWhiteColor;

    @FindBy(xpath = "//div[@title='Bengaluru']//div[@class='cityText']")
    private WebElement inMapgetCityName;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']/div/span/b")
    private List<WebElement> areaweatherdetails;

    @FindBy(xpath = "//div[@class='cityText'][text()='Bengaluru']")
    private WebElement mapselectcity;

    @FindBy(xpath = "//div[@class='cityText']")
    private List<WebElement> mapselectedcities;

    public void reachtheweather() throws InterruptedException {
        visibilityOf(notificationNotnow, 102000);
        notificationNotnow.click();
        departmentExtention.click();
        visibilityOf(weatherLink, 100);
        weatherLink.click();
    }

    public String pinyourcity(String bangalore) {
        SoftAssert softassert = new SoftAssert();
        searchText.click();
        searchText.sendKeys(bangalore);
        visibilityOf(verifyCheckBox, 5);
        if (verifyCheckBox.getAttribute("checked").equalsIgnoreCase("true")) {
            softassert.assertEquals(verifyCheckBox.getAttribute("checked"), "true");
            System.out.println("Default Selected");
        }
        softassert.assertAll();
        return bangalore;
    }

    public void validateTheCityOnthemap() {
        SoftAssert softassert = new SoftAssert();
        for (int i = 0; i < verifyselectedarea.size(); ) {
            if (verifyselectedarea.get(i).getAttribute("title").equalsIgnoreCase(verifyCheckBox.getAttribute("id"))) {
                softassert.assertEquals(verifyselectedarea.get(i).getAttribute("title"),
                        verifyCheckBox.getAttribute("id"));
                break;
            } else {
                i++;
            }

        }
        softassert.assertAll();
    }

    public HashMap validateweatherdetails() {
        HashMap<String, String> weatherdetails = new HashMap<String, String>();
        ArrayList<String> al = new ArrayList<String>();
        for (int i = 0; i < mapselectedcities.size() - 1; ) {
            if (mapselectedcities.get(i).getText().equalsIgnoreCase(verifyCheckBox.getAttribute("id"))) {
                mapselectedcities.get(i).click();
                break;
            } else {
                i++;
            }
        }
        for (int i = 0; i < areaweatherdetails.size(); i++) {
            String cityweathervalues = areaweatherdetails.get(i).getText();
            String[] splited = cityweathervalues.split(":");

            weatherdetails.put(splited[0], splited[1]);

        }
        return weatherdetails;
    }

    public Response compareUiandService() {
        SoftAssert sa = new SoftAssert();
        JsonPath jsondata;
        HashMap<String, Integer> anothermap = new HashMap<String, Integer>();
        weather = new OpenWeatherMap();
        map = new HashMap<String, String>(weather.writeReviewQueryParam("Bengaluru", "7fe67bf08c80ded756e598d6f8fedaea"));
        Response res = weather.wetherinfo(map);
        jsondata = res.jsonPath();

        HashMap<String, String> keysvaluepairs = validateweatherdetails();
        for (Map.Entry<String, String> entry : keysvaluepairs.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("humidity")) {

                sa.assertEquals(entry.getValue().replace("%", ""), jsondata.getString("main.humidity"));

            }


        }
        sa.assertAll();
        return res;
    }

}
