package tmall.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.dao.*;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseServlet extends HttpServlet {
    public abstract String add(HttpServletRequest request, HttpServletResponse response);
    public abstract String delete(HttpServletRequest request, HttpServletResponse response);
    public abstract String edit(HttpServletRequest request, HttpServletResponse response);
    public abstract String update(HttpServletRequest request, HttpServletResponse response);
    public  abstract String list(HttpServletRequest request, HttpServletResponse response);
    CategoryDAO categoryDAO=new CategoryDAO();
    OrderDAO orderDAO=new OrderDAO();
    OrderItemDAO orderItemDAO=new OrderItemDAO();
    ProductDAO productDAO=new ProductDAO();
    ProductImageDAO productImageDAO=new ProductImageDAO();
    PropertyDAO propertyDAO=new PropertyDAO();
    PropertyValueDAO propertyValueDAO=new PropertyValueDAO();
    ReviewDAO reviewDAO=new ReviewDAO();
    UserDAO userDAO=new UserDAO();


      public void service(HttpServletRequest request, HttpServletResponse response){

          String method= (String) request.getAttribute("method");
          try {
            Method  m = this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
              String  s= m.invoke(this,request,response).toString();

              if(s.startsWith("@"))
                  response.sendRedirect( s.substring(1));
              else if(s.startsWith("%"))
                  response.getWriter().print(s.substring(1));
              else
                  request.getRequestDispatcher(s).forward(request,response);

          } catch (IOException e) {
              e.printStackTrace();
          } catch (ServletException e) {
              e.printStackTrace();
          } catch (InvocationTargetException e) {
              e.printStackTrace();
          } catch (NoSuchMethodException e) {
              e.printStackTrace();
          } catch (IllegalAccessException e) {
              e.printStackTrace();
          }

      }
      public InputStream parseUpload(HttpServletRequest request,Map<String,String> maps)  {
          DiskFileItemFactory factory=new DiskFileItemFactory();
          ServletFileUpload upload=new ServletFileUpload(factory);
          InputStream inputStream=null;
          factory.setSizeThreshold(1024*10240);
          List list=null;
          try {
              list = upload.parseRequest(request);
              Iterator iterator=list.iterator();
              while(iterator.hasNext()){
                  FileItem fileItem= (FileItem) iterator.next();
                  if(!fileItem.isFormField()){
                      inputStream =fileItem.getInputStream();
                  }else{
                      String name=new String(fileItem.getFieldName().getBytes("ISO-8859-1"),"UTF-8");
                      String value=new String(fileItem.getString().getBytes("ISO-8859-1"),"UTF-8");
                      maps.put(name,value);
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }

          return inputStream;
      }
      public Page getPage(HttpServletRequest request){
          int start=0;
          int count=5;
          try{start= Integer.parseInt(request.getParameter("page.start"));}catch (Exception e){e.printStackTrace();}
          try{  count= Integer.parseInt(request.getParameter("page.count"));}catch (Exception e){e.printStackTrace();};
          Page page=new Page(start,count);
          return page;
      }

}
