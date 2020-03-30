package ca.xamercier.horizon.rocket.utils;

import ca.xamercier.horizon.rocket.MainHorizon;

public enum RocketState {
	
	Initialising("    Horizon", "      V0.4", "Initialising"),
	Ready("    Horizon", "     Ready", "Ready"),
	InFlight("    Horizon", "   In Flight", "InFlight"),
	Testing("    Horizon", "    Testing", "Testing"),
	Aborded("    Horizon", "    Aborded", "Aborded"),
	FlightDone("    Horizon", "  Flight Done!", "FlightDone");

    private static RocketState state;
    private String lcdMessage;
    private String lcdMessage2;
	private String name;
    
	RocketState(String lcdMessage, String lcdMessage2, String name) {
		this.lcdMessage = lcdMessage;
		this.lcdMessage2 = lcdMessage2;
		this.name = name;
    }

	public String getLcdMessage() {
		return this.lcdMessage;
	}
	
	public String getLcdMessage2() {
		return this.lcdMessage2;
	}
	
    public static RocketState getState() {
		return state;
	}
    
    public static boolean isState(RocketState check) {
        return state == check;
    }

    public static void setState(RocketState state) {
    	RocketState.state = state;
    	MainHorizon.getCommandCenter().send("NewState: " + RocketState.getState().getName());
    	System.out.println("[Rocket] NewState: " + RocketState.getState().getName());
    }

	public String getName() {
		return name;
	}
}
