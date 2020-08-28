package model.command;

import model.DataBase;
import model.CardModel;

public class DeleteCardModel implements Command {
	
	private int		id;
	private int		year;
	private String	event;
	
	
	public DeleteCardModel(int id) {
		this.id	= id;
		CardModel cm = DataBase.getInstance().getCardModel(id);
		year		= cm.getYear();
		event		= cm.getEvent();
	}

	
	
	@Override
	public void execute() {
		DataBase.getInstance().deleteCardModel(id);
	}

	@Override
	public void undo() {
		DataBase.getInstance().reAddCardModel(id, year, event);
	}



	@Override
	public CommandType getType() {
		return CommandType.DELETE;
	}

}
