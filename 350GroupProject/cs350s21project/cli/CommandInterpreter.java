package cs350s21project.cli;

public class CommandInterpreter 
{
	
	private Latitude setLatitude(String str)
	{
		String [] latitude = str.split("*");
		
	}
	
	private Longtitude setLongitude(String str)
	{
		String [] longitude = str.split("*");
	}
	
	private FieldOfView setFieldOfView(String degree)
	{
		
	}
	
	private Power setPower(String power)
	{
		return new Power(Double.parseDouble(power));
	}
	
	private Sensitivity setSensitivity(String sensitivity)
	{
		
	}
	private Time setTime(String time)
	{
		
	}

	public void evaluate(String commandText)
	{
		System.out.println("Hello World");
	}
	
	
}
