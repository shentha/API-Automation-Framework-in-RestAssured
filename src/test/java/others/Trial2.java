package others;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Trial2 {
	@Test
	public void getAllProgramSchemaValidation() {
		Response response = null;
		JsonPath jsonPath = null;
		String testCaseName = "Get All",  programId = "";

		
		try {
			System.out.println( "\ntestCaseName : " + testCaseName + "programId:" + programId); 

			Map<String, Object> program1 = new HashMap<>();
			program1.put("programId", 1153);
			program1.put("programName", "Automation Testing");
			program1.put("programDescription", "API Automation Testing");
			program1.put("online", false);

			Map<String, Object> program2 = new HashMap<>();
			program2.put("programId", 7572);
			program2.put("programName", "SDET26");
			program2.put("programDescription", "Automation Testing");
			program2.put("online", true);
			
			List<Map<String, Object>> programs = new ArrayList<>();
			programs.add(program1);
			programs.add(program2);
			
			String programsStr = new ObjectMapper().writeValueAsString(programs);
			jsonPath = JsonPath.from(programsStr);
					
			System.out.println( "response.getBody().asString():" + programsStr );
			assertThat ( programsStr, matchesJsonSchemaInClasspath("LMSProgramSchema.json"));
			
		}
		catch(Exception e ) {
			System.out.println( "Exception " + e.getMessage()); 
		}
}
}

