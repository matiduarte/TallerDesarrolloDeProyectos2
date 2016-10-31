package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class Forum {

    @SerializedName("messages")
    private String messages;

    public Forum(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

}
