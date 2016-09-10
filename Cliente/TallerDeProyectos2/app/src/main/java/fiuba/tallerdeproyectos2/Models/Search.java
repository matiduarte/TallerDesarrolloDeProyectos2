package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Search {

    @SerializedName("courses")
    private List<String> coursesData = new ArrayList<String>();

    public Search(List<String> coursesData) {
        this.coursesData = coursesData;
    }

    public List<String> getCoursesData() {
        return coursesData;
    }

    public void setCoursesData(List<String> coursesData) {
        this.coursesData = coursesData;
    }
}

