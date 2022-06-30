package jobsapiautomation;

public interface JobsConstants {
	
	int STATUS_200 = 200;
	int STATUS_400 = 400;
	int STATUS_401 = 401;
	int STATUS_404 = 404;
	String PATH_JOBS = System.getProperty("user.dir") + "/src/test/resources/Jobs_Data.xlsx";
	String SHEETNAME_GET = "GET";
	String SHEETNAME_POST = "POST";
	String SHEETNAME_PUT = "PUT";
	String SHEETNAME_DELETE = "DELETE";
}
