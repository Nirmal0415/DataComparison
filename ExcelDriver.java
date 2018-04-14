package utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDriver {

	public static void driveFile(String filePath, String driverSheet , String mailSheet , String dataSheet) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Logger logger=Logger.getLogger("ExcelDriver");
		ExcelReader xls = new ExcelReader(filePath);
		XSSFWorkbook wb =  xls.ReadFile(filePath);
		XSSFSheet drSheet = xls.GetSheet(driverSheet);
		XSSFSheet dtSheet =  xls.GetSheet(dataSheet);
		
		
		
		ArrayList<String> sheetOrder = xls.GetSheetNames(drSheet);
		logger.info("<-- Sheet Names -- > " + sheetOrder.toString());
		
		LinkedList<LinkedHashMap<String,String>> dataSet =  ExcelUtil.GetDataSet(dtSheet);
		
		for (LinkedHashMap<String,String> dataMap : dataSet )
		{
			xls.IterateSheet(sheetOrder,dataMap);
		}
		
		
		logger.info("Excel Driver complete");
		
	}
	
		
	
}
