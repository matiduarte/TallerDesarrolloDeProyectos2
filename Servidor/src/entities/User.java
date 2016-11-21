package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class User {  
	
	private int id;  
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private Boolean isActive;
	private Boolean isStudent;
	private String source;
	private String speciality;
	private Boolean isAdmin;
	
	  
	public String getSpeciality() {
		return speciality;
	}
	
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getId() {  
	    return id;  
	}  
	
	public void setId(int id) {  
	    this.id = id;  
	}  
	
	public String getFirstName() {  
	    return firstName;  
	}  
	
	public void setFirstName(String firstName) {  
	    this.firstName = firstName;  
	}  
	
	public String getLastName() {  
	    return lastName;  
	}  
	
	public void setLastName(String lastName) {  
	    this.lastName = lastName;  
	}
	
	public static User getById(int id){
		return (User)StoreData.getById(User.class, id);
	}
	
	public static User getByUserName(String userName){
		
		List<?> list = StoreData.getByField(User.class, "userName", userName);
		User user = null;
		if(list.size() > 0){
			user = (User)list.get(0);
		}
		return user;
	}
	
	public static User getByUserEmail(String email){
		
		List<?> list = StoreData.getByField(User.class, "email", email);
		User user = null;
		if(list.size() > 0){
			user = (User)list.get(0);
		}
		
		return user;
	}
	
	public static List<User> getAllPossibleTeachers(){
		//TODO: filtrar los estudiantes
		return (List<User>)StoreData.getByField(User.class, "1", "1");
	}
	
	public void save(){
		StoreData.save(this);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getIsActive() {  
	    return this.isActive;  
	}  
	
	public void setIsActive(Boolean isActive) {  
	    this.isActive = isActive;  
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public Boolean getIsStudent() {
		return isStudent;
	}
	
	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public static User getByAndEmailSource(String email, String source) {
		List<?> list = StoreData.getByTwoFields(User.class, "email", email, "source", source);
		User user = null;
		if(list!= null && list.size() > 0){
			user = (User)list.get(0);
		}
		
		return user;
	}

	public List<Certification> getCertificacions() {
		return Certification.getByStudentId(this.getId());
//		List<StudentExam> approvedFinals = StudentExam.getApprovedFinalsByStudentId(this.getId());
//		
//		ArrayList<Certification> result = new ArrayList<Certification>();
//		if(approvedFinals != null){
//			for (StudentExam approvedFinal : approvedFinals) {
//				CourseSession session = CourseSession.getById(approvedFinal.getSessionId());
//				if(session != null){
//					Course course = Course.getById(session.getCourseId(), this.getId());
//					
//					if(course != null){
//						Certification certification = new Certification();
//						certification.setCourseName(course.getName());
//						certification.setStudentName(this.getFirstName() + " " + this.getLastName());
//						certification.setResult(approvedFinal.getResult());
//						
//						User teacher = User.getById(course.getTeacherId());
//						if(teacher != null){
//							certification.setTeachertName(teacher.getFirstName() + " " + teacher.getLastName());
//						}
//						
//						result.add(certification);
//					}
//				}
//				
//			}
//		}
//		
//		return result;
	}
}  
