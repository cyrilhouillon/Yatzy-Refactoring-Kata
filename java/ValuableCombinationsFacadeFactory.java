import java.util.ArrayList;
import java.util.List;

import evaluators.ChanceStrategy;
import evaluators.EvaluationStrategy;
import evaluators.YatzyStrategy;
import observers.YatzyObserver;

public class ValuableCombinationsFacadeFactory {

	public static ValuableCombinationsFacade create(){
		List<EvaluationStrategy> strategies = new ArrayList<>();
		strategies.add(new ChanceStrategy());
		YatzyStrategy yatzyStrategy = new YatzyStrategy();
		yatzyStrategy.addObserver(new YatzyObserver());
		strategies.add(yatzyStrategy);
		// .....
		return new ValuableCombinationsFacade(strategies );
	}
}
