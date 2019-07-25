package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {

        String sql = "select * from user where username = ? and password = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);

        return user;
    }

    @Override
    public List<User> findAll() {


        String sql = "select * from user";

        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        //query 多结果集 和queryForObject

        return users;


    }

    @Override
    public void addUser(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delUser(Integer id) {
        String sql = "delete  from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findUser(Integer id) {
        String sql = "select * from user where id = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name = ?,gender = ? ,age = ? , address = ? , qq = ?, email = ? where id = ?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());

    }



    @Override
    public int findTotalCount() {
        String sql = "select count(*) from user";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public List<User> findByPage(int start, int rows) {
        String sql = "select * from user limit ?,?";

//        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), start, rows);
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class), start, rows);
        return list;
    }

    @Test

    public void chaXunTest() {
        UserDao userDaoimpl = new UserDaoImpl();

        User andy = findUserByUsernameAndPassword("Andy", "303");

        System.out.println(andy);
    }
}
