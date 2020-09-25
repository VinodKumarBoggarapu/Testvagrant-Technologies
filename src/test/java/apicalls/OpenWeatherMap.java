package apicalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.json.Json;


import java.util.HashMap;
import java.util.Map;


public class OpenWeatherMap {


    public Response response;


    public Map<String, String> writeReviewQueryParam(String Bengaluru, String appID) {
        Map<String, String> map = new HashMap<>();
        map.put("q", Bengaluru);
        map.put("appid", appID);
        return map;
    }


    public Response wetherinfo(Map<String, String> params) {
        RestAssured.baseURI = "http://api.openweathermap.org/data/2.5/weather";
        response = RestAssured.given()
                .queryParams(params)
                .when().get();
        response.then().log().all();
        return response;

    }

    public String getweatherInfodetails() {
        JsonPath jsondata = response.jsonPath();
        String responsedata = jsondata.getString("main.temp");
        System.out.println("temp of the city " + jsondata.getString("main.temp"));
        return responsedata;
    }


}