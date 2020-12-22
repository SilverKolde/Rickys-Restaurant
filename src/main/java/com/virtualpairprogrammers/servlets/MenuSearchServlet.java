package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.MenuItem;

@WebServlet("/searchResults.html")
public class MenuSearchServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String searchTerm = request.getParameter("searchTerm");
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		List<MenuItem> menuItems = menuDao.find(searchTerm);

		ServletContext context = getServletContext(); // it's from the httpservlet superclass
		RequestDispatcher dispatch;

		if (menuItems.size() == 0) {
			request.setAttribute("searchTerm", searchTerm);
			dispatch = context.getRequestDispatcher("/failedSearch.jsp");
		} else {
			request.setAttribute("menuItems", menuItems);
			dispatch = context.getRequestDispatcher("/searchResults.jsp");
		}

		dispatch.forward(request, response);
	}
}
