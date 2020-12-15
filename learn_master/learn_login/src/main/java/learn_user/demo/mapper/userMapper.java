package learn_user.demo.mapper;

import com.sun.javafx.collections.MappingChange;
import learn_user.demo.domain.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface userMapper {
    user  selectByUserName( @Param("userName") String userName,@Param("password") String password);
    List<user> findAllUserList();
    void  updatePasswordByUserName(Map<?,?> map);
    user findByUser(Map<?,?> map);
    void updateExcludePassword(Map<?,?> map);
    void  insertUser(user u);
}
