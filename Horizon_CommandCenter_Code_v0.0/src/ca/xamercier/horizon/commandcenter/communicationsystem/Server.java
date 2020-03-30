package ca.xamercier.horizon.commandcenter.communicationsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import ca.xamercier.horizon.commandcenter.CommandCenter;
import ca.xamercier.horizon.commandcenter.MainCommandCenterFrame;
import ca.xamercier.horizon.commandcenter.rocketcommands.RocketCommands;

public class Server extends Thread {

	private static ArrayList<ServerClient> clientList = new ArrayList<ServerClient>();

	private ServerSocket listenSocket = null;
	Socket clientSocket = null;
	BufferedReader in = null;

	// When we receive something
	public void getMessage(String msg) throws IOException {
		//MainCommandCenter.getLogger().info(msg);
		if (msg.startsWith("x")) {
			MainCommandCenterFrame.getLblXyz().setText(msg);
		} else {
			System.out.println("[Rocket] " + msg);
			MainCommandCenterFrame.getTextArea().append("\n" + "[Rocket] " + msg);
			RocketCommands.setCommandCenterState(msg);
		}
	}

	public void sendToRocket(String msg) {
		//MainCommandCenter.getLogger().info(msg);
		System.out.println("[CommandCenter] " + msg);
		MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] " + msg);
		for (ServerClient client : clientList) {
			client.send(msg);
		}
	}

	public void init(int port) {
		try {
			listenSocket = new ServerSocket(port);
			System.out.println("[CommandCenter] Communication system initiated.");
			MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] Communication system initiated.");
			//MainCommandCenter.getLogger().info("[CommandCenter] Communication system initiated.");
		} catch (IOException e1) {
			System.out.println("[CommandCenter] Communication system could not be initiated.");
			MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] Communication system could not be initiated.");
			//MainCommandCenter.getLogger().info("[CommandCenter] Communication system could not be initiated.");
		}
		start();
	}

	public void run() {
		while (true) {
			try {
				clientSocket = listenSocket.accept();
				System.out.println("[CommandCenter] Rocket is connected to the CommandCenter.");
				MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] Rocket is connected to the CommandCenter.");
				//MainCommandCenter.getLogger().info("[CommandCenter] Rocket is connected to the CommandCenter.");
				CommandCenter.isRocketConnected = true;
			} catch (IOException e) {
				return;
			}
			ServerClient client = new ServerClient(clientSocket, this);
			clientList.add(client);
			client.start();
		}
	}

	public static ArrayList<ServerClient> getClientList() {
		return clientList;
	}

	public void stopServer() {
		try {
			System.out.println("[CommandCenter] The communication system was shut down.");
			MainCommandCenterFrame.getTextArea().append("\n[CommandCenter] The communication system was shut down.");
			//MainCommandCenter.getLogger().info("[CommandCenter] The communication system was shut down.");
			listenSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (ServerClient serverClient : clientList) {
			serverClient.stopServerClient();
		}
	}

}