package com.fortwo.server.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fortwo.server.model.Chat;

@Path("/chat")
public class ChatResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Chat getChats(){
		return new Chat("hard coded chats", 1, 1);
	}
	
	@POST
	public void SendChat(Chat chat){
		
	}
}
