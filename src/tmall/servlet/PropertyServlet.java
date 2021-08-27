package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PropertyServlet extends BaseServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("name");
        int cid= Integer.parseInt(request.getParameter("cid"));
        Property property=new Property();
        property.setName(name);
        property.setCategory(categoryDAO.get(cid));
        propertyDAO.add(property);
        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        int cid=propertyDAO.get(id).getCategory().getId();
        propertyDAO.delete(id);
        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response) {
    int id= Integer.parseInt(request.getParameter("id"));
    Property property= propertyDAO.get(id);
    System.out.println(property.getId());
    request.setAttribute("p",property);
    return "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("name");
        int id= Integer.parseInt(request.getParameter("id"));
        int cid= Integer.parseInt(request.getParameter("cid"));
        Property property=new Property();
        property.setName(name);
        property.setCategory(categoryDAO.get(cid));
        property.setId(id);
        System.out.println(property.toString());
        propertyDAO.update(property);
        return "@admin_property_list?&cid="+cid;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response) {
        int cid= Integer.parseInt(request.getParameter("cid"));
        Category category=categoryDAO.get(cid);
        Page page=super.getPage(request);
        int total=propertyDAO.getTotal(cid);
        List<Property> propertys=propertyDAO.list(cid,page.getStart(),page.getCount());
        page.setTotal(total);
        page.setParam("&cid="+category.getId());

        request.setAttribute("page",page);
        request.setAttribute("category",category);
        request.setAttribute("propertys",propertys);

        return "admin/listProperty.jsp";

    }
}
