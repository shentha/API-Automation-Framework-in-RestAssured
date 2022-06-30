package jobsapiautomation;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static jobsapiautomation.JobsConstants.PATH_JOBS;
import static jobsapiautomation.JobsConstants.SHEETNAME_DELETE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static utils.PropertiesUtil.getConfig;
import static utils.PropertiesUtil.loadPropertiesFile;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ExcelUtils;

public class JobsDelete {

	@Test (  dataProvider = "deletePositiveProgramIds" , priority = 7, dataProviderClass = utils.JobsDataProvider.class)   
	public void deletePositiveCases(String testCaseName, String jobId, Integer rowIndexExcel , Integer expStatusCode ) {
		Response response = null;
		try{
			System.out.println( "testCaseName: " + testCaseName ); 

			response = given().queryParam("Job Id", jobId).when().delete();

			System.out.println( "Response :" + response.getBody().asString() );
			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_DELETE, rowIndexExcel, 4, response.getStatusCode() );
			if ( response.getStatusCode() != expStatusCode.intValue() )
				ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_DELETE, rowIndexExcel, 6, response.getBody().asString() );
			assertThat( response.getStatusCode(), comparesEqualTo( expStatusCode ));

			String responseJsonStr = response.getBody().asString().replaceAll("NaN", "null");
			// response body validation
			JsonPath jsonPath = new JsonPath(responseJsonStr);
			assertThat(jsonPath.get( "data.'Job Id'"), not(hasValue(jobId)) );	
			// Schema Validation 
			assertThat ( responseJsonStr, matchesJsonSchemaInClasspath("schemas\\JobsSchema.json"));
		}
		catch(Exception e) {
			System.out.println( "Exception in Delete :" + e.getMessage() );
		}
	}


	@Test (  dataProvider = "deleteNegativeProgramIds" , priority = 8, dataProviderClass = utils.JobsDataProvider.class)   
	public void deleteProgramNegativeCases(String testCaseName, String jobId, Integer rowIndexExcel, Integer expStatusCode ) {
		Response response = null;
		try{
			System.out.println( "testCaseName: " + testCaseName ); 

			response = given().queryParam("Job Id", jobId).when().delete();

			System.out.println( "Response :" + response.getBody().asString() );
			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_DELETE, rowIndexExcel, 4, response.getStatusCode() );
			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_DELETE, rowIndexExcel, 6, response.getBody().asString() );
			Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
		}
		catch(Exception e) {
			System.out.println( "Exception in Delete :" + e.getMessage() );
		}
	}
}
