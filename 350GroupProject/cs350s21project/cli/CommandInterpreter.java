package cs350s21project.cli;


//imports from javadoc
import cs350s21project.controller.CommandManagers;
import cs350s21project.controller.command.parser.ParseException;
import cs350s21project.controller.command.view.*;
import cs350s21project.datatype.*;



public class CommandInterpreter 
{
	CommandManagers cmd = CommandManagers.getInstance();
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
	private AgentID fuzeId;
	private AgentID sensorId;
	private AgentID munitionId;
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
			if(pieces[0].equalsIgnoreCase("create")// if the commands first string = create
			{
				
				if(pieces[1].equalsIgnoreCase("window"))//if the command second string = window
				{
					topViewCommand(pieces, command); // Calling helper method for command 1.1
				}
				
				
				else if(pieces[1].equalsIgnoreCase("actor"))//if the command second string = actor
				{
					// Calling helper method for command 1.1
				}
				
				
				
			}
			
			else if(pieces[0].equalsIgnoreCase("define")// if the commands first string = define
			{
				if(pieces[1].equalsIgnoreCase("ship"))//if the command second string =ship
				{
					
				}
				else if(pieces[1].equalsIgnoreCase("munition"))//if the command second string = munitions
				{
					if(pieces[2].equalsIgnoreCase("bomb"))
					{
						bombCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("missile"))
					{
						missileCommand(pieces,command);
					}
					
					//next munition goes here.
				}
				else if(pieces[1].equalsIgnoreCase("sensor"))//if the command second string = sensor
				{
					if(pieces[2].equalsIgnoreCase("radar"))
					{
						radarCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("thermal"))
					{
						thermalCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("sonar"))//passive
					{
						sonarCommand(pieces,command);
					}
					
					//next sensor goes here
					
				}
			}
			else if(pieces[0].equalsIgnoreCase("set"))
			{
				if(pieces[2].equalsIgnoreCase("load"))
				{
					setLoadMunitionCommand(pieces,command);
				}
				else if(pieces[2].equalsIgnoreCase("deploy"))
				{
						
				}
				else if(pieces[2].equalsIgnoreCase("speed "))
				{
					
				}
				else if(pieces[2].equalsIgnoreCase("course"))
				{
					setCourseCommand(pieces,command);
				}
				else if(pieces[2].equalsIgnoreCase("altitude"))
				{	
					
				}
			}
				
				
			
			else if(pieces[0].equalsIgnoreCase("delete"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@load"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@pause"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@resume"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@set"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@wait"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@force"))
			{
				
			}
			
			else if(pieces[0].equalsIgnoreCase("@exit"))
			{
				exitCommand(pieces,command);
			}
			
			
			
			
			
		}

	}
	
	// Views
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
	
	
	
	
	
	//Munitions
	private void bombCommand(String [] pieces, String command)
	{
		this.id = new AgentId(pieces[3]);
		cmd.schedule(new CommandMunitionDefineBomb(cmd,command,id));
		System.out.print("Created"+ this.id);
		
	}
	private void missileCommand(String [] pieces, String command)
	{
		this.id = new AgentId(pieces[3]);
		this.sensorId = new AgentId(pieces[6]);
		this.fuzeId = new AgentId(pieces[8]);
		distance = new DistanceNauticalMiles(Double.parseDouble(new AgentId(pieces[11])));
		
		
		cmd.schedule(new CommandMunitionDefineMissile(cmd,command,id,sensorId,fuzeId,distance));
		System.out.print("Created"+ this.id + "with sensor" + this.sensorId + "with fuze " + this.fuzeId + " and arming distance of: "+ distance);
		
	}
	
	
	
	//sensors
	private void radarCommand(String [] pieces,String command)
	{
		sensorId = new AgentId(pieces[3]);
		fov =  new AgentId(pieces[8]);
		power = new AgentId(pieces[10]);
		sensitivity = new AgentId(pieces[12]);
		
		sensitivity = Double.parseDouble(sensitivity);
		power = Double.parseDouble(power);
		AngleNavigational angleDegree = new AngleNavigational(Double.parseDouble(fov));
		
		cmd.schedule(new CommandSensorDefineRadar(cmd,command,sensorId,angleDegree,power, sensitivity));
		
	}
	private void thermalCommand(String[] pieces,String command)
	{
		sensorId = new AgentId(pieces[3]);
		fov =  new AgentId(pieces[8]);
		sensitivity = new AgentId(pieces[10]);
		
		sensitivity = Double.parseDouble(sensitivity);
		AngleNavigational angleDegree = new AngleNavigational(Double.parseDouble(fov));
		
		cmd.schedule(new CommandSensorDefineThermal(cmd,command,sensorId,angleDegreee,sensitivity));
		
	}
	private void sonarCommand(String[] pieces,String command)
	{
		String type = new AgentId(pieces[3]);
		sensorId = new AgentId(pieces[4]);
		if(type.equals("passive"))
		{
			sensitivity = new AgentId(pieces[7]);
			sensitivity = Double.parseDouble(sensitivity);
			cmd.schedule(new CommandSensorDefineSonarPassive(cmd,command,sensorId,sensitivity));
			
		}
		else if(type.contentEquals("active"))
		{
			
		}
		else
		{
			System.out.print("error");
		}
	}
	
	
	
	//Set Commands
	private void setLoadMunitionCommand(String[] pieces,String command)
	{
		this.id= new AgentId(pieces[1]);
		this.munitionId = new AgentId(pieces[4]);
		cmd.schedule(new CommanderActorLoadMunition(cmd,command,id,munitionId));
		System.out.println("Set munition for "+ this.id + "from "+this.munitionId);
	}
	private void setCourseCommand(String[] pieces,String command)
	{
		this.Id = new AgentId(pieces[1]);
		course = new Course(Double.parseDouble(pieces[3]));
		cmd.schedule(new CommandActorSetCourse(cmd,command,id,course));
		
	}
	
	
	
	//MISC
	private void forceCommand(String[] pieces,String command)
	{
		this.id = new AgentId(pieces[1]);
	}
	private void exitCommand(String[] pieces, String command)
	{
		id = new AgentId(pieces[2]);
		cmd.schedule( new CommandViewExitWindow(cmd,command,id));
	}
	
	
	
	//Next helper method for your command
	
	
}// End of CommandInterpreter