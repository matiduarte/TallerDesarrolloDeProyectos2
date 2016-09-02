package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dataBase.StoreData;

@XmlRootElement
public class Category {  
	
	private int id;  
	private String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public static Category getById(int id){
		return (Category)StoreData.getById(Category.class, id);
	}
	
	public static List<Category> getAll(){
		return (List<Category>)StoreData.getByField(Category.class, "1", "1");
	}
}