package tmall.servlet;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.DateUtil;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class OrderServlet extends BaseServlet {
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
      int total=orderDAO.getTotal();
      page.setTotal(total);

        List<Order> os=orderDAO.list(page.getStart(),page.getCount());
        orderItemDAO.fill(os);
        request.setAttribute("os",os);
        request.setAttribute("page",page);
        return "admin/listOrder.jsp";
    }

    public String  delivery(HttpServletRequest request,HttpServletResponse response){
        int id= Integer.parseInt(request.getParameter("id"));
        Order order=orderDAO.get(id);
        String status= OrderDAO.waitConfirm;
        order.setStatus(status);
        order.setDeliveryDate(DateUtil.d2t(new Date()));
        orderDAO.update(order);
        return "@admin_order_list";
    }
}
