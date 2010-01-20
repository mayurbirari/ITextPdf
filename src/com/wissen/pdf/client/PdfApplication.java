package com.wissen.pdf.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wissen.pdf.client.controller.PdfController;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author wissen16(Mayur Birari)
 */
public class PdfApplication implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	private HorizontalPanel containerPanel;

    private FlexTable pdfTable;

    private Button pdfButton;
    
    private Frame pdfFrame;
    /**
     * to load basic widgets and provide UI
     */
	public void onModuleLoad() {
		
		PdfController.getInstance().addTempObserver(this);
		pdfButton=new Button("Generate Pdf");
		pdfFrame=new Frame();
		pdfTable=new FlexTable();
		containerPanel=new HorizontalPanel();
		RootPanel.get("pageContent").add(containerPanel);
		pdfTable.setWidget(0, 0, pdfButton);
		containerPanel.add(pdfTable);
		/**
		 * to create pdf file from this button
		 */
		pdfButton.addClickHandler(new ClickHandler() {
			 @Override
	            public void onClick(ClickEvent event) {
	                createPdf();   
	            }
		});
		
	}
	/**
	 * generate pdf function
	 */
	void createPdf(){
		Window.alert("click is ok");
	PdfController.getInstance().createPdf();	
	}
	/**
	 * on success of pdf creation the Url is set on a frame
	 * @param Url url of pdf file
	 */
	public void notifySuccess(String Url){
		Window.alert("notify is ok");
		pdfFrame.setUrl("http://localhost:8090/pdfapplication/"+Url);
		pdfFrame.setSize("800px","1000px");
		pdfTable.setWidget(1, 0,pdfFrame);
	}
}

