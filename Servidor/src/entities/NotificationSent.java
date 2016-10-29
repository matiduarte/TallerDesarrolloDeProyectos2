package entities;

import java.util.List;

import dataBase.StoreData;

public class NotificationSent {
	private int id;
	private String key;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void save(){
		StoreData.save(this);
	}
	
	public static List<NotificationSent> getByKey(String key){
		return (List<NotificationSent>)StoreData.getByField(NotificationSent.class, "key", key);
	}
	
	public void delete(){
		StoreData.delete(this);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
