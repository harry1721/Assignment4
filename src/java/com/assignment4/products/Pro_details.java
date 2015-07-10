/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment4.products;

import java.io.PrintWriter;
import java.io.StringReader;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@Path("products")
public class Pro_details{
    mycon con=new mycon();
    Connection conn=null;
    @Context
    private UriInfo context;
    
    
    
    
    
    
    
    /**
     * Creates a new instance of ProductResource
     */
    public Pro_details() {
       //conn=con.getConnection();
    }

    /**
     * Retrieves representation of an instance of com.oracle.products.ProductResource
     * @return an instance of java.lang.String
     */
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public String getAllProducts() throws SQLException
   {
       if(conn==null)
       {
           return "not connected";
       }
       else {
           String q="Select * from products";
           PreparedStatement ps = conn.prepareStatement(q);
           ResultSet rs = ps.executeQuery();
           String r="";
           JSONArray proArr = new JSONArray();
           while (rs.next()) {
                Map pm = new LinkedHashMap();
                pm.put("productID", rs.getInt("product_id"));
                pm.put("name", rs.getString("name"));
                pm.put("description", rs.getString("description"));
                pm.put("quantity", rs.getInt("quantity"));
                proArr.add(pm);
           }
            r = proArr.toString();
          return  r.replace("},", "},\n");
        }
       
   }
   
   @GET
   @Path("{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public String getproduct(@PathParam("id") int id) throws SQLException {
   
      if(conn==null)
       {
           return "not connected";
       }
       else {
           String q="Select * from products where product_id = ?";
           PreparedStatement ps = conn.prepareStatement(q);
           ps.setInt(1,id);
           ResultSet rs = ps.executeQuery();
           String result="";
           JSONArray proArr = new JSONArray();
           while (rs.next()) {
                 Map pm = new LinkedHashMap();
                pm.put("productID", rs.getInt("product_id"));
                pm.put("name", rs.getString("name"));
                pm.put("description", rs.getString("description"));
                pm.put("quantity", rs.getInt("quantity"));
                proArr.add(pm);
           }    
                result = proArr.toString();
                
                 return result;
      }
   
   }
   
   @POST
   @Path("/products")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN)
   public String postProduct(String str) throws SQLException{
       JsonParser p= Json.createParser(new StringReader(str));
       Map<String,String> pm = new LinkedHashMap<String,String>();
       String key="";
       String val="";
       
       while(p.hasNext()){
        JsonParser.Event event=p.next();
            switch (event){
            case KEY_NAME :
              key = p.getString();
              break;
            case VALUE_STRING:
              val=p.getString();
              pm.put(key, val);
              break;
            case VALUE_NUMBER:     
              val=p.getString();
              pm.put(key, val);
              break;  
            default :
              break;  
            }
       }    
       if(conn == null){
           return "Not connected";
       }
       else {
            String q="INSERT INTO product (product_id,name,description,quantity) VALUES (?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(q);
            ps.setInt(1, Integer.parseInt(pm.get("product_id")));
            ps.setString(2, pm.get("name"));
            ps.setString(3, pm.get("description"));
            ps.setInt(4, Integer.parseInt(pm.get("quantity")));
            ps.executeUpdate();
            return "row has been inserted into the database";
           }
       
       
   }
   
   
   @PUT
   @Path("{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public String  putProduct(@PathParam("id")  int id,String str) throws SQLException{
    JsonParser p= Json.createParser(new StringReader(str));
       Map<String,String> pm = new LinkedHashMap<String,String>();
       String key="";
       String val="";
       
       while(p.hasNext()){
        JsonParser.Event event=p.next();
            switch (event){
            case KEY_NAME :
              key = p.getString();
              break;
            case VALUE_STRING:
              val=p.getString();
              pm.put(key, val);
              break;
            case VALUE_NUMBER:     
              val=p.getString();
              pm.put(key, val);
              break;  
            default :
              break;  
            }
       }    
       if(conn == null){
           return "Not connected";
       }
       else {
             String q="UPDATE product SET  name = ?, description = ?, quantity = ? WHERE product_id =?" ;          
             PreparedStatement pstmt=conn.prepareStatement(q);
            pstmt.setString(1, pm.get("name"));
            pstmt.setString(2, pm.get("description"));
            pstmt.setInt(3, Integer.parseInt(pm.get("quantity")));
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            return "row has been updated into the database";
           }
   
   }
 
   @DELETE
   @Path("{id}")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.TEXT_PLAIN)
   public String deleteProduct(@PathParam("id") int id) throws SQLException{
       
        if(conn==null)
        {
           return "not connected";
        }
        else {
           String q="DELETE FROM products WHERE product_id = ?";
           PreparedStatement ps = conn.prepareStatement(q);
           ps.setInt(1,id);
           ps.executeUpdate();
           return "The specified row is deleted";
           
        }
   
    }
}
