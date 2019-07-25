<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>

        function deleteUser(id) {
            if (confirm("你确定要删除么")) {
                location.href="${pageContext.request.contextPath}/delUserServlet?id="+id;

            }

        }
    </script>

</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

<%--   1.  加了两个按钮  --%>
    <div style="float: right;margin: 5px;">

<%--7.改动添加按钮对应的超链接--%>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
        <a class="btn btn-primary" href="add.html">删除选中</a>


    </div>



    <!-- 2.   添加内联表单-->
    <div style="float: left;">
        <!-- 这个label 的 for属性 和 <input>里的 id属性一致就会有点击汉字会进入框框 -->
        <form class="form-inline">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" class="form-control" id="exampleInputName2" >
            </div>
            <div class="form-group">
                <label for="exampleInputName3">籍贯</label>
                <input type="text" class="form-control" id="exampleInputName3" >
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="email" class="form-control" id="exampleInputEmail2" >
            </div>

            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>




    <form id="form" action="${pageContext.request.contextPath}/delUserServlet" method="post">
        <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <%--   5.加一列复选框         --%>
            <th><input type="checkbox" ></th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>

<%--        <c:forEach items="${pb.list}" var="user" varStatus="s">--%>
        <c:forEach items="${pb.list}" var="user" varStatus="s">
            <tr>
                <%--   5.加一列复选框         --%>
                <td><input type="checkbox" name="uid" value="${user.id}"></td>
                <td>${s.count}</td>
                <td>${user.name}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.qq}</td>
                <td>${user.email}</td>
                <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
                    <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id});">删除</a></td>
            </tr>

        </c:forEach>


    </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pd.currentPage==1}">
                    <li class="disabled">
                </c:if>
                <c:if test="${pd.currentPage!=1}">
                    <li>
                </c:if>
                    <a href="${pageContext.request.getContextPath()}/findUserByPageServlet?currentPage=${pb.currentPage-1}&rows=${3}" aria-label="Previous">
<%--                        这个currentPage是从后端调回来的，是用Java代码表示的--%>
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach begin="1" end="${pb.totalPage}" var="i">
<%--                    通过总页数来确定每个超链接会传递的current参数值，这个从1——》总页数 不但可以用做数字标注，还可以起到的传参的作用--%>
<%--                   这个小按钮其实是个超链接，点击改变的是url                           --%>

                    <c:if test="${pb.currentPage==i}">
                        <li class="active"><a href="${pageContext.request.getContextPath()}/findUserByPageServlet?currentPage=${i}&rows=${3}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage!=i}">
                        <li><a href="${pageContext.request.getContextPath()}/findUserByPageServlet?currentPage=${i}&rows=${3}">${i}</a></li>
                    </c:if>
                </c:forEach>

                    <c:if test="${pb.currentPage==pb.totalPage}">
                        <li><a href="${pageContext.request.getContextPath()}/findUserByPageServlet?currentPage=${pb.totalPage}&rows=${3}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${pb.currentPage!=pb.totalPage}">
                <li>
                    <a href="${pageContext.request.getContextPath()}/findUserByPageServlet?currentPage=${pb.currentPage+1}&rows=${3}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                    </c:if>
                <!-- 4.加了一行字 -->
                <span style="font-size: 25px;margin-left:5px">共${pb.totalCount}条记录 共${pb.totalPage}页</span>
            </ul>
        </nav>


    </div>


</div>


</body>
</html>
