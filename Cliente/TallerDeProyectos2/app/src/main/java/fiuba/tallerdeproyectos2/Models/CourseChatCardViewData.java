package fiuba.tallerdeproyectos2.Models;

public class CourseChatCardViewData {

    private String name;
    private String surname;
    private String time;
    private String message;
    private String date;

    public CourseChatCardViewData(String name, String surname, String time, String message, String date){
        this.name = name;
        this.surname = surname;
        this.time = time;
        this.message = message;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
