package cs350s21project.part2;

import org.junit.Test;

import cs350s21project.part2.Bomb.Coordinates;
import cs350s21project.part2.Bomb.E_ErrorType;

public class BehaviorTests 
{

	public static void main(String[] args) 
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
		
		Coordinates[] noErrorNoWind = new Coordinates[150];
		Coordinates[] noErrorWithWind = new Coordinates[150];
		Coordinates[] uniformErrorNoWind = new Coordinates[150];
		Coordinates[] gaussianErrorNoWind = new Coordinates[150];
		Coordinates[] uniformErrorWithWind = new Coordinates[150];
		Coordinates[] gaussianErrorWithWind = new Coordinates[150];
	
		
		//Drops bomb 100 times, adds coordinates into a coordinate array. Dont know how or best way to put into excel
		for(int i = 0; i < 101; i++)
		{
			noErrorNoWind[i] = bomb1.drop();
			System.out.println(noErrorNoWind[i]);

		}
		
		System.out.println("End of 1st");
	
		
		for(int j = 0; j < 101; j++)
		{
			noErrorWithWind[j] = bomb2.drop();		
			//System.out.println(noErrorWithWind[j].getX());
			System.out.println(noErrorWithWind[j].getY());

		}
		
		System.out.println("End of 2nd");
	
		
		for(int k = 0; k < 101; k++)
		{
			uniformErrorNoWind[k] = bomb3.drop();		
			//System.out.println(uniformErrorNoWind[k].getX());
			System.out.println(uniformErrorNoWind[k].getY());

		}
		
		System.out.println("End of 3rd");
		
	
		for(int l = 0; l < 101; l++)
		{
			gaussianErrorNoWind[l] = bomb4.drop();	
			System.out.println(gaussianErrorNoWind[l].getX());
			//System.out.println(gaussianErrorNoWind[l].getY());

		}
		
		System.out.println("End of 4th");

		for(int a = 0; a < 101; a++)
		{
			uniformErrorWithWind[a] = bomb5.drop();
			//System.out.println(uniformErrorWithWind[a].getX());
			System.out.println(uniformErrorWithWind[a].getY());

		}
		
		System.out.println("End of 5th");
		
		for(int s = 0; s < 101; s++)
		{
			gaussianErrorWithWind[s] = bomb6.drop();
			//System.out.println(gaussianErrorWithWind[s].getX());
			System.out.println(gaussianErrorWithWind[s].getY());

		}
		
		
	}
}
