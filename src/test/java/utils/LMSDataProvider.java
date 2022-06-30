package utils;

import java.util.HashMap;
import static lmsapiautomation.LMSConstants.*;
import org.testng.annotations.DataProvider;

public class LMSDataProvider {

	private static final String POSITIVE = "POSITIVE";
	private static final String NEGATIVE = "NEGATIVE";
	

	@DataProvider( name = "getPositiveProgramIds")
	static Object[][] getPositiveProgramIds()  {
		return loadProgramIds( SHEETNAME_GET, POSITIVE );
	}
	
	@DataProvider( name = "getNegativeProgramIds")
	static Object[][] getNegativeProgramIds()  {
		return loadProgramIds( SHEETNAME_GET, NEGATIVE );
	}
	
	@DataProvider( name = "postPositiveProgramIds")
	static Object[][] postPositiveProgramIds()  {
		return loadProgramDetail(SHEETNAME_POST, POSITIVE);
	}
	
	@DataProvider( name = "postNegativeProgramIds")
	static Object[][] postNegativeProgramIds()  {
		return loadProgramDetail(SHEETNAME_POST, NEGATIVE);
	}
	
	@DataProvider( name = "putPositiveProgramIds")
	static Object[][] putPositiveProgramIds()  {
		return loadProgramDetail(SHEETNAME_PUT, POSITIVE);
	}
	
	@DataProvider( name = "putNegativeProgramIds")
	static Object[][] putNegativeProgramIds()  {
		return loadProgramDetail(SHEETNAME_PUT, NEGATIVE);
	}
	
	@DataProvider( name = "deletePositiveProgramIds")
	static Object[][] deletePositiveProgramIds()  {
		return loadProgramIds(SHEETNAME_DELETE, POSITIVE);
	}
	
	@DataProvider( name = "deleteNegativeProgramIds")
	static Object[][] deleteNegativeProgramIds()  {
		return loadProgramIds(SHEETNAME_DELETE, NEGATIVE);
	}
	
	static Object[][] loadProgramIds(String SheetName, String caseType)  {
		Object progdata[][] = null;
		try {
			int totalRows = ExcelUtils.getRowCount(PATH_LMS, SheetName);
			String caseTypeInExcel ; 
			int noOfCases = 0;
			for (int i = 1; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_LMS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					noOfCases++;
				}
			}
			int noOfColumns = 4;
			progdata = new Object[noOfCases][noOfColumns];
			
			//System.out.println( "Row count = " + noOfCases + "Column count =" + noOfColumns ); 
            
			int testCaseIndex = 0, progIdIndex = 2 , statusCodeIndex = 3 , statusCode;
			for (int i = 1, j =0; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_LMS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					
					progdata[j][0] = ExcelUtils.getCellData(PATH_LMS, SheetName, i, testCaseIndex);    // loads testCaseName
					progdata[j][1] = ExcelUtils.getCellData(PATH_LMS, SheetName, i, progIdIndex);      // loads progId
					// loads RowIndex in Excel
					progdata[j][2] = i; 
					statusCode = Integer.parseInt( ExcelUtils.getCellData(PATH_LMS, SheetName, i, statusCodeIndex) );
					progdata[j][3] = statusCode ;
					//System.out.println( "TestCaseName = " + progdata[j][0] + " programId =" + progdata[j][1] ); 
				    j++;
				}
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return progdata;
	}
	
	
	static Object[][] loadProgramDetail(String SheetName, String caseType)  {
		Object[][] objArray = null;
	
		try {
			int totalRows = ExcelUtils.getRowCount(PATH_LMS, SheetName);
			int noOfCases = 0 ;
			String caseTypeInExcel ; 
			for (int i = 1; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_LMS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) noOfCases++;
			}
			System.out.println( "Row count = " + totalRows + "No of Cases present: "+ noOfCases); 
			int noOfColumns = 4;
			objArray = new Object[noOfCases][noOfColumns];
			
			int statusCode; 
			for (int i = 1, j = 0; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_LMS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					
					hashMap.put( "programId", ExcelUtils.getCellData(PATH_LMS, SheetName, i, 2));
					hashMap.put( "programName", ExcelUtils.getCellData(PATH_LMS, SheetName, i, 3));
					hashMap.put( "programDescription", ExcelUtils.getCellData(PATH_LMS, SheetName, i, 4));
					hashMap.put( "online", ExcelUtils.getCellData(PATH_LMS, SheetName, i, 5)); 
				    objArray[j][0] = ExcelUtils.getCellData(PATH_LMS, SheetName, i, 0); // stores testcaseName
				    objArray[j][1]=  hashMap;  
				    objArray[j][2]= i;  // stores rowIndexExcel
				    
				    statusCode = Integer.parseInt( ExcelUtils.getCellData(PATH_LMS, SheetName, i, 6));
				    objArray[j][3]= statusCode;
				    j++;
				}
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return objArray;
	}
	
}
