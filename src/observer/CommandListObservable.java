package observer;

public interface CommandListObservable {
	
	public void registerObserver(CommandListObserver o);
	public void removeObserver(CommandListObserver o);
	public void notifyObserversUndoStackState(boolean isEmpty);
	public void notifyObserversRedoStackState(boolean isEmpty);

}
