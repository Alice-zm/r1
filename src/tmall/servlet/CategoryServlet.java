package tmall.servlet;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryServlet extends BaseServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map=new HashMap<>();
        InputStream is=super.parseUpload(request,map);
        Category category=new Category();
        String name=map.get("name");
        category.setName(name);
        categoryDAO.add(category);

        String path1=request.getServletContext().getRealPath("/img/category");
        String path2=category.getId()+".jpg";
        File file=new File(path1,path2);
        file.getParentFile().mkdirs();
        try(FileOutputStream fos=new FileOutputStream(file);)
        {
            byte[] bytes=new byte[1024];
             while(-1!=is.read(bytes)){
            fos.write(bytes,0,bytes.length);
        }
             fos.flush();
            BufferedImage bufferedImage=ImageUtil.change2jpg(file);
            ImageIO.write(bufferedImage,"jpg",file);
        }catch (Exception e){
            e.printStackTrace();
        }

    return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
        Category category=  categoryDAO.get(id);
        request.setAttribute("category",category);
        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> maps=new HashMap<>();
        InputStream is=super.parseUpload(request,maps);
        System.out.println("post提交的参数"+maps);
       int id= Integer.parseInt(maps.get("id"));
        String name=maps.get("name");
        Category category=new Category();
        category.setName(name);
        category.setId(id);
        categoryDAO.update(category);

        try {
            if(is!=null&&is.available()!=0) {String path1=request.getServletContext().getRealPath("/img/category");
             String path2=category.getId()+".jpg";
             File file=new File(path1,path2);
             file.getParentFile().mkdirs();
             try(FileOutputStream fos=new FileOutputStream(file);)
             {
                 byte[] bytes=new byte[1024];
                 while(-1!=is.read(bytes)){
                     fos.write(bytes,0,bytes.length);
                 }
                 fos.flush();
                 BufferedImage bufferedImage=ImageUtil.change2jpg(file);
                 ImageIO.write(bufferedImage,"jpg",file);
             }catch (Exception e){
                 e.printStackTrace();
             }}
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response) {
        Page page=super.getPage(request);
        int total=categoryDAO.getTotal();
        page.setTotal(total);
        request.setAttribute("page",page);
        List categorys=categoryDAO.list(page.getStart(),page.getCount());
        request.setAttribute("categorys",categorys);
        return "admin/listCategory.jsp";
    }
}
