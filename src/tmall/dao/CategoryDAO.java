package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public int getTotal(){
        int total=0;
        try(Connection c=DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
        String sql="select count(*) from category ";
        ResultSet rs=s.executeQuery(sql);
        while (rs.next()){
           total=rs.getInt(1);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }
    public void add(Category category){
        String sql="insert into Category values(null,?)";
        try(Connection c= DBUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql);
        ){
            ps.setString(1,category.getName());
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                category.setId(id);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void update(Category category){
        String sql="update category set name=? where id=?";
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)){
        ps.setString(1,category.getName());
        ps.setInt(2,category.getId());
        ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();
            Statement s=c.createStatement();
        ){
            String sql="delete  from category where id="+id;
            s.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Category> list(int start,int count){
        List<Category> lists=new ArrayList<>();
        String sql="select * from category order by id  desc limit ?,? ";
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)
        ){
            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                Category category=new Category();
                category.setId(id);
                category.setName(name);
                lists.add(category);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

       return  lists;
    }

public List<Category> list(){
        return list(0,getTotal());
}


    public Category get(int cid) {
        Category category=null;
        try(Connection c=DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
            String sql="select * from category where id="+cid;
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
            category=new Category();
            category.setId(id);
            category.setName(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return category;
    }
}
