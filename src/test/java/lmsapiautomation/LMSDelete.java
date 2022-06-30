package lmsapiautomation;

import static io.restassured.RestAssured.given;
import static lmsapiautomation.LMSConstants.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import utils.ExcelUtils;

import static utils.PropertiesUtil.*;

public class LMSDelete {

	@Test (  dataProvider = "deletePositiveProgramIds" , priority = 7, dataProviderClass = utils.LMSDataProvider.class)   
	public void deletePositiveCases(String testCaseName, String programId, Integer rowIndex , Integer expStatusCode) {
		Response response = null;
		try{
			System.out.println( "testCaseName: " + testCaseName ); 

			response =
					given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programId.trim())
					.when().delete( getConfig("lmsProgramIdEndPoint") );

			System.out.println( "Response :" + response.getBody().asString() );
			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_DELETE, rowIndex, 4, response.getStatusCode() );
			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_DELETE, rowIndex, 5, response.getBody().asString() );
			Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue()); 
			Assert.assertEquals(response.getBody().asString(), ""); 
		}
		catch(Exception e) {
			System.out.println( "Exception in Delete :" + e.getMessage() );
		}
	}

	
	@Test (  dataProvider = "deleteNegativeProgramIds" , priority = 8, dataProviderClass = utils.LMSDataProvider.class)   
	public void deleteProgramNegativeCases(String testCaseName, String programId, Integer rowIndex , Integer expStatusCode) {
		Response response = null;
		try{
			System.out.println( "testCaseName: " + testCaseName ); 

			response =  given().auth().basic(getConfig("username"), getConfig("password")).pathParam("programId", programId.trim())
						 .when().delete( getConfig("lmsProgramIdEndPoint") );
			System.out.println( "Response :" + response.getBody().asString() );
			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_DELETE, rowIndex, 4, response.getStatusCode() );
			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_DELETE, rowIndex, 5, response.getBody().asString() );
			Assert.assertEquals(response.getStatusCode(), expStatusCode.intValue());
		}
		catch(Exception e) {
			System.out.println( "Exception in Delete :" + e.getMessage() );
		}
	}
}
