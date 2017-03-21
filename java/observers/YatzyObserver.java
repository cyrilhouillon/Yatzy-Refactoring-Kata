package observers;

import java.util.Observable;
import java.util.Observer;

import evaluators.YatzyStrategy;

public class YatzyObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if( o instanceof YatzyStrategy && arg instanceof int[]){
			int[] dices = (int[]) arg;
			System.out.println("Yatzy de "+ dices[0]);
		}
	}


}
