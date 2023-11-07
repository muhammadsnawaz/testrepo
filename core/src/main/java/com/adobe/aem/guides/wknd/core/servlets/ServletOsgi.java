package com.adobe.aem.guides.wknd.core.servlets;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.services.CustomService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import javax.servlet.ServletException;
import java.io.IOException;

@SuppressWarnings("serial")
@Component(service=Servlet.class,

property={

        Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",

        "sling.servlet.methods=" + HttpConstants.METHOD_GET,

        "sling.servlet.paths="+ "/bin/myDataSourcePoolServlet"
   })

public class ServletOsgi extends SlingSafeMethodsServlet {
	private final Logger log = LoggerFactory.getLogger(getClass());
		@Reference
		CustomService objCustomService;
	    @Override
	    protected void doGet(final SlingHttpServletRequest req,final SlingHttpServletResponse resp) throws ServletException, IOException {
	    	//String serviceVariable = objCustomService.serviceFuntion();
	        //log.debug("Node path in sevlet is ::"+serviceVariable);
	        resp.setContentType("text/plain");
	        //resp.getWriter().write("You have just called a servlet registered by Path by Afrin :: "+serviceVariable);
	    }
	
	
}
