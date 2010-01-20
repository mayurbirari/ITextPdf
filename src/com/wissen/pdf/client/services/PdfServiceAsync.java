package com.wissen.pdf.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * 
 * @author wissen16
 * Remote Service interface to create abstract method 
 */
public interface PdfServiceAsync {
	/**
	 * method to create pdf
	 * @param Url to generate this URL
	 * @param callback to return url asynchronously
	 */	
	void createPdf(String Url,AsyncCallback<String> callback);
	
}
