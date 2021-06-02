package cs350s21project.cli;

import cs350s21project.controller.CommandManagers;
import cs350s21project.controller.command.parser.ParseException;
import cs350s21project.controller.command.sensor.CommandSensorDefineAcoustic;
import cs350s21project.controller.command.sensor.CommandSensorDefineDepth;
import cs350s21project.controller.command.sensor.CommandSensorDefineDistance;
import cs350s21project.controller.command.sensor.CommandSensorDefineRadar;
import cs350s21project.controller.command.sensor.CommandSensorDefineSonarActive;
import cs350s21project.controller.command.sensor.CommandSensorDefineSonarPassive;
import cs350s21project.controller.command.sensor.CommandSensorDefineThermal;
import cs350s21project.controller.command.sensor.CommandSensorDefineTime;
import cs350s21project.controller.command.view.*;
import cs350s21project.datatype.*;
import cs350s21project.controller.command.actor.CommandActorCreateActor;
import cs350s21project.controller.command.actor.CommandActorDeployMunition;
import cs350s21project.controller.command.actor.CommandActorDeployMunitionShell;
import cs350s21project.controller.command.actor.CommandActorLoadMunition;
import cs350s21project.controller.command.actor.CommandActorSetAltitudeDepth;
import cs350s21project.controller.command.actor.CommandActorSetCourse;
import cs350s21project.controller.command.misc.CommandMiscExit;
import cs350s21project.controller.command.misc.CommandMiscPause;
import cs350s21project.controller.command.misc.CommandMiscResume;
import cs350s21project.controller.command.misc.CommandMiscSetUpdate;
import cs350s21project.controller.command.misc.CommandMiscWait;
import cs350s21project.controller.command.munition.CommandMunitionDefineBomb;
import cs350s21project.controller.command.munition.CommandMunitionDefineDepthCharge;
import cs350s21project.controller.command.munition.CommandMunitionDefineMissile;
import cs350s21project.controller.command.munition.CommandMunitionDefineShell;
import cs350s21project.controller.command.munition.CommandMunitionDefineTorpedo;




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
	private AgentID id2;
	
	
	

	public void evaluate(String commandText) throws ParseException
	{
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
			createCommand(k); 
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
					defineShipCommand(pieces, command);
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
					else if(pieces[2].equalsIgnoreCase("shell"))
					{
						shellCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("torpedo"))
					{
						torpedoCommand(pieces,command);
					}
					else if(pieces[1].equalsIgnoreCase("munition_shell"))
					{
					 deployMunitionShellCommand(pieces, command);
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
					else if(pieces[2].equalsIgnoreCase("distance"))
					{
						distanceSensorCommand(pieces,command);
					}
					else if(pieces[2].equalsIgnoreCase("sonar"))
					{
						if(pieces[3].equalsIgnoreCase("active"))
						{
							sonarCommandActive(pieces, command);
						}
						
						else if(pieces[3].equalsIgnoreCase("passive"))
						{
							sonarCommand(pieces,command);
						}
					
					}
					if(pieces[2].equalsIgnoreCase("acoustic"))
					{
						acousticCommand(pieces,command);
					}
					if(pieces[2].equalsIgnoreCase("depth"))
					{
						depthCommand(pieces,command);
					}
					if(pieces[2].equalsIgnoreCase("time"))
					{
						timeCommand(pieces,command);
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
				
				if(pieces[2].equalsIgnoreCase("load"))
				{
					setLoadMunitionCommand(pieces,command);
				}
				else if(pieces[2].equalsIgnoreCase("deploy"))
				{
					setDeployMunition(pieces, command);
				}
				else if(pieces[2].equalsIgnoreCase("speed "))
				{
					setSpeedCommand(pieces, command);
				}
				else if(pieces[2].equalsIgnoreCase("course"))
				{
					setCourseCommand(pieces,command);
				}
				else if(pieces[2].equalsIgnoreCase("altitude"))
				{	
					setAltitudeCommand(pieces, command);
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
				 pauseCommand(pieces, command);
			}
			else if(pieces[0].equalsIgnoreCase("@resume"))
			{
				resumeCommand(pieces, command);
			}
			else if(pieces[0].equalsIgnoreCase("@set"))
			{
				updateTimeCommand(pieces, command);
			}
			else if(pieces[0].equalsIgnoreCase("@wait"))
			{
				waitCommand(pieces, command);
			}
			else if(pieces[0].equalsIgnoreCase("@force"))
			{
				forceCommand(pieces, command);
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
	
	private void defineShipCommand(String [] pieces, String command)
	{
		//not finished
	}
	
	private void setSpeedCommand(String [] pieces, String command) {
		this.id = new AgentID(pieces[1]);
		this.speed = new Groundspeed(Double.parseDouble(pieces[2]));
		
		cmd.schedule(new CommandActorSetAltitudeDepth(cmd, command, this.id, this.altitude));
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
		DistanceNauticalMiles distance = new DistanceNauticalMiles(Double.parseDouble(pieces[11]));

		this.id = new AgentID(pieces[3]);
		this.sensorId = new AgentID(pieces[6]);
		this.fuzeId = new AgentID(pieces[8]);
		distance = new DistanceNauticalMiles(Double.parseDouble(pieces[11]));

		
		
		cmd.schedule(new CommandMunitionDefineMissile(cmd,command,id,sensorId,fuzeId,distance));
		System.out.print("Created"+ this.id + "with sensor" + this.sensorId + "with fuze " + this.fuzeId + " and arming distance of: "+ distance);
		
	}
	
	private void depthChargeCommand(String[] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		this.id2 = new AgentID(pieces[6]);
		
		cmd.schedule(new CommandMunitionDefineDepthCharge(cmd, command, this.id, this.id2));
	}
	
	private void deployMunitionShellCommand(String [] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		this.fuzeId = new AgentID(pieces[8]);
		this.azimuth = new AttitudeYaw(Double.parseDouble(pieces[11]));
		this.elevation = new AttitudePitch(Double.parseDouble(pieces[2]));
		cmd.schedule(new CommandActorDeployMunitionShell(cmd,command,this.id, this.fuzeId, this.azimuth, this.elevation));
		
	}
	private void shellCommand(String [] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		
		cmd.schedule(new CommandMunitionDefineShell(cmd,command,id));
		
	}
	
	private void torpedoCommand(String [] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		this.sensorId = new AgentID(pieces[6]);
		this.fuzeId = new AgentID(pieces[8]);
		this.time = new Time(Double.parseDouble(pieces[7]));
		cmd.schedule(new CommandMunitionDefineTorpedo(cmd,command,this.id,this.sensorId,this.fuzeId,this.time));
		
	}
	
	//sensors
	private void radarCommand(String [] pieces,String command)
	{
		this.id = new AgentID(pieces[3]);
		double temp = Double.parseDouble(pieces[8]);
		AngleNavigational angleDegree = new AngleNavigational(temp);
		this.fov =  new FieldOfView(angleDegree);
		this.power = new Power(Double.parseDouble(pieces[10]));
		this.sensitivity = new Sensitivity(Double.parseDouble(pieces[12]));

		cmd.schedule(new CommandSensorDefineRadar(cmd,command,this.id,this.fov,this.power, this.sensitivity));
		
	}
	private void thermalCommand(String[] pieces,String command)
	{
		this.id = new AgentID(pieces[3]);
		double temp = Double.parseDouble(pieces[8]);
		AngleNavigational angleDegree = new AngleNavigational(temp);
		this.fov =  new FieldOfView(angleDegree);
		this.sensitivity = new Sensitivity(Double.parseDouble(pieces[10]));

		cmd.schedule(new CommandSensorDefineThermal(cmd,command,this.id,this.fov,this.sensitivity));
		
	}
	
	private void sonarCommand(String[] pieces,String command)
	{
		this.id = new AgentID(pieces[4]);
		this.sensitivity = new Sensitivity(Double.parseDouble(pieces[7]));

		cmd.schedule(new CommandSensorDefineSonarPassive(cmd,command,this.id,this.sensitivity));
	}
	

	private void sonarCommandActive(String[] pieces, String command) 
	{
		this.id = new AgentID(pieces[4]);
		this.power = new Power(Double.parseDouble(pieces[7]));
		this.sensitivity = new Sensitivity(Double.parseDouble(pieces[9]));
		
		cmd.schedule(new CommandSensorDefineSonarActive(cmd, command, this.id, this.power, this.sensitivity));
		
	}
	

	private void distanceSensorCommand(String[] pieces, String command) 
	{
		this.id = new AgentID(pieces[3]);
		this.distance = new DistanceNauticalMiles(Double.parseDouble(pieces[7]));
		
		cmd.schedule(new CommandSensorDefineDistance(cmd, command, this.id, this.distance));
		
	}
	
	private void acousticCommand(String[] pieces,String command)
	{
		sensorId = new AgentID(pieces[3]);
		this.sensitivity = new Sensitivity(Double.parseDouble(pieces[7]));

		cmd.schedule(new CommandSensorDefineAcoustic(cmd,command,this.sensorId,this.sensitivity));
		
	}
	
	private void depthCommand(String[] pieces,String command)
	{
		sensorId = new AgentID(pieces[3]);
		this.altitude = new Altitude(Double.parseDouble(pieces[2]));
		cmd.schedule(new CommandSensorDefineDepth(cmd,command,this.sensorId,this.altitude));
		
	}
	
	private void timeCommand(String[] pieces,String command)
	{
		sensorId = new AgentID(pieces[3]);
		this.time = new Time(Double.parseDouble(pieces[7]));
		cmd.schedule(new CommandSensorDefineTime(cmd,command,this.sensorId,this.time));
		
	}
	
	//Set Commands
	private void setLoadMunitionCommand(String[] pieces,String command)
	{
		this.id= new AgentID(pieces[1]);
		this.munitionId = new AgentID(pieces[4]);
		cmd.schedule(new CommandActorLoadMunition(cmd,command,id,munitionId));
		System.out.println("Set munition for "+ this.id + "from "+this.munitionId);
	}
	private void setCourseCommand(String[] pieces,String command)
	{
		this.id = new AgentID(pieces[1]);
		course = new Course(Double.parseDouble(pieces[3]));
		cmd.schedule(new CommandActorSetCourse(cmd,command,this.id,this.course));
		
	}
	
	private void setDeployMunition(String[] pieces, String command) 
	{
		this.id = new AgentID(pieces[1]);
		this.id2 = new AgentID(pieces[4]);
		cmd.schedule(new CommandActorDeployMunition(cmd, command, this.id, this.id2));
		
	}

	
	
	//MISC
	private void forceCommand(String[] pieces,String command)
	{
		this.id = new AgentID(pieces[1]); ///This isnt finished ************
	}
	
	private void exitCommand(String[] pieces, String command)
	{
		id = new AgentID(pieces[2]);
		cmd.schedule( new CommandMiscExit(cmd,command));
	}
	
	private void pauseCommand(String[] pieces, String command)
	{
		cmd.schedule(new CommandMiscPause(cmd, command));
	}
	
	private void updateTimeCommand(String[] pieces, String command) 
	{
		cmd.schedule(new CommandMiscSetUpdate(cmd, command, this.time));
	}
	
	private void waitCommand(String[] pieces, String command)
	{

		cmd.schedule( new CommandMiscWait(cmd,command,this.time));
	}
	
	private void resumeCommand(String[] pieces, String command)
	{
		cmd.schedule( new CommandMiscResume(cmd,command));
	}
}
	
	//Next helper method for your command