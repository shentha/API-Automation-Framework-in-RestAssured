package utils;

import java.util.HashMap;
import static jobsapiautomation.JobsConstants.*;
import org.testng.annotations.DataProvider;

public class JobsDataProvider {

	private static final String POSITIVE = "POSITIVE";
	private static final String NEGATIVE = "NEGATIVE";
	

	@DataProvider( name = "getPositiveProgramIds")
	static Object[][] getPositiveProgramIds()  {
		return loadJobIdsGet( SHEETNAME_GET, POSITIVE );
	}
	
	@DataProvider( name = "getNegativeProgramIds")
	static Object[][] getNegativeProgramIds()  {
		return loadJobIdsGet( SHEETNAME_GET, NEGATIVE );
	}
	
	@DataProvider( name = "postPositiveProgramIds")
	static Object[][] postPositiveProgramIds()  {
		return loadJobDetail(SHEETNAME_POST, POSITIVE);
	}
	
	@DataProvider( name = "postNegativeProgramIds")
	static Object[][] postNegativeProgramIds()  {
		return loadJobDetail(SHEETNAME_POST, NEGATIVE);
	}
	
	@DataProvider( name = "putPositiveProgramIds")
	static Object[][] putPositiveProgramIds()  {
		return loadJobIdsPut(SHEETNAME_PUT, POSITIVE);
	}
	
	@DataProvider( name = "putNegativeProgramIds")
	static Object[][] putNegativeProgramIds()  {
		return loadJobIdsPut(SHEETNAME_PUT, NEGATIVE);
	}
	
	@DataProvider( name = "deletePositiveProgramIds")
	static Object[][] deletePositiveProgramIds()  {
		return loadJobIdsDelete(SHEETNAME_DELETE, POSITIVE);
	}
	
	@DataProvider( name = "deleteNegativeProgramIds")
	static Object[][] deleteNegativeProgramIds()  {
		return loadJobIdsDelete(SHEETNAME_DELETE, NEGATIVE);
	}
	
	static Object[][] loadJobIdsGet(String SheetName, String caseType)  {
		Object jobData[][] = null;
		try {
			int totalRows = ExcelUtils.getRowCount(PATH_JOBS, SheetName);
			String caseTypeInExcel ; 
			int noOfCases = 0;
			for (int i = 1; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					noOfCases++;
				}
			}
			int noOfColumns = 3;
			jobData = new Object[noOfCases][noOfColumns];
			
			//System.out.println( "Row count = " + noOfCases + "Column count =" + noOfColumns ); 
            
			int testCaseIndex = 0, expStatusCode, expStatusCodeIndex = 2;
			for (int i = 1, j =0; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					
					jobData[j][0] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, testCaseIndex);   // loads testCaseName
					jobData[j][1] = i;                                                                // loads RowIndex in Excel
					//System.out.println( "TestCaseName = " + jobData[j][0] + " rowIndex =" + jobData[j][1] ); 
					expStatusCode = Integer.parseInt(ExcelUtils.getCellData(PATH_JOBS, SheetName, i, expStatusCodeIndex) );
					jobData[j][2] = expStatusCode;
				    j++;
				}
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return jobData;
	}
	
	static Object[][] loadJobIdsDelete(String SheetName, String caseType)  {
		Object jobData[][] = null;
		try {
			int totalRows = ExcelUtils.getRowCount(PATH_JOBS, SheetName);
			String caseTypeInExcel ; 
			int noOfCases = 0;
			for (int i = 1; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					noOfCases++;
				}
			}
			int noOfColumns = 4;
			jobData = new Object[noOfCases][noOfColumns];
			
			//System.out.println( "Row count = " + noOfCases + "Column count =" + noOfColumns ); 
            
			int testCaseIndex = 0, jobIdIndex = 2, expStatusCodeIndex = 3, expStatusCode;
			for (int i = 1, j =0; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					
					jobData[j][0] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, testCaseIndex);   // loads testCaseName
					jobData[j][1] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, jobIdIndex);      // loads jobId
					jobData[j][2] = i;                                                                // loads RowIndex in Excel
					expStatusCode = Integer.parseInt( ExcelUtils.getCellData(PATH_JOBS, SheetName, i, expStatusCodeIndex) );
					jobData[j][3] = expStatusCode;   
					//System.out.println( "TestCaseName = " + jobData[j][0] + " jobId =" + jobData[j][1] ); 
				    j++;
				}
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return jobData;
	}
	
	
	static Object[][] loadJobIdsPut(String SheetName, String caseType)  {
		Object jobData[][] = null;
		try {
			int totalRows = ExcelUtils.getRowCount(PATH_JOBS, SheetName);
			String caseTypeInExcel ; 
			int noOfCases = 0;
			for (int i = 1; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					noOfCases++;
				}
			}
			int noOfColumns = 5;
			jobData = new Object[noOfCases][noOfColumns];
			
			//System.out.println( "Row count = " + noOfCases + "Column count =" + noOfColumns ); 
            
			int testCaseIndex = 0, jobIdIndex = 2, jobTitleIndex = 3, expStatusCodeIndex = 4, expStatusCode;
			for (int i = 1, j =0; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					
					jobData[j][0] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, testCaseIndex);   // loads testCaseName
					jobData[j][1] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, jobIdIndex);      // loads jobId
					jobData[j][2] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, jobTitleIndex); 
					jobData[j][3] = i;      // loads RowIndex in Excel
					expStatusCode = Integer.parseInt( ExcelUtils.getCellData(PATH_JOBS, SheetName, i, expStatusCodeIndex).trim());
					jobData[j][4] = expStatusCode;
					//System.out.println( "TestCaseName = " + jobData[j][0] + " jobId =" + jobData[j][1] ); 
				    j++;
				}
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return jobData;
	}
	
	
	static Object[][] loadJobDetail(String SheetName, String caseType)  {
		Object[][] objArray = null;
	
		try {
			int totalRows = ExcelUtils.getRowCount(PATH_JOBS, SheetName);
			int noOfCases = 0 ;
			String caseTypeInExcel ; 
			for (int i = 1; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) noOfCases++;
			}
			System.out.println( "Row count = " + totalRows + "No of Cases present: "+ noOfCases); 
			objArray = new Object[noOfCases][4];
			
			String cellValue = null; 
			int expStatusCode;
			for (int i = 1, j = 0; i <= totalRows; i++) 
			{
				caseTypeInExcel = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 1);
				if ( caseTypeInExcel.equals( caseType ) ) {
					HashMap<String, Object> hashMap = new HashMap<>();
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 2).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Id", cellValue);
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 3).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Title", cellValue);
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 4).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Company Name", cellValue);
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 5).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Location", cellValue);
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 6).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Type", cellValue);
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 7).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Posted time", cellValue);
					
					cellValue = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 8).trim();
					if ( !cellValue.equals(""))
					     hashMap.put( "Job Description", cellValue);
						
				    objArray[j][0] = ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 0); // stores testcaseName
				    objArray[j][1]=  hashMap;  
				    objArray[j][2]= i;  // stores rowIndexExcel
				    expStatusCode = Integer.parseInt( ExcelUtils.getCellData(PATH_JOBS, SheetName, i, 9).trim());
				    objArray[j][3] = expStatusCode;
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
