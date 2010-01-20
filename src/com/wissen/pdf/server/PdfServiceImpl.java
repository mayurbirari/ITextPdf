package com.wissen.pdf.server;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wissen.pdf.client.services.PdfService;

/**
* The server side implementation of the RPC service.
*/
@SuppressWarnings("serial")
public class PdfServiceImpl extends RemoteServiceServlet implements PdfService {


	String username = "root";
	String password = "wissen";
	Connection con;
	ResultSet rs;
	Statement stmt1;
	String connurl = "jdbc:mysql://localhost:3306/reports";
	PrintWriter pw1;
	String custid,cname,caddr,descr,amt;
	/**
	 * to fetch a record from database
	 */
	private void fetchReports(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(connurl, username, password);
			stmt1 = con.createStatement();
			rs = stmt1.executeQuery("select * from customer");
			while(rs.next()){
				custid=rs.getString(1);
				cname=rs.getString(2);
				caddr=rs.getString(3);
				descr=rs.getString(4);
				amt=rs.getString(5);
			}
			con.close();
		} catch (Exception e) {
			System.out.println("Sql Error" + e.getMessage());
		}
	}
	
	/**
	 * to create or generate pdf file by using itext
	 */
@Override
public String createPdf(String pdfUrl) {

	
	pdfUrl="Assignment.pdf";
	Document document = new Document();
	try {
		fetchReports();
		PdfWriter w=PdfWriter.getInstance(document, new FileOutputStream("Assignment.pdf"));
		document.open();
		PdfPTable mainTable =new PdfPTable(2);
		mainTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		PdfPCell emptyCell=new PdfPCell(new Paragraph("                   "));
		emptyCell.setHorizontalAlignment(1);
		emptyCell.setBorder(0);
		emptyCell.setColspan(2);
		mainTable.addCell(emptyCell);
	
		PdfPCell headerCell=new PdfPCell(new Paragraph("Sheet1"));
		headerCell.setHorizontalAlignment(1);
		headerCell.setBorder(0);
		headerCell.setColspan(2);
		mainTable.addCell(headerCell);
		mainTable.addCell(emptyCell);
	
		Font comFont=new Font(Font.TIMES_ROMAN);
		comFont.setSize(18);
		Phrase com=new Phrase("Wissen Labs",comFont);
		PdfPCell comCell=new PdfPCell(com);
		comCell.setHorizontalAlignment(0);
		comCell.setBorder(0);
		mainTable.addCell(comCell);
		
		
		Font projFont=new Font(Font.BOLD);
		projFont.setSize(25);
		projFont.setStyle("bold");
		projFont.setColor(new BaseColor(93,107,184));
		Phrase projectName=new Phrase("INVOICE",projFont);
		PdfPCell projectCell=new PdfPCell(projectName);
		projectCell.setHorizontalAlignment(2);
		projectCell.setBorder(0);
		mainTable.addCell(projectCell);
	
		mainTable.addCell(emptyCell);
		Font addrFont=new Font(Font.BOLD);
		addrFont.setSize(9);
		PdfPTable addrTable =new PdfPTable(1);
		PdfPCell row1=new PdfPCell(new Phrase("4th Floor, Rajvi Enclave",addrFont));
		row1.setBorder(0);
		addrTable.addCell(row1);
		PdfPCell row2=new PdfPCell(new Phrase("New Pandit Colony, Nasik, Maharashtra, India",addrFont));
		row2.setBorder(0);
		addrTable.addCell(row2);
		PdfPCell row3=new PdfPCell(new Phrase("Phone: 91 253 301 2029/91 253 301 2038",addrFont));
		row3.setBorder(0);
		addrTable.addCell(row3);
		
		
		PdfPTable dateTable =new PdfPTable(2);
		dateTable.setHorizontalAlignment(2);
		PdfPCell row11=new PdfPCell(new Phrase("DATE:",addrFont));
		row11.setHorizontalAlignment(2);
		row11.setBorder(0);
		dateTable.addCell(row11);
		PdfPCell row12=new PdfPCell(new Phrase("Jan 17th 2010",addrFont));
		row12.setBackgroundColor(new BaseColor(181,196,220));
		row12.setHorizontalAlignment(1);
		row12.setBorder(0);
		dateTable.addCell(row12);
		PdfPCell row21=new PdfPCell(new Phrase("INVOICE #",addrFont));
		row21.setHorizontalAlignment(2);
		row21.setBorder(0);
		dateTable.addCell(row21);
		PdfPCell iCell=new PdfPCell(new Phrase("62",addrFont));
		iCell.setHorizontalAlignment(1);
		dateTable.addCell(iCell);
		PdfPCell row32=new PdfPCell(new Phrase("Customer ID",addrFont));
		row32.setHorizontalAlignment(2);
		row32.setBorder(0);
		dateTable.addCell(row32);
		PdfPCell idCell=new PdfPCell(new Phrase(custid,addrFont));
		idCell.setHorizontalAlignment(1);
		dateTable.addCell(idCell);
		
		PdfPCell addrCell=new PdfPCell(addrTable);
		addrCell.setHorizontalAlignment(0);
		addrCell.setBorder(0);
		mainTable.addCell(addrCell);
		dateTable.setWidthPercentage(1);
		PdfPCell dateCell=new PdfPCell(dateTable);
		dateCell.setHorizontalAlignment(2);
		dateCell.setBorder(0);
		mainTable.addCell(dateCell);
		mainTable.addCell(emptyCell);	
		
		Font tabFont=new Font(Font.TIMES_ROMAN);
		tabFont.setSize(9);
		tabFont.setColor(BaseColor.WHITE);
		PdfPTable billTable =new PdfPTable(1);
		billTable.setHorizontalAlignment(0);
		PdfPCell row1Cell=new PdfPCell(new Phrase("BILL TO",tabFont));
		row1Cell.setHorizontalAlignment(0);
		row1Cell.setBorder(0);
		row1Cell.setBackgroundColor(new BaseColor(47,70,130));
		billTable.addCell(row1Cell);
		
		PdfPCell row2Cell=new PdfPCell(new Phrase(cname,addrFont));
		row2Cell.setHorizontalAlignment(0);
		row2Cell.setBorder(0);
		billTable.addCell(row2Cell);
		
		PdfPCell row3Cell=new PdfPCell(new Phrase(caddr,addrFont));
		row3Cell.setHorizontalAlignment(0);
		row3Cell.setBorder(0);
		billTable.addCell(row3Cell);
		
		PdfPCell billCell=new PdfPCell(billTable);
		billCell.setHorizontalAlignment(0);
		billCell.setBorder(0);
		billCell.setColspan(2);
		billTable.setWidthPercentage(40);
		mainTable.addCell(billCell);
		mainTable.addCell(emptyCell);
		mainTable.addCell(emptyCell);
		
		PdfPTable infoTable=new PdfPTable(4);
		billTable.setHorizontalAlignment(0);
		PdfPCell head1Cell=new PdfPCell(new Phrase("DESCRIPTION",tabFont));
		head1Cell.setHorizontalAlignment(1);
		head1Cell.setBackgroundColor(new BaseColor(47,70,130));
		head1Cell.setBorder(0);
		head1Cell.setColspan(3);
		infoTable.addCell(head1Cell);
		
		PdfPCell head2Cell=new PdfPCell(new Phrase("AMOUNT",tabFont));
		head2Cell.setHorizontalAlignment(1);
		head2Cell.setBackgroundColor(new BaseColor(47,70,130));
		head2Cell.setBorder(0);
		infoTable.addCell(head2Cell);
		
		PdfPCell data1Cell=new PdfPCell(new Phrase(descr,addrFont));
		data1Cell.setHorizontalAlignment(1);
		data1Cell.setColspan(3);
		data1Cell.setMinimumHeight(200);
		infoTable.addCell(data1Cell);
		
		PdfPCell data2Cell=new PdfPCell(new Phrase(amt,addrFont));
		data2Cell.setHorizontalAlignment(1);
		data2Cell.setMinimumHeight(200);
		infoTable.addCell(data2Cell);
		
		
		
		PdfPCell infoCell=new PdfPCell(infoTable);
		infoCell.setHorizontalAlignment(0);
		infoCell.setBorder(0);
		infoCell.setColspan(2);
		mainTable.addCell(infoCell);
		
		
		PdfPTable commentTable=new PdfPTable(1);
		commentTable.addCell(emptyCell);
		PdfPCell commCell=new PdfPCell(new Phrase("OTHERS COMMENTS",addrFont));
		commCell.setHorizontalAlignment(0);
		commCell.setBorder(0);
		commCell.setBackgroundColor(BaseColor.GRAY);
		commentTable.addCell(commCell);
		
		PdfPCell commdataCell=new PdfPCell();
		commdataCell.setHorizontalAlignment(0);
		commdataCell.setMinimumHeight(80);
		commdataCell.addElement(new Phrase("1 Total Payment Due in 30days",addrFont));
		commdataCell.addElement(new Phrase("2 Please include invoice number on your check",addrFont));
		commentTable.addCell(commdataCell);
		
		PdfPTable amountTable=new PdfPTable(2);
		PdfPCell r11=new PdfPCell(new Phrase("SUBTOTAL",addrFont));
		r11.setHorizontalAlignment(2);
		r11.setBorder(0);
		amountTable.addCell(r11);
		PdfPCell r12=new PdfPCell(new Phrase("$"+amt,addrFont));
		r12.setBackgroundColor(new BaseColor(181,196,220));
		r12.setHorizontalAlignment(1);
		r12.setBorder(0);
		amountTable.addCell(r12);
		PdfPCell r21=new PdfPCell(new Phrase("TAXRATE",addrFont));
		r21.setHorizontalAlignment(2);
		r21.setBorder(0);
		amountTable.addCell(r21);
		PdfPCell r22=new PdfPCell(new Phrase("0.000%",addrFont));
		r22.setHorizontalAlignment(1);
		r22.setBorder(0);
		amountTable.addCell(r22);
		PdfPCell r31=new PdfPCell(new Phrase("TAX",addrFont));
		r31.setHorizontalAlignment(2);
		r31.setBorder(0);
		amountTable.addCell(r31);
		PdfPCell r32=new PdfPCell(new Phrase("$0.00",addrFont));
		r32.setBackgroundColor(new BaseColor(181,196,220));
		r32.setHorizontalAlignment(1);
		r32.setBorder(0);
		amountTable.addCell(r32);
		PdfPCell r41=new PdfPCell(new Phrase("OTHER",addrFont));
		r41.setHorizontalAlignment(2);
		r41.setBorder(0);
		amountTable.addCell(r41);
		PdfPCell r42=new PdfPCell(new Phrase("$"+amt,addrFont));
		r42.setHorizontalAlignment(1);
		r42.setBorder(0);
		amountTable.addCell(r42);
		
		PdfPCell LINE=new PdfPCell();
		LINE.setBorder(0);
		LINE.setBorderWidthTop(1);
		LINE.setColspan(2);
		amountTable.addCell(LINE);
		PdfPCell r51=new PdfPCell(new Phrase("TOTAL",addrFont));
		r51.setHorizontalAlignment(2);
		r51.setBorder(0);
		r51.setBorderWidthTop(1);
		amountTable.addCell(r51);
		PdfPCell r52=new PdfPCell(new Phrase("$"+amt,addrFont));
		r52.setBackgroundColor(new BaseColor(181,196,220));
		r52.setHorizontalAlignment(1);
		r52.setBorder(0);
		r52.setBorderWidthTop(1);
		amountTable.addCell(r52);
		amountTable.addCell(emptyCell);
	    PdfPCell t1=new PdfPCell(new Phrase("Make checks payable to",addrFont));
		t1.setBorder(0);
		t1.setColspan(2);
		t1.setHorizontalAlignment(1);
		amountTable.addCell(t1);
		Font wFont=new Font(Font.BOLD);
		wFont.setSize(9);
		wFont.setStyle("bold");
		PdfPCell t2=new PdfPCell(new Phrase("Wissen Labs",wFont));
		t2.setBorder(0);
		t2.setColspan(2);
		t2.setHorizontalAlignment(1);
		amountTable.addCell(t2);
		
		
		PdfPTable commact=new PdfPTable(7);
		
		PdfPCell commtCell=new PdfPCell(commentTable);
		commtCell.setHorizontalAlignment(0);
		commtCell.setColspan(4);
		commtCell.setBorder(0);
		commact.addCell(commtCell);
		PdfPCell space =new PdfPCell(new Phrase(" "));
		space.setBorder(0);
		commact.addCell(space);
		
		//commentTable.setTotalWidth(245f);
	     //  commentTable.writeSelectedRows(0,-1,87,355,w.getDirectContent());
		amountTable.setHorizontalAlignment(2);
		PdfPCell amtCell=new PdfPCell(amountTable);
		amtCell.setColspan(2);
		amtCell.setHorizontalAlignment(2);
		amtCell.setBorder(0);
		commact.addCell(amtCell);
		
		
		PdfPCell caCell=new PdfPCell(commact);
		caCell.setColspan(2);
		caCell.setBorder(0);
		mainTable.addCell(caCell);
		
		
		mainTable.addCell(emptyCell);
		mainTable.addCell(emptyCell);
		
		
//		Font qFont=new Font(Font.STRIKETHRU);
//		qFont.setSize(13);
		Paragraph contactP =new Paragraph("If you have any questions about this invoice, please contact",addrFont);
		Font ancf=new Font();
		ancf.setSize(9);
		ancf.setColor(BaseColor.BLUE);
		Anchor anchor=new Anchor("sushrut@wissen.co.in",ancf);
		anchor.setReference("mailto:http://sushrut@wissen.co.in");
		Paragraph contactPi =new Paragraph("Sushrut Bidwai,",addrFont);
		contactPi.add(anchor);
		contactPi.add(", +91 986 023 8124");
		contactP.setAlignment(1);
		contactPi.setAlignment(1);
		
		
		Font thankFont=new Font(Font.BOLD);
		thankFont.setSize(12);
		thankFont.setStyle("bolditalic");
		Paragraph thankChunk=new Paragraph("Thank You For Your Business!",thankFont);
		thankChunk.setAlignment(1);
		document.add(mainTable);
		document.add(contactP);
		document.add(contactPi);
		document.add(new Paragraph("                           "));
		document.add(thankChunk);
		
		document.setPageCount(1);
		
		Rectangle rect=new Rectangle(document.getPageSize());
		document.setPageSize(rect);
	} catch (Exception e) {
		System.out.println("docerror: "+e.getMessage());
		}
	document.close();
	
	return pdfUrl;
}
}
