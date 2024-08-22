package repository;

import db.Database;
import model.Feature;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class FeatureRepository {

    public static void createFeatureTable(){

        try(Statement statement = Database.getConnection().createStatement()) {

            String query = "DROP SEQUENCE IF EXISTS features_id_seq;" +
                    "CREATE SEQUENCE features_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;" +
                    "CREATE TABLE IF NOT EXISTS features (" +
                    "id INTEGER DEFAULT nextval('features_id_seq') PRIMARY KEY, " +
                    "name VARCHAR(128) NOT NULL," +
                    "description VARCHAR(128) NOT NULL," +
                    "updated_at TIMESTAMP NOT NULL," +
                    "updated_by VARCHAR(128) NOT NULL," +
                    "uuid VARCHAR(128) NOT NULL" +
                    ")";

            statement.execute(query);

            System.out.println("features table has been successfully created!");

        } catch (Exception e) {
            throw new RuntimeException("Error: "+e);
        }

    }

    public Feature save(Feature feature) {

        String query = "INSERT INTO features (name, description, updated_at, updated_by, uuid) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement statement = Database.getConnection().prepareStatement(query)) {

            statement.setString(1,feature.getName());
            statement.setString(2,feature.getDescription());
            statement.setTimestamp(3,feature.getUpdatedAt());
            statement.setString(4,feature.getUpdatedBy());
            statement.setString(5,feature.getUuid());

            statement.executeUpdate();

            System.out.println("Feature saved successfully with name "+feature.getName());

        }catch (Exception e){
            throw new RuntimeException("Error: "+ e);
        }

        return feature;

    }

}