package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoursesResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private List<Courses> data;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Courses> getData() {
        return data;
    }

    public void setData(List<Courses> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
