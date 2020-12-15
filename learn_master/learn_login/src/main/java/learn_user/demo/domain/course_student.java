package learn_user.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class course_student {
    private Long studentFk;
    private Long courseFk;
    private String studentName;
    private String courseName;
    private Double score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date  createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private  Date updateTime;
    private String finalEvaluation;
    public Long getStudentFk() {
        return studentFk;
    }

    public void setStudentFk(Long studentFk) {
        this.studentFk = studentFk;
    }

    public Long getCourseFk() {
        return courseFk;
    }

    public void setCourseFk(Long courseFk) {
        this.courseFk = courseFk;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    @Override
    public String toString() {
        return "course_student{" +
                "studentFk=" + studentFk +
                ", courseFk=" + courseFk +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score=" + score +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", finalEvaluation='" + finalEvaluation + '\'' +
                '}';
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFinalEvaluation() {
        return finalEvaluation;
    }

    public void setFinalEvaluation(String finalEvaluation) {
        this.finalEvaluation = finalEvaluation;
    }

}
