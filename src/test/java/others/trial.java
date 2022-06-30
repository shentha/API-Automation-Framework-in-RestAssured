package others;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.github.fge.jsonschema.main.JsonSchema;

import io.restassured.path.json.JsonPath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertThat;

import org.json.simple.JSONValue;

public class trial {
	//@Test
	public static void main(String args[]) throws Exception {
		try {//JsonPath jsonPath = JsonPath.from(new File("C:\\workspace\\LMSAPIAutomation\\src\\test\\java\\jobsapiautomation\\JobsJsonWithNaN.json"));
		//FileInputStream fi = new FileInputStream(new File("C:\\workspace\\LMSAPIAutomation\\src\\test\\java\\jobsapiautomation\\JobsJsonWithNaN.json"));
		/*String data = new String (Files.readAllBytes(Paths.get("C:\\workspace\\LMSAPIAutomation\\src\\test\\java\\jobsapiautomation\\JobsJsonWithNaN.json")));
		System.out.println(data);
		//Map<String, String> jobId = jsonPath.get("data.'Job Id'"); 
		String updatedData = data.replaceAll("NaN", "null") ; 
		System.out.println(updatedData);
		JsonPath j = new JsonPath(updatedData);  */
		
		String data = new String (Files.readAllBytes(Paths.get("C:\\workspace\\LMSAPIAutomation\\src\\test\\java\\lmsapiautomation\\json\\LMSAllProgramsSchema.json")));
		//String resp = response.getBody().asString();
		//System.out.println( "response.getBody().asString():" + resp );
		
		System.out.println( "data:" + data );
		assertThat ( data, matchesJsonSchemaInClasspath("LMSProgramSchema.json"));
		
		final String json = "{\"foo\":\"bar\"}";
        
		/*final URI schemaUri = Test.class.getResource("ChildMessage.schema.json").toURI();
		final JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaUri.toString());
		final ProcessingReport report = schema.validate(new ObjectMapper().readTree(json));

		report.forEach(message -> System.out.println(message.getMessage())); */

		
		//fi.close();
		}
		catch( Exception e) {
			System.out.println( "Exception" + e.getMessage() );
		}
	} 
	
}
