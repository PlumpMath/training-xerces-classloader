package com.peergreen.jonas.tutorials.classloading;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.peergreen.jonas.tutorials.classloading.ClassLoaders.loader;

/**
 * A {@code ${NAME}} is ...
 *
 * @author Guillaume Sauthier
 */
public class ClassLoaderLocationFinderServlet extends HttpServlet {
    private static final String CONTENT_SUCCESS = "    <html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
            "          xml:lang=\"en\"\n" +
            "          lang=\"en\">\n" +
            "    <head>\n" +
            "        <title>Source ClassLoader</title>\n" +
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
            "                    <h1>TCCL OK <small>Class loaded successfully from TCCL</small></h1>\n" +
            "                </div>\n" +
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
            "                        <td>Classname</td>\n" +
            "                        <td>\n" +
            "                            <pre>\n" +
            "${classname}\n" +
            "                            </pre>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                        <td>Class</td>\n" +
            "                        <td>" +
            "                            <pre>\n" +
            "${type}" +
            "                            </pre>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                        <td>Source ClassLoader</td>\n" +
            "                        <td>" +
            "                            <pre>\n" +
            "${classloader}" +
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

    private static final String CONTENT_ERROR = "    <html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
            "          xml:lang=\"en\"\n" +
            "          lang=\"en\">\n" +
            "    <head>\n" +
            "        <title>Source ClassLoader</title>\n" +
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
            "                    <h1>TCCL KO <small>Class could not be loadedfrom TCCL</small></h1>\n" +
            "                </div>\n" +
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
            "                        <td>Classname</td>\n" +
            "                        <td>\n" +
            "                            <pre>\n" +
            "${classname}\n" +
            "                            </pre>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                        <td>Exception</td>\n" +
            "                        <td>" +
            "                            <pre>\n" +
            "${error}" +
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String classname = request.getParameter("classname");
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();

        try {
            Class<?> type = tccl.loadClass(classname);

            response.getWriter().print(
                    CONTENT_SUCCESS.replace("${classname}", classname)
                                   .replace("${type}", type.toString())
                                   .replace("${classloader}", loader(type).toString())
            );

        } catch (ClassNotFoundException e) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            response.getWriter().print(
                    CONTENT_ERROR.replace("${classname}", classname)
                            .replace("${error}", sw.getBuffer())
            );
        }
    }
}
