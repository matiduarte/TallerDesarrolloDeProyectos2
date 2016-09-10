package fiuba.tallerdeproyectos2.Rest;

import fiuba.tallerdeproyectos2.Models.SearchResponse;
import fiuba.tallerdeproyectos2.Models.CoursesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("course/search/{search}")
    Call<SearchResponse> getSearchCourses(@Path("search") String search);

    @GET("categories")
    Call<CoursesResponse> getCourses();
}

