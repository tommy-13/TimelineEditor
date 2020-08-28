package observer;

import model.CardModel;

public interface DataObserver {

	public void fireNewCardModel(CardModel transaction);
	public void fireChangedCardModel(CardModel transaction);
	public void fireDeletedCardModel(CardModel transaction);
	public void fireReseted();

}
