package controller;

import java.io.*;
import java.util.*;

import javax.swing.text.AbstractDocument.LeafElement;

import java.net.*;

public class Server implements Observer
{
	private static Server data = new Server();
	
//	private List<Player> players;
	
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
			
			//Aguarda pela conexao dos clientes
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
						
						MessageHandler handler = new MessageHandler(client);
						
						Thread thread = new Thread(handler);
						
						thread.start();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				if(clients.size() == 4)
				{
					return;
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

	public Boolean sendMessage(Socket client, byte[] bytes)
	{
		try {
			
			OutputStream output = client.getOutputStream();
			
			output.write(bytes, 0, bytes.length);			
			output.flush();
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		HashMap<String, Object> map = (HashMap<String, Object>) arg;
		
		String content = map.toString();
		
		byte[] bytes = content.getBytes();
		
		System.out.println("Reenviando mensagem para clientes. Conteudo:");
		System.out.println(content);
		
		//Reenvia a mensagem para todos os clientes
		for (Socket client : clients) {
			
			this.sendMessage(client, bytes);
		}
		
	}
}
