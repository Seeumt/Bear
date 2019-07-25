package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public List<User> findAll() {

        return dao.findAll();
    }

    @Override
    public void addUser(User user) {

        dao.addUser(user);


    }

    @Override
    public void delUser(String id) {
        dao.delUser(Integer.parseInt(id));

    }

    @Override
    public User findUser(String id) {
        return dao.findUser(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    /**
     * 通过当前页数和每页显示条数 通过sql对数据库进行操作
     * @param _currentPage
     * @param _rows
     * @return
     */
    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        if (currentPage<=0){
            currentPage=1;
        }
        /*
        这个判断其实可以加在Servlet
         */
        PageBean<User> pb = new PageBean<User>();

        pb.setRows(rows);

        int totalCount = dao.findTotalCount();
        pb.setTotalCount(totalCount);
        pb.setCurrentPage(currentPage);
        /*
        if (currentPage==totalCount){
            pb.setCurrentPage(totalCount);
        }
         */

        //用dao查询List集合
        int start = (currentPage - 1) * rows;

        List<User> list = dao.findByPage(start, rows);

        pb.setList(list);
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        pb.setTotalPage(totalPage);
        return pb;

    }
}
