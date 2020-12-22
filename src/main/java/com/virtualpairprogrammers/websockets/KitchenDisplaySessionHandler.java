package com.virtualpairprogrammers.websockets;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KitchenDisplaySessionHandler {

    private List<Session> sessions = new ArrayList<>();

    public void addSession(Session session) {
        sessions.add(session);
        sendAllOrders(session); // now if a new client connects, it receives all the other orders in the system
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    // send message to all of the sessions
    private void sendMessage(JSONObject message){
        // the idea is to loop through all of our sessions and send a message to each session (or mayb not)
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                // we end up here, if we've lost the client connection. So we remove that session.
                removeSession(session);
            }
        }
    }

    // send message to a single session
    private void sendMessage(JSONObject message, Session session){
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            // we end up here, if we've lost the client connection. So we remove that session.
            removeSession(session);
        }
    }

    public JSONObject generateJSONForOrder(Order order) {
        JSONObject json = new JSONObject();
        json.append("id", order.getId().toString());
        json.append("status", order.getStatus());
        json.append("content", order.toString());
        json.append("action", "add");
        json.append("update", new Date().toString());
        return json;
    }

    public void newOrder(Order order) {
        sendMessage(generateJSONForOrder(order));
    }

    public void amendOrder(Order order) {
        JSONObject json = new JSONObject();
        json.append("id", order.getId().toString());
        json.append("action", "remove"); // is it a new order? or order changed?
        sendMessage(json);
        newOrder(order);
    }

    private void sendAllOrders(Session session) {
        MenuDao dao = MenuDaoFactory.getMenuDao();
        List<Order> orders = dao.getAllOrders();
        for (Order order : orders) {
            sendMessage(generateJSONForOrder(order), session);
        }
    }
}
