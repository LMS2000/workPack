package learn_user.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class teacher {
    private Long tid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private  Date updateTime;
    private Integer gender;
    private Long classNo;
    private String collegeName;
    private String teacherNo;
    private  Long userNo;
    private String teacherName;
    private String classNoStr;

    public String getClassNoStr() {
        return classNoStr;
    }

    public void setClassNoStr(String classNoStr) {
        this.classNoStr = classNoStr;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Long getClassNo() {
        return classNo;
    }

    public void setClassNo(Long classNo) {
        this.classNo = classNo;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }


    @Override
    public String toString() {
        return "teacher{" +
                "tid=" + tid +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", gender=" + gender +
                ", classNo='" + classNo + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", teacherNo='" + teacherNo + '\'' +
                ", userNo=" + userNo +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }
}
