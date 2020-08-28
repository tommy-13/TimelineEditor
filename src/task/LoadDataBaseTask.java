package task;

import io.safeLoad.DataBaseLoader;

import java.awt.Component;

import language.Messages;

public class LoadDataBaseTask implements Task {
	
	private Component	parent;
	private boolean		isLoaded = false;
	
	public LoadDataBaseTask(Component parent) {
		this.parent = parent;
	}
	
	
	
	@Override
	public boolean isDone() {
		return isLoaded;
	}
	
	@Override
	public void execute() {
		isLoaded = DataBaseLoader.load(parent);
	}

	@Override
	public Component getParent() {
		return parent;
	}

	@Override
	public String getTitle() {
		return Messages.getString("Task.LoadTitle");
	}

	@Override
	public String getText() {
		return Messages.getString("Task.LoadText");
	}

}
