/*
** Listens for clients
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread
{
	private int port;
	private ServerSocket serverSocket = null;
	private Vector<ServerConnection> serverConnections;
	
	Server(int port)
	{
		this.port = port;
		this.serverConnections = new Vector<ServerConnection>();
		this.start();
	}
	
	public void run()
	{
		try
		{
			// Start listening on a port
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			System.out.println("Could not listen on port: " + port);
			System.exit(-1);
		}
		System.out.println("Waiting for clients on port: " + port);
		while (true)
		{
			try
			{
				// Accept clients and store their details
				serverConnections.add(new ServerConnection(serverSocket.accept(), this));
				System.out.println("Accepted Client");
			}
			catch (IOException e)
			{
				System.err.println("Accept failed on port: " + port);
				System.exit(-1);
			}
		}
	}

	// Sends a message to all clients currently connected.
	public void writeToAll(String msg)
	{
		try 
		{
            for (int i = 0; i < this.serverConnections.size(); i++)
			{
                ServerConnection client = this.serverConnections.get(i);
                client.out.println(msg);
            }
        }
        catch (Exception e)
		{
            System.out.println("Could not send message to all clients");
        }
	}

	public synchronized void removeClient(ServerConnection client)
	{
		serverConnections.remove(client);
		System.out.println("Disconnected Client");
		System.out.println("Number of clients: " + serverConnections.size());
	}
}

class ServerConnection extends Thread
{
	private Socket clientSocket;
	private Server server;
	private String inputLine;
	public PrintWriter out;
	private BufferedReader in;
	
	public ServerConnection(Socket socket, Server server)
	{
		clientSocket = socket;
		this.server = server;
		this.start();
	}
	
	public void run()
	{
		try
		{
			// set up the input/output objects
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			out.println("hey");
			
			// wait for the response from the client
			while (true)
			{
				inputLine = in.readLine();
				if (inputLine == null)
				{
					// client has disconnected
					server.removeClient(this);
					break;
				}
				// handle messages from clients
				if (inputLine.equals("hello"))
					out.println("hi there!");
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
