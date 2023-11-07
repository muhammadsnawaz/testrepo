package com.adobe.aem.guides.wknd.core.models;


import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.adobe.aem.guides.wknd.core.pojo.MenuPojo;
import com.adobe.aem.guides.wknd.core.utils.EnumConstants;
import com.adobe.aem.guides.wknd.core.utils.LinksUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class NavigationModel.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class NavigationModel {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationModel.class);

    /**
     * The resource.
     */
    @SlingObject
    private Resource resource;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The current page.
     */
    @Inject
    private Page currentPage;

    /**
     * The sub nav items list.
     */
    List<MenuPojo> subNavItemsList = new ArrayList<>();

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        LOGGER.debug("Inside Menu Model Init Method");
        InheritanceValueMap iProperties = new HierarchyNodeInheritanceValueMap(resource);
        String rootPagePath = iProperties.getInherited(EnumConstants.ROOTPAGE_PATH.getStringValue(), StringUtils.EMPTY);
        if (StringUtils.isNotBlank(rootPagePath)) {
            LOGGER.info("Updating Sub Nave List");
            Resource resourcePath = resourceResolver.getResource(rootPagePath);
            Page rootpage = resourcePath != null ? resourcePath.adaptTo(Page.class) : null;
            subNavItemsList = rootpage != null ? getFirstLevelItems(rootpage, subNavItemsList) : null;
        }

    }

    /**
     * Gets the first level items.
     *
     * @param rootPage        the root page
     * @param subNavItemsList the sub nav items list
     * @return the first level items
     */
    private List<MenuPojo> getFirstLevelItems(Page rootPage, List<MenuPojo> subNavItemsList) {
        Iterator<Page> firstLvelchildren = rootPage.listChildren();
        while (firstLvelchildren.hasNext()) {
            Page firstlevelPage = firstLvelchildren.next();
            if (!firstlevelPage.isHideInNav()) {
                getSubNavList(subNavItemsList, firstlevelPage);
            }
        }
        return subNavItemsList;

    }

    private void getSubNavList(List<MenuPojo> subNavItemsList, Page firstlevelPage) {
        MenuPojo navObj = new MenuPojo();
        ValueMap firstLevelPageProperties = firstlevelPage.getProperties();
        String pathLink = getPagePath(firstlevelPage, firstLevelPageProperties);
        if (StringUtils.isNotBlank(pathLink)) {
            navObj.setPageTitle(StringUtils
                    .isNotBlank(firstLevelPageProperties.get(EnumConstants.NAV_TITLE.getStringValue(), String.class))
                    ? firstLevelPageProperties.get(EnumConstants.NAV_TITLE.getStringValue(), String.class)
                    : firstlevelPage.getTitle());
            navObj.setPageLink(
                    LinksUtil.checkInternalExternalURLByResource(pathLink, resourceResolver.getResource(pathLink)));

            navObj.setShowLink(firstLevelPageProperties.get(EnumConstants.SHOW_LINK.getStringValue(), String.class));
            navObj.setRemoveFromMenu(firstLevelPageProperties.get("removeFromMenu", StringUtils.EMPTY));

            if (firstlevelPage.listChildren().hasNext()) {
                List<MenuPojo> secondLevelnavList = new ArrayList<>();
                secondLevelnavList = getFirstLevelItems(firstlevelPage, secondLevelnavList);
                navObj.setChildPages(secondLevelnavList);
            }
        }
        subNavItemsList.add(navObj);
    }

    /**
     * Sets the active parent.
     *
     * @param fromJson the from json
     * @param navObj   the nav obj
     */

    private String getPagePath(Page firstlevelPage, ValueMap firstLevelPageProperties) {
        return firstLevelPageProperties.containsKey(EnumConstants.REDIRECT_TARGET.getStringValue()) && StringUtils
                .isNotBlank(firstLevelPageProperties.get(EnumConstants.REDIRECT_TARGET.getStringValue(), String.class))
                ? firstLevelPageProperties.get(EnumConstants.REDIRECT_TARGET.getStringValue(), String.class)
                : firstlevelPage.getPath();
    }

    /**
     * Gets the sub nav items list.
     *
     * @return the sub nav items list
     */
    public List<MenuPojo> getSubNavItemsList() {
        return subNavItemsList;
    }

}
