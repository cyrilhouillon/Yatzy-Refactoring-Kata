package evaluators;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class YatzyStrategy extends Observable implements EvaluationStrategy  {

	private List<Observer> observers = new ArrayList<>();
	
	public void addObserver(Observer observer){
		observers.add(observer);
	}

	@Override
	public String getCombinationName() {
		return "Yatzy!";
	}

	@Override
	public int applyOn(int... dices) {
        int[] counts = new int[6];
        for (int dice : dices)
            counts[dice-1]++;
        for (int i = 0; i != 6; i++)
            if (counts[i] == 5){
            	notify(dices);
            	return 50;
            }
        return 0;
	}

	private void notify(int[] dices) {
		for (Observer obs : observers) {
			obs.update(this, dices);
		}
	}

}
