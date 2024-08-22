package model;

import java.sql.Timestamp;

public class User {

    public User(String name, String uuid, String email, Timestamp updatedAt) {
        this.name = name;
        this.uuid = uuid;
        this.email = email;
        this.updatedAt = updatedAt;
    }

    private Integer id;
    private String name;
    private String uuid;
    private String email;
    private Timestamp updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
