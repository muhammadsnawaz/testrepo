package com.adobe.aem.guides.wknd.core.pojo;



import java.util.List;

public class MenuPojo {

    private String pageTitle;
    private String pageLink;
    private String showLink;
    private String removeFromMenu;

    private List<MenuPojo> childPages;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public List<MenuPojo> getChildPages() {
        return childPages;
    }

    public void setChildPages(List<MenuPojo> childPages) {
        this.childPages = childPages;
    }

    public String getShowLink() {
        return showLink;
    }

    public void setShowLink(String showLink) {
        this.showLink = showLink;
    }

    public String getRemoveFromMenu() {
        return removeFromMenu;
    }

    public void setRemoveFromMenu(String removeFromMenu) {
        this.removeFromMenu = removeFromMenu;
    }
}