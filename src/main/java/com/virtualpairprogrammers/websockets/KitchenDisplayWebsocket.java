package com.virtualpairprogrammers.websockets;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/kitchenManagement")
public class KitchenDisplayWebsocket {

    // We've built here a websocket which allows a client to connect and disconnect. If a client connects, we'll store the session in our session handler

    @OnOpen
    public void open(Session session) {
        KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
        handler.addSession(session);
        System.out.println("KitchenDisplayWebsocket: opened session");
    }

    @OnClose
    public void close(Session session) {
        KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
        handler.removeSession(session);
        System.out.println("KitchenDisplayWebsocket: closed session");
    }

    // Error not handled correctly, so everytime a client disconnects (or refreshes the page), an error is thrown into the console
    @OnError
    public void onError (Throwable error) {
        throw new RuntimeException(error);
    }

    // This method will be called if our client sends a message to the server.
    @OnMessage                              //
    public void handleMessage(String message, Session session) {
        // Firstly, we have to convert the string to a JSON Object
        JSONObject json = new JSONObject(message);
        Long id = json.getLong("id");
        String status = json.getString("status");
        // with that information we want to update the status of our order.

        // update the status in the database:
        MenuDao menuDao = MenuDaoFactory.getMenuDao();
        menuDao.updateOrderStatus(id,status);

        // load up the order:
        Order order = menuDao.getOrder(id);
        // get handler
        KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
        // modify (amend) the order
        handler.amendOrder(order);
        System.out.println("KitchenDisplayWebsocket: message handled");
    }

}
