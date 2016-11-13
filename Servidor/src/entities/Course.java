package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class Course {  
	
	private int id;  
	private String description;
	private String name;
	private String pictureUrl;
	private Integer teacherId;
	private String teacherName;
	private boolean isSubscribed;
	
	private ArrayList<CourseSession> courseSessions;
	private ArrayList<CourseUnity> courseUnities;
	private ArrayList<CourseCalification> califications;
	private ArrayList<CourseComment> comments;
	
	private boolean isFinalExamAvailable;
	private boolean passFinalExam;
	private float finalExamResult;
	
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public ArrayList<CourseSession> getCourseSessions() {
		return courseSessions;
	}
	public void setCourseSessions(ArrayList<CourseSession> courseSessions) {
		this.courseSessions = courseSessions;
	}
	public ArrayList<CourseUnity> getCourseUnities() {
		return courseUnities;
	}
	public void setCourseUnities(ArrayList<CourseUnity> courseUnities) {
		this.courseUnities = courseUnities;
	}
	public ArrayList<CourseComment> getCourseComments() {
		return comments;
	}
	public void setCourseComments(ArrayList<CourseComment> comments) {
		this.comments = comments;
	}
	public ArrayList<CourseCalification> getCourseCalifications() {
		return califications;
	}
	public void setCourseCalifications(ArrayList<CourseCalification> califications) {
		this.califications = califications;
	}
	
	
	public static Course getById(int id){
		Course c = (Course)StoreData.getById(Course.class, id);
		if(c != null){
			ArrayList<CourseSession> sessions = (ArrayList<CourseSession>) CourseSession.getByCourseId(c.getId());
			c.setCourseSessions(sessions);
			CourseSession activeSession = Course.getActiveSession(sessions);
			
			ArrayList<CourseUnity> unities = (ArrayList<CourseUnity>) CourseUnity.getByCourseId(c.getId());
			if(activeSession != null){
				unities = Course.getActiveUnities(unities, activeSession);
			}
			
			c.setCourseUnities(unities);
			
			if(c.getTeacherId() != null){
				User teacher = User.getById(c.getTeacherId());
				if(teacher != null){
					c.setTeacherName(teacher.getFirstName() + " " + teacher.getLastName());
				}
			}
			
			c.setCourseCalifications((ArrayList<CourseCalification>) CourseCalification.getByCourseId(c.getId()));
			
			c.setCourseComments((ArrayList<CourseComment>) CourseComment.getByCourseId(c.getId()));
			
			c.setIsFinalExamAvailable(c.isFinalExamEvailable(c.getActiveSession()));
		}
		return c;
	}
	
	private static ArrayList<CourseUnity> getActiveUnities(ArrayList<CourseUnity> unities, CourseSession activeSession) {
		String dateString = activeSession.getDate();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		Date currentDate = new Date();
		
		try {
			Date date = format.parse(dateString);
			long startTimestamp = date.getTime()/1000;
			long currentTimestamp = currentDate.getTime()/1000;
			long difference = currentTimestamp - startTimestamp;
			float result = difference/(3600*24);//cantidad de dias de diferencia
			result = result/7; //semanas de distancia
			int activeUnity = (int) result; 
			
			ArrayList<CourseUnity> fixed = new ArrayList<CourseUnity>();
			for (int i = 0; i < unities.size(); i++) {
				CourseUnity current = unities.get(i);
				if(i <= activeUnity){
					current.setActive(true);
				}
				
				current.setExamTimeFinished(true);
				if(i == activeUnity - 1){
					current.setExamTimeFinished(false);
				}
				
				fixed.add(current);
			}
			
			return fixed;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return unities;
	}
	private static CourseSession getActiveSession(ArrayList<CourseSession> sessions) {
		for (CourseSession courseSession : sessions) {
			if(courseSession.isActive()){
				return courseSession;
			}
		}
		return null;
	}
	
	public CourseSession getCurrentSession() {
		ArrayList<CourseSession> sessions = (ArrayList<CourseSession>)CourseSession.getByCourseId(this.getId());
		
		for (CourseSession courseSession : sessions) {
			if(courseSession.isActive()){
				return courseSession;
			}
		}
		return null;
	}
	
	
	public static List<Course> getByCategoryId(int categoryId){
		List<CourseCategory> listOfCouseCategory = CourseCategory.getByCategoryId(categoryId);	 
		List<Course> listOfCourses = new ArrayList<Course>();
		
		for (CourseCategory courseCategory : listOfCouseCategory) {
			Course course = Course.getById(courseCategory.getCourseId());
			if(course != null && course.getTeacherId() != null){
				listOfCourses.add(course);
			}
		}
		
		return listOfCourses;
	}
	
	public static List<Course> getByTeacherId(int teacherId){
		return (List<Course>)StoreData.getByField(Course.class, "teacherId", String.valueOf(teacherId));
	}
	
	
	public static List<Course> getAll(){
		return (List<Course>)StoreData.getByField(Course.class, "1", "1");
	}

	public static List<Course> getAllActive(int studentId){
		List<Course> courses = (List<Course>)StoreData.getByField(Course.class, "1", "1");
		List<Course> coursesFixed = new ArrayList<Course>();
		for (Course course : courses) {
			if(course.getTeacherId() != null && course.hasActiveSession()){
				course.checkIfStudentIsSuscribed(studentId);
				coursesFixed.add(course);
			}
		}
		
		return coursesFixed;
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public static List<Course> search(String search) {
		List<Course> courses = (List<Course>)StoreData.getByField(Course.class, "name", search);
		List<Course> coursesFixed = new ArrayList<Course>();
		for (Course course : courses) {
			if(course.getTeacherId() != null){
				coursesFixed.add(course);
			}
		}
		
		List<Category> cateogries = Category.search(search);
		for (Category category : cateogries) {
			List<Course> categoryCourses = Course.getByCategoryId(category.getId());
			//TODO: Validar si ya esta el curso en la lista
			coursesFixed.addAll(categoryCourses);
		}
		return coursesFixed;
	}
	
	public List<Category> getCategories(){
		List<Category> categories = new ArrayList<Category>();
		List<CourseCategory> listOfCouseCategory = CourseCategory.getByCourseId(this.getId());	
		
		for (CourseCategory courseCategory : listOfCouseCategory) {
			Category c = Category.getById(courseCategory.getCategoryId());
			if(c != null){
				categories.add(c);
			}
		}
		
		return categories;
	}
	
	public void delete() {
		StoreData.delete(this);
	}

	public boolean hasActiveSession() {
		List<CourseSession> courseSessions = CourseSession.getByCourseId(this.getId());
		if(courseSessions != null && !courseSessions.isEmpty()){
			for (CourseSession courseSession : courseSessions) {
				if(courseSession.isActive()){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public CourseSession getActiveSession() {
		List<CourseSession> courseSessions = CourseSession.getByCourseId(this.getId());
		if(courseSessions != null && !courseSessions.isEmpty()){
			for (CourseSession courseSession : courseSessions) {
				if(courseSession.isActive()){
					return courseSession;
				}
			}
		}
		return null;
	}
	
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public boolean hasStarted() {
		return this.hasActiveSession();
	}

	public boolean hasStudents() {
		// ACA HAY Q HACER LA LOGICA QUE CHEQUEE SI TIENE UNA SESION ABIERTA O NO		
		return false;
	}

	public boolean isSuscribed() {
		return isSubscribed;
	}
	public void setSuscribed(boolean isSuscribed) {
		this.isSubscribed = isSuscribed;
	}
	public void checkIfStudentIsSuscribed(int studentId) {
		List<CourseSession> courseSessions = CourseSession.getByCourseId(this.getId());
		if(courseSessions != null && !courseSessions.isEmpty()){
			for (CourseSession courseSession : courseSessions) {
				if(courseSession.isActive()){
					StudentSession studentSessions = StudentSession.getByStudentIdAndSessionId(studentId, courseSession.getId());
					if(studentSessions != null){
						this.setSuscribed(true);
						break;
					}
				}
			}
		}
	}
	
	public void checkIfStudentPassFinalExam(int studentId) {
		CourseSession currentSession = this.getCurrentSession();

		String query = "SELECT * FROM StudentExam WHERE studentId = " + studentId + " AND sessionId = " + currentSession.getId() + " AND isFinal = true";
		List<StudentExam> exams = (List<StudentExam>)StoreData.getByCustomQuery(StudentExam.class, query);
		if(exams != null && !exams.isEmpty()){
			this.setPassFinalExam(true);
			this.setFinalExamResult(exams.get(0).getResult());
		}else{
			this.setPassFinalExam(false);
		}
		
	}
	
	public static List<Course> getSoon() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		String oneWeekDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		String query = "SELECT * FROM Course c INNER JOIN CourseSession cs ON c.id = cs.courseId WHERE c.teacherId > 0 AND STR_TO_DATE(cs.date, '%d/%m/%Y') > '" + date + "' AND STR_TO_DATE(cs.date, '%d/%m/%Y') < '" + oneWeekDate + "' GROUP BY c.id,cs.id";
		return (List<Course>)StoreData.getByCustomQuery(Course.class, query);
	}
	
	
	public CourseUnity getUnityWithExam() {
		List<CourseUnity> unities = Course.getActiveUnities((ArrayList<CourseUnity>) CourseUnity.getByCourseId(this.getId()), this.getActiveSession());
		
		if(unities.size() == 2){
			if(unities.get(0).isActive() && unities.get(1).isActive()){
				return unities.get(0);
			}
		}
		
		for (int i = 0; i < unities.size(); i++) {
			if(!unities.get(i).isActive() && i > 1){
				return unities.get(i-2);
			}
		}
		
		return null;
	}
	
	
	public CourseUnity getNewUnity() {
		List<CourseUnity> unities = Course.getActiveUnities((ArrayList<CourseUnity>) CourseUnity.getByCourseId(this.getId()), this.getActiveSession());
		
		for (int i = 0; i < unities.size(); i++) {
			if(!unities.get(i).isActive() && i > 0){
				return unities.get(i-1);
			}
		}
		
		if(!unities.isEmpty() && unities.get(unities.size() - 1).isActive()){
			return unities.get(unities.size() - 1);
		}
		
		return null;
	}
	
	public boolean isFinalExamEvailable(CourseSession session){
		List<CourseUnity> unities = Course.getActiveUnities((ArrayList<CourseUnity>) CourseUnity.getByCourseId(this.getId()), this.getActiveSession());
		
		
		for (int i = 0; i < unities.size(); i++) {
			if(!unities.get(i).isActive()){
				return false;
			}
		}
		
		return session.isFinalExamAvailable(unities.size());
	}
	
	public boolean getIsFinalExamAvailable() {
		return isFinalExamAvailable;
	}
	public void setIsFinalExamAvailable(boolean isFinalExamAvailable) {
		this.isFinalExamAvailable = isFinalExamAvailable;
	}
	public boolean isPassFinalExam() {
		return passFinalExam;
	}
	public void setPassFinalExam(boolean passFinalExam) {
		this.passFinalExam = passFinalExam;
	}
	public float getFinalExamResult() {
		return finalExamResult;
	}
	public void setFinalExamResult(float finalExamResult) {
		this.finalExamResult = finalExamResult;
	}
}
