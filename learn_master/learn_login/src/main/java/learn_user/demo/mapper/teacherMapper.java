package learn_user.demo.mapper;

import learn_user.demo.domain.teacher;
import java.util.List;
import java.util.Map;

public interface teacherMapper {
     List<teacher>  findByTeacherList();
     void insertTeacher(teacher teacher);
     void updateTeacher(teacher teacher);
     teacher findByTeacher(teacher teacher);
     teacher findByTeacherV2(Map<?,?> map);
}
