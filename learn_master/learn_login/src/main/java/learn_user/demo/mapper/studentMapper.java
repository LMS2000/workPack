package learn_user.demo.mapper;

import learn_user.demo.domain.student;

import java.util.List;
import java.util.Map;

public interface studentMapper {
     List<student> findByStudentList();
     void  insertStudent(student s);
     student findByStudent(Map<?,?> map);
     void  updateStudent(student s);
     List<student> findStudentByClass(long tid);
}
