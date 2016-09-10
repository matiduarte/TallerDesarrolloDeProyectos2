package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Courses {

    @SerializedName("allCategories")
    private List<String> allCategories = new ArrayList<String>();
    @SerializedName("soonCourses")
    private List<String> soonCourses = new ArrayList<String>();

    public Courses(List<String> allCategories, List<String> soonCourses) {
        this.allCategories = allCategories;
        this.soonCourses = soonCourses;
    }

    public List<String> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<String> allCategories) {
        this.allCategories = allCategories;
    }

    public List<String> getSoonCourses() {
        return soonCourses;
    }

    public void setSoonCourses(List<String> soonCourses) {
        this.soonCourses = soonCourses;
    }
}
