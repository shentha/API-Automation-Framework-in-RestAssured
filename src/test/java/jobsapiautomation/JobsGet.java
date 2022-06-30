package jobsapiautomation;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.PropertiesUtil.getConfig;
import static utils.PropertiesUtil.loadPropertiesFile;

import java.util.HashMap;
import java.util.Map;

import static jobsapiautomation.JobsConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ExcelUtils;

public class JobsGet {

	@BeforeTest
	public void BeforeTest() {
		loadPropertiesFile();
		baseURI = getConfig("jobsBaseURI");
		basePath = getConfig("jobsBasePath");
	}

	
	@Test ( dataProvider = "getPositiveProgramIds" , priority = 1, dataProviderClass = utils.JobsDataProvider.class)
	public void getRequestPositive(String testCaseName, Integer rowIndex, Integer expStatusCode ) {
		try {
			Response response = given().when().get();

			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_GET, rowIndex, 3, response.getStatusCode() );    
			Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue());
			
			// Schema Validation 
			String responseJsonStr = response.getBody().asString().replaceAll("NaN", "null");
			assertThat ( responseJsonStr, matchesJsonSchemaInClasspath("schemas\\JobsSchema.json"));
		}
		catch(Exception e ) {
			System.out.println( "Exception " + e.getMessage()); 
		}
	}  
	
	
	@Test ( dataProvider = "getNegativeProgramIds" , priority = 2, dataProviderClass = utils.JobsDataProvider.class)
	public void getRequestNegative(String testCaseName, Integer rowIndex, Integer expStatusCode ) {
		Response response = null;
		try {
			System.out.println( "\ntestCaseName : " + testCaseName ); 
			if ( testCaseName.contains("IncorrectURL") ) {
		        RestAssured.reset();
				String incorrectURL = testCaseName.split("\"")[1]; 
				System.out.println( "incorrectURL:" + incorrectURL );
				response = given().when().get( incorrectURL );

				baseURI = getConfig("jobsBaseURI");
				basePath = getConfig("jobsBasePath");
			}

			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_GET, rowIndex, 3, response.getStatusCode() );
			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_GET, rowIndex, 4, response.getBody().asString() );
			Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue());
		}
		catch(Exception e ) {
			System.out.println( "Exception " + e.getMessage()); 
		}
	} 
}
