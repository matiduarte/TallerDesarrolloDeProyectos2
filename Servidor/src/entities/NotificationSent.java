package entities;

import java.util.List;

import dataBase.StoreData;

public class NotificationSent {
	private int id;
	private String notification;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void save(){
		StoreData.save(this);
	}
	
	public static List<NotificationSent> getByKey(String notification){
		return (List<NotificationSent>)StoreData.getByField(NotificationSent.class, "notification", notification);
	}
	
	public void delete(){
		StoreData.delete(this);
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
	
}
