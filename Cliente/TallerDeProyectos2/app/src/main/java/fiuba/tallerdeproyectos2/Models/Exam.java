package fiuba.tallerdeproyectos2.Models;


import com.google.gson.annotations.SerializedName;

public class Exam {

    @SerializedName("questions")
    private String questions;

    public Exam(String questions) {
        this.questions = questions;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String allCategories) {
        this.questions = questions;
    }

}
