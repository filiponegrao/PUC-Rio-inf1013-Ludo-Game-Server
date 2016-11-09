package controller;

import java.io.*;
import java.util.*;

import javax.swing.text.AbstractDocument.LeafElement;

import java.net.*;

public class Server 
{
	private static Server data = new Server();
	private List<Player> players;
	public List<Socket> clients = new ArrayList<Socket>();
	public ServerSocket server;
	
	public Server() 
	{		
		
	}
	
	public static Server sharedInstance()
	{
		return data;
	}
	
	public void connect(int porta)
	{
		try {
			this.server = new ServerSocket(porta);
			System.out.println("ServerSocket criado");
			this.waitClients();
	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void waitClients()
	{
		new Thread() {
			
			@Override
			public void run()
			{
				while(clients.size() <= 4)
				{
					try {
						Socket client =  server.accept();
						clients.add(client);
						System.out.println("Cliente entrou");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}.start();
	}
	
	public void disconnect()
	{
		try {
			this.server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
