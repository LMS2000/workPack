package learn_user.demo.service;

import learn_user.demo.domain.user;

import java.util.HashMap;
import java.util.Map;

public interface userService {
    HashMap<String, Object> selectByUserName(String userName, String password);
    HashMap<String,Object> addUser(user u);
    Map<String,Object> updateUser(String token, String oldPassword,String newPassword);
}
