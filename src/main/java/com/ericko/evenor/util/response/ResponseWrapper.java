package com.ericko.evenor.util.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseWrapper {
    public final String detail;
    public final String message;

    public ResponseWrapper(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}
