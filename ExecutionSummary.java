package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExecutionSummary {

	public static void executionSummary() throws IOException {



		String excelFilePath = "./data/result.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
System.out.println("Result file reads successfully");
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		String sheetname = sheet.getSheetName();
		System.out.println(sheetname);
System.out.println("Results sheet navigated successfully");
		
		int r = sheet.getLastRowNum();
		int put = r + 1;
		System.out.println(put);
		
		System.out.println(put);
		try {
	Cell cell =	sheet.getRow(5).getCell(5);
		String formula= "SUM(F1:F2)";
cell.setCellFormula(formula);
		}catch(Exception e) {
			System.out.println(e);
		}
System.out.println("forumula applied");
		//sheet.getRow(put).getCell(5).setCellFormula(formula);
		inputStream.close();
		
				
		
		 FileOutputStream outputStream = new FileOutputStream(excelFilePath);
	        workbook.write(outputStream);
	        outputStream.close();
		
		
}}
