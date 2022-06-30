package testcases;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static utils.PropertiesUtil.getConfig;
import static utils.PropertiesUtil.loadPropertiesFile;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import utils.LMSDataProvider2;
import io.restassured.response.Response;
 
public class Trial {
 
 public static void main(String args[]) {
	 loadPropertiesFile();	
	 baseURI = getConfig("baseURI");
	 basePath = getConfig("basepath");
	 //updateProgramDesc();
		String programName = "updating Prg Name";
        String programDesc = "updating Prg Desc";
        String online ="true";
        
		String jsonBody = "{ \"programId\":,"
		          + "\"programName\":\"" +  programName + "\","
		          + "\"programDescription\":\"" + programDesc + "\","
		          + "\"online\":\"" + online  + "\""
		          + "}";
		System.out.println( "jsonBody" + jsonBody  ); 
		String s = "GET IncorrectURL : \"https://lms-admin-rest-service.herokuapp.com/program\"";
		//GET IncorrectURL : "https://lms-admin-rest-service.herokuapp.com/program"
        
        System.out.println( "split string" + s.split("\"")[0] ); 
        
 }
 
	public static void updateProgramDesc() {
		System.out.println( "Inside updateProgramDesc"   ); 

		JSONObject requestParams = new JSONObject();
		requestParams.put("programId", "");
		requestParams.put("programName", "Test");
		requestParams.put("programDescription", "Test");
		requestParams.put("online", "True");

		//System.out.println( "programId:" + columns.get(1)); 

		Response response =
				given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", "").body(requestParams).contentType(ContentType.JSON)
				.when().put( getConfig("programIdEndPoint") );
				//.then().statusCode( LMSConstants.STATUS_200 ).extract().response();

		System.out.println( "Response :" + response.getBody().asString() );
	}
}
