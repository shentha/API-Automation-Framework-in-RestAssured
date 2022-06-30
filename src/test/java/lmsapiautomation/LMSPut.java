package lmsapiautomation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.PropertiesUtil.getConfig;
import static lmsapiautomation.LMSConstants.*;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import utils.ExcelUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class LMSPut {

	@Test (  dataProvider = "putPositiveProgramIds" , priority = 5, dataProviderClass = utils.LMSDataProvider.class)   
	public void putPositiveCases(String testCaseName, HashMap<String,Object> programDetail , Integer rowIndexExcel, Integer expStatusCode) {
		Response response;
		System.out.println( "testCaseName : " +  testCaseName ); 

		response =
				given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programDetail.get("programId"))
				   .body(programDetail).contentType(ContentType.JSON)
				.when().put( getConfig("lmsProgramIdEndPoint") )
				.then().assertThat().body(matchesJsonSchemaInClasspath("schemas\\LMSSingleProgramSchema.json")).extract().response();

		System.out.println( "Response :" + response.prettyPrint() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_PUT, rowIndexExcel, 7, response.getStatusCode() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_PUT, rowIndexExcel, 8, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
		Assert.assertNotNull(response.getBody().jsonPath().getInt("programId") );
		JsonPath jsonPath = response.getBody().jsonPath();
		Assert.assertEquals(jsonPath.getString("programName"), programDetail.get("programName"));
		Assert.assertEquals(jsonPath.getString("programDescription"), programDetail.get("programDescription"));
		Assert.assertEquals(jsonPath.getBoolean("online"), Boolean.valueOf((String) programDetail.get("online")).booleanValue() ); 
	}

	
	@Test (  dataProvider = "putNegativeProgramIds" , priority = 6, dataProviderClass = utils.LMSDataProvider.class)   
	public void putNegativeCases(String testCaseName, HashMap<String,Object> programDetail , Integer rowIndexExcel, Integer expStatusCode) {
		Response response;
		System.out.println( "testCaseName : " +  testCaseName ); 
		if ( testCaseName.contains("ProgramId Null") ) {  
			StringBuffer jsonBodyStr = new StringBuffer("{ \"programId\":,\"programName\":\"");
			jsonBodyStr.append( programDetail.get("programName") ).append("\",")
			.append("\"programDescription\":\"").append(programDetail.get("programDescription")).append("\",")
			.append("\"online\":\"").append( programDetail.get("online")).append("\"")
			.append("}" );
			System.out.println( "\njsonBodyStr: " + jsonBodyStr );                            
			response = given().auth().basic(getConfig("username"), getConfig("password")).contentType(ContentType.JSON).body(jsonBodyStr)
					.when().put();
		} else {
			response =
					given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programDetail.get("programId"))
					.body(programDetail).contentType(ContentType.JSON)
					.when().put( getConfig("lmsProgramIdEndPoint") );
		}	
		System.out.println( "Response :" + response.prettyPrint() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_PUT, rowIndexExcel, 7, response.getStatusCode() );
		ExcelUtils.setCellData(PATH_LMS, SHEETNAME_PUT, rowIndexExcel, 8, response.getBody().asString() );
		Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
	}
}
