package learn_user.demo.service;

import learn_user.demo.domain.Class;
import learn_user.demo.vo.classDTO;

import java.util.List;
import java.util.Map;

public interface classService {
    List<Class> findClassList();
    Map<String,Object> insertClass(classDTO classDTO );
    Map<String,Object> updateClass(classDTO aClass );
    String [] findAllClassNo();
}
