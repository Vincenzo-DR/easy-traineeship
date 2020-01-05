package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Tirocinio;
import model.DAO.TirocinioDAO;

/**
 * Servlet implementation class ServletDocumentiTirocinioET
 */
@WebServlet("/ServletDocumentiTirocinioET")
public class ServletDocumentiTirocinioET extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TirocinioDAO tirocinioDAO = new TirocinioDAO(); 
//	String userET = "1";
	public ServletDocumentiTirocinioET()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String userET = (String) request.getSession().getAttribute("userET");
		String userET = "1";
		System.out.println("USERET ="+userET);
/*		if(userET == null || (!userET.equals("0") || !userET.equals("1") || !userET.equals("2") || !userET.equals("3"))) {
			response.sendRedirect("login.jsp");
			return;	
		}*/
		ArrayList<Tirocinio> listaTirocinio=new ArrayList<Tirocinio>();
//		String statoTirocinio = (String) request.getSession().getAttribute("statoTirocinio");
		String statoTirocinio = "Accettato e in attesa di firma";
		
		if(userET.equals("1") &&(statoTirocinio.equals("Accettato e in attesa di firma") || statoTirocinio.equals("Accettato e in attesa di firma dalla Segreteria, Ente e Admin")))
		{
			listaTirocinio=tirocinioDAO.allTirocinioByStato(statoTirocinio);
		}
		else if(userET.equals("2") && (statoTirocinio.equals("Accettato e in attesa di firma") || statoTirocinio.equals("Accettato e in attesa di firma dall' Admin")))
		{
			listaTirocinio=tirocinioDAO.allTirocinioByStato(statoTirocinio);
		}
		else if(userET.equals("3") && (statoTirocinio.equals("Accettato e in attesa di firma") || statoTirocinio.equals("Accettato e in attesa di firma dall'Ente e Admin")))
		{
			String partitaIva = (String) request.getSession().getAttribute("partitaIva");
			listaTirocinio=tirocinioDAO.allDocumentiDaFirmareByEnte(partitaIva, statoTirocinio);
		}
		else if(userET.equals("0") && statoTirocinio.equals("Accettato e in attesa di firma"))
		{
			int matricola = (int) request.getSession().getAttribute("matricola");
			listaTirocinio= tirocinioDAO.allDocumentiDaFirmareByStudente(matricola, statoTirocinio);
		}
		request.setAttribute("listaTirocinio", listaTirocinio);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("DocumentiET.jsp");// Controlla jsp
		dispatcher.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}