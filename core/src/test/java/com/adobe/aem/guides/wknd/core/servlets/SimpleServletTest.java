/*
 *  Copyright 2018 Adobe Systems Incorporated
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.ServletException;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(AemContextExtension.class)
class SimpleServletTest {

    private QueryBuilder mockQueryBuilder;
    private ResourceResolver mockResourceResolver;


    private SimpleServlet fixture = new SimpleServlet();

    @Test
    void doGet(AemContext context) throws ServletException, IOException {
        context.build().resource("/content/test", "jcr:title", "resource title").commit();
        context.currentResource("/content/test");

        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();

        Map<String, String> predicates = new HashMap<>();
        Map<String, Integer> compPageMap = new HashMap<>();
        predicates.put("path","/content/wknd");
        predicates.put("type","cq:Page");

        final Query query = mock(Query.class);

        SearchResult result = mock(SearchResult.class);
        when(request.getParameter("pagePath")).thenReturn("/content/wknd");
        when(request.getParameter("componentPath")).thenReturn("wknd/components/content/text");
        when(mockQueryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
        when(query.getResult()).thenReturn(result);
        //Mockito.when(mockQueryBuilder.createQuery(PredicateGroup.create(predicates),mockSession)).thenReturn(mockQuery);
        //Mockito.when(mockQuery.getResult()).thenReturn((com.day.cq.search.result.SearchResult) mockSearchResult);

        fixture.doGet(request, response);

        assertEquals("Title = resource title", response.getOutputAsString());
    }

    @BeforeEach
    void setUp() {
        Session session = mockResourceResolver.adaptTo(Session.class);
        mockQueryBuilder = mock(QueryBuilder.class);
        doReturn(mockQueryBuilder).when(mockResourceResolver).adaptTo(QueryBuilder.class);
    }

    @Test
    void testDoGet() {
    }
}
