package cs350s21project.cli;


//imports from javadoc
import cs350s21project.controller.CommandManagers;
import cs350s21project.controller.command.parser.ParseException;
import cs350s21project.controller.command.view.*;
import cs350s21project.datatype.*;



public class CommandInterpreter 
{
	//all fields
	private Altitude altitude;
	private AttitudeYaw azimuth;
	private CoordinateWorld3D coordinates;
	private Course course;
	private DistanceNauticalMiles distance;
	private AttitudePitch elevation;
	private String filename;
	private FieldOfView fov;
	private AgentID id;
	private Latitude latitude;
	private Longitude longitude;
	private Power power;
	private Sensitivity sensitivity;
	private int size;
	private Groundspeed speed;
	private Time time;
	
	//fields for command 1.1 create top view window
	private Latitude center;
	private Latitude extent;
	private Latitude gridSpacing ;
	private Longitude longGridSpacing;
	private Longitude horizontalExtent;
	private Longitude longCenter;
	

	public void evaluate(String commandText) throws ParseException
	{
		
		//Parses commandText into separate commands if there are more than 1
		if(commandText.isEmpty() || commandText.equals(""))
		{
			throw new ParseException("\"" + commandText + "\" is an invalid command");
		}
		
		String[] pieces = commandText.split("//");

		if (pieces.length == 0 || pieces[0].equals(""))
		{
			return;
		}

		String command = pieces[0];
		pieces = command.split(";");
		
		for (String k : pieces)
		{
			createCommand(k); //create specific command from each separated commands given
		}
	}//end of evaluate
	
	
	//where we create if statements to match our commands
	private void createCommand(String k) throws ParseException
	{
		String command = k.trim();
		
		if (!command.isEmpty() && command != null)
		{
			
			String[] pieces = command.split(" ");
			
			//Command 1.1 create top window view
			if(pieces[0].equalsIgnoreCase("create") && pieces[1].equalsIgnoreCase("window"))// if the commands first 2 strings = create and window
			{
				topViewCommand(pieces, command); // Calling helper method for command 1.1
				
			}//End of command 1.1
			
			//*****************next command code below*****************
			//else if(pieces[0].equalsIgnoreCase("???") && pieces[1].equalsIgnoreCase("???"))
			
			
			
			
			
		}
	}
	
	// Command 1.1 helper method
	private void topViewCommand(String [] pieces, String command) 
	{
		this.id = new AgentID(pieces[2]); //create AgentID through cs350s21project.datatype.AgentID from javadoc
		
		if(pieces[3].equalsIgnoreCase("top") && pieces[4].equalsIgnoreCase("view")) //If the command matches top view
		{
			 this.size = Integer.parseInt(pieces[6]);
		}
		
		//Test variables to make program work until Latitude/Longitude parse figured out
		this.center = new Latitude(49, 39, 32);
		this.extent = new Latitude(0, 10, 0);
		this.gridSpacing = new Latitude(0, 0, 30);
			 
		this.longCenter = new Longitude(117, 25, 30);
		this.horizontalExtent = new Longitude(0, 10, 0);
		this.longGridSpacing = new Longitude(0, 0, 30);
			 
		//Calling actual command to create top view window, from "use command" in command 1.1 description on pdf
		//CommandViewCreateWindowTop code is hidden in the jar, found correct parameters in javadoc
		new CommandViewCreateWindowTop(CommandManagers.getInstance(), command, this.id, this.size, this.center, this.extent,
            								this.gridSpacing, this.longCenter, this.horizontalExtent, this.longGridSpacing);
	} // End of command 1.1 helper method
	
	
	
	//Next helper method for your command
	
	
}// End of CommandInterpreter