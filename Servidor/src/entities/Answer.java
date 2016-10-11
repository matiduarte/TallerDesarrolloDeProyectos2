package entities;

import dataBase.StoreData;

public class Answer {
	private int id;
	private int questionId;
	private String answer;
	private Boolean isCorrect;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getQuestionId() {
		return questionId;
	}


	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public Boolean getIsCorrect() {
		return isCorrect;
	}


	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}


	public void save(){
		StoreData.save(this);
	}
	
}
