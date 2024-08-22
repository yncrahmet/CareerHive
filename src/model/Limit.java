package model;

import java.sql.Timestamp;

public class Limit {

    public Limit(Integer planId, Integer featureId, Integer limit, Timestamp updatedAt, String updatedBy) {
        this.planId = planId;
        this.featureId = featureId;
        this.limit = limit;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    private Integer id;
    private Integer planId;
    private Integer featureId;
    private Integer limit;
    private Timestamp updatedAt;
    private String updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    @Override
    public String toString() {
        return "Limit{" +
                "id=" + id +
                ", planId=" + planId +
                ", featureId=" + featureId +
                ", limit=" + limit +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
