package com.droidshop.api.model.error;

import java.util.List;

public class WebServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private int exceptionCode;
    private List<String> exceptionMessageList;

    public WebServiceException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public WebServiceException(int exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }

    public WebServiceException(int exceptionCode, String exceptionMessage, List<String> exceptionMessageList) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
        this.exceptionMessageList = exceptionMessageList;
    }

    public String getExceptionMessage() {
        return super.getMessage();
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public List<String> getExceptionMessageList() {
        return exceptionMessageList;
    }

    public void setExceptionMessageList(List<String> exceptionMessageList) {
        this.exceptionMessageList = exceptionMessageList;
    }
}
