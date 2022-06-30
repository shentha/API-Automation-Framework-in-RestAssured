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

public class LMSDELETERequest {
	Response response = null;
	
    @Test 
	public void deleteProgram() {
		List columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("DEL_Exist_PrgId");
		try{
			System.out.println( "Inside deleteProgram" ); 

			response =
					given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", columns.get(1))
					.when().delete( getConfig("programIdEndPoint") )
					.then().statusCode(LMSConstants.STATUS_200).extract().response();

			System.out.println( "Response :" + response.toString() );
			Assert.assertEquals(response.getBody().asString(), ""); 

			// do : write the output in excel 
		}
		catch(Exception e) {
			System.out.println( "Exception in Delete :" + e.getMessage() );
		}
	}
    
	//@Test (  dataProvider = "programIds" , priority = 3 , dataProviderClass = utils.LMSDataProvider.class)    // priority = 2 ,
	public void getProgramId(String programId) {
		System.out.println( "Inside getProgramId"   ); 
		System.out.println( "programId:" + programId   ); 

		response = given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programId.trim()).when()
				.get( getConfig("programIdEndPoint") ).then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.prettyPrint() );
		Assert.assertEquals(response.getBody().jsonPath().getInt("programId"), Integer.parseInt(programId)); 
		// do : write the output in excel 
	}


	//@Test (  dataProvider = "progdata" , priority = 2, dataProviderClass = utils.LMSDataProvider.class)   //  ,
	public void createProgram(String programId, String programName, String programDescription, String online) {
		System.out.println( "Inside createProgram" ); 
		JSONObject requestParams = new JSONObject();
		requestParams.put("programId", programId.trim());
		requestParams.put("programName", programName);
		requestParams.put("programDescription", programDescription);
		requestParams.put("online", online);

		System.out.println( "programId =" + programId ); 

		response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(requestParams)
				.when().post();
		System.out.println( "StatusCode:" + response.getStatusCode() );
		System.out.println( "Response:" + response.prettyPrint() );

		Assert.assertEquals(response.getStatusCode(), LMSConstants.STATUS_200); 

		// do : write the output in excel 
	}


	//@Test //(  dataProvider = "progdata" , priority = 4, dataProviderClass = utils.LMSDataProvider.class)   //  ,
	public void updateProgram(String programId, String programName, String programDescription, String online) {
		System.out.println( "Inside updateProgram" ); 

		JSONObject requestBody = new JSONObject();
		requestBody.put("programId", programId.trim());
		requestBody.put("programName", programName);
		requestBody.put("programDescription", programDescription);
		requestBody.put("online", online);

		response =
				given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programId.trim()).body(requestBody).contentType(ContentType.JSON)
				.when().put( getConfig("programIdEndPoint") )
				.then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.prettyPrint() );
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId") );
		Assert.assertEquals(response.getBody().toString().contains(programDescription), true);

		// do : write the output in excel 
	}


	//@Test //(  dataProvider = "programIds" , priority = 5, dataProviderClass = utils.LMSDataProvider.class)   // priority = 5 ,
	public void deleteProgram(String programId) {

		try{
			System.out.println( "Inside deleteProgram" ); 

			response =
					given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programId.trim())
					.when().delete( getConfig("programIdEndPoint") )
					.then().statusCode(LMSConstants.STATUS_200).extract().response();

			System.out.println( "Response :" + response.toString() );

			/*
			 * Response :{
		    "timestamp": "2022-01-13T18:59:15.160+00:00",
		    "status": 500,
		    "error": "Internal Server Error",
		    "path": "/programs/3590"
		} */

			// do : write the output in excel 
		}
		catch(Exception e) {
			System.out.println( "Exception in Delete :" + e.getMessage() );
		}
	}
}
