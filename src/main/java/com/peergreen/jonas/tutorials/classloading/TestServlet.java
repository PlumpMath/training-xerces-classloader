package com.peergreen.jonas.tutorials.classloading;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.peergreen.jonas.tutorials.classloading.ClassLoaders.loader;

/**
 * A {@code ${NAME} is ...
 *
 * @author Guillaume Sauthier
 */
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object instance = DocumentBuilderFactory.newInstance();
        request.setAttribute("documentBuilderFactory", instance);
        request.setAttribute("documentBuilderFactoryClass", instance.getClass());
        request.setAttribute("documentBuilderFactoryClassLoader", loader(instance.getClass()));

        // We only do a simple redirection here
        // As we want to load a JSPX page, Jasper will load the XML content of the JSP
        // That may (or may not, given the configuration) trigger a Classloading error
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/status.jspx");
        dispatcher.forward(request, response);

    }
}
