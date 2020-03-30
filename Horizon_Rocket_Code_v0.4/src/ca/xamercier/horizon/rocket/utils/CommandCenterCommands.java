package ca.xamercier.horizon.rocket.utils;

import ca.xamercier.horizon.rocket.MainHorizon;

public class CommandCenterCommands {
	
	public static void CommandCenter(String msg) throws InterruptedException {
		String[] parts = msg.split(":");
		if (parts.length != 2) {
			return;
		}
		if (msg.contains("FlightCommand:")) {
			if(parts[1].equalsIgnoreCase(" Launch")) {
				if(RocketState.isState(RocketState.Ready)) {
					MainHorizon.getFlightControllerThread().start();
				}
			} else if(parts[1].equalsIgnoreCase(" Abort")) {
				if(RocketState.getState().equals(RocketState.InFlight)) {
					RocketState.setState(RocketState.Aborded);
				}
			} else if(parts[1].equalsIgnoreCase(" TestTVC")) {
				TestTVCUtils.TestTvc();
			}
		}
	}

}
