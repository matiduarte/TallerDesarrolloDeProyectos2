package fiuba.tallerdeproyectos2.Models;

public class CoursesCardViewData {

    private String courseTitle;
    private String courseImageUrl;
    private String courseId;

    public CoursesCardViewData(String courseTitle, String courseImageUrl, String courseId){
        this.courseTitle = courseTitle;
        this.courseImageUrl = courseImageUrl;
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImage(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
