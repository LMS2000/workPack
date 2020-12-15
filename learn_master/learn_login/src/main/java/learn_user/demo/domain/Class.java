package learn_user.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Class {
    private Long classNo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
    private String className;
    private  String classTeacherStr;
    private String classCollegeName;
    private Long classTeacher;

    public Date getCreateTime() {
        return createTime;
    }



    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    @Override
    public String toString() {
        return "Class{" +
                "classNo=" + classNo +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", className='" + className + '\'' +
                ", classTeacherStr='" + classTeacherStr + '\'' +
                ", classCollegeName='" + classCollegeName + '\'' +
                ", classTeacher=" + classTeacher +
                '}';
    }

    public Long getClassNo() {
        return classNo;
    }

    public void setClassNo(long classNo) {
        this.classNo = classNo;
    }

    public String getClassTeacherStr() {
        return classTeacherStr;
    }

    public void setClassTeacherStr(String classTeacherStr) {
        this.classTeacherStr = classTeacherStr;
    }

    public Long getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Long classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getClassCollegeName() {
        return classCollegeName;
    }

    public void setClassCollegeName(String classCollegeName) {
        this.classCollegeName = classCollegeName;
    }
}
