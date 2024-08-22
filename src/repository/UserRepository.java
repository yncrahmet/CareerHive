package repository;

import db.Database;
import model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserRepository {

    public static void createUserTable(){

        try(Statement statement = Database.getConnection().createStatement()){

            String query = "DROP SEQUENCE IF EXISTS user_id_seq;"+
                    "CREATE SEQUENCE user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;" +
                    "CREATE TABLE IF NOT EXISTS \"user\" (" +
                    "id INTEGER DEFAULT nextval('user_id_seq') PRIMARY KEY," +
                    "name VARCHAR(128) NOT NULL, " +
                    "uuid VARCHAR(128) NOT NULL, " +
                    "email VARCHAR(128) NOT NULL, " +
                    "updated_at TIMESTAMP NOT NULL" +
                    ")";

            statement.execute(query);

            System.out.println("users table has been successfully created!");

        } catch (Exception e) {
            throw new RuntimeException("Error: "+e);
        }

    }

    public User save(User user) {

        String query = "INSERT INTO \"user\" (name, uuid, email, updated_at) VALUES (?, ?, ?, ?);";

        try(PreparedStatement statement = Database.getConnection().prepareStatement(query)) {

            statement.setString(1,user.getName());
            statement.setString(2, user.getUuid());
            statement.setString(3,user.getEmail());
            statement.setTimestamp(4,user.getUpdatedAt());

            statement.executeUpdate();

            System.out.println("User saved successfully with name "+user.getName());

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

        return user;

    }

}