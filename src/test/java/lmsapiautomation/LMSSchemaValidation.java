package lmsapiautomation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.PropertiesUtil.getConfig;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class LMSSchemaValidation {
	
	@Test ( priority = 9)
	public void validateSchema() {
		Response response = given().auth().basic( getConfig("username"), getConfig("password"))
				.when().get()
				.then().assertThat().body(matchesJsonSchemaInClasspath("schemas\\LMSAllProgramsSchema.json")).extract().response();

	}
}
