package entities;

import java.util.List;

import dataBase.StoreData;

public class StudentExam {
	private int id;
	private int studentId;
	private int unityId;
	private int sessionId;
	private float result;
	private boolean isFinal;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void save(){
		StoreData.save(this);
	}
	
	public void delete(){
		StoreData.delete(this);
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getUnityId() {
		return unityId;
	}

	public void setUnityId(int unityId) {
		this.unityId = unityId;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	public boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
}
