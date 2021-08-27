package tmall.servlet;

import tmall.bean.User;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserServlet extends BaseServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response) {
        Page page=super.getPage(request);
        page.setTotal(userDAO.getTotal());
        List<User> users=userDAO.list(page.getStart(),page.getCount());
       request.setAttribute("us",users);
       request.setAttribute("page",page);
        return "admin/listUser.jsp";
    }
}
