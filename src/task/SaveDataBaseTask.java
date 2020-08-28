package task;

import io.safeLoad.DataBaseSaver;

import java.awt.Component;

import language.Messages;

public class SaveDataBaseTask implements Task {
	
	private Component	parent;
	private boolean		isSaved = false;
	
	public SaveDataBaseTask(Component parent) {
		this.parent = parent;
	}
	
	
	
	@Override
	public boolean isDone() {
		return isSaved;
	}
	
	@Override
	public void execute() {
		isSaved = DataBaseSaver.safe(false, parent);
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public String getTitle() {
		return Messages.getString("Task.SaveTitle");
	}

	@Override
	public String getText() {
		return Messages.getString("Task.SaveText");
	}

}
