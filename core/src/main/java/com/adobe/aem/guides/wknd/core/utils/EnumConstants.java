package com.adobe.aem.guides.wknd.core.utils;

public enum EnumConstants {

    DOT_HTML_APPENDER(".html"), ROOTPAGE_PATH("rootPagePath"), NAV_TITLE("jcr:title"), REDIRECT_TARGET("cq:redirectTarget"), SHOW_LINK("showLink");

    private String stringValue;

    private EnumConstants(String constant) {
        this.stringValue = constant;
    }

    public String getStringValue() {
        return stringValue;
    }

}