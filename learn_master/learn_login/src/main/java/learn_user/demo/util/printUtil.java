package learn_user.demo.util;

import learn_user.demo.domain.course;
import learn_user.demo.enums.statusEnum;
import learn_user.demo.service.courseService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import learn_user.demo.enums.printEnum;
public class printUtil {
        private static final String XML ="xls";
        private static final String XLSX ="xlsx";


    public static String batchImport(MultipartFile file, courseService courseService ) throws Exception {
          String fileName =file.getOriginalFilename();
          String subsume = fileName.substring(fileName.lastIndexOf("."));
          if(!XML.equals(subsume.substring(1)))
          {
              if(!XLSX.equals(subsume.substring(1))){
                  return "录入失败,文件不对";
              }

          }
        try{
            InputStream is = file.getInputStream();
             XSSFWorkbook  workbook = new XSSFWorkbook(is);
            //有多少个sheet
            int sheets = workbook.getNumberOfSheets();
            if(sheets>1){return "sheet过多";}
            XSSFSheet  sheet =workbook.getSheetAt(0);
            XSSFRow rowOne = sheet.getRow(0);
              boolean  b= printEnum.isExcelNorw(rowOne);
              if(b==false){
                  return "格式不对!";
              }
                int rows = sheet.getPhysicalNumberOfRows();
                for (int j = 1; j < rows; j++) {
                    course course1=new course();
                    //获得第 j 行
                    XSSFRow row = sheet.getRow(j);
                    course1.setCourseNo(row.getCell(0).getStringCellValue());
                    course1.setCourseName(row.getCell(1).getStringCellValue());
                    course1.setLearnTime(row.getCell(2).getStringCellValue());
                    course1.setEndTime(row.getCell(3).getStringCellValue());
                    course1.setCourseType(typeHelper.getType(row.getCell(4).getStringCellValue()));
                    course1.setCreateTime(new Date());
                    Map<String,Object> maps =    courseService.insertCourse(course1);
                    String resCode =(String)maps.get("resCode");
                    if(statusEnum.STATUS_406.equals(resCode))
                    {
                        return "Excel中包含了已存在的课程编号";
                    }
                }


        }catch(Exception e){
            e.printStackTrace();
        }
        return "导入数据成功";
    }

    public static void exportExcel(OutputStream out,List<?> datas, List<String> titles){
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            XSSFSheet sheet = wb.createSheet("new sheet");
            writeTitleToExcel(wb ,sheet ,titles  );
            writeDataToExcel(wb,sheet,datas,titles);
            wb.write(out);
            out.flush();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


    private static int writeTitleToExcel(XSSFWorkbook workbook, Sheet sheet, List<String> titles){
        int colIndex=0;
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setFontHeightInPoints((short) 14);
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(25);
        for (String title : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(title);
            colIndex++;
        }
        return colIndex;
    }
    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int oldWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > oldWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, oldWidth);
            }
        }
    }


    private static void writeDataToExcel(XSSFWorkbook workbook,Sheet sheet, List<?> datas, List<String> titles) {
        int cellIndex = writeTitleToExcel(workbook, sheet, titles);
        writeDataToExcel(workbook, sheet, datas);
        autoSizeColumns(sheet, cellIndex);
    }

    public static void writeDataToExcel(XSSFWorkbook xssfWorkbook, Sheet sheet, List<?> data)
    {
        int rowIndex = 1;
        Font dataFont = xssfWorkbook.createFont();
        dataFont.setFontName("simsun");
        dataFont.setColor(IndexedColors.BLACK.index);
        XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
        dataStyle.setFont(dataFont);
        Iterator<?> iterator = data.iterator();
        while(iterator.hasNext())
        {
            Row dataRow = sheet.createRow(rowIndex);
            Object col =iterator.next();
            Class clazz =col.getClass();
            int cellIndex = 0;
            Field [] fields =clazz.getDeclaredFields();
            for(Field field:fields)
            {
                Cell cell = dataRow.createCell(cellIndex);
                String fieldName =field.getName();
                String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

                Method method;
                Object value =null;
                try{
                    method=clazz.getMethod(methodName,new Class[]{});
                    value=method.invoke(col,new Object[]{});
                    System.out.println(methodName+"---"+value);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                cell.setCellValue(value+"");
                cellIndex ++;
            }
            rowIndex++;
        }
    }
}
