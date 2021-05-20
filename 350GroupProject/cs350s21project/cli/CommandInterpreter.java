package cs350s21project.cli;

public class CommandInterpreter 
{

	CommandInterpreter()
	{
		evaluate("Hello World");
	}
	
	public void evaluate(String commandText)
	{
		System.out.println(commandText);
	}
	
	
}
