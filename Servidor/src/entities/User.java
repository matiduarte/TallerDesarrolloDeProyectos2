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
	if(list != null){
		return (User)list.get(0);
	}
	return (User)list;
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

}  