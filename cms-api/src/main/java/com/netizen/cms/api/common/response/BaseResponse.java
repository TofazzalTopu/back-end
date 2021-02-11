package com.netizen.cms.api.common.response;

/**
 *
 * @author riad
 */
public class BaseResponse {
    
    private String message;
    
    private int messageType;    
   

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
   
    
    
}
