package network;

import java.io.*;
import java.net.*;

// Communicator class
public class Communicator
{
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Boolean connected;

	// Constructor
	public Communicator()
	{
		connected = false;
	}

	// Assigns a socket to this communicator
	public void assignSocket(Socket socket)
	{
		this.socket = socket;
	}

	// Initializes the communicator
	public Boolean init()
	{
		try
		{
			// Set up the input/output objects
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Flag connected as true
			connected = true;

			// Return success
			return true;
		}
		catch (Exception e)
		{
			// Connection failed, so flag connected as false
			connected = false;

			// Return failure
			return false;
		}
	}

	// Send a message to the socket
	public void sendMessage(String msg)
	{
		out.println(msg);
	}

	// Recieve a message from the socket
	// Returns the message if successful, null otherwise
	public String recieveMessage()
	{
		try
		{
			// Read the message
			String read = in.readLine();

			// If the server has disconnected
			if (read == null)
			{
				// Disconnect client
				disconnect();
				return null;
			}
			else
				return read;
		}
		catch (Exception e)
		{
			// Disconnect if unsuccessful
			disconnect();
			return null;
		}
	}

	// Cleans up resources
	private Boolean free()
	{
		try
		{
			out.close();
			in.close();
			socket.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	// Connection status
	// Returns true if connected, false otherwise
	public Boolean isConnected()
	{
		return connected;
	}

	// Disconnects the client
	// Returns true if success, false otherwise
	public Boolean disconnect()
	{
		// Try and free resources
		if (this.free())
		{
			this.connected = false;
			return true;
		}
		else
			return false;
	}	
}
