package utils;



import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.ExecutionSummary;



public class ExcelUtil {
	static Logger logger=Logger.getLogger("ExcelUtil");

	public static String GetCoulmName(String placeholder) {

		String ColumnName;

		try {
			ColumnName = placeholder.replaceAll(".*\\[|\\].*", "");
		} catch (Exception exception) {

			ColumnName = "";

		}

		return ColumnName;

	}

	public static LinkedList<LinkedHashMap<String, String>> GetDataSet(
			XSSFSheet dataSheet ) {

		Iterator<Row> rowIterator = dataSheet.iterator();

		int headerRownum = 0;

		LinkedList<LinkedHashMap<String, String>> dataSet = new LinkedList<LinkedHashMap<String, String>>();

		LinkedList<String> header = new LinkedList<String>();
		int columNum = 0; // Initialize number of columns

		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();

			

			// get header name of the first row

			if (headerRownum == 0) {

				columNum = row.getLastCellNum();

				for (int i = 0; i < columNum; i++) {
					Cell currentCell = row.getCell(i);

					header.add(GetCellValue(currentCell).toLowerCase());
//					header.add(GetCellValue(currentCell));
				}
			}

			if (headerRownum != 0) {

				LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//				columNum = row.getLastCellNum();

				for (int i = 0; i < columNum; i++) {

					Cell currentCell = row.getCell(i);

					dataMap.put(header.get(i).toString(),
//							GetCellValue(currentCell).toLowerCase());
							GetCellValue(currentCell));

				}
				dataSet.add(dataMap);
			}

			headerRownum++;
//			logger.info("Row Num:\t" + headerRownum);
		}

		return dataSet;

	}

	
	public static String GetCellValue(Cell currentCell) {

		String Value = "";

		if (currentCell != null
				&& currentCell.getCellType() != Cell.CELL_TYPE_BLANK)

			switch (currentCell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				Value = Integer.toString((int) (currentCell
						.getNumericCellValue()));
				if (DateUtil.isCellDateFormatted(currentCell)) {
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	                                                           
                  Value = dateFormat.format(currentCell.getDateCellValue());
                   
                } else {
                    Double value = currentCell.getNumericCellValue();
                    Long longValue = value.longValue();
                    Value = new String(longValue.toString());
                }
				break;
			
			case Cell.CELL_TYPE_STRING:
				Value = currentCell.getStringCellValue();
				
				break;
			case Cell.CELL_TYPE_BOOLEAN:
                Value = new String(new Boolean(
                        currentCell.getBooleanCellValue()).toString());
                break;
                
			case Cell.CELL_TYPE_ERROR:
				 Value = new String(new Boolean(
	                        currentCell.getBooleanCellValue()).toString());
				 break;
		      
			case Cell.CELL_TYPE_FORMULA:
				 Value = new String(new Boolean(
	                        currentCell.getBooleanCellValue()).toString());
				 break;
            case Cell.CELL_TYPE_BLANK:
                Value = "";
			
			}
				//logger.info(currentCell+" "+Value);

		return Value;

	}

	public static LinkedHashMap<String, String> getMatchedRow(
			LinkedList<LinkedHashMap<String, String>> dataSet, String[] keys,
			String[] values) {

		LinkedHashMap<String, String> matchedMap = null;

		for (LinkedHashMap<String, String> dataMap : dataSet) {
			LinkedList<String> compareResult = new LinkedList<String>();


			for (int idx = 0; idx < keys.length; idx++) {

				String key = keys[idx];
				String value = "";
				if (values[idx] != null)
					value = values[idx].toLowerCase().trim();
//					value = values[idx];
				//logger.info(key+"  "+value);

				if (dataMap.containsKey(key)
//						&& dataMap.get(key).toLowerCase().equals(value)) {
						&& dataMap.get(key).equalsIgnoreCase(value)) {
					compareResult.add("matched");
				}
				if (compareResult.size() == keys.length)
					return dataMap;
			}
		}

		return matchedMap;
		
	}

	public static LinkedList<LinkedHashMap<String, String>> getColumnBasedRows(
			LinkedList<LinkedHashMap<String, String>> dataSet,
			String columnName, String value) {

		LinkedList<LinkedHashMap<String, String>> resultant = new LinkedList<LinkedHashMap<String, String>>();
		for (LinkedHashMap<String, String> set : dataSet) {
			if (set.get(columnName).equals(value))
				resultant.add(set);
//			logger.info(set);

		}

		return resultant;

	}

	public static List<String> getColumnValues(
			LinkedList<LinkedHashMap<String, String>> list, String columnName) {

		List<String> strList = new LinkedList<String>();

		if (list.size() > 0)

			for (LinkedHashMap<String, String> map : list) {
				strList.add(map.get(columnName));
			}

		else {

			logger.info("The Config list is Emtpty");
		}

		return strList;
	}

	public static List<String> getUniqueColumnValues(
			LinkedList<LinkedHashMap<String, String>> list, String columnName) {

		List<String> strList = new LinkedList<String>();

		if (list.size() > 0)

			for (LinkedHashMap<String, String> map : list) {

				if (!strList.contains(map.get(columnName)))
					strList.add(map.get(columnName));
			}

		else {

			logger.info("The Config list is Emtpty");
		}

		return strList;
	}

	public static void printHashMapList(
			LinkedList<LinkedHashMap<String, String>> list) {

		if (list.size() > 0)

			for (LinkedHashMap<String, String> map : list) {

				logger.info("----------Row------------" );
				
				for (String key : map.keySet()) {

					logger.info("Column : " + key + " Value : "
							+ map.get(key));
				}
			}

		else {

			logger.info("The list is Emtpty to print");
		}

	}
	
	
	public static void printHashMapList(
			LinkedHashMap<String, String> list) {

		logger.info("----------Row------------" );
				
				for (String key : list.keySet()) {

					logger.info("Column : " + key + " Value : "
							+ list.get(key));
				}
	}
	
	public static  LinkedHashMap<String, String> GetResultSummary (LinkedList<LinkedHashMap<String, String>> result) {
		
		LinkedHashMap<String, String> rSummary = new LinkedHashMap<>();
		
		rSummary.put("Total Data Mismatch Count", "0");
		rSummary.put("Total DEP Row Count", "0");
		rSummary.put("Total SF Row Count", "0");
		rSummary.put("Total Row difference count", "0");
		rSummary.put("Total Missing data Count", "0");
		rSummary.put("Test Status", "Pass");
		
		
		String[] intCol = {"Total Data Mismatch Count","Total DEP Row Count","Total Row difference count","Total Missing data Count","Total SF Row Count"};
 		
		if (result.size() > 0)
			
			for(LinkedHashMap<String, String> row : result) {
				
				for(String key : intCol) {
					Integer val = Integer.parseInt(row.get(key));
					Integer summaryVal = Integer.parseInt(rSummary.get(key));
					Integer finalSum = summaryVal + val;
					rSummary.put(key, finalSum.toString());
				}
				
				if(row.get("Test Status").equalsIgnoreCase("fail")) {
				
					rSummary.put("Test Status", "Fail");
				}
				
				
			}
		
		
		
		
		return rSummary;
		
		
	}
	
	
	
	public static void createResultBook (String fileName, LinkedList<LinkedHashMap<String, String>> data) throws IOException{
		
		FileOutputStream out = new FileOutputStream(fileName);
		Workbook wb = new XSSFWorkbook();
		Sheet s = wb.createSheet("Results");
		Row r = null;
		Cell c = null;
		XSSFCellStyle green = (XSSFCellStyle) wb.createCellStyle();
		XSSFCellStyle red = (XSSFCellStyle) wb.createCellStyle();
		XSSFCellStyle header = (XSSFCellStyle) wb.createCellStyle();
		
		
		
		Font f = wb.createFont();
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		header.setFont(f);
		 
		green.setFillForegroundColor(HSSFColor.GREEN.index);
		green.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
		
		red.setFillForegroundColor(HSSFColor.RED.index);
		red.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND );
		
		int rowNum =  (short)0;
		int cellNum = (short)0 ;
		
		// create header
		
		r = s.createRow(rowNum);
		
		if(rowNum == 0 ){
			
			for(String colName : data.get(0).keySet()){
				
				c = r.createCell(cellNum);
				c.setCellValue(colName);
				c.setCellStyle(header);
			
				cellNum++;
				
			}
			rowNum++;
			cellNum = (short) 0;
		}
		
		for(LinkedHashMap<String, String> row : data){
			
			// create a new row
			
			r = s.createRow(rowNum);
			cellNum = (short) 0;
			for(String colName : row.keySet()){
					c = r.createCell(cellNum);
					c.setCellValue(row.get(colName));
					if(colName.equals("Test Status")){
						
						if(row.get(colName).equalsIgnoreCase("pass"))
							c.setCellStyle(green);
						else{
							c.setCellStyle(red);
						}
						
					}
					
					cellNum++;
					
				}
			rowNum++;	
			}
			
		wb.write(out);
		
		out.close();
		
	}

}
