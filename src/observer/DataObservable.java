package observer;

import model.CardModel;

public interface DataObservable {
	
	public void registerObserver(DataObserver o);
	public void removeObserver(DataObserver o);
	public void notifyObserversNewCardModel(CardModel transaction);
	public void notifyObserversChangedCardModel(CardModel transaction);
	public void notifyObserversDeletedCardModel(CardModel transaction);
	public void notifyObserversReseted();

}
