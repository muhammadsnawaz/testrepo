/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service=Servlet.class,
           property={
                   Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
                   "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                   "sling.servlet.resourceTypes="+ "wknd/components/structure/page",
                   "sling.servlet.extensions=" + "txt"
           })

public class SimpleServlet extends SlingSafeMethodsServlet {

    @Reference
    ResourceResolverFactory rrf;
    @Reference
    QueryBuilder queryBuilder;

    private Hit searchHit;
    int noOfComp =0;
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
    	log.debug("inside the do get of servlet by resource type");
        Session session = req.getResourceResolver().adaptTo(Session.class);
        //User Mapper
        /*ResourceResolver resourceResolver = null;
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE,"systemusertest");
        try {
            resourceResolver = rrf.getServiceResourceResolver(param);
            Session session = resourceResolver.adaptTo(Session.class);
            //resourceResolver.getResource("/content/wknd");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }*/

        String pagePath = req.getParameter("pagePath");
        String componentPath = req.getParameter("componentPath");

        //Query Builder code
        ArrayList<String> listOfPagePath = new ArrayList();
        Map<String, String> predicates = new HashMap<>();
        Map<String, Integer> compPageMap = new HashMap<>();
        predicates.put("path",pagePath);
        predicates.put("type","cq:Page");

        Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult result = query.getResult();

        if (result != null) {
            List<Hit> hits = result.getHits();
            hits.forEach(searchHit -> {
                try {
                    listOfPagePath.add(searchHit.getPath());
                    int compPerPage = numberOfOccurrence(searchHit.getPath(),componentPath,session);
                    compPageMap.put(searchHit.getTitle(),compPerPage);
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println(compPageMap);
        //Query builder ends

        resp.setContentType("text/plain");
        resp.getWriter().write("List of Pages with number component occurrences is : " + compPageMap + "\n");
    }

    private int numberOfOccurrence(String pagePath, String componentPath, Session session){

        Map<String, String> predicates = new HashMap<>();
        int compPerPage =0;
        predicates.put("path",pagePath);
        predicates.put("1_property","sling:resourceType");
        predicates.put("1_property.value", componentPath);
        predicates.put("1_property.operation","equals");
        predicates.put("p.guessTotal","true");
        final Query queryComp = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        SearchResult resultComp = queryComp.getResult();
        if (resultComp != null) {
            compPerPage = resultComp.getHits().size();
        }
        return compPerPage;
    }

}
