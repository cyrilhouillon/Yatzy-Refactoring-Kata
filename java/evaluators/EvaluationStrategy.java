package evaluators;

public interface EvaluationStrategy {
	
	String getCombinationName();

	int applyOn(int... dice) ;

}
