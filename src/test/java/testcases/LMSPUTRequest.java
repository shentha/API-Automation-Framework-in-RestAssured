package testcases;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
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

public class LMSPUTRequest {

	Response response = null;

	//@Test ( priority = 4 )
	public void updateProgramDesc() {
		List columns = (ArrayList<String>)LMSDataProvider2.data_allCases.get("PUT_PrgDesc");
		System.out.println( "Inside updateProgramDesc"   ); 

		JSONObject requestParams = new JSONObject();
		requestParams.put("programId", columns.get(1));
		requestParams.put("programName", columns.get(2));
		requestParams.put("programDescription", columns.get(3));
		requestParams.put("online", columns.get(4));

		System.out.println( "programId:" + columns.get(1)); 

		response =
				given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", columns.get(1)).body(requestParams).contentType(ContentType.JSON)
				.when().put( getConfig("programIdEndPoint") )
				.then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.getBody().asString() );
	}

	@Test ( priority = 4 ) 
	public void updateProgramIdNull() {
		System.out.println( "Inside updateProgramDesc"   ); 
        
		String programName = "updating Prg Name";
        String programDesc = "updating Prg Desc";
        String online ="true";
        
		
		String jsonBodyStr = "{ \"programId\":,"
				          + "\"programName\":\"" +  programName + "\","
				          + "\"programDescription\":\"" + programDesc + "\","
				          + "\"online\":\"" + online  + "\""
				          + "}";
		//JSONObject jsonBody = new JSONObject();


		System.out.println( "jsonBodyStr:" + jsonBodyStr); 

		Response response =
				given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", "\"\"").body(jsonBodyStr).contentType(ContentType.JSON)
				.when().put( getConfig("programIdEndPoint") );
				//.then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.getBody().asString() );
	}
}
