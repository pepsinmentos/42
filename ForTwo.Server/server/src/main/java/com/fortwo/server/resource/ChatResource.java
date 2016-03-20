package com.fortwo.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fortwo.server.model.Chat;

@Path("/chat2")
public class ChatResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Chat getChats(){
		System.out.println("Something");
		return new Chat("hard coded chats", 1, 1,2);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Chat SendChat(Chat chat){
		if(chat != null)
		{
		System.out.println("Message received: " + chat.getMessage());
		}
		else
		{
			System.out.println("null ;(");		
			
		}	
		//TODO: save to chat repository and return ID
		return chat;
	}
}
