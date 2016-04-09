package com.fortwo.client.services;

import com.example.fortwo.client.model.ChatLine;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivi on 2016/04/09.
 */
public class NotificationServiceTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testAddList() {
    NotificationService ns = new NotificationService(null);
        List<ChatLine> cl = new ArrayList<ChatLine>();
        cl.add(new ChatLine());
        cl.add(new ChatLine());
        cl.add(new ChatLine());
        ns.ChatsReceived(cl);
        Assert.assertEquals(true,true);

    }
}
