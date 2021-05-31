package cs350s21project.cli;


//imports from javadoc
import cs350s21project.controller.CommandManagers;
import cs350s21project.controller.command.*;
import cs350s21project.controller.command.actor.CommandActorCreateActor;
import cs350s21project.controller.command.actor.CommandActorSetAltitudeDepth;
import cs350s21project.controller.command.misc.CommandMiscExit;
import cs350s21project.controller.command.munition.CommandMunitionDefineBomb;
import cs350s21project.controller.command.munition.CommandMunitionDefineDepthCharge;
import cs350s21project.controller.command.munition.CommandMunitionDefineMissile;
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
	private AgentID id2;
	
	
	

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
	

	private void createCommand(String k) throws ParseException
	{
		String command = k.trim();
		String [] pieces;
		
		if (!command.isEmpty() && command != null)
		{
			
			pieces = command.split(" ");
			

			if(pieces[0].equalsIgnoreCase("create"))
			{
				
				if(pieces[1].equalsIgnoreCase("window"))
				{
					topViewCommand(pieces, command);
				}
				
				
				else if(pieces[1].equalsIgnoreCase("actor"))
				{
					createActorCommand(pieces, command);
				}
				
				
				
			}
			
			else if(pieces[0].equalsIgnoreCase("define"))
			{
				if(pieces[1].equalsIgnoreCase("ship"))
				{
					
				}
				else if(pieces[1].equalsIgnoreCase("munition"))
				{
					if(pieces[2].equalsIgnoreCase("bomb"))
					{
						bombCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("missile"))
					{
						missileCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("depth_charge"))
					{
						depthChargeCommand(pieces,command);
					}
					
					//next munition goes here.
				}
				else if(pieces[1].equalsIgnoreCase("sensor"))
				{
					if(pieces[2].equalsIgnoreCase("radar"))
					{
						radarCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("thermal"))
					{
						thermalCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("sonar"))
					{
						sonarCommand(pieces,command);
					}
					
					//next sensor goes here
					
				}
				
				
			}
			else if(pieces[0].equalsIgnoreCase("delete"))
			{
				if(pieces[1].equalsIgnoreCase("window"))
				{
					deleteWindowCommand(pieces, command);
				}
			}
			else if(pieces[0].equalsIgnoreCase("set"))
			{
				if(pieces[2].equalsIgnoreCase("altitude|depth"))
				{
					setAltitudeCommand(pieces, command);
				}
			}
			else if(pieces[0].equalsIgnoreCase("@load"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@wait"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@pause"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@set"))
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
		this.id = new AgentID(pieces[2]); 
		this.size = Integer.parseInt(pieces[6]);

		//Test variables to make program work until Latitude/Longitude parse figured out
		this.center = new Latitude(49, 39, 32);
		this.extent = new Latitude(0, 10, 0);
		this.gridSpacing = new Latitude(0, 0, 30);
			 
		this.longCenter = new Longitude(117, 25, 30);
		this.horizontalExtent = new Longitude(0, 10, 0);
		this.longGridSpacing = new Longitude(0, 0, 30);
			 
		cmd.schedule(new CommandViewCreateWindowTop(cmd, command, this.id, this.size, this.center, this.extent,
            								this.gridSpacing, this.longCenter, this.horizontalExtent, this.longGridSpacing));
	} 
	
	
	private void deleteWindowCommand(String [] pieces, String command) 
	{
		this.id = new AgentID(pieces[2]);
		
		cmd.schedule(new CommandViewDeleteWindow(cmd, command, this.id));
	}
	
	
	
	//actors
	private void setAltitudeCommand(String [] pieces, String command) 
	{
		this.id = new AgentID(pieces[1]);
		this.altitude = new Altitude(Double.parseDouble(pieces[2]));

		cmd.schedule(new CommandActorSetAltitudeDepth(cmd, command, this.id, this.altitude));
	}
	
	private void createActorCommand(String [] pieces, String command) 
	{
		this.id = new AgentID(pieces[2]);
		this.id2 = new AgentID(pieces[4]);
	
		/* Must parse further and split up lat, long, and alt
		this.latitude = 
		this.longitude = 
		this.altitude = 
		*/
		
		this.coordinates = new CoordinateWorld3D(this.latitude,this.longitude, this.altitude);
		
		cmd.schedule(new CommandActorCreateActor(cmd, command, this.id, this.id2, this.coordinates, this.course, this.speed));
	}
	
	
	
	//Munitions
	private void bombCommand(String [] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		cmd.schedule(new CommandMunitionDefineBomb(cmd,command,id));
		System.out.print("Created"+ this.id);
		
	}
	private void missileCommand(String [] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		this.sensorId = new AgentID(pieces[6]);
		this.fuzeId = new AgentID(pieces[8]);
		//DistanceNauticalMiles distance = new DistanceNauticalMiles(Double.parseDouble(new AgentID(pieces[11])));
		
		
		cmd.schedule(new CommandMunitionDefineMissile(cmd,command,id,sensorId,fuzeId,distance));
		System.out.print("Created"+ this.id + "with sensor" + this.sensorId + "with fuze " + this.fuzeId + " and arming distance of: "+ distance);
		
	}
	
	private void depthChargeCommand(String[] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		this.id2 = new AgentID(pieces[6]);
		
		cmd.schedule(new CommandMunitionDefineDepthCharge(cmd, command, this.id, this.id2));
	}
	
	
	
	//sensors
	private void radarCommand(String [] pieces,String command)
	{
		sensorId = new AgentID(pieces[3]);
		//fov =  new AgentID(pieces[8]);
		//power = new AgentId(pieces[10]);
		//sensitivity = new AgentId(pieces[12]);
		
		//sensitivity = Double.parseDouble(sensitivity);
		//power = Double.parseDouble(power);
		//AngleNavigational angleDegree = new AngleNavigational(Double.parseDouble(fov));
		
		//cmd.schedule(new CommandSensorDefineRadar(cmd,command,sensorId,angleDegree,power, sensitivity));
		
	}
	private void thermalCommand(String[] pieces,String command)
	{
		//sensorId = new AgentId(pieces[3]);
		//fov =  new AgentId(pieces[8]);
		//sensitivity = new AgentId(pieces[10]);
		
		//sensitivity = Double.parseDouble(sensitivity);
		//AngleNavigational angleDegree = new AngleNavigational(Double.parseDouble(fov));
		
		//cmd.schedule(new CommandSensorDefineThermal(cmd,command,sensorId,angleDegreee,sensitivity));
		
	}
	private void sonarCommand(String[] pieces,String command)
	{
		//String type = new AgentID(pieces[3]);
		sensorId = new AgentID(pieces[4]);
		//if(type.equals("passive"))
		{
			//sensitivity = new AgentId(pieces[7]);
			//sensitivity = Double.parseDouble(sensitivity);
			//cmd.schedule(new CommandSensorDefineSonarPassive(cmd,command,sensorId,sensitivity));
			
		}
		//else if(type.contentEquals("active"))
		{
			
		}
	//	else
		{
			System.out.print("error");
		}
	}
	
	
	
	//MISC
	private void exitCommand(String[] pieces, String command)
	{
		id = new AgentID(pieces[2]);
		cmd.schedule( new CommandMiscExit(cmd,command));
	}
	
	
	
	//Next helper method for your command
	
	
}// End of CommandInterpreter