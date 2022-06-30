
package testcases;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import static utils.PropertiesUtil.getConfig;
import static utils.PropertiesUtil.loadPropertiesFile;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterMethod;
import io.restassured.response.Response;
import lmsapiautomation.LMSConstants;
import utils.LMSDataProvider2;

public class LMSGETRequestTest {

	Response response = null;
	
	@BeforeSuite
	public void beforeSuite() {
		loadPropertiesFile();
		LMSDataProvider2.loadExcelDataForAllCases();	
		baseURI = getConfig("baseURI");
		basePath = getConfig("basepath");
	}
	
	
	//@Test ( priority = 1)
	public void getWithoutAuthorization() {
		System.out.println( "getWithoutAuthorization" ); 
		response = given().when().get();

		System.out.println( "StatusCode:" + response.getStatusCode() );
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_401); 
	}
	
	
	//@Test ( priority = 2)
	public void getWithAuthorization() {
		System.out.println( "getWithAuthorization" ); 
		response = given().auth().basic( getConfig("username"), getConfig("password")).when().get();

		System.out.println( "StatusCode:" + response.getStatusCode() );
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200);   
	}
	
	
	//@Test ( priority = 3)
	public void getIncorrectURL() {
		System.out.println( "getIncorrectURL" ); 
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("Incorrect_URL");
		RestAssured.reset();
		String incorrectURL = (String)columns.get(0);
		
		System.out.println( "incorrectURL:" + incorrectURL );
		response = given().auth().basic( getConfig("username"), getConfig("password")).when().get(incorrectURL);

		System.out.println( "StatusCode:" + response.getStatusCode() );
		
		baseURI = getConfig("baseURI");
		basePath = getConfig("basepath"); 

		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_404); 
		Assert.assertEquals( response.getBody().jsonPath().getString("error") , "Not Found");
	}
	
	
	//@Test ( priority = 4 )
	public void getExistingProgramId() {
		System.out.println( "Inside getExistingPrgId"   ); 
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("GET_Existing_PrgId");
		String programId = (String)columns.get(1);
		System.out.println( "programId:" + programId   ); 
		//String programId = "";
		response = given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programId).when()
				.get( getConfig("programIdEndPoint") ).then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.getStatusCode() );
		System.out.println( "Response :" + response.prettyPrint() );
		Assert.assertEquals(response.getBody().jsonPath().getInt("programId"), Integer.parseInt(programId)); 
		// do : write the output in excel 
	}
	
	
	//@Test ( priority = 5 )
	public void getNonExistingPrgId() {
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("GET_NonExisting_PrgId");
		System.out.println( "Inside getNonExistingPrgId"   ); 
		String programId = (String)columns.get(1);
		System.out.println( "programId:" + programId   ); 
		
		response = given().auth().basic( getConfig("username"), getConfig("password")).pathParam("programId", programId).when()
				.get( getConfig("programIdEndPoint") ).then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.getBody().asString() );
		Assert.assertEquals(response.getBody().asString(), "null"); 
	}
	
	
	//@Test ( priority = 6 )
	public void getPrgIdZero() {
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("GET_PrgId_0");
		System.out.println( "Inside getPrgIdZero"   ); 
		String programId = (String)columns.get(1);
		System.out.println( "programId:" + programId   ); 

		response = given().auth().basic( getConfig("username"), getConfig("password")).pathParam("programId", programId).when()
				.get( getConfig("programIdEndPoint") ).then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		//System.out.println( "Response :" + response.getBody().asString() );
		Assert.assertEquals(response.getBody().asString(), "null"); 
	} 

	
	//@Test ( priority = 7 )
	public void getAlphaNumProgId() {
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("GET_AlphaNum_PrgId");
		System.out.println( "Inside getAlphaNumProgId"   ); 
		String programId = (String)columns.get(1);
		System.out.println( "programId:" + programId   ); 

		response = given().auth().basic( getConfig("username"), getConfig("password")).pathParam("programId", programId).when()
				.get( getConfig("programIdEndPoint") ).then().statusCode( LMSConstants.STATUS_400 ).extract().response();

		//System.out.println( "Response :" + response.getBody().asString() );
		Assert.assertTrue(response.getBody().asString().contains("Bad Request")); 
	} 
}
