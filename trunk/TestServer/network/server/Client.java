package network.server;

import network.Communicator;

import java.net.*;

// Main server client
public class Client extends Communicator
{
	// Constructor
	public Client(Socket socket)
	{
		assignSocket(socket);
	}
}
