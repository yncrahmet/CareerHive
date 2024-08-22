package repository;

import db.Database;
import model.UserFeatureLimit;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserFeatureLimitRepository {

    public static void createUserFeatureLimitTable() {

        try (Statement statement = Database.getConnection().createStatement()) {

            String query = "DROP SEQUENCE IF EXISTS user_feature_limits_id_seq;" +
                    "CREATE SEQUENCE user_feature_limits_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;" +
                    "CREATE TABLE IF NOT EXISTS user_feature_limits (" +
                    "id INTEGER DEFAULT nextval('user_feature_limits_id_seq') PRIMARY KEY, " +
                    "uuid VARCHAR(128) NOT NULL, " +
                    "feature_id INTEGER NOT NULL, " +
                    "limits INTEGER NOT NULL, " +
                    "updated_at TIMESTAMP NOT NULL, " +
                    "updated_by VARCHAR(128) NOT NULL, " +
                    "user_id INTEGER NOT NULL, " +
                    "CONSTRAINT user_feature_limits_feature_id_fkey FOREIGN KEY (feature_id) REFERENCES features(id) NOT DEFERRABLE, " +
                    "CONSTRAINT user_feature_limits_user_id_fkey FOREIGN KEY (user_id) REFERENCES \"user\"(id) NOT DEFERRABLE " +
                    ")";


            statement.execute(query);

            System.out.println("user_feature_limits table has been successfully created!");

        } catch (Exception e){
            throw new RuntimeException("Error: "+e);
        }

    }

    public UserFeatureLimit save(UserFeatureLimit ufl) {

        String query = "INSERT INTO user_feature_limits (uuid, feature_id, limits, updated_at, updated_by,user_id) VALUES (?, ?, ?, ?, ?,?);";

        try(PreparedStatement statement = Database.getConnection().prepareStatement(query)) {

            statement.setString(1,ufl.getUuid());
            statement.setInt(2,ufl.getFeatureId());
            statement.setInt(3,ufl.getLimits());
            statement.setTimestamp(4, ufl.getUpdatedAt());
            statement.setString(5,ufl.getUpdatedBy());
            statement.setInt(6,ufl.getUserId());

            statement.executeUpdate();

            System.out.println("UserFeatureLimit added successfully");

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

        return ufl;

    }

}