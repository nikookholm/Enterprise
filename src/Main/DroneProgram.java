package Main;

public abstract class DroneProgram implements IDroneProgram {

	private String name;
	
	public DroneProgram(String programName) {
		name = programName;
	}
	
	public String getName()
	{
		return name;
	}
	
}
