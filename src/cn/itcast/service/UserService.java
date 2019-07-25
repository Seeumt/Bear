package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;

public interface UserService {
    User login(User user);

    List<User> findAll();

    void addUser(User user);

    void delUser(String id);

    User findUser(String id);

    void updateUser(User user);

    PageBean<User> findUserByPage(String currentPage,String rows);
}
