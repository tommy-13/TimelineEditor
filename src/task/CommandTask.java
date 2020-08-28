package task;

import java.awt.Component;

import language.Messages;
import model.command.Command;
import model.command.CommandList;

public class CommandTask implements Task {
	
	private Component	parent;
	private Command		command;
	private String		title;
	private String		text;
	
	public CommandTask(Component parent, Command command) {
		this.parent = parent;
		this.command = command;
		title = Messages.getString("Task.CommandTitle");
		text  = Messages.getString("Task.CommandText") + ": " + command.getType().toString();
	}
	
	
	
	@Override
	public boolean isDone() {
		return true;
	}
	
	@Override
	public void execute() {
		command.execute();
		CommandList.getInstance().put(command);
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getText() {
		return text;
	}

}
