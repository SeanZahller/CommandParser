/*
 * Sean Zahller
 * 5/3/2021
 * CSCD 350 Task3.5 Final Iteration
 */

package cs350s21task2;

public class Airplane 
{
	private String id;
	private Mover mover;
	
	public Airplane(String id, Mover mover)
	{
		this.id = id;
		this.mover = mover;
	}
	
	public String getID()
	{
		return this.id;
	}
	
	public Mover getMover()
	{
		return this.mover;
	}
	
	public void update()
	{
		mover.update();
	}

}
