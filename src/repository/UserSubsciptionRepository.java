package repository;

import db.Database;
import model.UserSubscription;

import java.sql.*;

public class UserSubsciptionRepository {

    public static void createUserFeatureLimitTable() {

        try (Statement statement = Database.getConnection().createStatement()) {

            String query = "DROP SEQUENCE IF EXISTS user_subscription_id_seq;" +
                    "CREATE SEQUENCE user_subscription_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;" +
                    "CREATE TABLE IF NOT EXISTS user_subscriptions (" +
                    "id INTEGER DEFAULT nextval('user_subscription_id_seq') PRIMARY KEY, " +
                    "uuid VARCHAR(128) NOT NULL, " +
                    "user_id INTEGER NOT NULL, " +
                    "plan_id INTEGER NOT NULL, " +
                    "start_date TIMESTAMP NOT NULL, " +
                    "end_date TIMESTAMP NOT NULL, " +
                    "renewal_date TIMESTAMP NOT NULL, " +
                    "status VARCHAR(128) NOT NULL," +
                    "updated_at TIMESTAMP NOT NULL, " +
                    "updated_by VARCHAR(128) NOT NULL, " +
                    "CONSTRAINT user_subscriptions_user_id_fkey FOREIGN KEY (user_id) REFERENCES \"user\"(id) NOT DEFERRABLE, " +
                    "CONSTRAINT user_subscriptions_plan_id_fkey FOREIGN KEY (plan_id) REFERENCES plans(id) NOT DEFERRABLE " +
                    ")";


            statement.execute(query);

            System.out.println("user_subscriptions table has been successfully created!");

        } catch (Exception e){
            throw new RuntimeException("Error: "+e);
        }

    }

    public UserSubscription save(UserSubscription userSubscription) {

        String query = "INSERT INTO user_subscriptions (uuid, user_id, plan_id, start_date, end_date,renewal_date, status, updated_at, updated_by) VALUES (?, ?, ?, ?, ?,?,?,?,?);";

        try(PreparedStatement statement = Database.getConnection().prepareStatement(query)) {

            statement.setString(1, userSubscription.getUuid());
            statement.setInt(2,userSubscription.getUserId());
            statement.setInt(3,userSubscription.getPlanId());
            statement.setTimestamp(4,userSubscription.getStartDate());
            statement.setTimestamp(5,userSubscription.getEndDate());
            statement.setTimestamp(6,userSubscription.getRenewalDate());
            statement.setString(7,userSubscription.getStatus());
            statement.setTimestamp(8,userSubscription.getUpdatedAt());
            statement.setString(9,userSubscription.getUpdatedBy());

            statement.executeUpdate();

            System.out.println("UserSubscription added successfully");

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

        return userSubscription;

    }

    public void findNotActiveSubs() {

        String query = "SELECT \"user\".name, \"user\".email, user_subscriptions.plan_id, user_subscriptions.status " +
                "FROM \"user\" " +
                "INNER JOIN user_subscriptions ON \"user\".id = user_subscriptions.user_id " +
                "WHERE user_subscriptions.status = 'Deactive';";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                    System.out.println("Subscriber's Name: " + resultSet.getString("name"));
                    System.out.println("Subscriber's Email: " + resultSet.getString("email"));
                    System.out.println("Subscriber's Plan: " + resultSet.getInt("plan_id"));
                    System.out.println("Subscription Status: "+ resultSet.getString("status"));
                    System.out.println("-------------------------------------------------------------");

            }

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

    }

    public void findSubscriberLimit(Integer userSubsId) {

        String query = "SELECT user_subscriptions.id AS subscription_id, features.id AS feature_id, " +
                "COALESCE(SUM(limits.limit), 0) + COALESCE(SUM(ufl.limits), 0) AS total_limit " +
                "FROM user_subscriptions " +
                "INNER JOIN limits ON user_subscriptions.plan_id = limits.plan_id " +
                "INNER JOIN features ON limits.feature_id = features.id " +
                "LEFT JOIN user_feature_limits ufl ON ufl.feature_id = limits.feature_id AND ufl.user_id = user_subscriptions.user_id " +
                "WHERE user_subscriptions.id = ? " +
                "GROUP BY user_subscriptions.id, features.id;";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userSubsId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                System.out.println("Subscriber id: " + resultSet.getInt("subscription_id"));
                System.out.println("Feature id: " + resultSet.getInt("feature_id"));
                System.out.println("Total limit: " + resultSet.getInt("total_limit"));
                System.out.println("-------------------------------------------------------------");

            }

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e);
        }

    }

    public void findSubsWithBronzePlanFrom2024() {

        String query = "SELECT user_subscriptions.id AS subscription_id, plans.id AS plan_id, \"user\".name AS user_name, " +
                "user_subscriptions.start_date AS subscription_date " +
                "FROM user_subscriptions " +
                "INNER JOIN \"user\" ON user_subscriptions.user_id = \"user\".id " +
                "INNER JOIN plans ON user_subscriptions.plan_id = plans.id " +
                "WHERE EXTRACT(YEAR FROM start_date) = 2024 AND plans.name = 'bronze';";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                System.out.println("Subscriber id: " + resultSet.getInt("subscription_id"));
                System.out.println("Plan id: " + resultSet.getInt("plan_id"));
                System.out.println("User's name: " + resultSet.getString("user_name"));
                System.out.println("Subscription date: " + resultSet.getString("subscription_date"));
                System.out.println("-------------------------------------------------------------");

            }

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

    }

}