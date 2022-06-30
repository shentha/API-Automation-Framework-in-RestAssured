package utils;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {
	private static FileInputStream fi;
	private static FileOutputStream fo;
	private static XSSFWorkbook wb;
	private static  XSSFSheet ws;
	private static XSSFRow row;
	private static XSSFCell cell;
	private static CellStyle style ;
	private static short formatIndex = 1 ;
	
	public static int getRowCount(String xlFile, String xlSheet) throws IOException {
		int rowCount=0;
		try {
			fi = new FileInputStream(xlFile);
			wb = new XSSFWorkbook(fi);
			ws = wb.getSheet(xlSheet);
			rowCount = ws.getLastRowNum();
			//int rowCount = ws.getPhysicalNumberOfRows();
			wb.close();
			fi.close();
		}
		catch(Exception e) {
			System.out.println( "Exception  " + e.getMessage() ); 
		}
		return rowCount;
	}
	
	public static int getCellCount(String xlFile, String xlSheet, int rowNum) throws IOException{
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		int columnCount = row.getLastCellNum();
		wb.close();
		fi.close();
		return columnCount;
	}

	public static String getCellData(String xlFile, String xlSheet, int rowNum, int columnNum) throws IOException{
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.getCell(columnNum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public static void setCellData(String xlFile, String xlSheet, int rowNum, int columnNum, String data)  {
		try {
			fi = new FileInputStream(xlFile);
			wb = new XSSFWorkbook(fi);
			ws = wb.getSheet(xlSheet);
			row = ws.getRow(rowNum);
			cell = row.createCell(columnNum);
			cell.setCellValue(data);
			fo = new FileOutputStream(xlFile);
			wb.write(fo);
			wb.close();
			fo.close();
			fi.close();
		}
		catch(Exception e) {
			System.out.println( "Exception " + e.getMessage()); 
		}
	}
	
	public static void setCellData(String xlFile, String xlSheet, int rowNum, int columnNum, int data)  {
		try {
			fi = new FileInputStream(xlFile);
			wb = new XSSFWorkbook(fi);
			ws = wb.getSheet(xlSheet);
			row = ws.getRow(rowNum);
			cell = row.createCell(columnNum);
			style = wb.createCellStyle();
			cell.setCellValue(data);
			style.setDataFormat(formatIndex );
			cell.setCellStyle( style );
			fo = new FileOutputStream(xlFile);
			wb.write(fo);
			wb.close();
			fo.close();
			fi.close();
		}
		catch(Exception e) {
			System.out.println( "Exception " + e.getMessage()); 
		}
	}
		
	/*public static void main(String[] args) {
		// ReadExcelSheet();
	}*/
}