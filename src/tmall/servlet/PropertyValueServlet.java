package tmall.servlet;

import tmall.bean.Product;
import tmall.bean.PropertyValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PropertyValueServlet extends BaseServlet {
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
        int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue pv =propertyValueDAO.get(pvid);
        pv.setValue(value);
        propertyValueDAO.update(pv);
        return "%success";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response) {
        int pid= Integer.parseInt(request.getParameter("pid"));
        Product product=productDAO.get(pid);
        List<PropertyValue> list= propertyValueDAO.list(pid);
        request.setAttribute("pvs",list);
        request.setAttribute("p",product);
        return "admin/editPropertyValue.jsp";
    }
}
