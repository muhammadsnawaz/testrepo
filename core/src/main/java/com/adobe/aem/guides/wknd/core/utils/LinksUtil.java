package com.adobe.aem.guides.wknd.core.utils;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class LinkCheckerUtil.
 */
public class LinksUtil {

    /**
     * The Constant LOG.
     */
    private static final Logger log = LoggerFactory.getLogger(LinksUtil.class);

    /**
     * Links util.
     * <p>
     * This method is used to stop the instantiation of this class.
     */
    private LinksUtil() {
    }

    /**
     * Validate url.
     *
     * @param url      the url
     * @param resource the resource
     * @return the string
     */
    public static String checkInternalExternalURLByResource(String url, Resource resource) {
        try {
            log.debug("Starts linksutil class checkInternalExternalURL method !");
            if (resource != null && isCQPage(resource) && url.startsWith("/")) {
                return new StringBuilder(url).append(EnumConstants.DOT_HTML_APPENDER.getStringValue()).toString();
            }
        } catch (Exception e) {
            log.error("Exception in method {}", e);
        }
        return url;
    }

    /**
     * Validate url.
     *
     * @param url      the url
     * @param resource the resource
     * @return the string
     */
    public static boolean checkInternalURLByResource(String url, Resource resource) {
        boolean internal = false;
        try {
            log.debug("Starts linksutil class checkInternalURLByResource method !");
            if (resource != null && isCQPage(resource) && url.startsWith("/")) {
                internal = true;
            }
        } catch (Exception e) {
            log.error("Exception in method {}", e);
        }
        return internal;
    }

    /**
     * Check internal url by page.
     *
     * @param page the page
     * @return the url
     */
    public static String checkInternalURLByPage(Page page) {
        String url = null;
        if (page != null) {
            url = page.getPath();
            return new StringBuilder(url).append(EnumConstants.DOT_HTML_APPENDER.getStringValue()).toString();
        }
        return url;
    }

    /**
     * Checks if is CQ page.
     *
     * @param resource the resource
     * @return true, if is CQ page
     */
    public static boolean isCQPage(Resource resource) {
        log.debug("Starts linksutil class IsCQPage method !");
        ValueMap properties = resource.adaptTo(ValueMap.class);
        if (properties != null) {
            String primaryType = properties.get(JcrConstants.JCR_PRIMARYTYPE, String.class);
            if (StringUtils.isNotBlank(primaryType) && primaryType.equals(NameConstants.NT_PAGE)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check internal URL by path.
     *
     * @param path             the path
     * @param resourceResolver the resource resolver
     * @return the string
     */
    public static String checkInternalURLByPath(String path, ResourceResolver resourceResolver) {
        log.debug("LinksUtil :: checkInternalURLByPath :: Start");
        String url = null;
        try {
            if (null != path) {
                if (path.startsWith("/") && StringUtils.contains(path, EnumConstants.DOT_HTML_APPENDER.getStringValue())) {
                    return path;
                } else if (path.startsWith("/") && !StringUtils.contains(path, EnumConstants.DOT_HTML_APPENDER.getStringValue()) && StringUtils.contains(path, "?")) {
                    String queryString = StringUtils.substringAfter(path, "?");
                    String urlString = StringUtils.substringBefore(path, new StringBuilder("?").append(queryString).toString());
                    return new StringBuilder(urlString).append(EnumConstants.DOT_HTML_APPENDER.getStringValue()).append("?").append(queryString).toString();
                } else {
                    Resource resource = resourceResolver.getResource(path);
                    url = checkInternalExternalURLByResource(path, resource);
                }
            }
            log.debug("LinksUtil :: checkInternalURLByPath :: End");
        } catch (Exception e) {
            log.error("LinksUtil :: checkInternalURLByPath :: Exception {}", e);
        }
        return url;
    }

    /**
     * Checks if is new window.
     *
     * @param newWindow the new window
     * @return the string
     */
    public static String isNewWindow(String newWindow) {
        log.debug("In isNewWindow method of LinksUtil.");
        if ("true".equals(newWindow)) {
            return "_blank";
        } else {
            return "_self";
        }
    }

    /**
     * Gets the object from json.
     *
     * @param jsonString the json string
     * @param obj        the obj
     * @return the object from json
     */
    public static Object getObjectFromJson(String jsonString, Object obj) {
        Gson gson = new Gson();
        Object returnValue = null;
        try {
            returnValue = gson.fromJson(jsonString, obj.getClass());
        } catch (Exception e) {
            log.error("Exception occured in WebServiceUtil :: getObjectFromJson --> ", e);
        }
        return returnValue;
    }

    public static String getCssTagClass(String[] ctaClassName, ResourceResolver resolver) {
        String[] cta = ctaClassName;
        List<String> ctaClassList = new ArrayList<>();
        try {
            TagManager tagmanager = (null != resolver) ? resolver.adaptTo(TagManager.class) : null;
            if (tagmanager != null) {
                for (String item : cta) {
                    Tag producttag = tagmanager.resolve(item);
                    if (null != producttag) {
                        ctaClassList.add(producttag.getName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("LinksUtil :: Exception", e);
        }
        return StringUtils.join(ctaClassList, " ");
    }
}