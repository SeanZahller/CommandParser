/*
 * Sean Zahller
 * 5/3/2021
 * CSCD 350 Task3.5 Final Iteration
 */


package cs350s21task2;

public class Mover 
{
	private double x;
	private double y;
	private double z;
	private double heading;
	private double headingTarget;
	private boolean headingTargetDirection;
	private double speedHorizontal;
	private double speedHorizontalTarget;
	private double speedVertical;
	private double accelerationHorizontal;
	private double turnRate;
	
	public Mover(double x, double y, double z, double heading, double speedHorizontal, double speedVertical, double accelerationHorizontal, double turnRate)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.heading = heading;
		this.speedHorizontal = speedHorizontal;
		this.speedVertical = speedVertical;
		this.accelerationHorizontal = accelerationHorizontal;
		this.turnRate = turnRate;
		//when setters arent set initialize targets to the actual speed and heading
		this.headingTarget = this.heading;
		this.speedHorizontalTarget = this.speedHorizontal;
	}

	public double getAccelerationHorizontal()
	{
		return this.accelerationHorizontal;
	}
	
	public double getHeading()
	{
		return this.heading;
	}
	
	public double getHeadingTarget()
	{
		return this.headingTarget;
	}
	
	public boolean getHeadingTargetDirection()
	{
		return this.headingTargetDirection;
	}
	
	public double getSpeedHorizontal()
	{
		return this.speedHorizontal;
	}
	
	public double getSpeedHorizontalTarget()
	{
		return this.speedHorizontalTarget;
	}
	
	public void setSpeedHorizontalTarget​(double speed)
	{
		this.speedHorizontalTarget = speed;
	}
	
	public double getSpeedVertical()
	{
		return this.speedVertical;
	}
	
	public void setSpeedVertical​(double speed)
	{
		this.speedVertical = speed;
	}
	
	public String getState()
	{
		return this.x + "," + this.y + "," + this.z + "," + this.heading + "," + this.speedHorizontal + "," + this.speedVertical;
	}
	
	public double getTurnRate()
	{
		return this.turnRate;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
	public double getZ()
	{
		return this.z;
	}
	
	public void setHeadingTarget​(double h, boolean d)
	{
		this.headingTarget = h;
		this.headingTargetDirection = d;
	}
	
	public void update()
	{
		//update horizontal speed
		if(this.speedHorizontalTarget > this.speedHorizontal)
		{
			this.speedHorizontal += this.accelerationHorizontal;
			
			if(this.speedHorizontal > this.speedHorizontalTarget)
			{
			this.speedHorizontal = this.speedHorizontalTarget;
			}
		}
		
		else if(this.speedHorizontalTarget < this.speedHorizontal)
		{
			this.speedHorizontal -= this.accelerationHorizontal;
				
			if(this.speedHorizontal < this.speedHorizontalTarget)
			{
				this.speedHorizontal = this.speedHorizontalTarget;
			}
		}
		

		//update heading
		//always goes clockwise by adding
		if(this.headingTargetDirection)
		{
			if(this.heading < this.headingTarget)
			{
				this.heading += this.turnRate;
					
				if(this.heading > this.headingTarget)
				{
					this.heading = this.headingTarget;
				}
			}
							
			else if(this.heading > this.headingTarget)
			{
				this.heading += this.turnRate;
						
				if(this.heading > 360)
				{
					this.heading = this.heading - 360;				
				}
			}
		}

		else if(!this.headingTargetDirection)
		{
			if(this.heading > this.headingTarget)
			{
								
				this.heading -= this.turnRate;
						
				if(this.heading < this.headingTarget)
				{
					this.heading = this.headingTarget;
				}
			}
							
			else if(this.heading < this.headingTarget)
			{
				this.heading -= this.turnRate;
								
				if(this.heading < 0)
				{
					this.heading = 360 + this.heading;
				}
			}
		}
				
		//update x, y, and z position
		double polar = 90 - this.heading;

		this.x += Math.cos(Math.toRadians(polar)) * this.speedHorizontal;
		this.y += Math.sin(Math.toRadians(polar)) * this.speedHorizontal;
		this.z += this.speedVertical;


	}
}
