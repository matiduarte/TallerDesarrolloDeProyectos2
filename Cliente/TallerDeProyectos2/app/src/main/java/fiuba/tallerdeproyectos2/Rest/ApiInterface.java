package fiuba.tallerdeproyectos2.Rest;

import fiuba.tallerdeproyectos2.Models.ServerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("course/search/{search}")
    Call<ServerResponse> getSearchCourses(@Path("search") String search);

    @GET("categories")
    Call<ServerResponse> getCourses();
}

