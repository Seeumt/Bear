package cn.itcast.dao;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;

/**
 * 操作数据库
 */
public interface UserDao {
    User findUserByUsernameAndPassword(String username, String password);

    List<User> findAll();

    void addUser(User user);

    void delUser(Integer id);

    User findUser(Integer id);

    void updateUser(User user);


    int findTotalCount();

    List<User> findByPage(int start, int rows);
}
