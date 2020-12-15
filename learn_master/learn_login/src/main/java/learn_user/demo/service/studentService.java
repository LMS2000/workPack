package learn_user.demo.service;

import learn_user.demo.domain.student;

import java.util.HashMap;
import java.util.List;

public interface studentService {
   void updateStudent( student student);
   HashMap<String ,Object> addStudent(student student);
   HashMap<String,Object> getStudent(String token);
}
