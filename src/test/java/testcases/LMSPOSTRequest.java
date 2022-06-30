package testcases;

import static io.restassured.RestAssured.given;
import static utils.PropertiesUtil.getConfig;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lmsapiautomation.LMSConstants;
import utils.LMSDataProvider2;

public class LMSPOSTRequest {

	Response response = null;

	
	@Test ( priority = 8 )
	public void postProgramTest() {
		System.out.println( "\nTestCase: Inside postProgramTest" ); 
		List columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_Record");
		postProgram(columns);
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200); 
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId"));
	}
	
	
	void postProgram(List columns) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("programId", columns.get(1));
		requestParams.put("programName", columns.get(2));
		requestParams.put("programDescription", columns.get(3));
		requestParams.put("online", columns.get(4));

		System.out.println( "programId =" + columns.get(1) ); 

		response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(requestParams)
				.when().post().then().extract().response();
		System.out.println( "StatusCode:" + response.getStatusCode() );
		System.out.println( "Response:" + response.prettyPrint() );
		// do : write the output in excel 
	}
	
	
	@Test ( priority = 9 )
	public void postPreviouslyDeletedProgram() {
		System.out.println( "\nTestCase: Inside postPreviouslyDeletedProgram" ); 
		List columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_DeletedRecord");
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("programId", columns.get(1));
		requestParams.put("programName", columns.get(2));
		requestParams.put("programDescription", columns.get(3));
		requestParams.put("online", columns.get(4));

		System.out.println( "programId From Excel =" + columns.get(1) ); 

		response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(requestParams)
				.when().post();
		String generatedPrgId = response.getBody().jsonPath().getString("programId");
		System.out.println( "Auto generated programId =" + generatedPrgId ); 
		response = given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", generatedPrgId).when()
				.get( getConfig("programIdEndPoint") ).then().statusCode( LMSConstants.STATUS_200 ).extract().response();
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId")); 
		
		response =
				given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", generatedPrgId)
				.when().delete( getConfig("programIdEndPoint") )
				.then().statusCode(LMSConstants.STATUS_200).extract().response();
		
		System.out.println( "StatusCode:" + response.getStatusCode() );
		//requestParams.replace("programId", generatedPrgId);
		response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(requestParams)
				.when().post().then().statusCode( LMSConstants.STATUS_200 ).extract().response();
		String prgIdGenInSecondPOST = response.getBody().jsonPath().getString("programId");
		System.out.println( "Auto generated programId during 2nd POST =" + prgIdGenInSecondPOST ); 
		Assert.assertNotNull(prgIdGenInSecondPOST); 
		// do : write the output in excel 
	}
	
	
	@Test ( priority = 10 )
	public void postAlphaNumPrgId() {
		List columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_AlphaNum_PrgId");
		System.out.println( "\nTestCase: Inside postAlphaNumPrgId"   ); 
		postProgram(columns);
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_400); 
		//System.out.println( "Response :" + response.getBody().asString() );
		Assert.assertTrue(response.getBody().asString().contains("Bad Request")); 
	}

	
	@Test ( priority = 11 )
	public void postPrgDescBlank() {
		List columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_PrgDesc_Blank");
		System.out.println( "\nTestCase: Inside postPrgDescBlank"   ); 
		postProgram(columns);
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200); 
		System.out.println( "Response :" + response.getBody().asString() );
		Assert.assertEquals(response.getBody().jsonPath().getString("programDescription"), ""); 
	}
	
	
	@Test ( priority = 12 )
	public void postExistProgramId() {
		System.out.println( "\nTestCase: Inside postExistProgramId" ); 
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_ExistPrgId");
		postProgram(columns);
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200); 
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId"));
	}
	
	
	@Test ( priority = 13 )
	public void postProgramIdZero() {
		System.out.println( "\nTestCase: Inside postProgramIdZero" ); 
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_PrgId_0");
		postProgram(columns);
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200); 
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId"));
	}
	
	
	@Test ( priority = 14 )
	public void postProgramNameInt() {
		System.out.println( "\nTestCase: Inside postProgramNameInt" ); 
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_PrgName_Int");
		postProgram(columns);
		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200); 
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId"));
	}
	
	
	@Test ( priority = 15 )
	public void postProgramIdNull() {
		System.out.println( "\nTestCase: Inside postProgramIdNull" ); 
		List<String> columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("POST_PrgId_Null");
		JSONObject requestParams = new JSONObject();
		requestParams.put("programName", columns.get(2));
		requestParams.put("programDescription", columns.get(3));
		requestParams.put("online", columns.get(4));

		System.out.println( "programId =" + columns.get(1) ); 

		response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(requestParams)
				.when().post().then().extract().response();
		System.out.println( "StatusCode:" + response.getStatusCode() );
		System.out.println( "Response:" + response.prettyPrint() );

		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_400); 
		Assert.assertTrue(response.getBody().asString().contains("Bad Request")); 
	}
}
