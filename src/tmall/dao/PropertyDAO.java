package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {
    public int getTotal(int cid){
        int total=0;
        try(Connection c= DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
            String sql="select count(*) from Property where cid="+cid;
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                total=rs.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return total;
    }
    public void add(Property bean){
        String sql="insert into Property values(null,?,?)";
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql);
        ){
            ps.setInt(1,bean.getCategory().getId());
            ps.setString(2,bean.getName());
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                int id=rs.getInt(1);
                bean.setId(id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void update(Property bean){
        String sql="update property set cid=?,name=? where id=?";
        System.out.println(sql);
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql);
        ){
            ps.setInt(1,bean.getCategory().getId());
            ps.setString(2,bean.getName());
            ps.setInt(3,bean.getId());
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(int id){
        try(Connection c=DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
            String sql="delete  from Property where id="+id;
           s.execute(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Property get(int id){
        Property bean=new Property();
        bean.setId(id);
        try(Connection c=DBUtil.getConnection();
        Statement s=c.createStatement();
        ){
            String sql="select * from property where id="+id;
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                int cid=rs.getInt("cid");
                String name=rs.getString("name");
                Category category=new CategoryDAO().get(cid);
                bean.setName(name);
                bean.setCategory(category);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;
    }
    public PropertyValue get(int ptid, int pid ) {
        PropertyValue bean = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from PropertyValue where ptid = " + ptid + " and pid = " + pid;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                bean= new PropertyValue();
                int id = rs.getInt("id");

                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }
    public List<Property> list(int cid) {
        return list(cid, 0, getTotal(cid));
    }
    public List<Property> list(int cid,int start,int count){
        List<Property> beans=new ArrayList<>();
        String sql="select * from Property where cid=? order by id desc limit ?,?";
        try(Connection c=DBUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql);
        ){
            ps.setInt(1,cid);
            ps.setInt(2,start);
            ps.setInt(3,count);
          ResultSet rs=ps.executeQuery();
          while(rs.next()){
              Property bean=new Property();
              int id=rs.getInt(1);
              String name=rs.getString("name");
              Category category=new CategoryDAO().get(cid);
              bean.setCategory(category);
              bean.setName(name);
              bean.setId(id);
              beans.add(bean);
          }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beans;
    }

}
