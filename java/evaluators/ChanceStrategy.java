package evaluators;

public class ChanceStrategy implements EvaluationStrategy {
	
	@Override
	public String getCombinationName() {
		return "Chance";
	}

	@Override
	public int applyOn(int... dice) {
	    int total = 0;
	    total += dice[0];
	    total += dice[1];
	    total += dice[2];
	    total += dice[3];
	    total += dice[4];
	    return total;
	}

}
