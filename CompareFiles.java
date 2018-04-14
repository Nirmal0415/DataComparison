package main;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import utils.ExcelReader;
import utils.ExcelUtil;

public class CompareFiles {

	static // Here we need to create logger instance so we need to pass Class name for
	// which we want to create log file in the used classname
	Logger logger = Logger.getLogger("CompareFiles");

	private static LinkedList<LinkedHashMap<String, String>> results = new LinkedList<>();
	private static LinkedHashMap<String, String> resultRow;

	public LinkedHashMap<String, String> compare_data() throws Exception {
		try {
			logger.info("compare data method called");

			ExcelReader xls;
			xls = new ExcelReader("./data/Execution_Driver.xlsx");
			logger.info("Execution driver sheet reads successfully from the mentioned path");
			XSSFSheet recordSheet = xls.GetSheet("Driver");
			LinkedList<LinkedHashMap<String, String>> dataSet = ExcelUtil.GetDataSet(recordSheet);

			for (LinkedHashMap<String, String> record : dataSet) {
				resultRow = new LinkedHashMap<String, String>();
				if (record.get("execute").equalsIgnoreCase("Yes")) {

					String sourceFilePath = record.get("sourcefile");
					String sourceSheetName = record.get("source sheet");

					String destFilePath = record.get("destinationfile");
					String destSheetName = record.get("dest sheet");

					String sourceColMap = record.get("sourcecolmap");
					String destColMap = record.get("destcolmap");

					resultRow.put("SourceFile", getFileName(sourceFilePath));
					resultRow.put("Source Sheet Name", sourceSheetName);
					resultRow.put("DestFile", getFileName(destFilePath));
					resultRow.put("Destination Sheet Name", destSheetName);

					String[] avoidCol = record.get("avoid col").split(",");

					String[] primaryKey = record.get("primary key").toLowerCase().split(",");

					LinkedList<LinkedHashMap<String, String>> sourceData = getData(sourceFilePath, sourceSheetName,
							avoidCol);

					LinkedList<LinkedHashMap<String, String>> destData = getData(destFilePath, destSheetName, avoidCol);

					if (sourceColMap.length() > 0) {

						LinkedList<LinkedHashMap<String, String>> sourceMap = getData(sourceColMap, "colMap");

						sourceData = reKeyDataset(sourceData, sourceMap);

					}

					if (destColMap.length() > 0) {

						LinkedList<LinkedHashMap<String, String>> destMap = getData(destColMap, "colMap");

						destData = reKeyDataset(destData, destMap);

					}

					compareData(sourceData, destData, primaryKey);
					ExcelUtil.printHashMapList(results);

					// create a work book

				}

			}
			ExcelUtil.createResultBook("./data/result.xlsx", results);
			ExcelUtil.printHashMapList(ExcelUtil.GetResultSummary(results));
			
			
			
			logger.info("Result book created");
			JFrame frame1 = new JFrame("Success popup");
			JOptionPane.showMessageDialog(frame1, "Compared Successfully...");
		} catch (Exception e) {
			JFrame frame2 = new JFrame("Failure popup");
			JOptionPane.showMessageDialog(frame2, "Comparision Failed. Please check log for further details");

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ExcelUtil.GetResultSummary(results);
	}

	public static LinkedList<LinkedHashMap<String, String>> getData(String filePath, String sheetName,
			String[] avoidCol)
					throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

		LinkedList<LinkedHashMap<String, String>> dataSet = null;

		ExcelReader xls;
		xls = new ExcelReader(filePath);
		XSSFSheet recordSheet = xls.GetSheet(sheetName);
		if (recordSheet != null) {

			dataSet = ExcelUtil.GetDataSet(recordSheet);
		}

		return cleanDataSet(dataSet, avoidCol);

	}

	public static LinkedList<LinkedHashMap<String, String>> getReKeyedData(String filePath, String sheetName,
			LinkedList<LinkedHashMap<String, String>> colNameMap)
					throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

		LinkedList<LinkedHashMap<String, String>> dataSet = null;

		ExcelReader xls;
		xls = new ExcelReader(filePath);
		XSSFSheet recordSheet = xls.GetSheet(sheetName);
		if (recordSheet != null) {

			dataSet = ExcelUtil.GetDataSet(recordSheet);
		}

		return reKeyDataset(dataSet, colNameMap);

	}

	public static LinkedList<LinkedHashMap<String, String>> getData(String filePath, String sheetName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

		LinkedList<LinkedHashMap<String, String>> dataSet = null;

		ExcelReader xls;
		xls = new ExcelReader(filePath);
		XSSFSheet recordSheet = xls.GetSheet(sheetName);
		if (recordSheet != null) {

			dataSet = ExcelUtil.GetDataSet(recordSheet);
		}

		return dataSet;

	}

	private static LinkedList<LinkedHashMap<String, String>> reKeyDataset(
			LinkedList<LinkedHashMap<String, String>> dataSet, LinkedList<LinkedHashMap<String, String>> colNameMap) {

		LinkedList<LinkedHashMap<String, String>> reKeyedSet = new LinkedList<LinkedHashMap<String, String>>();

		for (LinkedHashMap<String, String> data : dataSet) {

			LinkedHashMap<String, String> reKeyData = new LinkedHashMap<String, String>();

			for (String key : data.keySet()) {

				String[] keys = { "actual" };
				String[] values = { key };

				LinkedHashMap<String, String> map = ExcelUtil.getMatchedRow(colNameMap, keys, values);
				if (map != null)
					reKeyData.put(map.get("expected").toLowerCase(), data.get(key));
				else
					reKeyData.put(key, data.get(key));

			}

			reKeyedSet.add(reKeyData);
		}

		return reKeyedSet;

	}

	private static LinkedList<LinkedHashMap<String, String>> cleanDataSet(
			LinkedList<LinkedHashMap<String, String>> dataSet, String[] avoidCol) {

		LinkedList<LinkedHashMap<String, String>> resultantSet = new LinkedList<LinkedHashMap<String, String>>();
		List<String> colNames = Arrays.asList(avoidCol);

		for (LinkedHashMap<String, String> dataMap : dataSet) {

			LinkedHashMap<String, String> newMap = new LinkedHashMap<String, String>();

			Set<String> keys = dataMap.keySet();

			for (String key : keys) {

				if (!colNames.contains(key))
					newMap.put(key, dataMap.get(key));

			}

			resultantSet.add(newMap);
		}

		return resultantSet;

	}

	public static void getRowCount(LinkedList<LinkedHashMap<String, String>> dataSet1,
			LinkedList<LinkedHashMap<String, String>> dataSet2) {

		int file1Count = dataSet1.size();
		int file2Count = dataSet2.size();

		if (file1Count == file2Count) {

			logger.info("Pass row count are equal");
			resultRow.put("Test Status", "Pass");
		}

		else {

			logger.info("Fail row counts are different");
			logger.info("File1 row count :\t" + file1Count);
			logger.info("File2 row count :\t" + file2Count);
			resultRow.put("Test Status", "Fail");

		}

	}

	private static void compareData(LinkedList<LinkedHashMap<String, String>> sourceFile,
			LinkedList<LinkedHashMap<String, String>> destinationFile, String[] primaryKey) throws IOException {

		logger.info("---------------------><------------------------------");
		logger.info("Comparing Data for " + resultRow.get("SourceFile") + " and " + resultRow.get("DestFile"));
		int rowCount = 1;
		int misMacthCount = 0;
		int missingRowCount = 0;

		LinkedList<LinkedHashMap<String, String>> dataSet1;
		LinkedList<LinkedHashMap<String, String>> dataSet2;

		int sourceRowCount = sourceFile.size();
		int destRowCount = destinationFile.size();

		if (sourceRowCount > destRowCount || sourceRowCount == destRowCount) {
			dataSet1 = sourceFile;
			dataSet2 = destinationFile;

		} else {

			dataSet1 = destinationFile;
			dataSet2 = sourceFile;
			
			
			logger.info("There are row difference between source and destination files. Hence swaping the input files");

		}

		resultRow.put("Test Status", "Pass");
		
		String ASCI = "_x000D_";

		//String logpath = "./log/log.txt";
		
		for (LinkedHashMap<String, String> dataMap : dataSet1) {
			List<String> values = new LinkedList<String>();
			for (String key : primaryKey)
				values.add(dataMap.get(key));

			LinkedHashMap<String, String> matchedRow = ExcelUtil.getMatchedRow(dataSet2, primaryKey,
					values.toArray(new String[0]));

			if (matchedRow != null) {
				for (String key : dataMap.keySet()) {
//System.out.println(matchedRow.get(key));
					if (dataMap.get(key).trim().equals(matchedRow.get(key).trim())) {
//					if (dataMap.get(key).equals(matchedRow.get(key))) {
						continue;
// ASCI value replacement						
					}if (dataMap.get(key).contains(ASCI)){
						String.valueOf(ASCI).replace(ASCI, "");
					}
					
		
					else {
						resultRow.put("Test Status", "Fail");
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// logger.info("row:\t");
						logger.info("Mismatch in row:\t" + rowCount);
						logger.info("Mismatch in Column:\t" + key);
						logger.info("value in source file:\t" + "'" + dataMap.get(key) + "'");
						logger.info("value in destination file:\t" + "'" + matchedRow.get(key) + "'");
						misMacthCount++;
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<");
					}

				}

			}

			else {
				resultRow.put("Test Status", "Fail");
				logger.info("Source file row number :\t" + rowCount + ", not found in the destination file");
				missingRowCount++;
			}

			rowCount++;

		}
		int sourceRowCountvalue = sourceFile.size();
		int destRowCountvalue = destinationFile.size();
		int rowMismatchCountvalue = (sourceRowCountvalue) - (destRowCountvalue);
		if (rowMismatchCountvalue < 0) {
			rowMismatchCountvalue = rowMismatchCountvalue * (-1);
						
		}
		logger.info("Total number of Mismatch between two files :\t" + misMacthCount);
		resultRow.put("Total Data Mismatch Count", Integer.toString(misMacthCount));

		logger.info("Total number of rows missing between two files :\t" + rowMismatchCountvalue);

		logger.info("Total number of rows in source file :\t" + sourceRowCount);
		logger.info("Total number of rows in destination file :\t" + destRowCount);

		resultRow.put("Total DEP Row Count", Integer.toString(sourceRowCount));
		resultRow.put("Total SF Row Count", Integer.toString(destRowCount));
		resultRow.put("Total Row difference count" , Integer.toString(rowMismatchCountvalue));
		resultRow.put("Total Missing data Count", Integer.toString(missingRowCount));

		String message = "";

		if (missingRowCount > 0) {
			message = Integer.toString(missingRowCount) + ", Missing data found in the destination file";
		resultRow.put("Remarks", message);}
		else{
		message = "0, Missing data found. Please check the logs for detailed results";
		resultRow.put("Remarks", message);
		
		//resultRow.put("Log", logpath);
		}
		results.add(resultRow);
		
		

	}
	

	private static String getFileName(String filePath) {

		String fileName = "";

		int index = filePath.lastIndexOf("\\");
		fileName = filePath.substring(index + 1);

		return fileName;

	}
	

}
