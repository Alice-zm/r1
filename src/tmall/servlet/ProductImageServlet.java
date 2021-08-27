package tmall.servlet;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.ImageUtil;

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

public class ProductImageServlet extends BaseServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response) {
     String type=request.getParameter("type");
        System.out.println(type);
     String type2;
     type2=(type.equals("type_single"))?"Single":"Detail";
        System.out.println(type2);
     int pid= Integer.parseInt(request.getParameter("pid"));
     ProductImage productImage=new ProductImage();
     productImage.setProduct(productDAO.get(pid));
     productImage.setType(type);
     productImageDAO.add(productImage);

     Map<String,String> maps=new HashMap<>();
     InputStream is=parseUpload(request,maps);
     String path=request.getServletContext().getRealPath("/img/product"+type2);
     File file=new File(path,productImage.getId()+".jpg");
        System.out.println(file.getAbsolutePath());
     file.getParentFile().mkdirs();
        try {
            if(is!=null&&is.available()!=0){
                try(FileOutputStream fos=new FileOutputStream(file)){
                    int len;
                    byte[] bytes=new byte[1024*1024];
                    while(( len=is.read(bytes))!=-1){
                        fos.write(bytes,0,len);
                    }
                    fos.flush();
                    BufferedImage bufferedImage= ImageUtil.change2jpg(file);
                    ImageIO.write(bufferedImage,"jpg",file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_productImage_list?pid="+pid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        int id= Integer.parseInt(request.getParameter("id"));
       int pid= productImageDAO.get(id).getProduct().getId();
        productImageDAO.delete(id);
        return "@admin_productImage_list?pid="+pid;
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
        int pid= Integer.parseInt(request.getParameter("pid"));
        Product product=productDAO.get(pid);
        List<tmall.bean.ProductImage> pisSingle= productImageDAO.list(product,"type_single");
        List<tmall.bean.ProductImage> pisDetail= productImageDAO.list(product,"type_detail");
        request.setAttribute("p",product);
        request.setAttribute("pisSingle",pisSingle);
        request.setAttribute("pisDetail",pisDetail);
        return "admin/listProductImage.jsp";
    }
}
