package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;
import com.virtualpairprogrammers.websockets.KitchenDisplaySessionHandler;
import com.virtualpairprogrammers.websockets.KitchenDisplaySessionHandlerFactory;

@WebServlet("/processorder.html")
public class ProcessOrderServlet extends HttpServlet{
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
	MenuDao menuDao = MenuDaoFactory.getMenuDao();
	List<Order> orders = menuDao.getAllOrders();
	request.setAttribute("orders", orders);
	
	List<String> statuses = new ArrayList<>();
	statuses.add("order accepted");
	statuses.add("payment received");
	statuses.add("being prepared");
	statuses.add("ready for collection");
	
	request.setAttribute("statuses", statuses);

	ServletContext context = getServletContext();
	RequestDispatcher dispatch = context.getRequestDispatcher("/processorder.jsp");
	dispatch.forward(request,response);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		Long id = Long.valueOf(request.getParameter("id"));
		String status =  request.getParameter("status");
		System.out.println(id + " : " + status);
		menuDao.updateOrderStatus(id,status);

		// when an order statud is updated (http://localhost:8080/processorder.html), a form is submitted in processorder.jsp file with a POST method (hence, doPost method here)
		// then we'll take the id from req params and find the order.
		Order order = menuDao.getOrder(id);
		// then we'll get we'll get the handler
		KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
		// and modify the information
		handler.amendOrder(order);

		doGet(request,response);
	}
}
