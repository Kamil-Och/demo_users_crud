package demo_users_crud;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import demo_users_crud.DbConnectionManager;
import demo_users_crud.UserEndpoint;

/**
 * Unit test for simple App.
 */
public class TestUserEndpoint extends UserEndpoint {
    public TestUserEndpoint() {
        this.dbConnectionManager = Mockito.mock(DbConnectionManager.class);
    }
    @Test
    public void testCreateUser() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User created successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "male");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithExistingUser() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);

        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User already exists");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithNoUsername() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "username is required");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithNoAge() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "age is required");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithInvalidAgelessthan0() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", -1);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "age must be between 0 and 150");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithInvalidAgelessmorethan150() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 151);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "age must be between 0 and 150");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithNoGender() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "gender is required");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithInvalidGender() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("age", 20);
        testJsonObject.put("gender", "test");
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "gender must be male, female or other");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithMale() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User created successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "male");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithFemale() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "female");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("female");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User created successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "female");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testCreateUserWithOther() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "other");
        testJsonObject.put("age", 20);
        testJsonObject.put("email", "test@email.com");

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("other");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User created successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "other");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }
    
    @Test
    public void testCreateUserWithNoEmail() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("username", "test");
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);

        String testString = testJsonObject.toString();
       
        String returnString = this.createUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "email is required");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testGetUser() throws SQLException {
        Long testId = 1L;
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.getUsers(testId);
        
        JSONObject testObject = new JSONObject();
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "male");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testGetUserWithNoId() throws SQLException {
        Long testId = null;
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.getUsers(testId);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "id is required");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testGetUserWithInvalidId() throws SQLException {
        Long testId = -1L;
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.getUsers(testId);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "id must be greater than 0");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testGetUserWithUserNotFound() throws SQLException {
        Long testId = 1L;
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.getUsers(testId);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User not found");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUser() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("id", 1);
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User updated successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "male");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUserWithOnlyAge() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("id", 1);
        testJsonObject.put("age", 20);

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User updated successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "male");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUserWithOnlyGender() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("id", 1);
        testJsonObject.put("gender", "male");

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User updated successfully");
        testObject.put("id", 1);
        testObject.put("username", "test");
        testObject.put("age", 20);
        testObject.put("gender", "male");
        testObject.put("email", "test@email.com");
        testObject.put("accountCreationTime", "2021-01-01 00:00:00");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUserWithUserNotFound() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("id", 1);
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false);
        Mockito.when(resultSetMock.getInt("id")).thenReturn(1);
        Mockito.when(resultSetMock.getString("username")).thenReturn("test");
        Mockito.when(resultSetMock.getInt("age")).thenReturn(20);
        Mockito.when(resultSetMock.getString("gender")).thenReturn("male");
        Mockito.when(resultSetMock.getString("email")).thenReturn("test@email.com");
        Mockito.when(resultSetMock.getString("accountCreationTime")).thenReturn("2021-01-01 00:00:00");

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User not found");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUserWithNoId() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "id is required");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUserWithInvalidId() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("id", -1);
        testJsonObject.put("gender", "male");
        testJsonObject.put("age", 20);

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "id must be greater than 0");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testUpdateUserWithNoAgeNoGender() throws SQLException {
        JSONObject testJsonObject = new JSONObject();
        testJsonObject.put("id", 1);

        String testString = testJsonObject.toString();
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        String returnString = this.updateUser(testString);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "age or gender is required");

        assertTrue(returnString.equals(testObject.toString()));
    }


    @Test
    public void testDeleteUser() throws SQLException {
        Long testId = 1L;
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(true);
        String returnString = this.deleteUser(testId);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User deleted successfully");

        assertTrue(returnString.equals(testObject.toString()));
    }

    @Test
    public void testDeleteUserWithUserNotFound() throws SQLException {
        Long testId = 1L;
         
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(this.dbConnectionManager.query(Mockito.anyString())).thenReturn(resultSetMock);

        Mockito.when(resultSetMock.next()).thenReturn(false);
        String returnString = this.deleteUser(testId);
        
        JSONObject testObject = new JSONObject();
        testObject.put("message", "User not found");

        assertTrue(returnString.equals(testObject.toString()));
    }
}
