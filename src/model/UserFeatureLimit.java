package model;

import java.sql.Timestamp;

public class UserFeatureLimit {

    public UserFeatureLimit(String uuid, Integer featureId, Integer limits, Timestamp updatedAt, String updatedBy, Integer userId) {
        this.featureId = featureId;
        this.uuid = uuid;
        this.limits = limits;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.userId = userId;
    }

    private Integer id;
    private String uuid;
    private Integer featureId;
    private Integer limits;
    private Timestamp updatedAt;
    private String updatedBy;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getLimits() {
        return limits;
    }

    public void setLimits(Integer limits) {
        this.limits = limits;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserFeatureLimit{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", featureId=" + featureId +
                ", limits=" + limits +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", userId=" + userId +
                '}';
    }
}