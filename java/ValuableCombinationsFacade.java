import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evaluators.EvaluationStrategy;

public class ValuableCombinationsFacade {
	
	private final List<EvaluationStrategy> strategies;

	public ValuableCombinationsFacade(List<EvaluationStrategy> strategies) {
		this.strategies = strategies;
	}

	public Map<String, Integer> getValuableCombinations(Yatzy yatzy) {
		HashMap<String, Integer> correctResults = new HashMap<String, Integer>();
		for (EvaluationStrategy strategy : strategies) {
			int score = strategy.applyOn(yatzy.getDices());
			if(score > 0){
				correctResults.put(strategy.getCombinationName(), score);
			}
		}
		return correctResults;
	}
	
}
