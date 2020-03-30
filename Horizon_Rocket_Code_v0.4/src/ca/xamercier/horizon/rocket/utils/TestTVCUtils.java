package ca.xamercier.horizon.rocket.utils;

import ca.xamercier.horizon.rocket.MainHorizon;

public class TestTVCUtils {

	public static void TestTvc() throws InterruptedException {
		if (RocketState.isState(RocketState.Ready)) {
			RocketState.setState(RocketState.Testing);
			MainHorizon.getCommandCenter().send("Testing TVC in progress.");
			System.out.println("[Rocket] Testing TVC in progress.");
			Thread.sleep(4000);
			RocketState.setState(RocketState.Ready);
			MainHorizon.getCommandCenter().send("Testing TVC done.");
			System.out.println("[Rocket] Testing TVC done.");
		} else {
			MainHorizon.getCommandCenter().send("This action is not permitted in the current RocketState.");
			System.out.println("[Rocket] This action is not permitted in the current RocketState.");
		}
	}

}