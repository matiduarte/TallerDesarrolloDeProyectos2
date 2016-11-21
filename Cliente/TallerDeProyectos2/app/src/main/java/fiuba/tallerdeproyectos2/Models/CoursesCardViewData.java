package fiuba.tallerdeproyectos2.Models;

public class CoursesCardViewData {

    private String courseTitle;
    private String courseImageUrl;
    private String courseId;
    private String courseCalification;

    public CoursesCardViewData(String courseTitle, String courseImageUrl, String courseId, String courseCalification){
        this.courseTitle = courseTitle;
        this.courseImageUrl = courseImageUrl;
        this.courseId = courseId;
        this.courseCalification = courseCalification;
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

    public String getCourseCalification() {
        return courseCalification;
    }

    public void setCourseCalification(String courseCalification) {
        this.courseCalification = courseCalification;
    }
}
