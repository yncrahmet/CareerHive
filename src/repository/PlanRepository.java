package repository;

import db.Database;
import model.Plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PlanRepository {

    public static void createPlanTable(){

        try(Statement statement = Database.getConnection().createStatement()){

            String query = "DROP SEQUENCE IF EXISTS plans_id_seq;" +
                    "CREATE SEQUENCE plans_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1;" +
                    "CREATE TABLE IF NOT EXISTS plans (" +
                    "id INTEGER DEFAULT nextval('plans_id_seq') PRIMARY KEY, " +
                    "name VARCHAR(128) NOT NULL, " +
                    "description VARCHAR(128) NOT NULL, " +
                    "uuid VARCHAR(128) NOT NULL, " +
                    "updated_at TIMESTAMP NOT NULL, " +
                    "updated_by VARCHAR(128) NOT NULL" +
                    ")";

            statement.execute(query);

            System.out.println("plans table has been successfully created!");

        } catch (Exception e) {
            throw new RuntimeException("Error: "+e);
        }

    }

    public Plan save(Plan plan) {

        String query = "INSERT INTO plans (name, description, uuid, updated_at, updated_by) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement statement = Database.getConnection().prepareStatement(query)) {

            statement.setString(1,plan.getName());
            statement.setString(2,plan.getDescription());
            statement.setString(3,plan.getUuid());
            statement.setTimestamp(4,plan.getUpdatedAt());
            statement.setString(5,plan.getUpdatedBy());

            statement.executeUpdate();

            System.out.println("Plan saved successfully with name "+plan.getName());

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

        return plan;

    }

    public void deletePlanById(Integer planId) {

        String deleteLimitsQuery = "DELETE FROM limits WHERE plan_id = ?";
        String deleteSubscriptionsQuery = "DELETE FROM user_subscriptions WHERE plan_id = ?";
        String deletePlanQuery = "DELETE FROM plans WHERE id = ?";

        try (Connection connection = Database.getConnection()) {

            try (PreparedStatement deleteLimit = connection.prepareStatement(deleteLimitsQuery);
                 PreparedStatement deleteSubscription = connection.prepareStatement(deleteSubscriptionsQuery);
                 PreparedStatement deletePlan = connection.prepareStatement(deletePlanQuery)) {

                deleteLimit.setInt(1, planId);
                deleteLimit.executeUpdate();

                deleteSubscription.setInt(1, planId);
                deleteSubscription.executeUpdate();

                deletePlan.setInt(1, planId);
                deletePlan.executeUpdate();

                System.out.println("Successfully deleted the plan with id " + planId);

            } catch (SQLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e);
        }

    }

}