package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    public static final String filename = "testdata.xlsx";
    public static final String currentDir = System.getProperty("user.dir");
    public static String filePath;
    public static XSSFWorkbook xssfWorkbook;
    public static XSSFSheet xssfSheet;
    public static XSSFRow xssfRow;
    public static XSSFCell xssfCell;

    public static void setSheenName(String sheenName) throws IOException {
        if(Platform.getCurrent().toString().equalsIgnoreCase("MAC")){
            filePath = currentDir + "/src/test/resources/";
        } else if (Platform.getCurrent().toString().contains("Win")) {
            filePath = currentDir + "\\src\\test\\resources\\";

        }
        FileInputStream inputStream = new FileInputStream(filePath + filename);
        xssfWorkbook = new XSSFWorkbook(inputStream);
        xssfSheet = xssfWorkbook.getSheet(sheenName);

    }
    public static String getCell(int rowNum, int colNum){
        xssfRow = xssfSheet.getRow(rowNum);
        xssfCell = xssfRow.getCell(colNum);
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(xssfCell);
    }
    public XSSFRow getRow(int rowNum){
        return xssfSheet.getRow(rowNum);
    }
    public static void  setCell(String value, int rowNum, int colNum) throws IOException {
        xssfCell = xssfSheet.getRow(rowNum).getCell(colNum);
        if(xssfCell == null){
            xssfRow.createCell(colNum);
            xssfCell.setCellValue(value);
        }else {xssfCell.setCellValue(value);}
        FileOutputStream fileOutputStream = new FileOutputStream(filePath + filename);
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
