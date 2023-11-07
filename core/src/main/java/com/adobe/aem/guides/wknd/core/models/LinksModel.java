package com.adobe.aem.guides.wknd.core.models;


import com.adobe.aem.guides.wknd.core.utils.LinksUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * This class LinksModel will read the requestAttribute and provide the valid URL.
 *
 * @author Sarfraz
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinksModel {

    /**
     * Logger configuration for LinksModel
     */
    private static final Logger log = LoggerFactory.getLogger(LinksModel.class);

    @SlingObject
    private ResourceResolver resolver;

    @RequestAttribute
    private String linkURL;

    @RequestAttribute
    private String newWindow;

    @RequestAttribute
    private String shortURL;

    @PostConstruct
    protected void init() {
        log.debug("***** LinksModel :: init :: Start *****");
        try {
            linkURL = StringUtils.isNotBlank(linkURL) ? LinksUtil.checkInternalURLByPath(linkURL, resolver) : StringUtils.EMPTY;
            newWindow = LinksUtil.isNewWindow(newWindow);
            shortURL = StringUtils.isNotBlank(shortURL) ? shortURL : StringUtils.EMPTY;
        } catch (Exception e) {
            log.error("***** LinksModel :: init :: Error *****", e);
        }
        log.debug("***** LinksModel :: init :: End *****");
    }

    public String getLinkURL() {
        return linkURL;
    }

    public String getNewWindow() {
        return newWindow;
    }

    public String getShortURL() {
        return shortURL;
    }
}