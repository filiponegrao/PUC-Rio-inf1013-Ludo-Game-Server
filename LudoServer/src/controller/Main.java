package controller;

public class Main 
{
	
	public static void main(String[] args) 
	{
		Server.sharedInstance().connect(6969);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        Server.sharedInstance().disconnect();
		    }
		}));
	}

}
