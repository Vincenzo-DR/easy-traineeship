package controller;

import interfacce.UserInterface;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.TirocinioDAO;
import model.Tirocinio;


/**
 * Servlet implementation class SerlvetGestioneRichiesteEnteET.
 */
@WebServlet("/ServletGestioneRichiesteEnteET")
public class ServletGestioneRichiesteEnteET extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @see HttpServlet#HttpServlet()
   */
  public ServletGestioneRichiesteEnteET() {
    super();
  }

  /**
   * Method doGet().
   * 
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Controllo autenticazione tramite parametro in sessione (3 = EnteConvenzionato).
    String userET = (String) request.getSession().getAttribute("userET");
    if ((userET == null) || (!userET.equals("3"))) {
      response.sendRedirect("login.jsp");
      return;
    }
    //Istanzio il tirocinioDaO, e prendo il flag dalla jsp.
    TirocinioDAO tirocinioDaO = new TirocinioDAO();
    String flag = request.getParameter("flag");
    //recupero l'utente dalla sessione
    UserInterface user = (UserInterface) request.getSession().getAttribute("user");
    //Istanzio due arraylist, uno dove inserisco tutti i tirocinanti in base all'ente
    ArrayList<Tirocinio> listaRichiesteEnte = new ArrayList<Tirocinio>();
    //L'altro dove successivamente inseriṛ i Tirocini collegati 
    //solo in un determinato stato. "In attesa Ente"
    ArrayList<Tirocinio> listaRichiesteEnteInAttesa = new ArrayList<Tirocinio>();
    // Ricerco tutte le richieste all'ente e li inserisco nella listaRichiesteEnte
    try {
      listaRichiesteEnte = tirocinioDaO.allTirocinioByEnte(user.getEmail());
      if (listaRichiesteEnte != null) {
        for (int i = 0; i < listaRichiesteEnte.size(); i++) {
          if (listaRichiesteEnte.get(i).getStatoTirocinio().equals("In attesa Ente")) {
            listaRichiesteEnteInAttesa.add(listaRichiesteEnte.get(i));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (flag != null) {
      // Visualizza Lista Richieste con flag = 1
      if (flag.equals("1")) {
        if (listaRichiesteEnte != null) {
          request.setAttribute("listaRichiesteEnte", listaRichiesteEnteInAttesa);
        }
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
        dispatcher.forward(request, response);
      } else if (flag.equals("2")) {
        // Accetta Richiesta - Prendo il codice del tirocinio dalla 
        //request e chiamo il metodo del DAO
        // per modificare lo stato del tirocinio in "Accettato e in attesa di firma"
        try {
          String codice = request.getParameter("codice");
          tirocinioDaO.modificaStatoTirocinio(Integer.valueOf(codice),
              "Accettato e in attesa di firma");
        } catch (Exception e) {
          e.printStackTrace();
          //Setto il codice per il toastr alla pagina successiva 2 di errore 1 di successo
          request.setAttribute("cod", "2");
          RequestDispatcher dispatcher =
              request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
          dispatcher.forward(request, response);
        }
        request.setAttribute("cod", "1");
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
        dispatcher.forward(request, response);
      } else if (flag.equals("3")) {
        // Rifuta Richiesta - Prendo il codice del tirocinio
        // dalla request e chiamo il metodo del DAO
        // per modificare lo stato del tirocinio
        try {
          String codice = request.getParameter("codice");
          String motivazione = request.getParameter("motivazione");
          tirocinioDaO.modificaStatoTirocinio(Integer.valueOf(codice), "Rifiutato");
          Tirocinio tirocinio = new Tirocinio();
          tirocinio = tirocinioDaO.TirocinioByCodTirocinio(Integer.valueOf(codice));
          //salvo la motivazione del rifiuto nel campo "Descrizione ENTE" del DataBase
          tirocinio.setDescrizioneEnte(motivazione);
          tirocinioDaO.modificaTirocinio(tirocinio);
          // Salvo lo storico nel vecchio tirocinio e istanzio un nuovo tirocinio
          // con i valori della vecchia richiesta 
          // per impostarlo allo stato precedente
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

          if (tirocinioDaO.tirocinioAttivo(tirocinio.getMatricola()) == null) {
            tirocinioDaO.inserisciTirocinio(tirocinio2);
          }

        } catch (Exception e) {
          e.printStackTrace();
          //Inserisco il cod per il toastr di errore
          request.setAttribute("cod", "4");
          RequestDispatcher dispatcher =
              request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
          dispatcher.forward(request, response);
        }
        //Inserisco il cod per il toastr di successo
        request.setAttribute("cod", "3");
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("_areaEnteET/VisualizzaRichiestaEnteET.jsp");
        dispatcher.forward(request, response);
      }
    }


  }

  /**
   * Method doPost().
   * 
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}
