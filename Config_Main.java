package main;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import utils.ExcelUtil;

public class Config_Main {
    public static LinkedHashMap<String, String> mainmethod() throws IOException, Exception {
    	// Here we need to create logger instance so we need to pass Class name for 
    			 //which  we want to create log file in the used classname
    			     Logger logger=Logger.getLogger("Config_Main");
    			    

    			   // configure log4j properties file
    			   PropertyConfigurator.configure("./log/log4j.properties");	
    			   
    		logger.info("Main Method called");

    /*  Data_Testing callsfdcvbs=new Data_Testing();      // Calling SFDC-HADOOP VB script
      callsfdcvbs.sfdcvbscall();
      Thread.sleep(20000);
      
      Data_Testing sfdcvbs=new Data_Testing();          // Moving Text files to local directory
      sfdcvbs.run_sfdcvbscall();      
      
      /*text_excel Createxl=new text_excel();             // Converting text file to Excel file
      Createxl.covert_text_xl();
      
      Col_dbExcel col_dbxl=new Col_dbExcel();           // Moving columns and data types to XL
      col_dbxl.db_Excel();
      
      Thread.sleep(20000);
      
      Data_Testing calldbvbs=new Data_Testing();        // Calling HADOOP-NETEZZA VB script
      calldbvbs.dbvbscall();

      /*Db_Excel DbtoXl_stg0 = new Db_Excel();            // Extracting all the columns from the stg0 table
      DbtoXl_stg0.pull_stg0_dbExcel();
      
     db_excel_stg1 DbtoXl_stg1 =new db_excel_stg1();   // Extracting all the columns from the stg1 table
      DbtoXl_stg1.pull_stg1_dbExcel();
     
     Thread.sleep(45000); */         

      CompareFiles comp_data =new CompareFiles();       // Compare data between two XL files
      LinkedHashMap<String, String> rSummary= comp_data.compare_data();
     //  ExecutionSummary.executionSummary();
       

        System.out.println("Automation Completed");
        return rSummary;
    }
    
}

