package learn_user.demo;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import learn_user.demo.domain.*;
import learn_user.demo.domain.Class;
import learn_user.demo.mapper.classMapper;
import learn_user.demo.mapper.courseMapper;
import learn_user.demo.mapper.teacherMapper;
import learn_user.demo.mapper.userMapper;
import learn_user.demo.service.*;
import learn_user.demo.util.tokenWebUtil;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class DemoApplicationTests {
    @Autowired
     classService classService ;
    @Autowired
    studentService studentService ;
    @Autowired
    learn_user.demo.mapper.classMapper classMapper ;
    @Test
    void contextLoads() {

        Long[] sidByClassNo = classMapper.findSidByClassNo(602312L);
        System.out.println(sidByClassNo);
        for(Long sidByClass:sidByClassNo )
        {
            System.out.println(sidByClass);
        }
    }
    @Test
    void  contextLoads2()
    {

    }

}
