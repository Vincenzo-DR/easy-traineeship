package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import interfacce.UserInterface;
import model.Tirocinio;
import model.DAO.TirocinioDAO;

/**
 * Servlet implementation class SerlvetGestioneRichiesteEnteET
 */
@WebServlet("/ServletGestioneRichiesteEnteET")
public class ServletGestioneRichiesteEnteET extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestioneRichiesteEnteET() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /**
	     * Controllo autenticazione tramite parametro in sessione (3 = EnteConvenzionato).
	     */
	    String userET = (String) request.getSession().getAttribute("userET");
	    if ((userET == null) || (!userET.equals("3"))) {
	      response.sendRedirect("login.jsp");
	      return;
	    }
	    
	    TirocinioDAO tirocinioDAO = new TirocinioDAO();
	    String flag = request.getParameter("flag");
	    UserInterface user = (UserInterface)request.getSession().getAttribute("user");
	    ArrayList<Tirocinio> listaRichiesteEnte=new ArrayList<Tirocinio>();
	    ArrayList<Tirocinio> listaRichiesteEnteInAttesa = new ArrayList<Tirocinio>();
	  //Ricerco tutte le richieste all'ente e li inserisco nella listaRichiesteEnte
		try {
			listaRichiesteEnte=tirocinioDAO.allTirocinioByEnte(user.getEmail());
			if(listaRichiesteEnte!=null) {
				for (int i=0; i<listaRichiesteEnte.size(); i++) {
					if (listaRichiesteEnte.get(i).getStatoTirocinio().equals("In attesa dell Ente")) {
						listaRichiesteEnteInAttesa.add(listaRichiesteEnte.get(i));	
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (flag!=null) {
	    	
	    	//Visualizza Lista Richieste
	    	if (flag.equals("1")) {
	    		if(listaRichiesteEnte!=null)
	    		{
	    			request.setAttribute("listaRichiesteEnte", listaRichiesteEnteInAttesa);
	    		}
	    		RequestDispatcher dispatcher = request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
	            dispatcher.forward(request, response);
	    	}
	    	
	    	//Accetta Richiesta - Prendo il codice del tirocinio dalla request e chiamo il metodo del DAO per modificare lo stato del tirocinio 
	    	else if (flag.equals("2")) {
	    		try {
	    			String codice = request.getParameter("codice");
	    			tirocinioDAO.modificaStatoTirocinio(Integer.valueOf(codice), "Accettato e in attesa di firma (tutti)");
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		RequestDispatcher dispatcher = request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
	            dispatcher.forward(request, response);
	    	}
	    	//Rifuta Richiesta - Prendo il codice del tirocinio dalla request e chiamo il metodo del DAO per modificare lo stato del tirocinio
	    	else if (flag.equals("3")) {
	    		try {
	    			String codice = request.getParameter("codice");
	    			String motivazione = request.getParameter("motivazione");
	    			tirocinioDAO.modificaStatoTirocinio(Integer.valueOf(codice), "Rifiutato");
	    			Tirocinio tirocinio = new Tirocinio();
	    			tirocinio = tirocinioDAO.TirocinioByCodTirocinio(Integer.valueOf(codice));
	    			Tirocinio tirocinio2 = new Tirocinio();
	    			tirocinio2.setDataInizioTirocinio(tirocinio.getDataInizioTirocinio());
	    			tirocinio2.setCfuPrevisti(tirocinio.getCfuPrevisti());
	    			tirocinio2.setCompetenze(tirocinio.getCompetenze());
	    			tirocinio2.setCompetenzeAcquisire(tirocinio.getCompetenzeAcquisire());
					tirocinio2.setAttivitaPreviste(tirocinio.getAttivitaPreviste());
					tirocinio2.setSvolgimentoTirocinio(tirocinio.getSvolgimentoTirocinio());
					tirocinio2.setStatoTirocinio("In attesa della Segreteria");
					tirocinio2.setMatricola(tirocinio.getMatricola());
	    			tirocinio2.setTirocinante(tirocinio.getTirocinante());
	    			tirocinioDAO.inserisciTirocinio(tirocinio2);
	    			System.out.println(motivazione);
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		
	    		RequestDispatcher dispatcher = request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
	            dispatcher.forward(request, response);
	    	}
	    }
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
