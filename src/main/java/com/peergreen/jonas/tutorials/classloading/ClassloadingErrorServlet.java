package com.peergreen.jonas.tutorials.classloading;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@code ${NAME}} is ...
 *
 * @author Guillaume Sauthier
 */
public class ClassloadingErrorServlet extends HttpServlet {

    private static final String CONTENT = "    <html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
            "          xml:lang=\"en\"\n" +
            "          lang=\"en\">\n" +
            "    <head>\n" +
            "        <title>Status > Failure</title>\n" +
            "        <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"/>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "    <div class=\"container-fluid\">\n" +
            "        <div class=\"row-fluid\">\n" +
            "            <div class=\"span2\">\n" +
            "                <!--Sidebar content-->\n" +
            "            </div>\n" +
            "            <div class=\"span8\">\n" +
            "\n" +
            "                <div class=\"page-header\">\n" +
            "                    <h1>Classloading Error</h1>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"alert alert-error\">\n" +
            "                  <h4 class=\"alert-heading\">Oooops !</h4>\n" +
            "                  There was a ClassCastException, full details below...\n" +
            "                </div>\n" +
            "<form class=\"well form-search\" method=\"get\" action=\"load\">\n" +
            "  <input type=\"text\"" +
            "         name=\"classname\"" +
            "         placeholder=\"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl\"" +
            "         class=\"input-xxlarge search-query\" />\n" +
            "  <button type=\"submit\" class=\"btn btn-primary\">Try loading</button>\n" +
            "</form>" +
            "\n" +
            "                <table class=\"table table-striped\">\n" +
            "                    <thead>\n" +
            "                    <tr>\n" +
            "                        <th>Attribute</th>\n" +
            "                        <th>Value</th>\n" +
            "                    </tr>\n" +
            "                    </thead>\n" +
            "                    <tbody>\n" +
            "                    <tr>\n" +
            "                        <td>Throwable</td>\n" +
            "                        <td>\n" +
            "                            <pre>\n" +
            "${throwable}\n" +
            "                            </pre>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                    </tbody>\n" +
            "                </table>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    </body>\n" +
            "    </html>";

    private static final String ERROR_EXCEPTION = "javax.servlet.error.exception";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Throwable t = (Throwable) request.getAttribute(ERROR_EXCEPTION);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        response.getWriter().print(
                CONTENT.replace("${throwable}", sw.getBuffer())
                                   );
    }
}
