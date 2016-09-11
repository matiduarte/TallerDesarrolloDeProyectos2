package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class Courses {

    @SerializedName("allCategories")
    private String allCategories;
    @SerializedName("soonCourses")
    private String soonCourses;

    public Courses(String allCategories, String soonCourses) {
        this.allCategories = allCategories;
        this.soonCourses = soonCourses;
    }

    public String getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(String allCategories) {
        this.allCategories = allCategories;
    }

    public String getSoonCourses() {
        return soonCourses;
    }

    public void setSoonCourses(String soonCourses) {
        this.soonCourses = soonCourses;
    }
}
