package demo_users_crud;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import java.sql.ResultSet;



@Path("users")
public class UserEndpoint {
    DbConnectionManager dbConnectionManager = new DbConnectionManager();
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject response = new JSONObject();

        if (!jsonObject.has("username")) {
            response.put("message", "username is required");
            return response.toString();
        }

        if (!jsonObject.has("age")) {
            response.put("message", "age is required");
            return response.toString();
        }else if (jsonObject.getInt("age") < 0 || jsonObject.getInt("age") > 150) {
            response.put("message", "age must be between 0 and 150");
            return response.toString();
        }

        if (!jsonObject.has("gender")) {
            response.put("message", "gender is required");
            return response.toString();
        }else if (!Arrays.asList("male", "female", "other").contains(jsonObject.getString("gender"))) {
            response.put("message", "gender must be male, female or other");
            return response.toString();
        }

        if (!jsonObject.has("email")) {
            response.put("message", "email is required");
            return response.toString();
        }

        try {
            dbConnectionManager.connect();

            String sql_validate = QuerySets.selectUserByNameQuery(jsonObject.getString("username"));

            dbConnectionManager.query(sql_validate);
            ResultSet resultSet = dbConnectionManager.query(sql_validate);

            if (resultSet.next()) {
                response.put("message", "User already exists");
                dbConnectionManager.close();
                return response.toString();
            }

            String sql = QuerySets.insertUserQuery(jsonObject.getString("username"), 
                                                    jsonObject.getInt("age"), 
                                                    jsonObject.getString("gender"),
                                                    jsonObject.getString("email"));

            dbConnectionManager.update(sql);
            response.put("message", "User created successfully");

            String response_sql = QuerySets.selectUserByNameQuery(jsonObject.getString("username"));
            ResultSet response_resultSet = dbConnectionManager.query(response_sql);

            if(response_resultSet.next()){
                response.put("id", response_resultSet.getInt("id"));
                response.put("username", response_resultSet.getString("username"));
                response.put("age", response_resultSet.getInt("age"));
                response.put("gender", response_resultSet.getString("gender"));
                response.put("email", response_resultSet.getString("email"));
                response.put("accountCreationTime", response_resultSet.getString("accountCreationTime"));
            }


            dbConnectionManager.close();
        }catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Something went wrong. User creation failed");
            return response.toString();
        }
       
        return response.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers(@QueryParam("id") Long id) {
        JSONObject response = new JSONObject();
        
        if(id == null) {
            response.put("message", "id is required");
            return response.toString();
        } else if (id < 0) {
            response.put("message", "id must be greater than 0");
            return response.toString();
        }

        try {
            dbConnectionManager.connect();

            String sql = QuerySets.selectUserByIdQuery(id);
            ResultSet resultSet = dbConnectionManager.query(sql);

            if(resultSet.next()){
                response.put("id", resultSet.getInt("id"));
                response.put("username", resultSet.getString("username"));
                response.put("age", resultSet.getInt("age"));
                response.put("gender", resultSet.getString("gender"));
                response.put("email", resultSet.getString("email"));
                response.put("accountCreationTime", resultSet.getString("accountCreationTime"));
            }else{
                response.put("message", "User not found");
            }
            
            dbConnectionManager.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Something went wrong");
        }

        return response.toString();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject response = new JSONObject();

        if(!jsonObject.has("id")) {
            response.put("message", "id is required");
            return response.toString();
        } else if (jsonObject.getLong("id") < 0) {
            response.put("message", "id must be greater than 0");
            return response.toString();
        }

        if(!jsonObject.has("age") && !jsonObject.has("gender")) {
            response.put("message", "age or gender is required");
            return response.toString();
        }

        if(jsonObject.has("age") && (jsonObject.getInt("age") < 0 || jsonObject.getInt("age") > 150)) {
            response.put("message", "age must be between 0 and 150");
            return response.toString();
        }

        if (jsonObject.has("gender") && (!Arrays.asList("male", "female", "other").contains(jsonObject.getString("gender"))) ) {
            response.put("message", "gender must be male, female or other");
            return response.toString();
        }

        try {
            dbConnectionManager.connect();

            String sql_verify = QuerySets.selectUserByIdQuery(jsonObject.getLong("id"));
            ResultSet resultSet_verify = dbConnectionManager.query(sql_verify);
            if (!resultSet_verify.next()) {
                response.put("message", "User not found");
                dbConnectionManager.close();
                return response.toString();
            }
            
            String sql = "";

            if(jsonObject.has("age")) {
                if(jsonObject.has("gender")) {
                    sql = QuerySets.updateUserQuery(jsonObject.getLong("id"), jsonObject.getInt("age"), jsonObject.getString("gender"));
                }else {
                    sql = QuerySets.updateUserQuery(jsonObject.getLong("id"), jsonObject.getInt("age"));
                }
            }else {
                sql = QuerySets.updateUserQuery(jsonObject.getLong("id"), jsonObject.getString("gender"));
            }

            dbConnectionManager.update(sql);

            response.put("message", "User updated successfully");

            String sql_get = QuerySets.selectUserByIdQuery(jsonObject.getLong("id"));
            ResultSet resultSet = dbConnectionManager.query(sql_get);

            if(resultSet.next()){
                response.put("id", resultSet.getInt("id"));
                response.put("username", resultSet.getString("username"));
                response.put("age", resultSet.getInt("age"));
                response.put("gender", resultSet.getString("gender"));
                response.put("email", resultSet.getString("email"));
                response.put("accountCreationTime", resultSet.getString("accountCreationTime"));
            }

            dbConnectionManager.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Something went wrong");
        }

        return response.toString();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@QueryParam("id") Long id){
        if (id == null) {
            JSONObject response = new JSONObject();
            response.put("message", "id is required");
            return response.toString();
        } else if (id < 0) {
            JSONObject response = new JSONObject();
            response.put("message", "id must be greater than 0");
            return response.toString();
        }

        JSONObject response = new JSONObject();

        try {
            dbConnectionManager.connect();

            String sql_verify = QuerySets.selectUserByIdQuery(id);
            ResultSet resultSet_verify = dbConnectionManager.query(sql_verify);
            if (!resultSet_verify.next()) {
                response.put("message", "User not found");
                dbConnectionManager.close();
                return response.toString();
            }

            String sql = QuerySets.deleteUserQuery(id);
            dbConnectionManager.update(sql);

            response.put("message", "User deleted successfully");

            dbConnectionManager.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Something went wrong");
        }

        return response.toString();
    }
    

}

