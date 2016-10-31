package fiuba.tallerdeproyectos2.Models;

public class CourseChatCardViewData {

    private String name;
    private String surname;
    private String time;
    private String message;

    public CourseChatCardViewData(String name, String surname, String time, String message){
        this.name = name;
        this.surname = surname;
        this.time = time;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
