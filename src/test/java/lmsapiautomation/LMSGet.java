package lmsapiautomation;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static lmsapiautomation.LMSConstants.PATH_LMS;
import static lmsapiautomation.LMSConstants.SHEETNAME_GET;
import static org.junit.Assert.assertThat;
import static utils.PropertiesUtil.getConfig;
import static utils.PropertiesUtil.loadPropertiesFile;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ExcelUtils;

public class LMSGet {

	@BeforeSuite
	public void beforeSuite() {
		loadPropertiesFile();
	}

	@BeforeTest
	public void BeforeTest() {
		baseURI = getConfig("lmsBaseURI");
		basePath = getConfig("lmsBasePath");
	}


	@Test ( dataProvider = "getPositiveProgramIds" , priority = 1, dataProviderClass = utils.LMSDataProvider.class)
	public void getRequestPositive(String testCaseName, String programId, Integer rowIndex, Integer expStatusCode) {
		Response response = null;
		try {
			System.out.println( "\ntestCaseName : " + testCaseName + "programId:" + programId); 

			if ( testCaseName.equals("Get All")) {
				response = given().auth().basic( getConfig("username"), getConfig("password"))
						.when().get()
						.then().assertThat().body(matchesJsonSchemaInClasspath("schemas\\LMSAllProgramsSchema.json")).extract().response();
			}
			else {
				response = given().auth().basic( getConfig("username"), getConfig("password")).pathParam("programId", programId)
						.when().get( getConfig("lmsProgramIdEndPoint") );
			}

			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_GET, rowIndex, 4, response.getStatusCode() );
			if(!testCaseName.equals("Get All")) {
				ExcelUtils.setCellData(PATH_LMS, SHEETNAME_GET, rowIndex, 5, response.getBody().asString() );
			}

			Assert.assertEquals( response.getStatusCode(), expStatusCode.intValue());

			if(testCaseName.equals("Existing ProgramId")) {
				Assert.assertEquals(response.getBody().jsonPath().getInt("programId"), Integer.parseInt(programId));
				assertThat ( response.getBody().asString(), matchesJsonSchemaInClasspath("schemas\\LMSSingleProgramSchema.json"));
			}

			if ( testCaseName.equals("Get ProgramId 0") ||  testCaseName.equals("Non Existing ProgramId")) {
				Assert.assertEquals(response.getBody().asString(), "null"); 
			} 
		}
		catch(Exception e ) {
			System.out.println( "Exception " + e.getMessage()); 
		}
	}


	@Test ( dataProvider = "getNegativeProgramIds" , priority = 2, dataProviderClass = utils.LMSDataProvider.class)
	public void getRequestNegative(String testCaseName, String programId, Integer rowIndex, Integer expStatusCode) {
		Response response = null;
		try {
			System.out.println( "\ntestCaseName : " + testCaseName + "programId:" + programId); 
			if ( testCaseName.contains("IncorrectURL") ) {
				RestAssured.reset();
				String incorrectURL = testCaseName.split("\"")[1]; 
				System.out.println( "incorrectURL:" + incorrectURL );
				response = given().auth().basic( getConfig("username"), getConfig("password")).when().get( incorrectURL );

				baseURI = getConfig("lmsBaseURI");
				basePath = getConfig("lmsBasePath");
			}
			else if ( testCaseName.contains("Without Authorization") ) {
				response = given().when().get();
			}
			else {
				response = given().auth().basic( getConfig("username"), getConfig("password")).pathParam("programId", programId).when()
						.get( getConfig("lmsProgramIdEndPoint") );
			}
			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_GET, rowIndex, 4, response.getStatusCode() );
			ExcelUtils.setCellData(PATH_LMS, SHEETNAME_GET, rowIndex, 5, response.getBody().asString() );
			Assert.assertEquals( response.getStatusCode(), expStatusCode.intValue());
		}
		catch(Exception e ) {
			System.out.println( "Exception " + e.getMessage()); 
		}
	} 
}
