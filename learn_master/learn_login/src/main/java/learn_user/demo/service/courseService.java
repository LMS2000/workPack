package learn_user.demo.service;

import learn_user.demo.domain.course;
import learn_user.demo.domain.course_student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface courseService {
    List<course> findByCourseList();
    Map<String,Object> insertSomeCourse(String classNo,String courseNo);
    List<course> getCourseListByStudent(String token);
    List<course> getCourseListByTeacher(String token);
    Map<String,Object> insertCourse(course course);
    Map<String,Object> updateCourse(course course);
    Map<String,Object> insertCourseStudent(String token ,Integer  courseId);
    Map<String,Object> insertCourseTeacher(String token ,Integer courseId);
    List<course> findCourseByUnChange(String token,long role);
     List<course_student> findScoreByCourse(String token);
     Map<String,Object> updateScore(course_student course_student );
     Map<String,Object> deleteCourseByStudentId(Long cid,String token);
    List<course> getCourseListByStudentV2(String token);
    Map<String,Object>  updateStatus(Integer status);
     Integer   findStatus();
     String [] findAllCid();
}
