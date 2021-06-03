package cs350s21project.cli;

import cs350s21project.controller.CommandManagers;
import cs350s21project.controller.command.parser.ParseException;
import cs350s21project.controller.command.sensor.CommandSensorDefineRadar;

import cs350s21project.controller.command.sensor.CommandSensorDefineDistance;
import cs350s21project.controller.command.sensor.CommandSensorDefineRadar;
import cs350s21project.controller.command.sensor.CommandSensorDefineSonarActive;

import cs350s21project.controller.command.sensor.CommandSensorDefineSonarPassive;
import cs350s21project.controller.command.sensor.CommandSensorDefineThermal;
import cs350s21project.controller.command.view.*;
import cs350s21project.datatype.*;
import cs350s21project.controller.command.actor.CommandActorCreateActor;
import cs350s21project.controller.command.actor.CommandActorDeployMunition;
import cs350s21project.controller.command.actor.CommandActorLoadMunition;
import cs350s21project.controller.command.actor.CommandActorSetAltitudeDepth;
import cs350s21project.controller.command.actor.CommandActorSetCourse;
import cs350s21project.controller.command.actor.CommandActorSetState;
import cs350s21project.controller.command.misc.CommandMiscExit;

import cs350s21project.controller.command.misc.CommandMiscLoad;

import cs350s21project.controller.command.misc.CommandMiscPause;
import cs350s21project.controller.command.misc.CommandMiscSetUpdate;

import cs350s21project.controller.command.munition.CommandMunitionDefineBomb;
import cs350s21project.controller.command.munition.CommandMunitionDefineDepthCharge;
import cs350s21project.controller.command.munition.CommandMunitionDefineMissile;




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
	private AgentID sonartype;
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
					
				}
				else if(pieces[2].equalsIgnoreCase("course"))
				{
					setCourseCommand(pieces,command);
				}
				else if(pieces[2].equalsIgnoreCase("altitude"))
				{	
					
				}
			}
				
			else if(pieces[0].equalsIgnoreCase("@load"))
			{
				loadCommand(pieces,command);
			}
			else if(pieces[0].equalsIgnoreCase("@pause"))
			{
				 pauseCommand(pieces, command);
			}
			else if(pieces[0].equalsIgnoreCase("@resume"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@set"))
			{
				updateTimeCommand(pieces, command);
			}
			else if(pieces[0].equalsIgnoreCase("@wait"))
			{
				
			}
			else if(pieces[0].equalsIgnoreCase("@force"))
			{
				forceCommand(pieces,command);
			}
			
			else if(pieces[0].equalsIgnoreCase("@exit"))
			{
				exitCommand(pieces,command);
			}
			
		}

	}
	private Latitude setLatitude(String str)
	{
		String [] lat = str.split("[*#*\"]");
		return new Latitude(Integer.parseInt(lat[0]),Integer.parseInt(lat[1]),Double.parseDouble(lat[2]));
	}
	private Longitude setLongtitude(String str)
	{
		String [] lon = str.split("[*#*\"]");
		return new Longitude(Integer.parseInt(lon[0]),Integer.parseInt(lon[1]),Double.parseDouble(lon[2]));
	}
	private CoordinateWorld3D setCoordinates(String coordinate)
	{
		String[] coordinates = coordinate.split("/");
		Latitude lat = setLatitude(coordinates[0]);
		Longitude lon = setLongtitude(coordinates[1]);
		Altitude alt = new Altitude(Integer.parseInt(coordinates[2]));
		CoordinateWorld3D location = new CoordinateWorld3D(lat,lon,alt);
		
		return location;
	}
	
	private Power setPower(String power)
	{
		return new Power(Double.parseDouble(power));
	}
	private Sensitivity setSensitivity(String sensitivity)
	{
		return new Sensitivity(Double.parseDouble(sensitivity));
	}
	private FieldOfView setFov(String degree)
	{
		AngleNavigational view = new AngleNavigational(Double.parseDouble(degree));
		return new FieldOfView(view);
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
		
		String temp[] = pieces[6].split("/");
		
		String [] lat = temp[0].split("[*#*\"]");
		this.latitude = new Latitude(Integer.parseInt(lat[0]),Integer.parseInt(lat[1]),Double.parseDouble(lat[2]));
		
		String [] lon = temp[1].split("[*#*\"]");
		this.longitude = new Longitude(Integer.parseInt(lon[0]), Integer.parseInt(lon[1]),Double.parseDouble(lon[2]));
		
		this.altitude = new Altitude(Double.parseDouble(temp[2]));
		
		this.coordinates = new CoordinateWorld3D(this.latitude,this.longitude, this.altitude);
		
		this.course = new Course(Double.parseDouble(pieces[9]));
				
		this.speed = new Groundspeed(Double.parseDouble(pieces[11]));
		
		cmd.schedule(new CommandActorCreateActor(cmd, command, this.id, this.id2, this.coordinates, this.course, this.speed));
	}
	
	
	
	//Munitions
	private void bombCommand(String [] pieces, String command)
	{
		this.id = new AgentID(pieces[3]);
		cmd.schedule(new CommandMunitionDefineBomb(cmd,command,id));
	
		
	}
	private void missileCommand(String [] pieces, String command)
	{


		this.id = new AgentID(pieces[3]);
		this.sensorId = new AgentID(pieces[6]);
		this.fuzeId = new AgentID(pieces[8]);
		distance = new DistanceNauticalMiles(Double.parseDouble(pieces[11]));

		
		
		cmd.schedule(new CommandMunitionDefineMissile(cmd,command,id,this.sensorId,this.fuzeId,distance));
		
		
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
		
		
		fov =  this.setFov(pieces[8]);
		power = this.setPower(pieces[10]);
		sensitivity = this.setSensitivity(pieces[12]);
		
		cmd.schedule(new CommandSensorDefineRadar(cmd,command,sensorId,fov,power, sensitivity));

		

		
	}
	private void thermalCommand(String[] pieces,String command)
	{
		sensorId = new AgentID(pieces[3]);
		
		fov =  this.setFov(pieces[8]);
		sensitivity = this.setSensitivity(pieces[10]);
		
		
		
		cmd.schedule(new CommandSensorDefineThermal(cmd,command,sensorId,fov,sensitivity));
		
	}
	
	private void sonarCommand(String[] pieces,String command)
	{

		sonartype = new AgentID(pieces[3]);
		
		sensorId = new AgentID(pieces[4]);
		/*if(sonartype.equalsIgnoreCase("passive"))
		{
			sensitivity = this.setSensitivity(pieces[7]);
			cmd.schedule(new CommandSensorDefineSonarPassive(cmd,command,sensorId,sensitivity));
			
		}
		else if(type.equalsIgnoreCase("active"))
		{
			System.out.print("active sensor");
		}*/
	//	else
		//{
		//	System.out.print("error");
	//	}
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
		cmd.schedule(new CommandActorSetCourse(cmd,command,id,course));
		
	}
	
	private void setDeployMunition(String[] pieces, String command) 
	{
		this.id = new AgentID(pieces[1]);
		this.id2 = new AgentID(pieces[4]);
		cmd.schedule(new CommandActorDeployMunition(cmd, command, this.id, this.id2));
		
	}

	
	
	//MISC
	private void loadCommand(String[] pieces,String command)
	{
		this.id = new AgentID(pieces[1]);
		cmd.schedule(new CommandMiscLoad(cmd,command, pieces[1]));
	}
	private void forceCommand(String[] pieces,String command)
	{
		this.id = new AgentID(pieces[1]);
		
		coordinates = setCoordinates(pieces[4]);
		course = new Course(Double.parseDouble(pieces[7]));
		speed = new Groundspeed(Double.parseDouble(pieces[9]));
		
		cmd.schedule(new CommandActorSetState(cmd, command,id, coordinates,course,speed));

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
	
	
	//Next helper method for your command
	
	
}// End of CommandInterpreter