package si.nextIntCalculator;

public abstract class NextIntCalculator {

	protected int lowerBound;
	protected int upperBound;
	protected int currentValue;
	
	public final int getLowerBound() {
		return lowerBound;
	}
	public final int getUpperBound() {
		return upperBound;
	}
	public final int getCurrentValue() {
		return currentValue;
	}
	public final void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	public final void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
	
	public final int setCurrentValue(int suggestion) {
		currentValue = getNextInt(suggestion);
		return currentValue;
	}
	
	protected abstract int getNextInt(int suggestion);
	
}
