
public class YatzyLogger extends Yatzy{
	private Yatzy yatzy;

	public YatzyLogger(Yatzy yatzy){
		super(yatzy.dice);
		this.yatzy = yatzy;
	}
	
	@Override
	public int[] getDices() {
		System.out.println(yatzy.getDices()[0] +", " +yatzy.getDices()[1] +", " +yatzy.getDices()[2] +", " +yatzy.getDices()[3] +", " +yatzy.getDices()[4]);
		return super.getDices();
	}
	
}
