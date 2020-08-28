package model;

import io.safeLoad.SaveState;

import java.util.ArrayList;
import java.util.List;

import observer.DataObservable;
import observer.DataObserver;

public class DataBase implements DataObservable {
	
	private static DataBase uniqueDataBase = new DataBase();
	public static DataBase getInstance() {
		return uniqueDataBase;
	}
	
	
	private int nextId = 1;
	private CardModels cardModels;
	
	private DataBase() {
		cardModels = new CardModels();
	}
	
	public void reset() {
		nextId = 1;
		cardModels.clear();
		
		SaveState.getInstance().setSavingNecessary(false);
		notifyObserversReseted();
	}
	
	public void load(List<CardModel> cs) {
		cardModels.clear();
		this.nextId = cs.size()+1;
		for(CardModel c : cs) {
			cardModels.addCardModel(c);
		}

		SaveState.getInstance().setSavingNecessary(false);
		notifyObserversReseted();
	}
	
	
	public int addCardModel(int year, String event) {
		CardModel c = new CardModel(nextId, year, event);
		nextId++;
		cardModels.addCardModel(c);

		SaveState.getInstance().setSavingNecessary(true);
		notifyObserversNewCardModel(c);
		return c.getId();
	}
	
	public void deleteCardModel(int id) {
		CardModel c = cardModels.getCardModel(id);
		cardModels.deleteCardModel(c);
		nextId--;
		if(id != nextId) {
			CardModel lastC = cardModels.getCardModel(nextId);
			cardModels.deleteCardModel(lastC);
			lastC.setId(id);
			cardModels.addCardModel(lastC);
		}

		SaveState.getInstance().setSavingNecessary(true);
		notifyObserversDeletedCardModel(c);
	}
	
	public void changeCardModel(int id, int year, String event) {
		CardModel c = cardModels.getCardModel(id);
		cardModels.deleteCardModel(c);
		c.setYear(year);
		c.setEvent(event);
		cardModels.addCardModel(c);

		SaveState.getInstance().setSavingNecessary(true);
		notifyObserversChangedCardModel(c);
	}

	public void reAddCardModel(int id, int year, String event) {
		if(id == nextId) {
			addCardModel(year, event);
		}
		else {
			CardModel c = cardModels.getCardModel(id);
			cardModels.deleteCardModel(c);
			cardModels.addCardModel(new CardModel(id, year, event));
			addCardModel(c.getYear(), c.getEvent());
		}
	}

	
	
	public int getNextId() {
		return nextId;
	}
	
	/**
	 * Returns the copy of the CardModel with the id <code>id</code>.
	 * @param id
	 * @return
	 */
	public CardModel getCardModel(int id) {
		CardModel c = cardModels.getCardModel(id);
		return new CardModel(c);
	}
	
	public CardModel[] getAllCardModels() {
		return cardModels.getAllCardModels();
	}
	
	

	
	
	private List<DataObserver> dataObserver = new ArrayList<DataObserver>();
	
	@Override
	public void registerObserver(DataObserver o) {
		if(!dataObserver.contains(o)) {
			dataObserver.add(o);
		}
	}
	@Override
	public void removeObserver(DataObserver o) {
		if(dataObserver.contains(o)) {
			dataObserver.remove(o);
		}
	}
	@Override
	public void notifyObserversNewCardModel(CardModel cm) {
		for(DataObserver o : dataObserver) {
			o.fireNewCardModel(cm);
		}
	}
	@Override
	public void notifyObserversChangedCardModel(CardModel cm) {
		for(DataObserver o : dataObserver) {
			o.fireChangedCardModel(cm);
		}
	}
	@Override
	public void notifyObserversDeletedCardModel(CardModel cm) {
		for(DataObserver o : dataObserver) {
			o.fireDeletedCardModel(cm);
		}
	}
	@Override
	public void notifyObserversReseted() {
		for(DataObserver o : dataObserver) {
			o.fireReseted();
		}
	}

	public boolean isEmpty() {
		return nextId == 1;
	}
	
}
