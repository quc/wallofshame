package com.wallofshame;

import org.apache.commons.io.FileUtils;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class WallOfShameServer {
    private static final String welcomePage = "<html>\n"
                + "<head>\n"
                
                + "</head>\n"
                + "</html>";

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Connector con = new SelectChannelConnector();
        String port = System.getProperty("port","8080");
        con.setPort(Integer.valueOf(port));
        server.addConnector(con);
        //server.addHandler(welcomeFileHandler());

        WebAppContext wac = new WebAppContext();
        if ("production".equals(System.getProperty("env"))) {
            wac.setResourceBase("./timesheet");
        } else {
            if (new File("./app/webapp/WEB-INF/classes").exists()) {
                FileUtils.cleanDirectory(new File("./app/webapp/WEB-INF/classes"));
            }
            FileUtils.copyDirectory(
                    new File("./out/production/app"),
                    new File("./app/webapp/WEB-INF/classes")
            );
            wac.setResourceBase("./app/webapp");
        }

        wac.setDescriptor("WEB-INF/web.xml");
        wac.setContextPath("/timesheet");
        server.setHandler(wac);
        server.start();
    }

    private static CruiseServerWelcomeFileHandler welcomeFileHandler() {
        CruiseServerWelcomeFileHandler welcomeFileHandler = new CruiseServerWelcomeFileHandler();
        welcomeFileHandler.setContextPath("/");
        return welcomeFileHandler;
    }

    static class CruiseServerWelcomeFileHandler extends ContextHandler {
            public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                    throws IOException, ServletException {
                if (target.equals("/")) {
                    response.setHeader("Content-Type", "text/html");
                    PrintWriter writer = response.getWriter();
                    writer.write(welcomePage);
                    writer.close();
                }
            }
        }


}
