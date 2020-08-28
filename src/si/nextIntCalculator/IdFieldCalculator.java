package si.nextIntCalculator;

public class IdFieldCalculator extends NextIntCalculator {
	
	public IdFieldCalculator(int lowerBound, int upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.currentValue = upperBound;
	}

	
	@Override
	protected int getNextInt(int suggestion) {
		/* value > upperBound or value < lowerBound is not allowed */
		if(suggestion > upperBound) {
			return upperBound;
		}
		else if(suggestion < lowerBound) {
			return lowerBound;
		}
		else {
			return suggestion;
		}
	}

}
