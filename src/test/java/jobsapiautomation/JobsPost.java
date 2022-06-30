package jobsapiautomation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static jobsapiautomation.JobsConstants.PATH_JOBS;
import static jobsapiautomation.JobsConstants.SHEETNAME_POST;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ExcelUtils;


public class JobsPost {
	
	@Test ( dataProvider = "postPositiveProgramIds" , priority = 3, dataProviderClass = utils.JobsDataProvider.class)
	public void postProgramPositiveCases(  String testCaseName, HashMap<String,Object> jobDetail , Integer rowIndex  , Integer expStatusCode ) {
		Response response = null;
		System.out.println( "\nTestCase: " + testCaseName ); 

		response = (Response) given().queryParams(jobDetail).when().post();
		
		ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_POST, rowIndex, 10, response.getStatusCode() );
		if ( response.getStatusCode() != expStatusCode.intValue() )
			ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_POST, rowIndex, 12, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
		
		String responseJsonStr = response.getBody().asString().replaceAll("NaN", "null");
		validateResponseBodyPost( jobDetail, responseJsonStr);
		
		// Schema Validation 
		assertThat ( responseJsonStr, matchesJsonSchemaInClasspath("schemas\\JobsSchema.json"));
	} 
	
	
	public void validateResponseBodyPost(HashMap<String,Object> jobDetail, String responseString) {
		JsonPath jsonPath = new JsonPath(responseString);
		
		Map<String, String> jobIdsMap = jsonPath.get( "data.'Job Id'");
		String keyToFetch = null;
		String jobId = (String) jobDetail.get( "Job Id" );
		for ( String key : jobIdsMap.keySet()) {
 			if ( jobIdsMap.get(key) !=null && jobIdsMap.get(key).equals( jobId ) ) {
 				keyToFetch = key;
 			    break;
 			}
		}
		assertThat( jsonPath.get("data.'Job Title'"), hasEntry( keyToFetch, (String) jobDetail.get( "Job Title" )) );
		assertThat( jsonPath.get("data.'Job Company Name'"), hasEntry( keyToFetch, (String) jobDetail.get( "Job Company Name" )) );
		assertThat( jsonPath.get("data.'Job Location'"), hasEntry( keyToFetch, (String) jobDetail.get( "Job Location" )) );
		assertThat( jsonPath.get("data.'Job Type'"), hasEntry( keyToFetch, (String) jobDetail.get( "Job Type" )) );
		assertThat( jsonPath.get("data.'Job Posted time'"), hasEntry( keyToFetch, (String) jobDetail.get( "Job Posted time" )) );
		assertThat( jsonPath.get("data.'Job Description'"), hasEntry( keyToFetch, (String) jobDetail.get( "Job Description" )) );
	} 
	
	
	@Test ( dataProvider = "postNegativeProgramIds" , priority = 4, dataProviderClass = utils.JobsDataProvider.class)
	public void postProgramNegativeCases( String testCaseName, HashMap<String,Object> jobDetail , Integer rowIndex , Integer expStatusCode ) {
		Response response = null;
		System.out.println( "\nTestCase: " + testCaseName ); 
		
		response = given().queryParams(jobDetail).when().post();

		System.out.println( "\nresponse.getStatusCode(): " + response.getStatusCode() ); 
		ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_POST, rowIndex, 10, response.getStatusCode() );
		ExcelUtils.setCellData(PATH_JOBS, SHEETNAME_POST, rowIndex, 12, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
		System.out.println( "Response:" + response.prettyPrint() );
	}

}
