package learn_user.demo.mapper;

import learn_user.demo.domain.Class;
import java.util.List;
import java.util.Map;

public interface classMapper {
 List<Class> findByClassList();
 Class findByClass(Map<String,Object> map);
 void insertClass(Class c);
 void updateClass(Class c);
 Long[] findSidByClassNo(Long classNo);
String [] findAllClassNo();
}
