package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private String data;
    @SerializedName("message")
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
