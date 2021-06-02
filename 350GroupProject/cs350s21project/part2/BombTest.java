package cs350s21project.part2;
import org.junit.Test;
import cs350s21project.part2.Bomb.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BombTest{
	@Test
	public void testWind(){
		Bomb bomb = new Bomb(0,0,0,0,null,0,45,10);
		double direction = 45.0;
		double actualDirection = bomb.getWindDirection();
		
		double speed = 10.0;
		double actualSpeed = bomb.getWindSpeed();
		assertEquals(speed,actualSpeed,0, "getWindDirection() " );
		assertEquals(direction, actualDirection,0, "getWindSpeed() ");
	}
	@Test
	public void testRelease()
	{
		Bomb bomb = new Bomb(100,200,300,10,null,0,0,0);
		Coordinates ogCoords = bomb.getReleaseCoordinates();
		double alt = bomb.getReleaseAltitude();
		double desc = bomb.getDescentSpeed();
		
		double expectedX = 100;
		double expectedY = 200;
		double expectedAlt = 300;
		double expectedDesc = 10;
		
		assertEquals(expectedX, ogCoords.getX() , 0, "getX()");
		assertEquals(expectedY, ogCoords.getY() , 0, "getY() "); 
		assertEquals(expectedAlt,alt,0, "getReleaseAlt()");
		assertEquals(expectedDesc,desc,0,"getDescentSpeed()"); 
	}
	@Test
	public void testError()
	{

		E_ErrorType exError1 = E_ErrorType.NONE;
		E_ErrorType exError2 = E_ErrorType.GAUSSIAN;
		E_ErrorType exError3 = E_ErrorType.UNIFORM;
		
		Bomb bomb1 = new Bomb(100,200,300,10,exError1,10,0,0);
		Bomb bomb2 = new Bomb(100,200,300,10,exError2,10,0,0);
		Bomb bomb3 = new Bomb(100,200,300,10,exError3,10,0,0);
		
		double ErrorRange1 = bomb1.getErrorRange();
		double ErrorRange2 = bomb2.getErrorRange();
		double ErrorRange3 = bomb3.getErrorRange();
		double expectedErrorRange = 10;
		
		E_ErrorType error1 = bomb1.getErrorType();
		E_ErrorType error2 = bomb2.getErrorType();
		E_ErrorType error3 = bomb3.getErrorType();
		
		
		
		assertEquals(exError1.hashCode(), error1.hashCode(), 0, "getErrorType()");
		assertEquals(exError2.hashCode(),error2.hashCode(), 0, "getErrorType()");
		assertEquals(exError3.hashCode(),error3.hashCode(), 0, "getErrorType()");
		
		assertEquals(expectedErrorRange,ErrorRange1, 0,"getErrorRange()");
		assertEquals(expectedErrorRange,ErrorRange2, 0,"getErrorRange()");
		assertEquals(expectedErrorRange,ErrorRange3, 0,"getErrorRange()");
	}
	
	public void behavioralTest() //Dont know if it needs @Test above, or if private or public or how to run, or if multiple methods needed for each test
	{
		
		E_ErrorType exError1 = E_ErrorType.NONE;//todo
		E_ErrorType exError2 = E_ErrorType.GAUSSIAN;
		E_ErrorType exError3 = E_ErrorType.UNIFORM;
		
		Bomb bomb1 = new Bomb(200,300,1500,100,exError1,0,0,0); // No error no wind
		Bomb bomb2 = new Bomb(200,300,1500,100,exError1,0,60,25); // No error with wind
		Bomb bomb3 = new Bomb(200,300,1500,100,exError3,150,0,0); // Uniform error no wind
		Bomb bomb4 = new Bomb(200,300,1500,100,exError2,150,0,0); // Gaussian error no wind
		Bomb bomb5 = new Bomb(200,300,1500,100,exError3,150,60,25); // Uniform error with wind
		Bomb bomb6 = new Bomb(200,300,1500,100,exError2,150,60,25); // Gaussian error with wind
		
		Coordinates[] noErrorNoWind = new Coordinates[100];
		Coordinates[] noErrorWithWind = new Coordinates[100];;
		Coordinates[] uniformErrorNoWind = new Coordinates[100];;
		Coordinates[] gaussianErrorNoWind = new Coordinates[100];;
		Coordinates[] uniformErrorWithWind = new Coordinates[100];;
		Coordinates[] gaussianErrorWithWind = new Coordinates[100];;
		
		//Drops bomb 100 times, adds coordinates into a coordinate array. Dont know how or best way to put into excel
		for(int i = 0; i < 101; i++)
		{
			noErrorNoWind[i] = bomb1.drop();
			noErrorWithWind[i] = bomb2.drop();
			uniformErrorNoWind[i] = bomb3.drop();
			gaussianErrorNoWind[i] = bomb4.drop();
			uniformErrorWithWind[i] = bomb5.drop();
			gaussianErrorWithWind[i] = bomb6.drop();
			
			//Add each to excel file here?
			//Maybe 6 different loops and 6 different excel files to add to?
		}
	}
	
}
