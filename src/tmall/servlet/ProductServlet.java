package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class ProductServlet  extends BaseServlet{
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response) {
        int cid= Integer.parseInt(request.getParameter("cid"));
        String name=request.getParameter("name");
       String subTitle=request.getParameter("subTitle");
       float orignalPrice= Float.parseFloat(request.getParameter("orignalPrice"));
       float promotePrice= Float.parseFloat(request.getParameter("promotePrice"));
       int stock= Integer.parseInt(request.getParameter("stock"));
       Product product=new Product();
       product.setName(name);
       product.setCategory(categoryDAO.get(cid));
       product.setOrignalPrice(orignalPrice);
       product.setPromotePrice(promotePrice);
       product.setSubTitle(subTitle);
       product.setStock(stock);
       product.setCreateDate( new Date());
       productDAO.add(product);
       return "@admin_product_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
        int cid=productDAO.get(id).getCategory().getId();
        productDAO.delete(id);
        return "@admin_product_list?cid="+cid;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
        Product product=productDAO.get(id);
        request.setAttribute("p",product);
        return "admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response) {
        int cid= Integer.parseInt(request.getParameter("cid"));
        int id= Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        String subTitle=request.getParameter("subTitle");
        float orignalPrice= Float.parseFloat(request.getParameter("orignalPrice"));
        float promotePrice= Float.parseFloat(request.getParameter("promotePrice"));
        int stock= Integer.parseInt(request.getParameter("stock"));
        Product product=new Product();
        product.setName(name);
        product.setCategory(categoryDAO.get(cid));
        product.setOrignalPrice(orignalPrice);
        product.setPromotePrice(promotePrice);
        product.setSubTitle(subTitle);
        product.setStock(stock);
        product.setCreateDate( new Date());
        product.setId(id);
        productDAO.update(product);
        return "@admin_product_list?cid="+cid;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response) {
        int cid= Integer.parseInt(request.getParameter("cid"));
        Category category=categoryDAO.get(cid);
        Page page=super.getPage(request);
        int total=productDAO.getTotal(cid);
        List<Product> p=productDAO.list(cid,page.getStart(),page.getCount());
        page.setTotal(total);

        request.setAttribute("page",page);
        request.setAttribute("c",category);
        request.setAttribute("ps",p);

        return "admin/listProduct.jsp";


    }
}
