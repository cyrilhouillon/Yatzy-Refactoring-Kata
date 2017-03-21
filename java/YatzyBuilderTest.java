import static org.junit.Assert.*;

import org.junit.Test;

public class YatzyBuilderTest {

	@Test(expected = RuntimeException.class)
	public void should_not_accept_more_than_5_dices_at_init() {
		YatzyBuilder builder = YatzyBuilder.with(new Integer[]{1, 2, 3, 4, 5, 1});
	}

}
