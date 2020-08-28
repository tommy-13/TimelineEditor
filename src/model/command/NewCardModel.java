package model.command;

import model.DataBase;

public class NewCardModel implements Command {
	
	private int		id;
	private int		year;
	private String	event;
	
	
	public NewCardModel(int year, String event) {
		this.id		= 0;
		this.year	= year;
		this.event	= event;
	}

	
	
	@Override
	public void execute() {
		id = DataBase.getInstance().addCardModel(year, event);
	}

	@Override
	public void undo() {
		DataBase.getInstance().deleteCardModel(id);
	}



	@Override
	public CommandType getType() {
		return CommandType.NEW;
	}

}
