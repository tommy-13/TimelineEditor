package model.command;

import model.DataBase;
import model.CardModel;

public class ChangeCardModel implements Command {
	
	private int id;
	private int oldYear, newYear;
	private String oldEvent, newEvent;
	
	
	public ChangeCardModel(int id, int newYear, String newEvent) {
		this.id 		= id;
		this.newYear	= newYear;
		this.newEvent	= newEvent;
		
		CardModel oldCard	= DataBase.getInstance().getCardModel(id);
		this.oldYear		= oldCard.getYear();
		this.oldEvent		= oldCard.getEvent();
	}

	
	
	@Override
	public void execute() {
		DataBase.getInstance().changeCardModel(id, newYear, newEvent);
	}

	@Override
	public void undo() {
		DataBase.getInstance().changeCardModel(id, oldYear, oldEvent);
	}

	@Override
	public CommandType getType() {
		return CommandType.EDIT;
	}

}
