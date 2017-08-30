package com.revature.rideshare.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class PointOfInterestType implements Serializable {

	private static final long serialVersionUID = 1609658818097998686L;

	@Id
	@Column(name = "TYPE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TYPE_ID_SEQUENCE")
	@SequenceGenerator(name = "TYPE_ID_SEQUENCE", sequenceName = "TYPE_ID_SEQUENCE")
	private int typeId;

	@Column(name = "TYPE_NAME", nullable = false)
	private String typeName;

	public PointOfInterestType() {
	}

	public PointOfInterestType(int typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "PointOfInterestType [typeId=" + typeId + ", typeName=" + typeName + "]";
	}

}
