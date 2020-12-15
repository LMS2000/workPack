package learn_user.demo.service;

import learn_user.demo.domain.student;
import learn_user.demo.domain.teacher;

import java.util.HashMap;
import java.util.List;

public interface teacherService {
    HashMap<String,Object> insertTeacher(teacher teacher);
    void updateTeacher(teacher teacher);
    List<student> findStudentByClass(String token);
    List<teacher> findByTeacherList();
}
