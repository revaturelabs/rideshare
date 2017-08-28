package com.revature.rideshare.json;

import java.util.ArrayList;

/**
 * This class is used to create an Action this can be in an Attachment
 */
public class Action {
	
	private String id;
	private String data_source;
	private String style;
	
	/*
	 * Provide a string to give this specific action a name. The name will be returned to your 
	 * Action URL along with the message's callback_id when this action is invoked.
	 */
	private String name;
	
	// The user-facing label for the message button or menu representing this action.
	private String text;
	
	// Provide 'button' when this action is a message button or provide 'select' when the action is a message menu.
	private String type;
	
	// Provide a string identifying this specific action. 
	// It will be sent to your Action URL along with the name and attachment's callback_id. 
	private String value;
	
	// Used only with message menus. 
	// The individual options to appear in this menu, provided as an array of option fields.
	private ArrayList<Option> options;
	
	/**
	 * no-arg constructor
	 */
	public Action() {}
	
	public Action(String id, String dataSource, String style, String name, String text, String type, ArrayList<Option> options) {
		super();
		this.id = id;
		this.data_source = dataSource;
		this.style = style;
		this.name = name;
		this.text = text;
		this.type = type;
		this.options = options;
	}
	
	/**
	 * Option-less constructor used to create an Action
	 * @param name
	 * @param text
	 * @param type
	 * @param options
	 */
	public Action(String name, String text, String type, ArrayList<Option> options) {
		super();
		this.name = name;
		this.text = text;
		this.type = type;
		this.options = options;
	}
	
	/**
	 * Constructor used to create an Action without options
	 * @param name
	 * @param text
	 * @param type
	 * @param value
	 */
	public Action(String name, String text, String type, String value) {
		super();
		this.name = name;
		this.text = text;
		this.type = type;
		this.value = value;
	}

	/**
	 * Get the name of the action
	 * @return the name of the action
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the action
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the text of the action
	 * @return the text of the action
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text of the action
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get the type of the action
	 * @return the type of the action
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type of the action
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the value of the action
	 * @return the value of the action
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value of the action
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Get the list of options that the action contains
	 * @return the list of options
	 */
	public ArrayList<Option> getOptions() {
		return options;
	}

	/**
	 * Set the list of options that the action contains
	 * @param options
	 */
	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * String representation of the Action
	 */
	@Override
	public String toString() {
		return "Action [name=" + name + ", text=" + text + ", type=" + type + ", value=" + value + ", options="
				+ options + "]";
	}
}
