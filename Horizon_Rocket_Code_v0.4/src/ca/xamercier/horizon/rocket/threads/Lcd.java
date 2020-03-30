package ca.xamercier.horizon.rocket.threads;

import java.io.IOException;

import ca.xamercier.horizon.rocket.MainHorizon;
import ca.xamercier.horizon.rocket.utils.RocketState;

public class Lcd extends Thread {

	RocketState lastState = null;

	public void run() {
		try {
			MainHorizon.getScreen().init();
			MainHorizon.getScreen().home();
			MainHorizon.getScreen().backlight(true);
			MainHorizon.getScreen().init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		while (true) {
			
			
			if (lastState != RocketState.getState()) {
				try {
					MainHorizon.getScreen().home();
					MainHorizon.getScreen().clear();
					MainHorizon.getScreen().print(RocketState.getState().getLcdMessage());
					MainHorizon.getScreen().setCursor(0, 1);
					MainHorizon.getScreen().print(RocketState.getState().getLcdMessage2());
					lastState = RocketState.getState();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			

		}
		
		
		
		
		
	}
}