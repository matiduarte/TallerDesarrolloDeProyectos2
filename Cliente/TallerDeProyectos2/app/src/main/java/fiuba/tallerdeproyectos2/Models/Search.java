package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("courses")
    private String coursesData;

    public Search(String coursesData) {
        this.coursesData = coursesData;
    }

    public String getCoursesData() {
        return coursesData;
    }

    public void setCoursesData(String coursesData) {
        this.coursesData = coursesData;
    }
}

