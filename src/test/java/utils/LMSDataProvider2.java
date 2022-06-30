package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.testng.annotations.DataProvider;

public class LMSDataProvider2 {
	private static String sheetName_DDT = "CREATE";
	private static String sheetName_AllCases = "Sheet1";
	private static String path_DDTData = System.getProperty("user.dir") + "/src/test/resources/Data.xlsx";
	private static String path_Data_AllCases = System.getProperty("user.dir") + "/src/test/resources/Test_Data_AllCases.xlsx";
	public static HashMap data_allCases = new HashMap();
	private static int columns_AllCases = 5;

	public static void loadExcelDataForAllCases() {
		try {
			int noOfRows = ExcelUtils.getRowCount(path_Data_AllCases, sheetName_AllCases);
			int noOfColumns = ExcelUtils.getCellCount(path_Data_AllCases, sheetName_AllCases, 0);

			List<String> rowData = null;
			String key = null;

			//System.out.println( "Row count = " + noOfRows + "Column count =" + noOfColumns ); 

			for (int i = 1; i <= noOfRows; i++) 
			{
				rowData = new ArrayList<String>(5);
				for (int j = 0; j < noOfColumns; j++) 
				{
					if (j==0) {
						key = ExcelUtils.getCellData(path_Data_AllCases, sheetName_AllCases, i, j).trim();
						//System.out.println( "Key:" +  key );
					}
					else {
						rowData.add(ExcelUtils.getCellData(path_Data_AllCases, sheetName_AllCases, i, j).trim()  );
						//System.out.println( "Value:" +  ExcelUtils.getCellData(path_Data_AllCases, sheetName_AllCases, i, j) );
					}
				}
				data_allCases.put(key, rowData);
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
	}


	@DataProvider( name = "progdata")
	static String[][] getProgData()  {
		String progdata[][] = null;
		try {
			int noOfRows = ExcelUtils.getRowCount(path_DDTData, sheetName_DDT);
			int noOfColumns = ExcelUtils.getCellCount(path_DDTData, sheetName_DDT, 1);
			progdata = new String[noOfRows][noOfColumns];

			//System.out.println( "Row count = " + noOfRows + "Column count =" + noOfColumns ); 

			for (int i = 1; i <= noOfRows; i++) 
			{
				for (int j = 0; j < noOfColumns; j++) 
				{
					progdata[i - 1][j] = ExcelUtils.getCellData(path_DDTData, sheetName_DDT, i, j);
					//System.out.println( "Row = " + i + "Column =" + j + "value =" + progdata[i - 1][j]); 
				}
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return progdata;
	}

	@DataProvider( name = "programIds")
	static String[][] getProgramId()  {
		String progdata[][] = null;
		try {
			//System.out.println( "In get_programId" ); 
			String path = System.getProperty("user.dir") + "/src/test/resources/Data.xlsx";
			int noOfRows = ExcelUtils.getRowCount(path, sheetName_DDT);
			int noOfColumns = 1;
			progdata = new String[noOfRows][noOfColumns];

			//System.out.println( "Row count = " + noOfRows + "Column count =" + noOfColumns ); 

			int j = 0;
			for (int i = 1; i <= noOfRows; i++) 
			{
				progdata[i - 1][j] = ExcelUtils.getCellData(path, sheetName_DDT, i, 0);
			}
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
		return progdata;
	}

	/*public static void main(String args[])  {
		try {
			getDataForAllCases();
			System.out.println( "\n " ); 
			//get_prog_data();
		}
		catch ( Exception e) {
			System.out.println( "Exception" + e.getMessage()); 
		}
	} */
	
	
}
