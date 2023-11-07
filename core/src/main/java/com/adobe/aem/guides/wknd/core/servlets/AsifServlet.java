package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service= Servlet.class,

        property={

                Constants.SERVICE_DESCRIPTION + "=Asif Demo Servlet",

                "sling.servlet.methods=" + HttpConstants.METHOD_GET,

                "sling.servlet.paths="+ "/bin/myLifeServlet"
        })
public class AsifServlet extends SlingSafeMethodsServlet {
        @Override
        protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {

                resp.setContentType("text/plain");
                resp.getWriter().write("You have just called a servlet registered by Path by Afrin");
        }
}

