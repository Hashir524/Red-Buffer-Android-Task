package android.rb.test.task.service.remote;

public class ApiUtils {

    private ApiUtils() {
    }

    public static final String BASE_URL = "http://54.82.176.74:3031";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
