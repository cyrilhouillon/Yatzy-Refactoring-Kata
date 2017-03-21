import java.util.Arrays;
import java.util.List;

public class YatzyBuilder {

	List<Integer> dices;
	
	private YatzyBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public static YatzyBuilder with(Integer... dices){
		YatzyBuilder builder = new YatzyBuilder();
		if(dices.length > 5){
			throw new RuntimeException("Pas plus de 5 dés!");
		}
		builder.dices = Arrays.asList(dices);
		return builder;
	}
	
	public YatzyBuilder addDice(Integer dice){
		if(dices.size() == 5){
			throw new RuntimeException("Pas plus de 5 dés!");
		}
		dices.add(dice);
		return this;
	}
	
	public Yatzy build(){
		return new YatzyLogger(new Yatzy(dices.get(0), dices.get(1), dices.get(2), dices.get(3), dices.get(4)));
	}
}
