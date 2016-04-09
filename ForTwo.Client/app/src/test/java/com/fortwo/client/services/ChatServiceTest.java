package com.fortwo.client.services;

import android.content.Context;

import com.example.fortwo.client.model.ChatLine;
import com.fortwo.client.services.ChatService;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Vivi on 2016/04/01.
 */
public class ChatServiceTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testGetUnreadChatLines() throws Exception {
        ChatService service = new ChatService(null, new UserServiceStub());
        List<ChatLine> c = service.getUnreadChatLines();

        for (ChatLine chatLine : c ) {
            System.out.println(chatLine.getMessage());
        }


    }
}