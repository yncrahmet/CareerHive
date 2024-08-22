package repository;

import db.Database;
import model.Limit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class LimitRepository {

    public static void createLimitTable() {

        try (Statement statement = Database.getConnection().createStatement()) {

            String query = "DROP SEQUENCE IF EXISTS limits_id_seq;" +
                    "CREATE SEQUENCE limits_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;" +
                    "CREATE TABLE IF NOT EXISTS limits (" +
                    "id INTEGER DEFAULT nextval('limits_id_seq') PRIMARY KEY," +
                    "plan_id INTEGER NOT NULL," +
                    "feature_id INTEGER NOT NULL," +
                    "\"limit\" INTEGER NOT NULL," +
                    "updated_at TIMESTAMP NOT NULL," +
                    "updated_by VARCHAR(128) NOT NULL," +
                    "CONSTRAINT limits_feature_id_fkey FOREIGN KEY (feature_id) REFERENCES features(id) NOT DEFERRABLE," +
                    "CONSTRAINT limits_plan_id_fkey FOREIGN KEY (plan_id) REFERENCES plans(id) NOT DEFERRABLE" +
                    ")";

            statement.execute(query);

            System.out.println("limits table has been successfully created!");

        }catch (Exception e){
            throw new RuntimeException("Eror: "+e);
        }

    }

    public Limit save(Limit limit) {

        String query = "INSERT INTO limits (plan_id, feature_id, \"limit\", updated_at, updated_by) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement statement = Database.getConnection().prepareStatement(query)) {

            statement.setInt(1,limit.getPlanId());
            statement.setInt(2,limit.getFeatureId());
            statement.setInt(3,limit.getLimit());
            statement.setTimestamp(4,limit.getUpdatedAt());
            statement.setString(5,limit.getUpdatedBy());

            statement.executeUpdate();

            System.out.println("Limit added successfully");

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

        return limit;

    }

    public void updatePlanLimitForFeature(Integer newLimit, Integer planId, Integer featureId){

        String query = "UPDATE limits " +
                "SET \"limit\" = ? " +
                "WHERE plan_id = ? AND feature_id = ?;";

        try(Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1,newLimit);
            preparedStatement.setInt(2,planId);
            preparedStatement.setInt(3,featureId);

            preparedStatement.executeUpdate();

            System.out.println("Succesfully updated!");

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

    }

}