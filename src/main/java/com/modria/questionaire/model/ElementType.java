package com.modria.questionaire.model;

public enum ElementType {

	NONE(0),
	TEXT(1),
	TEXTBOX(2),
	RADIO(3),
	CHECKBOX(4),
	GROUP(5);
	
	private int type;
	
	ElementType(int type) {
		this.type = type;
	}
	
	public static ElementType valueOf(int type) {
		for(ElementType el : ElementType.values()) {
			if(el.type==type) {
				return el;
			}
		}
		return null;
	}

	public int getType() {
		return type;
	}
	
	
}
