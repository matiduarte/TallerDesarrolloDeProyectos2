package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class CourseUnity {  
	
	private int id;  
	private int courseId;
	private String name;
	private String description;
	private String html;
	private String videoUrl;
	private int videoSize;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static CourseUnity getById(int id){
		return (CourseUnity)StoreData.getById(CourseUnity.class, id);
	}
	
	public static List<CourseUnity> getByCourseId(int courseId){
		return (List<CourseUnity>)StoreData.getByField(CourseUnity.class, "courseId", String.valueOf(courseId));
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public void delete(){
		StoreData.delete(this);
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public int getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(int videoSize) {
		this.videoSize = videoSize;
	}
}