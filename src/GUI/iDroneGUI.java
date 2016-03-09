package GUI;

import de.yadrone.base.navdata.HDVideoStreamData;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.navdata.VideoStreamData;

public interface iDroneGUI {
	
	public VideoListener videoListener = new VideoListener() {
		
		@Override
		public void receivedVideoStreamData(VideoStreamData arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void receivedHDVideoStreamData(HDVideoStreamData arg0) {
			// TODO Auto-generated method stub
			
		}
	};

}
