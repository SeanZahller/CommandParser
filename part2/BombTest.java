package cs350s21project.part2;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BombTest extends Bomb{
	@Test
	public void testWind(){
		assertEquals();
		getWindDirection();
		getWindSpeed();
	}
	@Test
	public void testRelease(){
		getReleaseCoordinates();
		getX();
		getY();
		getReleaseAltitude();
	}
	@Test
	public void testError(){
		getErrorType();
		getErrorRange();
	}
}
