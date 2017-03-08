package com.lightningrobotics.scout_862;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import static android.os.Environment.MEDIA_BAD_REMOVAL;
import static android.os.Environment.MEDIA_CHECKING;
import static android.os.Environment.MEDIA_EJECTING;
import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.MEDIA_MOUNTED_READ_ONLY;
import static android.os.Environment.MEDIA_NOFS;
import static android.os.Environment.MEDIA_REMOVED;
import static android.os.Environment.MEDIA_SHARED;
import static android.os.Environment.MEDIA_UNKNOWN;
import static android.os.Environment.MEDIA_UNMOUNTABLE;
import static android.os.Environment.MEDIA_UNMOUNTED;

/**
 * Created by khees on 12/28/2016.
 */

public class FileUtils {

    public static int maxX = 25;
    public static int maxY = 100;
    public static String[][] appData;
    public static int matchCounter = 1;

    public String[][] excelToArray(String path) {
        File file;
        String[][] data = new String[maxX][maxY];
        try {

            file = new File(path);

            FileInputStream myInput = new FileInputStream(file);
            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();

            //make blank 2D array to hold data
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    data[x][y] = "0";
                }
            }
            for (int y = 0; y < maxY; y++) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                for (int x = 0; x < maxX; x++) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    data[x][y] = myCell.toString();
                    //get rid of periods
                    data[x][y] = data[x][y].replaceAll("[.]", "period");
                    data[x][y] = data[x][y].replaceAll("period0", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void collumnToExcel(String path, File extDir) {
        File file;
        try {
            //New Workbook
            Workbook wb = new HSSFWorkbook();
            //New Cell
            Cell c = null;
            //New Sheet
            Sheet sheet1 = null;
            sheet1 = wb.createSheet("Sheet1");

            for (int x = 0; x < maxX; x++) {
                Row row = sheet1.createRow(matchCounter);
                c = row.createCell(x);
                if (appData[x][matchCounter].equals("")) {
                    c.setCellValue("0");
                } else {
                    c.setCellValue(appData[x][matchCounter]);
                }
            }


            // Create a path where we will place our List of objects on external storage

            file = new File(path);
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file);
                wb.write(os);
            } catch (IOException e) {
            } catch (Exception e) {
            } finally {
                try {
                    if (null != os)
                        os.close();
                } catch (Exception ex) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void arrayToExcel(String path) {
        File file;
        try {
            //New Workbook
            Workbook wb = new HSSFWorkbook();
            //New Cell
            Cell c = null;
            //New Sheet
            Sheet sheet1 = null;
            sheet1 = wb.createSheet("Sheet1");

            for (int y = 0; y < maxY; y++) {
                Row row = sheet1.createRow(y);
                for (int x = 0; x < maxX; x++) {
                    c = row.createCell(x);
                    if (x == 1 || x == 24) {
                        c.setCellValue("");
                    }
                    if (appData[x][y].equals("")) {
                        c.setCellValue("0");
                    } else {
                        c.setCellValue(appData[x][y]);
                    }
                }
            }

            // Create a path where we will place our List of objects on external storage
            file = new File(path);
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file);
                wb.write(os);
            } catch (IOException e) {
            } catch (Exception e) {
            } finally {
                try {
                    if (null != os)
                        os.close();
                } catch (Exception ex) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToArray(int colNum, int rowNum, String value) {
        appData[colNum][rowNum] = value;
    }
}