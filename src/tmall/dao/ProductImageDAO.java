package tmall.dao;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {
    public static final String type_single="type_single";
    public static final String type_detail="type_detail";
    public int getTotal(){
        int total=0;
        try(Connection c= DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
            String sql="select count(*) from ProductImage";
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                total=rs.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return total;
    }
    public void add(ProductImage bean){
        String sql="insert into ProductImage values(null,?,?)";
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql);
        ){
            ps.setInt(1,bean.getProduct().getId());
            ps.setString(2,bean.getType());
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            while(rs.next()){
                int id=rs.getInt(1);
                bean.setId(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update(ProductImage bean){

    }
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
            String sql="delete from ProductImage where id="+id;
            s.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public ProductImage get(int id){
        ProductImage bean=new ProductImage();
        try(Connection c=DBUtil.getConnection();
         Statement s=c.createStatement();
        ){
            String sql="select * from ProductImage where id="+id;
            ResultSet rs=s.executeQuery(sql);
        while(rs.next()){
            int pid=rs.getInt("pid");
            String type=rs.getString("type");
            Product product=new ProductDAO().get(pid);
            bean.setId(id);
            bean.setProduct(product);
            bean.setType(type);
        }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return bean;
    }
    public List<ProductImage> list(Product product,String type){
        return list(product,type,0,getTotal());
    }
    public List<ProductImage> list(Product p,String type,int start,int count){
    List<ProductImage> beans=new ArrayList<>();
    String sql="select * from ProductImage where pid=? and type=? order by id desc limit ?,?";
    try(Connection c=DBUtil.getConnection();
    PreparedStatement ps=c.prepareStatement(sql);
    ){
        ps.setInt(1,p.getId());
        ps.setString(2,type);
        ps.setInt(3,start);
        ps.setInt(4,count);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            ProductImage bean=new ProductImage();
            int id=rs.getInt(1);
            bean.setType(type);
            bean.setProduct(p);
            bean.setId(id);
            beans.add(bean);
        }
    }catch(SQLException e){
        e.printStackTrace();
    }
    return beans;
    }
}
