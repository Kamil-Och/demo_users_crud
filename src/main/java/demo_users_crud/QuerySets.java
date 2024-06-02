package demo_users_crud;

public class QuerySets {

    public static String selectUserByNameQuery(String username) {
        return "SELECT * FROM public.users WHERE username = '" + username + "';";
    }

    public static String insertUserQuery(String username, int age, String gender, String email) {
        String sql = "INSERT INTO public.users (username, age, gender, email) VALUES ";
        sql = sql + "('" + username;
        sql = sql + "', '" + age; 
        sql = sql + "', '" + gender; 
        sql = sql + "', '" + email + "');";
        return sql;
    }

    public static String selectUserByIdQuery(Long id) {
        return "SELECT * FROM public.users WHERE id = " + id + ";";
    }

    public static String updateUserQuery(Long id, int age, String gender){
        return "UPDATE public.users SET age = " + age + ", gender = '" + gender + "' WHERE id = " + id + ";";
    }

    public static String updateUserQuery(Long id, int age){
        return "UPDATE public.users SET age = " + age + " WHERE id = " + id + ";";
    }

    public static String updateUserQuery(Long id, String gender){
        return "UPDATE public.users SET gender = '" + gender + "' WHERE id = " + id + ";";
    }

    public static String deleteUserQuery(Long id){
        return "DELETE FROM public.users WHERE id = " + id + ";";
    }

}
