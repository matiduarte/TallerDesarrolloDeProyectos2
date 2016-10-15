package entities;

import java.util.ArrayList;

public class DataTransfer {
	private String question;
	private int unityId;
	private int questionId;
	private ArrayList<Answer> answerList;
	private boolean edit;
	
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
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	
	
	
}
