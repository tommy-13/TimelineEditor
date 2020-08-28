package observer;

public interface SavePathObservable {
	
	public void registerObserver(SavePathObserver o);
	public void removeObserver(SavePathObserver o);
	public void notifyObserversSavePathChange();

}
