import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class YatziUse {

	@Test
	public void test() {
		Yatzy yatzi = YatzyBuilder.with(1, 1, 2, 2, 1).build();
		ValuableCombinationsFacade facade = ValuableCombinationsFacadeFactory.create();
		Map<String, Integer> results = facade.getValuableCombinations(yatzi);
		for (Entry<String, Integer> result : results.entrySet()) {
			System.out.println(result.getKey() + " : " + result.getValue());
		}
	}

}
