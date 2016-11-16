package controller;

import java.net.*;

public class Player 
{
	public Socket socket;
	 
	 public String nickname;
	 
	 public String team;
	 
	 public Player(Socket socket, String nickname, String team)
	 {
	  this.socket = socket;
	  this.nickname = nickname;
	  this.team = team;
	 }
	 
}
