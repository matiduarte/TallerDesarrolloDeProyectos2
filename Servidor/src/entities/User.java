package entities;

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
private String speciality;
  
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
}  