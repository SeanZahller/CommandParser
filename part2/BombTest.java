package cs350s21project.part2;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BombTest extends Bomb{
	@Test
	public void testWind(){
		Bomb bomb = new Bomb(0,0,0,0,null,0,45,10);
		double direction = 45.0;
		double actualDirection = bomb.getWindDirection();
		
		double speed = 10.0;
		double actualSpeed = bomb.getWindSpeed();
		assertEquals(Speed,actualSpeed,0, "getWindDirection() " );//todo
		assertEquals(direction, actualDirection,0, "getWindSpeed() ");//todo
	}
	@Test
	public void testRelease()
	{
		Bomb bomb = new Bomb(100,200,300,10,null,0,0,0);
		double x = actualCoords.getX();
		double y = actualCoords.getY();
		double alt = bomb.getReleaseAltitude();
		double desc = bomb.getDescentSpeed();
		
		double expectedX = 100;
		double expectedY = 200;
		double expectedAlt = 300;
		double expectedDesc =10;
		
		assertEquals(expectedX, x , 0, "getX()");//todo
		assetEquals(expectedY, y , 0, "getY() "); 
		assertEquals(expectedAlt,alt,0, "getReleaseAlt()");
		assertEquals(expectedDesc,desc,0,"getDescentSpeed()"); //todo
	}
	@Test
	public void testError()
	{

		E_ErrorType exError1 = E_ErrorType.None;//todo
		E_ErrorType exError2 = E.ErrorType.GAUSSIAN;
		E_ErrorType exError3 = E.ErrorType.UNIFORM;
		
		Bomb bomb1 = new Bomb(100,200,300,10,exError1,10,0,0);//todo
		Bomb bomb2 = new Bomb(100,200,300,10,exError2,10,0,0);.//todo
		Bomb bomb3 = new Bomb(100,200,300,10,exError3,10,0,0);//todo
		
		double ErrorRange = bomb.getErrorRange();
		double expectedErrorRange = 10;
		
		E_ErrorType error1 = bomb.getErrorType();
		E_ErrorType error2 = bomb.getErrorType();
		E_ErrorType error3 = bomb.getErrorType();
		
		
		
		assertEquals(exError1,error1);
		assertEquals(exError2,error2);
		assertEquals(exError3,error3);
		
		assertEquals(expectedErrorRange,ErrorRange, 0,"getErrorRange()"); //todo
	}
}
