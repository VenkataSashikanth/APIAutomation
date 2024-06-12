package practiceApiAuto.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {
    private static FileInputStream ExcelFile;
    private static XSSFSheet ExcelWSheet;

    private static XSSFWorkbook ExcelWBook;

    private static XSSFCell Cell;

    private static XSSFRow Row;
    private String path;

    public XLUtility(String path) {
        this.path=path;
    }


    //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

    public void setExcelFile(String sheetName) throws Exception {
        try {
            // Open the Excel file
            ExcelFile = new FileInputStream(path);
            // Access the required test data sheet
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
            ExcelFile.close();
        } catch (Exception e){
            throw (e);
        }
    }

    public int getRowCount(String sheetName) throws IOException {
        ExcelFile=new FileInputStream(path);
        ExcelWBook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet= ExcelWBook.getSheet(sheetName);
        int rowcount=ExcelWSheet.getLastRowNum();
        ExcelWBook.close();
        ExcelFile.close();
        return rowcount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        ExcelFile=new FileInputStream(path);
        ExcelWBook=new XSSFWorkbook(ExcelFile);
        ExcelWSheet= ExcelWBook.getSheet(sheetName);
        Row=ExcelWSheet.getRow(rownum);
        int cellcount=Row.getLastCellNum();
        ExcelWBook.close();
        ExcelFile.close();
        return cellcount;
    }

    //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
    public String getCellData(String sheetName, int RowNum, int ColNum) throws IOException {
        /*fileInputStream=new FileInputStream(path);
        ExcelWBook=new XSSFWorkbook(fileInputStream);*/
        ExcelFile=new FileInputStream(path);
        ExcelWBook=new XSSFWorkbook(ExcelFile);
        ExcelWSheet=ExcelWBook.getSheet(sheetName);
        Row=ExcelWSheet.getRow(RowNum);
        Cell=Row.getCell(ColNum);
        DataFormatter formatter=new DataFormatter();
        String data;
        try {
            data=formatter.formatCellValue(Cell);
        }catch (Exception e){
            data="";
        }
        ExcelWBook.close();
        ExcelFile.close();
        return data;
        /*try{
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;
        }catch (Exception e){
            return"";
        }*/
    }

    //This method is to write in the Excel cell, Row num and Col num are the parameters
    /*public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{
        try{
            Row  = ExcelWSheet.getRow(RowNum);
            Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
            if (Cell == null) {
                Cell = Row.createCell(ColNum);
                Cell.setCellValue(Result);
            } else {
                Cell.setCellValue(Result);
            }

            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(Constant.Path_TestData + Constant.File_TestData);
            ExcelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch(Exception e){
            throw (e);
        }

    }*/
}
