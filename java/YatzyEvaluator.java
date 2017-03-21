import evaluators.EvaluationStrategy;

public class YatzyEvaluator {

	
	private EvaluationStrategy strategy;

	public YatzyEvaluator(EvaluationStrategy strategy) {
		this.strategy = strategy;
	}
	
	public int evaluate(Yatzy yatzy){
		return strategy.applyOn(yatzy.getDices());
	}
}
