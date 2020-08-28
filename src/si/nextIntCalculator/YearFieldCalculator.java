package si.nextIntCalculator;

import globals.GlobalConstants;

public class YearFieldCalculator extends NextIntCalculator {
	
	public YearFieldCalculator() {
		this.lowerBound = GlobalConstants.MIN_YEAR;
		this.upperBound = GlobalConstants.MAX_YEAR;
		this.currentValue = GlobalConstants.DEFAULT_YEAR;
	}

	@Override
	public int getNextInt(int suggestion) {
		/* value > upperBound or value < lowerBound is not allowed */
		if(suggestion > upperBound) {
			return upperBound;
		}
		else if(suggestion < lowerBound) {
			return lowerBound;
		}
		else if(suggestion == 0) {
			if(currentValue <= 0) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			return suggestion;
		}
	}

}
