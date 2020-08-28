package model.command;

import language.Messages;

public enum CommandType {

	NEW,
	DELETE,
	EDIT,
	NO;
	
	public String toString() {
		return Messages.getString("CommandType." + name());
	}
	
}
