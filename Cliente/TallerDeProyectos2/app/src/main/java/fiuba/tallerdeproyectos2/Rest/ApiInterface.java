package fiuba.tallerdeproyectos2.Rest;

import fiuba.tallerdeproyectos2.Models.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("proyectos2/course/search/{search}")
    Call<ServerResponse> getSearchCourses(@Path("search") String search);

    @GET("proyectos2/categories")
    Call<ServerResponse> getCourses();

    @GET("proyectos2/course/{id}")
    Call<ServerResponse> getCourseDataById(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("proyectos2/student")
    Call<ServerResponse> postStudentData(@Field("email")String email, @Field("firstName")String firstName, @Field("lastName")String lastName, @Field("source")String source);

    @GET("proyectos2/unity/{id}")
    Call<ServerResponse> getUnitDataById(@Path("id") Integer id);
}

