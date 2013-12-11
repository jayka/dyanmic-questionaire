package com.modria.questionaire.model;

import java.util.HashMap;
import java.util.Map;

public class AnswersForm {

	private Map<String, String> answersMap = new HashMap<String, String>();

	public Map<String, String> getAnswersMap() {
		return answersMap;
	}

	public void setAnswersMap(Map<String, String> answersMap) {
		this.answersMap = answersMap;
	}
	
	
}
