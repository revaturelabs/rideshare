package com.revature.rideshare.json;

import java.util.ArrayList;
import java.util.List;

import com.revature.rideshare.util.EquivalenceUtilities;

/**
 * This class is used to create a JSON object for passing messages to the user
 * by creating Java objects which have the hierarchical structure of a slack
 * message. <br>
 * <br>
 * To create an interactive message, first create a set of options or buttons,
 * then attach these to an actions array, then attach the actions array to an
 * attachment. You can then add that attachment to a SlackJSONBuilder object
 * which can be converted into String format and sent to a user as a slack
 * message.<br>
 * <br>
 * <b>Notable Fields:</b><br>
 * {@link channel channel}<br>
 * {@link #text}<br>
 * {@link #response_type}<br>
 * {@link #attachments}<br>
 * <br>
 * <b>Primary Constructor:</b><br>
 * {@link #SlackJSONBuilder(String, String, String, List) SlackJSONBuilder(String channel, String text, String response_type, List attachments)}
 */
public class SlackJSONBuilder {

	/**
	 * Name of the channel, username, or user_id
	 */
	private String channel;

	/**
	 * The basic text of the message. Only required if the message contains zero
	 * attachments.
	 */
	private String text;

	/**
	 * This field cannot be specified for a brand new message and must be used
	 * only in response to the execution of message button action or a slash
	 * command response.<br>
	 * <br>
	 * Expects one of two values: - in_channel — display the message to all
	 * users in the channel where a message button was clicked. Messages sent in
	 * response to invoked button actions are set to in_channel by default. -
	 * ephemeral — display the message only to the user who clicked a message
	 * button. Messages sent in response to Slash commands are set to ephemeral
	 * by default.
	 */
	private String response_type;
	private String bot_id;
	private String type;
	private String subtype;
	private String ts;

	/**
	 * Provide a JSON array of {@link Attachment } objects. Adds additional
	 * components to the message. Messages should contain no more than 20
	 * attachments.
	 */
	private List<Attachment> attachments;

	/**
	 * No-arg constructor
	 */
	public SlackJSONBuilder() {
	}

	public SlackJSONBuilder(String text, String botId, String type, String subtype, String ts,
			List<Attachment> attachments) {
		this.text = text;
		this.bot_id = botId;
		this.type = type;
		this.subtype = subtype;
		this.ts = ts;
		this.attachments = attachments;
	}

	/**
	 * Constructor used for creating a Request JSON object
	 * 
	 * @param channel
	 * @param text
	 * @param response_type
	 * @param attachments
	 */
	public SlackJSONBuilder(String channel, String text, String response_type, List<Attachment> attachments) {
		this.channel = channel;
		this.text = text;
		this.response_type = response_type;
		this.attachments = attachments;
	}

	/**
	 * Gets the channel name that the message will be sent to.
	 * 
	 * @return the channel name
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel name
	 * 
	 * @param channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * Gets the message text.
	 * 
	 * @return the message text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the message text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get the response_type of the message
	 * 
	 * @return
	 */
	public String getResponse_type() {
		return response_type;
	}

	/**
	 * Sets the response_type of the message
	 * 
	 * @param response_type
	 */
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	/**
	 * Get the list of attachments that the message contains
	 * 
	 * @return list of attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}

	/**
	 * Set the list of attachments that the message contains
	 * 
	 * @param attachments
	 */
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getBot_id() {
		return bot_id;
	}

	public void setBot_id(String bot_id) {
		this.bot_id = bot_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public void addDelimiters() {
		for (Attachment attachment : this.attachments) {
			List<Action> actions = attachment.getActions();
			for (int i = 0; i < actions.size(); i++) {
				if (actions.get(i).getType().equals("select")) {
					List<Option> actionOptions = actions.get(i).getOptions();
					for (Option option : actionOptions) {
						// TODO:use nonstandard delimiter character
						option.setValue("" + i + "-" + option.getValue());
					}
				}
			}
		}
	}

	/**
	 * String representation of the Request message.
	 */
	@Override
	public String toString() {
		return "RideRequestJSON [channel=" + channel + ", text=" + text + "]";
	}

	@Override
	public int hashCode()
	{
		return new String(channel + text + type + EquivalenceUtilities.ListHash(getAttachments())).hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		SlackJSONBuilder otherJSONBuilder = (SlackJSONBuilder) other;
		if (otherJSONBuilder == null) {
			return false;
		}
		if (!EquivalenceUtilities.SafeCompare(otherJSONBuilder.getChannel(), getChannel())) {
			return false;
		}
		if (!EquivalenceUtilities.SafeCompare(otherJSONBuilder.getText(), getText())) {
			return false;
		}
		if (!EquivalenceUtilities.SafeCompare(otherJSONBuilder.getType(), getType())) {
			return false;
		}
		if (!EquivalenceUtilities.SafeCompare(otherJSONBuilder.getAttachments(), getAttachments()))
		{
			return false;
		}
		return true;
	}
}
