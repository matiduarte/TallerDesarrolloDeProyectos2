package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class CourseData {

    @SerializedName("course")
    private String courseData;

    public CourseData(String courseData) {
        this.courseData = courseData;
    }

    public String getCourseData() {
        return courseData;
    }

    public void setCourseData(String courseData) {
        this.courseData = courseData;
    }
}
