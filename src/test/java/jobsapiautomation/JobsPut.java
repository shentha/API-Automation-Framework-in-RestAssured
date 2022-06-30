package jobsapiautomation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static jobsapiautomation.JobsConstants.PATH_JOBS;
import static jobsapiautomation.JobsConstants.SHEETNAME_POST;
import static jobsapiautomation.JobsConstants.SHEETNAME_PUT;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ExcelUtils;

public class JobsPut {
	
	@Test (  dataProvider = "putPositiveProgramIds" , priority = 5, dataProviderClass = utils.JobsDataProvider.class)   
	public void putPositiveCases(String testCaseName, String jobId, String jobTitle, Integer rowIndexExcel, Integer expStatusCode ) {
		Response response;
		System.out.println( "testCaseName : " +  testCaseName ); 

		response = given().queryParams("Job Id", jobId, "Job Title", jobTitle)
				.when().put();

		ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_PUT, rowIndexExcel, 5, response.getStatusCode() );
		if ( response.getStatusCode() != expStatusCode.intValue() )
		    ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_POST, rowIndexExcel, 7, response.getBody().asString() );
		
		assertThat( response.getStatusCode(), comparesEqualTo( expStatusCode ));
		
		String responseJsonStr = response.getBody().asString().replaceAll("NaN", "null");
		validateResponseBodyPut( jobId, jobTitle, responseJsonStr);
		
		// Schema Validation 
		assertThat ( responseJsonStr, matchesJsonSchemaInClasspath("schemas\\JobsSchema.json"));
	}

	
	public void validateResponseBodyPut(String jobId, String jobTitle, String responseJsonStr) {
		JsonPath jsonPath = new JsonPath(responseJsonStr);
		
		Map<String, String> jobIdsMap = jsonPath.get( "data.'Job Id'");
		assertThat( jobIdsMap , hasValue( jobId ) );
		String keyToFetch = null;
		for ( String key : jobIdsMap.keySet()) {
 			if ( jobIdsMap.get(key) !=null && jobIdsMap.get(key).equals( jobId ) ) {
 				keyToFetch = key;
 			    break;
 			}
		}
		assertThat( jsonPath.get("data.'Job Title'"), hasEntry( keyToFetch, jobTitle ) );
	}
	
	
	@Test (  dataProvider = "putNegativeProgramIds" , priority = 6, dataProviderClass = utils.JobsDataProvider.class)   
	public void putNegativeCases(String testCaseName, String jobId, String jobTitle, Integer rowIndexExcel , Integer expStatusCode ) {
		Response response;
		System.out.println( "testCaseName : " +  testCaseName ); 
        if ( testCaseName.contains("JobId is null") ) {   
        	response = given().queryParams("Job Title", jobTitle)
    				.when().put();
        }
        else {
    		response = given().queryParams("Job Id", jobId, "Job Title", jobTitle)
    				   .when().put();
        }
		System.out.println( "Response :" + response.prettyPrint() );
		ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_PUT, rowIndexExcel, 5, response.getStatusCode() );
		ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_PUT, rowIndexExcel, 7, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
	}

}
