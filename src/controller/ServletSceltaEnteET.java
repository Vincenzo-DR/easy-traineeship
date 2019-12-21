package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.TirocinioDAO;

/**
 * Servlet implementation class ServletInvioRichiestaEnteET.
 */
@WebServlet("/ServletSceltaEnteET")
public class ServletSceltaEnteET extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final TirocinioDAO tirocinioDAO = new TirocinioDAO();
	String mess = null;

	public ServletSceltaEnteET() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * Controllo autenticazione tramite parametro in sessione (0 = Studente).
		 */
		String userET = (String) request.getSession().getAttribute("userET");
		if ((userET == null) || (!userET.equals("0"))) {
			response.sendRedirect("login.jsp");
			return;
		}
		// Controllo nome ente
		String ente = request.getParameter("ente");
		if (ente.length() == 0) {
			throw new IllegalArgumentException("Il campo Ente e' vuoto");
		} else if (ente.length() > 64) {
			throw new IllegalArgumentException("Il campo Ente supera la lunghezza consentita");
		} else if (!ente.matches("^[ 0-9a-zA-Z\\.]+$")) {
			throw new IllegalArgumentException("Il campo Ente non rispetta il formato");
		}
		// Controllo nome tirocinante
		String nome = request.getParameter("nome");
		if (nome.length() == 0) {
			throw new IllegalArgumentException("Il campo Nome e' vuoto");
		} else if (nome.length() > 50) {
			throw new IllegalArgumentException("Il campo Nome supera la lunghezza consentita");
		} else if (!nome.matches("^[ 0-9a-zA-Z\\.]+$")) {
			throw new IllegalArgumentException("Il campo Nome non rispetta il formato");
		}
		// Controllo cognome tirocinante
		String cognome = request.getParameter("cognome");
		if (cognome.length() == 0) {
			throw new IllegalArgumentException("Il campo Cognome e' vuoto");
		} else if (cognome.length() > 50) {
			throw new IllegalArgumentException("Il campo Cognome supera la lunghezza consentita");
		} else if (!cognome.matches("^[ 0-9a-zA-Z\\.]+$")) {
			throw new IllegalArgumentException("Il campo Cognome non rispetta il formato");
		}
		// Controllo facoltà
		String facolta = request.getParameter("facolta");
		if (facolta.length() == 0) {
			throw new IllegalArgumentException("Il campo Facolta' e' vuoto");
		} else if (facolta.length() > 50) {
			throw new IllegalArgumentException("Il campo Facolta' supera la lunghezza consentita");
		} else if (!facolta.matches("^[ a-zA-Z]+$")) {
			throw new IllegalArgumentException("Il campo Facolta' non rispetta il formato");
		}
		// Controllo descrizione
		String descrizione = request.getParameter("descrizione");
		if (descrizione.length() == 0) {
			throw new IllegalArgumentException("Il campo Descrizione e' vuoto");
		} else if (descrizione.length() > 256) {
			throw new IllegalArgumentException("Il campo Descrizione supera la lunghezza consentita");
		}
		int matricola = (int) request.getSession().getAttribute("matricola");// da capire
		// se la passiamo in sessione
		String partitaIva = (String) request.getParameter("partitaIva");
		ArrayList<Integer> codTirocinio = tirocinioDAO.allTirocinioTirocinante(matricola);  //creare un metodo che restituisca un solo codTirocinio
		try {
			if (true /*(tirocinioDAO.richiestaEnte(codTirocinio, partitaIva, descrizione)) // metodo per associare l ente e la descrizione
						&& (tirocinioDAO.modificaStatoTirocinio(codTirocinio,"inviataAllEnte")*/)// metodo per settare lo stato
			{
				throw new IllegalArgumentException("L'invio della richiesta non e' stato effettuato");
			} else {
				request.setAttribute("L'invio della richiesta e' avvenuto con successo", mess);
				RequestDispatcher dispatcher = request.getRequestDispatcher("risultato.jsp");// Controlla jsp
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

