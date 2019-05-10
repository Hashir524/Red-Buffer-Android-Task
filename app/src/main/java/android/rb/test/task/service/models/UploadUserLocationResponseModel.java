package android.rb.test.task.service.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadUserLocationResponseModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    private final static long serialVersionUID = -1438442965150754898L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UploadUserLocationResponseModel() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param username
     * @param email
     * @param createdAt
     * @param lng
     * @param lat
     */
    public UploadUserLocationResponseModel(Integer id, String username, String email, String lat, String lng, String updatedAt, String createdAt) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}