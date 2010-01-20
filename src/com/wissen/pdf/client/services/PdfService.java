package com.wissen.pdf.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
/**
 * 
 * @author wissen16
 * Remote Service interface to create abstract method 
 */
@RemoteServiceRelativePath("greet")
public interface PdfService extends RemoteService {
	/**
	 * method to create pdf
	 * @param Url to generate this URL
	 * @return specified Url
	 */
	String createPdf(String Url);
}
