package android.rb.test.task.service.models;

import java.util.List;

public class LocationList {

    private List<AllUsersLocationResponse> allUsersLocationResponseList;

    public LocationList() {
    }

    public LocationList(List<AllUsersLocationResponse> allUsersLocationResponseList) {
        this.allUsersLocationResponseList = allUsersLocationResponseList;
    }


    public List<AllUsersLocationResponse> getAllUsersLocationResponseList() {
        return allUsersLocationResponseList;
    }

    public void setAllUsersLocationResponseList(List<AllUsersLocationResponse> allUsersLocationResponseList) {
        this.allUsersLocationResponseList = allUsersLocationResponseList;
    }
}
