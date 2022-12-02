/*
 * Sean Zahller
 * 5/3/2021
 * CSCD 350 Task3.3
 */

package cs350s21task2;


public class tester {

	public static void main(String[] args) {
		
		double x = 100;
		double y = -50;
		double z = 0;
		double heading = 0;
		double sH = 2;
		double sV = 0;
		double aH = 1;
		double tR = 2;
		

		Mover theMover = new Mover(x, y, z, heading, sH, sV, aH, tR);
		//Airplane daPlane = new Airplane("Airplane", theMover);
		
		for(int iStep = 0; iStep < 250; iStep++)
		{
			
			if(iStep == 20)
			{
				theMover.setHeadingTarget​(45,true);
			}
			
			else if(iStep == 40)
			{
				theMover.setHeadingTarget​(180, false);
			}
			
			else if(iStep == 200)
			{
				theMover.setHeadingTarget​(270, true);
			}
			
			System.out.println(iStep + ", " + theMover.getState());
			
			theMover.update();
		
		
			System.out.println("Original Plane = x: " + theMover.getX() + ", y: " + theMover.getY() + ", z: " + theMover.getZ() + ", Heading: " + theMover.getHeading() + 
				", Speed Horizontal " + theMover.getSpeedHorizontal() + ", SpeedVertical: " + theMover.getSpeedVertical() + ", AccelerationHorizontal: " + aH + ", TurnRate: " + tR);
		}
	}

}