package utils;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public  class ExcelReader {

	Logger logger=Logger.getLogger("ExcelReader");
	
	static Map<String, String> mailAttributes;
	
	static XSSFWorkbook wb;
	
	
	public ExcelReader(String filePath) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
	
		wb = ReadFile(filePath);
		
	}
	
	public XSSFWorkbook ReadFile(String filePath) throws IOException {
		
		 FileInputStream file = new FileInputStream(new File(filePath));
		 XSSFWorkbook workbook = new XSSFWorkbook(file);
		 return workbook;
		 
	}
	
	public  XSSFSheet GetSheet(String sheetName) {
		
		XSSFSheet sheet = wb.getSheet(sheetName);
		
		if(sheet == null){
			
			logger.info("Please check the sheeet name, the shhet with name " + sheetName+"is not found in the workBook");
			
		}
		
		
		return sheet;
	}
	
	

	 
	 
	    public static String passmap(HashMap<String, String> status) {
	    	
	    	String s = "Hi All, <br><br> Please find the Sanity check status for NL catalyst. <br><br> <table border='1'><tr><th>SCENARIOS</th><th>STATUS</th></tr>";
	        //String s = "";
	        s = "<table border='1'>";
	        Set mapSet = (Set) status.entrySet();
	        Iterator mapIterator = mapSet.iterator();
	        while (mapIterator.hasNext()) {
	            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
	            s += "<tr><td>" + mapEntry.getKey().toString() + "</td><td>" + mapEntry.getValue().toString() + "</td></tr>";
	        }
	        s += "</table>";
	        return s;
	    }
	
	 
	
	public void IterateSheet (ArrayList<String> SheetNames , LinkedHashMap<String, String> dataMap) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		
		HashMap<String, String> status = new LinkedHashMap<String, String>();
		
		for (String sheetName : SheetNames) {
			
		//	System.out.println("<-- Executing Sheet -- > " + sheetName);
			
			XSSFSheet xlSheet = GetSheet(sheetName);
			
			
			try 
				{
					executeSheet(xlSheet,dataMap);
					System.out.println(sheetName + "\t"+"Success");
					
					status.put(sheetName, "Success");
					
				}
			
			catch (Exception ex){
				
				System.out.println(sheetName +"\t"+ "Failed");
				status.put(sheetName, "Failed");
				System.out.println(ex);
			}
						
			
			//System.out.println("<----------------------------------- > ");
		}
		
		String mailContent = mailAttributes.get("mailContent");
		
		String statusconent = passmap(status);
				
		mailContent = mailContent+statusconent;
		
		mailAttributes.put("mailContent", mailContent);
		
		
	}
	
	
	public void executeSheet(XSSFSheet sheet , LinkedHashMap<String, String> dataMap) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		
			
		  Iterator<Row> rowIterator = sheet.iterator();
		  
		  int i = 0 ; // to skip first row (header row of the sheet)
		  
		  while (rowIterator.hasNext()) 
	        {
	        	
	            Row row = rowIterator.next();
	            //For each row, iterate through all the columns
	         //   Iterator<Cell> cellIterator = row.cellIterator();
	            
	            if (i > 0) {
	            
	           String action = row.getCell(0).getStringCellValue();
	           String element = row.getCell(1).getStringCellValue();
	           
	           Cell Valcell = row.getCell(2);
	           
	           String value = ExcelUtil.GetCellValue(Valcell);
	                      
	           int indx = value.indexOf('[');
	           
	           if(indx >= 0) {
	        	   
	        	   String colName = ExcelUtil.GetCoulmName(value);
	        	   String absoluteVal = dataMap.get(colName);
	        	   
	        	   value = absoluteVal;
	        	   
	           }
	           
	           
	           
	            }
	            i++;
	        }
		
	}
	
	
	public ArrayList<String> GetSheetNames (XSSFSheet sheet) {
		
		 //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        ArrayList<String> SheetNames = new ArrayList<String>();
        while (rowIterator.hasNext()) 
        {
        	
            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
             
            while (cellIterator.hasNext()) 
            {
                Cell cell = cellIterator.next();
                SheetNames.add(cell.getStringCellValue());
                            
            }
            
        }
        
        return SheetNames;
		
	}
	
	
}


