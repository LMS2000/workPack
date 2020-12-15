package learn_user.demo.mapper;

import learn_user.demo.domain.course;
import learn_user.demo.domain.course_student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface courseMapper {
List<course> findCourseByStudent(long sid);
void insertCourse(course course);
void updateCourse(course course);
List<course> findCourseByTeacher(@Param("tid") long tid);
List<course> findByCourseList();
void  insertCourseStudent(Map<?,?> map);
void  updateCourseByTeacher(Map<?,?> map);
course  findByCourse(Map<?,?> map);
List<course> findCourseByUnChange(long sid);
List<course>    findCourseByUnChangeTeacher(long tid);
 List<course_student>     findScoreByCourse(long tid);
  void  updateScore(course_student  course_student );
  void deleteCourseByStudentId(Map<?,?> map);
  Integer findStatus();
  void updateStatus(int courseStatus);
 List<course>    findCourseByStudentV2(long sid);
 Integer checkMax(Long cid);
 String [] findAllCid();
 course_student findCourseStudent(Map<?,?> map);
 }
