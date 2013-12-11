package com.modria.questionaire.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="questionaire")
public class Element {

	@Id
	Integer id;
//	int parentId;
	Integer masterId;
	
	Integer type;
	String value;
	String displayText;
	
	String metadata; // json

	@ManyToOne(cascade=CascadeType.ALL, optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="parentId")
	Element parent;
	
	@OneToMany(mappedBy="parent", fetch=FetchType.EAGER)
	List<Element> children;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

/*	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}*/

	public int getMasterId() {
		return masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	

	public List<Element> getChildren() {
		return children;
	}

	public void setChildren(List<Element> children) {
		this.children = children;
	}

	public Element getParent() {
		return parent;
	}

	public void setParent(Element parent) {
		this.parent = parent;
	}
	
	
}
