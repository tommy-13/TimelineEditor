package task;

import java.awt.Component;

import language.Messages;
import model.command.CommandList;

public class UndoTask implements Task {
	
	private Component	parent;
	private String		title;
	private String		text;
	
	
	public UndoTask(Component parent) {
		this.parent = parent;
		title = Messages.getString("Task.UndoTitle");
		text  = Messages.getString("Task.UndoText") + ": " +
				CommandList.getInstance().getTopUndoCommand().toString();
	}
	
	
	
	@Override
	public boolean isDone() {
		return true;
	}
	
	@Override
	public void execute() {
		CommandList.getInstance().undo();
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
