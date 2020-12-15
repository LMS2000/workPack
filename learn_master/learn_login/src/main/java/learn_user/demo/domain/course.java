package learn_user.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class course extends course_student {
    private Long  cid;
    private String courseNo;
    private String courseName;
    private String substituteTeacher;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date    updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private  Date   createTime;
    private String startTime;
    private String   endTime;
    private  String learnTime;
    private  Integer courseType;
    private  Integer term;
    private String academicYear;
    private String courseDesc;
    private String coursePlace;
    private Long courseMaxCount;
    private  Long currentCount;

    public Long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Long currentCount) {
        this.currentCount = currentCount;
    }

    public Long getCourseMaxCount() {
        return courseMaxCount;
    }

    public void setCourseMaxCount(Long courseMaxCount) {
        this.courseMaxCount = courseMaxCount;
    }

    public String getCoursePlace() {
        return coursePlace;
    }

    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public Long getCid() {
        return cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getSubstituteTeacher() {
        return substituteTeacher;
    }

    public void setSubstituteTeacher(String substituteTeacher) {
        this.substituteTeacher = substituteTeacher;
    }



    public String getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "course{" +
                "cid=" + cid +
                ", courseNo='" + courseNo + '\'' +
                ", courseName='" + courseName + '\'' +
                ", substituteTeacher='" + substituteTeacher + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", learnTime='" + learnTime + '\'' +
                ", courseType=" + courseType +
                ", term=" + term +
                ", academicYear='" + academicYear + '\'' +
                ", courseDesc='" + courseDesc + '\'' +
                ", coursePlace='" + coursePlace + '\'' +
                ", courseMaxCount=" + courseMaxCount +
                ", currentCount=" + currentCount +
                '}';
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }
}
