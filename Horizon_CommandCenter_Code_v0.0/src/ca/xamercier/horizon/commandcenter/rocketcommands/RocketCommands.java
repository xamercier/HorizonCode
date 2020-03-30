package ca.xamercier.horizon.commandcenter.rocketcommands;

import ca.xamercier.horizon.commandcenter.CommandCenter;
import ca.xamercier.horizon.commandcenter.MainCommandCenterFrame;

public class RocketCommands {

	public static void LaunchFlight() {
		CommandCenter.getServer().sendToRocket("FlightCommand: Launch");
	}

	public static void AbortFlight() {
		CommandCenter.getServer().sendToRocket("FlightCommand: Abort");
	}

	public static void TestTVC() {
		CommandCenter.getServer().sendToRocket("FlightCommand: TestTVC");
	}

	public static void setCommandCenterState(String msg) {
		String[] parts = msg.split(":");
		if (parts.length != 2) {
			return;
		}
		if (msg.contains("NewState:")) {
			MainCommandCenterFrame.getlblHorizonState().setText("Horizon State: " + parts[1]);
			;
		}
	}

}