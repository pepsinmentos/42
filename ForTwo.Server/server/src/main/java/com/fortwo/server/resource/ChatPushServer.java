package com.fortwo.server.resource;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;

import com.fortwo.server.model.Chat;

@Path("/")
@AtmosphereService(dispatch = true, interceptors = { AtmosphereResourceLifecycleInterceptor.class,
		TrackMessageSizeInterceptor.class }, path = "/chat", servlet = "org.glassfish.jersey.servlet.ServletContainer")
public class ChatPushServer {
	
	//@Inject
	private BroadcasterFactory factory;
	
	@Context
	private HttpServletRequest request;

	@GET
	@Path("/{userId}")
	public void configureAtmosphereResource(@PathParam("userId") long userId) {
		System.out.println("UserId connected: " + Long.toString(userId));
		AtmosphereResource r = (AtmosphereResource) request.getAttribute(ApplicationConfig.ATMOSPHERE_RESOURCE);		
		
		if(factory != null){
			System.out.println("Injection worked!"); 
		}

		if (r != null) {
			r.addEventListener(new AtmosphereResourceEventListenerAdapter.OnDisconnect() {

				@Override
				public void onDisconnect(AtmosphereResourceEvent event) {
					if (event.isCancelled()) {
						System.out.println(
								String.format("Browser %s unexpectedly disconnected", event.getResource().uuid()));
					} else if (event.isClosedByClient()) {
						System.out
								.println(String.format("Browser %s closed the connection", event.getResource().uuid()));
					}
				}
			});
			
			r.write("hello and welcome");
		} else {
			throw new IllegalStateException();
		}
	}

	@POST		
	@Consumes(MediaType.APPLICATION_JSON)
	@org.atmosphere.config.service.Message(encoders = {com.fortwo.server.model.JacksonEncoder.class}, decoders = {com.fortwo.server.model.JacksonDecoder.class})
	public void broadcast(Chat m) {		
		Chat message = m;
		System.out.println("Sending message: " + message.getMessage() + " from userId: " + message.getUserId() + " to recipient: " + message.getRecipientId());
		AtmosphereResource r = (AtmosphereResource) request.getAttribute(ApplicationConfig.ATMOSPHERE_RESOURCE);
		
		

		if (r != null) {
			System.out.println("FUCK YEAH: " + message);

			r.getBroadcaster().broadcast(message);
		} else {
			throw new IllegalStateException();
		}  
	}

	/*
	 * @Inject private BroadcasterFactory factory;
	 * 
	 * // For demonstrating javax.inject.Named
	 * 
	 * @Inject
	 * 
	 * @Named("/chat") private Broadcaster broadcaster;
	 * 
	 * @Inject private AtmosphereResource r;
	 * 
	 * @Inject private AtmosphereResourceEvent event;
	 * 
	 * @Heartbeat public void onHeartbeat(final AtmosphereResourceEvent event) {
	 * System.out.println("Heartbeat send by " + event.getResource()); }
	 * 
	 * @Ready public void onReady(AtmosphereResource r){
	 * System.out.println(String.format("Browser %s connected", r.uuid()));
	 * System.out.println(String.format("BroadcastFactory used %s",
	 * factory.getClass().getName())); System.out.println(String.format(
	 * "Broadcaster injected %s", broadcaster.getID())); }
	 * 
	 * @Disconnect public void onDisconnect(){ if(event.isCancelled()){
	 * System.out.println(String.format("Browser %s unexpectedly disconnected",
	 * event.getResource().uuid())); } else if (event.isClosedByClient()) {
	 * System.out.println(String.format("Browser %s closed the connection",
	 * event.getResource().uuid())); }
	 * 
	 * }
	 * 
	 * @org.atmosphere.config.service.Message(encoders = {JacksonEncoder.class},
	 * decoders = {JacksonDecoder.class}) public Chat onMessage(Chat message)
	 * throws IOException { System.out.println(String.format("%s just sent %s",
	 * message.getUserId(), message.getMessage())); return message; }
	 * 
	 */
}
