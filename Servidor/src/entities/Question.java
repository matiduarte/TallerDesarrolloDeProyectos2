package entities;

import java.util.List;

import dataBase.StoreData;

public class Question {
	private int id;
	private String question;
	private int unityId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getUnityId() {
		return unityId;
	}
	public void setUnityId(int unityId) {
		this.unityId = unityId;
	}
	public void save(){
		StoreData.save(this);
	}
	
	public static List<Question> getByUnityId(int unityId){
		return (List<Question>)StoreData.getByField(Question.class, "unityId", String.valueOf(unityId));
	}
	
	public static Question getById(int id){
		return (Question)StoreData.getById(Question.class, id);
	}
	
	public void delete(){
		StoreData.delete(this);
	}
	
}
