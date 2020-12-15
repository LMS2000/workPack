package learn_user.demo.enums;

import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.ArrayList;
import java.util.List;

public class printEnum {
    private static final String COURSE_CID="课程id";
    private static final String COURSE_NO="课程编号";
    private  static  final String COURSE_NAME="课程名";
    private  static  final String COURSE_SUBSTITUTE_TEACHER="任教老师";
    private  static  final  String COURSE_UPDATE_TIME ="课程修改时间";
    private  static  final  String COURSE_CREATE_TIME ="课程创建时间";
    private  static  final  String COURSE_START_TIME="开课时间";
    private  static  final  String COURSE_END_TIME="结课时间";
    private  static  final  String COURSE_LEARN_TIME="课时";
    private  static  final  String COURSE_TYPE="课程类型";
    private static  final  String COURSE_TERM="课程学期";
    private  static  final  String COURSE_ACADEMIC_YEAR="课程学年";
    private static  final  String COURSE_DESC="课程描述";
    private  static  final  String COURSE_PLACE="上课地点";


    public static boolean isExcelNorw(XSSFRow row){
        if(row.getPhysicalNumberOfCells()>6) {
            return false;
        }

        if(COURSE_NO.equals(row.getCell(0).getStringCellValue().trim()))
        {
            if(COURSE_NAME.equals(row.getCell(1).getStringCellValue().trim()))
            {
                if(COURSE_LEARN_TIME.equals(row.getCell(2).getStringCellValue().trim()))
                {
                    if(COURSE_END_TIME.equals(row.getCell(3).getStringCellValue().trim()))
                    {
                        if(COURSE_TYPE.equals(row.getCell(4).getStringCellValue().trim()))
                        {
                            return true;
                        }
                    }
                }
            }
        }

       return false;
    }
    public static List<String> getCurseTitle()
    {
        List<String> title =new ArrayList<>();
        title.add(COURSE_CID);
        title.add(COURSE_NO);
        title.add(COURSE_NAME);
        title.add(COURSE_SUBSTITUTE_TEACHER);
        title.add(COURSE_UPDATE_TIME);
        title.add(COURSE_CREATE_TIME);
        title.add(COURSE_START_TIME);
        title.add(COURSE_END_TIME);
        title.add(COURSE_LEARN_TIME);
        title.add(COURSE_TYPE);
        title.add(COURSE_TERM);
        title.add(COURSE_ACADEMIC_YEAR);
        title.add(COURSE_DESC);
        title.add(COURSE_PLACE);
        return title;
    }
}
