package com.virtualpairprogrammers.servlets;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value="/kitchenAsyncServlet", asyncSupported = true) // here we will tell java that this is an asynchronos servlet
public class KitchenAsyncServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // the magic that makes the whole thing work asynchronously. its pretty much the only way to do that. read the api for more information
        AsyncContext asyncContext = request.startAsync(request, response);
        KitchenAsyncTask task = new KitchenAsyncTask();
        task.setAsyncContext(asyncContext); // now this task implements runnable (is a thread) and has request and response objects inside asynccontext
        asyncContext.start(task);

        /**
         *  About asyncContext.start() method...
         *      Turns out there was a guy who did some investigation how this method works and under the hood, it doen't start a background worker thread.
         *      It just takes a thread from the tomcat thread pool. so it just ends on and starts another inside the same pool. so it's completely useless.
         *      The same deal is with Jetty. Don't know about the other web servers.
         */
    }

}
