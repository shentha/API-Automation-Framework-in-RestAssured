package lmsapiautomation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static lmsapiautomation.LMSConstants.*;
import static utils.PropertiesUtil.getConfig;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;


public class LMSPost {
	
	@Test ( dataProvider = "postPositiveProgramIds" , priority = 3, dataProviderClass = utils.LMSDataProvider.class)
	public void postProgramPositiveCases(  String testCaseName, HashMap<String,Object> programDetail , Integer rowIndex, Integer expStatusCode) {
		Response response = null;
		System.out.println( "\nTestCase: " + testCaseName ); 
		response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(programDetail)
				   .when().post()
				   .then().assertThat().body(matchesJsonSchemaInClasspath("schemas\\LMSSingleProgramSchema.json")).extract().response();
		
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_POST, rowIndex, 7, response.getStatusCode() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_POST, rowIndex, 8, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
		Assert.assertEquals( response.getBody().jsonPath().getString("programName"), programDetail.get("programName"));
		Assert.assertEquals( response.getBody().jsonPath().getString("programDescription"), programDetail.get("programDescription"));
		String online = (String) programDetail.get("online");
		Assert.assertEquals( response.getBody().jsonPath().getBoolean("online"), Boolean.valueOf(online).booleanValue() ); 
		System.out.println( "Response:" + response.prettyPrint() );
	} 
	
	
	@Test ( dataProvider = "postNegativeProgramIds" , priority = 4, dataProviderClass = utils.LMSDataProvider.class)
	public void postProgramNegativeCases( String testCaseName, HashMap<String,Object> programDetail , Integer rowIndex , Integer expStatusCode) {
		Response response = null;
		System.out.println( "\nTestCase: " + testCaseName ); 
		
       if ( testCaseName.contains("Online Field blank")){
			StringBuffer jsonBodyStr = new StringBuffer( "{ \"programId\":" );
			             jsonBodyStr.append( programDetail.get("programId") )
			             .append( ",\"programName\":\"" ).append( programDetail.get("programName") ).append( "\"," )
			             .append( "\"programDescription\":\"").append( programDetail.get("programDescription") ).append( "\"," )
			             .append( "\"online\":}" );
			System.out.println( "\njsonBodyStr: " + jsonBodyStr );              
			response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(jsonBodyStr)
					.when().post();
		}
		else {
			response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(programDetail)
					.when().post();
		}
		System.out.println( "\nresponse.getStatusCode(): " + response.getStatusCode() ); 
		System.out.println( "\nexpStatusCode.intValue(): " + expStatusCode.intValue() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_POST, rowIndex, 7, response.getStatusCode() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_POST, rowIndex, 8, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
		System.out.println( "Response:" + response.prettyPrint() );
	}
	

}
