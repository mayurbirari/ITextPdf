package com.wissen.pdf.client.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wissen.pdf.client.PdfApplication;
import com.wissen.pdf.client.services.PdfService;
import com.wissen.pdf.client.services.PdfServiceAsync;

/**
 * this class is use to control services of pdf generation
 * @author wissen16
 * class for to give async callback to remote service methods
 */
public class PdfController {

	
	private final PdfServiceAsync pdfService = 
		GWT.create(PdfService.class);
	private static PdfController _instance;
  
	private static PdfApplication pdfObserver;
	
	private PdfController() {
	}

	/**
	 * to set service to async service
	 * @return returns pdfservice class
	 */
	public PdfServiceAsync getPdfService() {
		return pdfService;
	}
	
	/**
	 * without  a new operator controller get instantiate
	 * @return  a instance of controller
	 */
	public static synchronized PdfController getInstance() {
		if (_instance == null) {
			_instance = new PdfController();
		}
		return _instance;
	}
	/**
	 *  this method to make pdfapp as observer
	 * @param observer
	 */
	public void addTempObserver(PdfApplication observer) {
		pdfObserver=observer;
	}
	
	/**
	 * to create pdf give call to remote service methods
	 */
	public void createPdf() {
		 Window.alert("controller is ok");
		pdfService.createPdf("",pdfCallback);
	}
	
	/**
	 * CallBack to remote service method asynchronously 
	 */
	AsyncCallback<String> pdfCallback = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("call back fail :"+caught);
			GWT.log("Error in conversion ", caught);
		}
		@Override
		public void onSuccess(String pdfUrl) {
			 Window.alert("Pdf created Successfully:"+pdfUrl);
			pdfObserver.notifySuccess(pdfUrl);
		}
	};
}
