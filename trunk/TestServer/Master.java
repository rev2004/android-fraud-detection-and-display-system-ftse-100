import network.server.*;

// Master server class
class Master
{
	public static void main(String[] args)
	{
		// Create a server
		Server server = new Server(9011);

		// Attempt to start listening
		if (!server.listen())
		{
			// Failure
			System.out.println("Failed to listen");
			return;
		}

		// Create a handle to the server
		new AlertServerHandler(server);
    }
}
