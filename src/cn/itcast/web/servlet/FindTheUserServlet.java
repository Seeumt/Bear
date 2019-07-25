package cn.itcast.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/findTheUserServlet")
public class FindTheUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        1.获取用户名
        String username = request.getParameter("username");
        //2.调用service层来判断用户名是否存在
        Map<String,Object> map = new HashMap<String,Object>();
/**
 * 设置响应回的数据格式为json
 *
 */
        response.setContentType("application/json;charset=utf-8");
        if ("Andy".equals(username)) {
            map.put("userExist",true);
            map.put("msg", "您的用户名过于个性");

        } else {
            map.put("userExist", false);
            map.put("msg", "完全ok");
        }

        ObjectMapper mapper = new ObjectMapper();

//        String json = mapper.writeValueAsString(map);
//        response.getWriter().write(json);
        mapper.writeValue(response.getWriter(),map);




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
