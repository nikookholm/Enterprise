package Navigation.ScanListeners;

public class WallSL extends ScanListener {

	private boolean jobsDone = false;;
	
	@Override
	public void execute() {
		
		while (!jobsDone)
		{
			// Loop der skal køre til at opgaven er fuldført (For denne at finde en WALLPOI) 
		}
		
	}

	@Override
	public boolean hasExecuted() {
		return jobsDone;
	}

}
