package com.revature.rideshare.json;

import java.util.ArrayList;

/**
 * This class is used to create the Attachments that a RideRequestJSON object contains
 */
public class Attachment {
	
	private String id;
	
	// The basic text of the message. Only required if the message contains zero attachments.
	private String text;
	
	// The basic text of the message. Only required if the message contains zero attachments.
	private String fallback;
	
	/* The provided string will act as a unique identifier for the collection of buttons within the attachment. 
	 * It will be sent back to your message button action URL with each invoked action. 
	 * This field is required when the attachment contains message buttons.
	 * It is key to identifying the interaction you're working with.
	 */
	private String callback_id;
	
	// Used to visually distinguish an attachment from other messages. Accepts hex values and a few named colors.
	private String color;
	
	// Even for message menus, remains the default value default.
	private String attachment_type;
	
	/**
	 * A collection of actions (buttons or menus) to include in the attachment. 
	 * Required when using message buttons or message menus. 
	 * A maximum of 5 actions per attachment may be provided.
	 */
	private ArrayList<Action> actions;
	
	/**
	 * no-arg constructor
	 */
	public Attachment() {}
	
	public Attachment(String id, String text, String fallback, String callback_id, String color, String attachment_type, ArrayList<Action> actions) {
		this.id = id;
		this.text = text;
		this.fallback = fallback;
		this.callback_id = callback_id;
		this.color = color;
		this.attachment_type = attachment_type;
		this.actions = actions;
	}
	
	/**
	 * Constructor used for creating an Attachment
	 * @param text
	 * @param fallback
	 * @param callback_id
	 * @param color
	 * @param attachment_type
	 * @param actions
	 */
	public Attachment(String text, String fallback, String callback_id, String color, String attachment_type, ArrayList<Action> actions) {
		this.text = text;
		this.fallback = fallback;
		this.callback_id = callback_id;
		this.color = color;
		this.attachment_type = attachment_type;
		this.actions = actions;
	}
	
	public Attachment(String fallback, String callback_id, String color, String attachment_type, ArrayList<Action> actions) {
		this.fallback = fallback;
		this.callback_id = callback_id;
		this.color = color;
		this.attachment_type = attachment_type;
		this.actions = actions;
	}

	/**
	 * Get the text of the attachment
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of the attachment
	 * @param textd
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get the fallback message
	 * @return the fallback message
	 */
	public String getFallback() {
		return fallback;
	}

	/**
	 * Set the fallback message
	 * @param fallback
	 */
	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	/**
	 * Get the callback id
	 * @return the callback_id
	 */
	public String getCallback_id() {
		return callback_id;
	}

	/**
	 * Set the callback id
	 * @param callback_id
	 */
	public void setCallback_id(String callback_id) {
		this.callback_id = callback_id;
	}

	/**
	 * Get the color of the attachment
	 * @return  the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Set the color of the attachment
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Get the attachment_type
	 * @return the attachment_type
	 */
	public String getAttachment_type() {
		return attachment_type;
	}

	/**
	 * Set the attachment_type
	 * @param attachment_type
	 */
	public void setAttachment_type(String attachment_type) {
		this.attachment_type = attachment_type;
	}

	/**
	 * Get the list of actions that this attachment contains
	 * @return list of actions
	 */
	public ArrayList<Action> getActions() {
		return actions;
	}

	/**
	 * Set the list of actions that this attachment contains
	 * @param actions
	 */
	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * String representation of an Attachment
	 */
	@Override
	public String toString() {
		return "Attachment [text=" + text + ", fallback=" + fallback + ", callback_id=" + callback_id + ", color="
				+ color + ", attachment_type=" + attachment_type + ", actions=" + actions + "]";
	}
}