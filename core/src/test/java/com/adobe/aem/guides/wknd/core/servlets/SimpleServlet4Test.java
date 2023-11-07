package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Session;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SimpleServlet4Test {
    @Mock
    SlingHttpServletRequest mockRequest;

    @Mock
    SlingHttpServletResponse mockResponse;

    @Mock
    ResourceResolver mockResourceResolver;

    @Mock
    Session mockSession;


    SimpleServlet mockSimpleServlet = new SimpleServlet();

    private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

    @Mock
    private QueryBuilder queryBuilder;

    @Mock
    private Query query;

    @Mock
    private SearchResult searchResult;

    private List<Resource> queryResults = new ArrayList<>();
    @org.junit.Before
    public void setUp() throws Exception {
        /* Query Builder is adapted from Resource Resolver */
        aemContext.registerAdapter(ResourceResolver.class, QueryBuilder.class, queryBuilder);
        lenient().when(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
        lenient().when(query.getResult()).thenReturn(searchResult);
        lenient().when(searchResult.getResources()).thenReturn(queryResults.iterator()); // test content/result is returned, can add further dummy implementation based on the actual code statements
    }

    @org.junit.Test
    public void doGet() throws ServletException, IOException {

        Query mockQuery = Mockito.mock(Query.class);
        QueryBuilder mockQueryBuilder = Mockito.mock(QueryBuilder.class);
        ArrayList<String> listOfPagePath = new ArrayList();
        Map<String, String> predicates = new HashMap<>();
        Map<String, Integer> compPageMap = new HashMap<>();
        predicates.put("path","/content/wknd");
        predicates.put("type","cq:Page");

        Mockito.when(mockRequest.getResourceResolver()).thenReturn(mockResourceResolver);
        Mockito.when(mockResourceResolver.adaptTo(Session.class)).thenReturn(mockSession);
        Mockito.when(mockRequest.getParameter("pagePath")).thenReturn("/content/wknd");
        Mockito.when(mockRequest.getParameter("componentPath")).thenReturn("wknd/components/content/text");
        //Mockito.when(mockPredicateGroup.create(predicates)).thenReturn(mockPredicateGroup);
        //Mockito.when(mockQueryBuilder.createQuery(mockPredicateGroup,mockSession)).thenReturn(mockQuery);
        //mockQuery = mock(Query.class);
        //mockQueryBuilder = mock(QueryBuilder.class);
        //PrivateAccessor.setField(SimpleServlet4Test, "queryBuilder", mockQueryBuilder);
        PredicateGroup mockPredicateGroup = PredicateGroup.create(predicates);
        //Mockito.when(mockQueryBuilder.createQuery(mockPredicateGroup,mockSession)).thenReturn(mockQuery);
        Mockito.when(mockQueryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(mockQuery);
        mockSimpleServlet.doGet(mockRequest,mockResponse);

    }
}