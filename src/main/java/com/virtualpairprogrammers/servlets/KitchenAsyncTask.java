package com.virtualpairprogrammers.servlets;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class KitchenAsyncTask implements Runnable {

    private AsyncContext asyncContext;

    public void setAsyncContext(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {

        HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");

        Long size = Long.parseLong(request.getParameter("size"));

        MenuDao dao = MenuDaoFactory.getMenuDao();

        // Here we tell server to go to sleep until a new order comes in.
        //      In the browser the order number (size) is always incremented, so the next order number is ready and waiting for the order.
        //      Therefore size is always bigger than the number of all orders. We will not move forward until a new order comes in
        while (dao.getAllOrders().size() < size) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                asyncContext.complete(); // this says our processing is finished
                throw new RuntimeException(e);
            }
        }
        Order order = dao.getOrder(size);
        out.println("<p><strong>Next order:</strong><br>" + order.toString() + "</p>");
        out.close();

        asyncContext.complete();
    }
}
