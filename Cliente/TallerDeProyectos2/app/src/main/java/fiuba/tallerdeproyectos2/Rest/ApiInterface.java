package fiuba.tallerdeproyectos2.Rest;

import fiuba.tallerdeproyectos2.Models.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("proyectos2/course/search/{search}")
    Call<ServerResponse> getSearchCourses(@Path("search") String search);

    @GET("proyectos2/categories/student/{studentId}")
    Call<ServerResponse> getCourses(@Path("studentId") Integer studentId);

    @GET("proyectos2/course/{id}")
    Call<ServerResponse> getCourseDataById(@Path("id") Integer id, @Query("studentId") Integer studentId);

    @FormUrlEncoded
    @POST("proyectos2/student")
    Call<ServerResponse> postStudentData(@Field("email")String email, @Field("firstName")String firstName, @Field("lastName")String lastName, @Field("source")String source);

    @FormUrlEncoded
    @POST("proyectos2/student/subscribe")
    Call<ServerResponse> postStudentSubscribe(@Field("studentId")Integer studentId, @Field("sessionId")Integer sessionId);

    @FormUrlEncoded
    @POST("proyectos2/student/unsubscribe")
    Call<ServerResponse> postStudentUnsubscribe(@Field("studentId")Integer studentId, @Field("sessionId")Integer sessionId);

    @GET("proyectos2/student/subscriptions/{studentId}")
    Call<ServerResponse> getSubscriptions(@Path("studentId") Integer studentId);

    @GET("proyectos2/unity/{id}")
    Call<ServerResponse> getUnitDataById(@Path("id") Integer id, @Query("studentId") Integer studentId);

    @GET("proyectos2/unity/test/{id}")
    Call<ServerResponse> getUnitExam(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("proyectos2/exam")
    Call<ServerResponse> postPassExam(@Field("studentId")Integer studentId, @Field("sessionId")Integer sessionId, @Field("unityId")Integer unityId, @Field("result")Float result);

    @FormUrlEncoded
    @POST("proyectos2/forum")
    Call<ServerResponse> postForumMessage(@Field("studentId")Integer studentId, @Field("sessionId")Integer sessionId, @Field("message")String message);

    @GET("proyectos2/forum/{sessionId}")
    Call<ServerResponse> getForum(@Path("sessionId") Integer sessionId);

    @FormUrlEncoded
    @POST("proyectos2/comment")
    Call<ServerResponse> postCourseComments(@Field("studentId")Integer studentId, @Field("courseId")Integer courseId, @Field("message")String message);

    @FormUrlEncoded
    @POST("proyectos2/calification")
    Call<ServerResponse> postCourseCalifications(@Field("studentId")Integer studentId, @Field("courseId")Integer courseId, @Field("calification")Integer calification);

    @GET("proyectos2/certification/{userId}")
    Call<ServerResponse> getCertificatesByUser(@Path("userId") Integer userId);
}

