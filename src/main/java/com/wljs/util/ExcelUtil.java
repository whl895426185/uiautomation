package com.wljs.util;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.wljs.pojo.UiAutomation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


    /**
     * 检查excel文件是否合法
     *
     * @param filePath
     * @return
     */
    public List<UiAutomation> readExcel(String filePath) {
        //有序集合
        List<UiAutomation> list = new ArrayList<UiAutomation>();

        InputStream inputStream = null;
        try {

            String message = checkExcelFormat(filePath);
            if (null != message) {
                return null;
            }

            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (isExcel2007(filePath)) {
                isExcel2003 = false;
            }

            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);
            inputStream = new FileInputStream(file);

            /** 根据版本选择创建Workbook的方式 */
            Workbook workbook = null;
            if (isExcel2003) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }
            list = readExcelContent(workbook);


            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    inputStream = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return list;


    }

    /**
     * 检查excel文件是否合法
     *
     * @param filePath
     * @return
     */
    private String checkExcelFormat(String filePath) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null
                || !(isExcel2003(filePath) ||
                isExcel2007(filePath))) {
            return "文件名不是excel格式";
        }
        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return "文件不存在";
        }
        return null;
    }


    /**
     * 读取excel文件内容
     *
     * @param wb
     * @return
     */
    private List<UiAutomation> readExcelContent(Workbook wb) {
        List<UiAutomation> list = new ArrayList<UiAutomation>();
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);

        /** 得到Excel的行数 */
        int totalRows = sheet.getPhysicalNumberOfRows();

        for (int i = 0; i < totalRows; i++) {
            Row row = sheet.getRow(i);

            if (i < 2) {
                continue;
            }
            //获取每一行到列数
            int totalCells = sheet.getRow(i).getPhysicalNumberOfCells();

            UiAutomation automation = new UiAutomation();
            for (int j = 0; j < totalCells; j++) {
                String value = null;
                if (null != row.getCell(j)) {
                    if (row.getCell(j).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 数字类型

                        value = String.valueOf(row.getCell(j).getNumericCellValue());
                    } else {
                        value = row.getCell(j).getStringCellValue();
                    }
                }
                if (0 == j) {
                    automation.setEvent(value);
                } else if (1 == j) {
                    automation.setType(value);
                } else if (2 == j) {
                    automation.setName(value);
                } else if (3 == j) {
                    automation.setValue(value);
                }

            }
            list.add(automation);
        }
        return list;
    }


}
