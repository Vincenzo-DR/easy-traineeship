package test.testET;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import controller.DbConnection;
import controller.ServletRichiestaInizioTirocinioET;
import java.io.*;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Student;
import org.junit.jupiter.api.*;
/*
 * 
 * 
 * Classe per il testing della ServletRichiestaInizioTirocinio .
 * Questa classe di test � stata scritta secondo la
 * metodologia BLACK BOX.
 * 
 * 
*/

class ServletRichiestaInizioTirocinioETTest {

  Connection conn = new DbConnection().getInstance().getConn();
  // Creazione mock
  HttpServletRequest requestMock = mock(HttpServletRequest.class);
  HttpServletResponse responseMock = mock(HttpServletResponse.class);
  HttpSession sessionMock = mock(HttpSession.class);
  ServletRichiestaInizioTirocinioET test = new ServletRichiestaInizioTirocinioET();
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
  Student s = 
      new Student("a.delpiero10@studenti.unisa.it", "Alessandro", "Del Piero", 'M', "password", 0);
  /*
   * Prima di ogni @test simuliamo la sessione di uno studente (0)
   * ed inseriamo una matricola di un tirocinante
   */
  @BeforeEach
  public void setUp() {
    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("userET")).thenReturn("0");
    when(sessionMock.getAttribute("user")).thenReturn(s);
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("512109999");
  }
  /*
   * Dopo ogni @test eliminiamo il tirocinante inserito
   */
  @AfterEach
  void tearDown() {
    try {
      Statement stmtSelect = conn.createStatement();
      stmtSelect.executeUpdate("DELETE FROM User WHERE EMAIL='a.delpiero10@studenti.unisa.it';");
      conn.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Test case TC_GR_6.01: Campo Nome vuoto
  @Test
  void testCampoNomeVuoto() {
    Student s = new Student("a.delpiero10@studenti.unisa.it", "", "Del Piero", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Nome' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.02: Campo Nome troppo lungo
  @Test
  void testCampoNomeTroppoLungo() {
    Student s = new Student("a.delpiero10@studenti.unisa.it",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "Del Piero", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Nome' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.03: Campo Nome non rispetta il formato
  @Test
  void testCampoNomeFormatoErrato() {
    Student t =
        new Student("a.delpiero10@studenti.unisa.it", "Ale99", "Del Piero", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(t);
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Nome' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.04: Campo Cognome vuoto
  @Test
  void testCampoCognomeVuoto() {
    Student s = new Student("a.delpiero10@studenti.unisa.it", "Alessandro", "", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Cognome' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.05: Campo Cognome troppo lungo
  @Test
  void testCampoCognomeTroppoLungo() {
    Student s = new Student("a.delpiero10@studenti.unisa.it", "Alessandro",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Cognome' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.06: Campo Cognome non rispetta il formato
  @Test
  void testCampoCognomeFormatoErrato() {
    Student s =
        new Student("a.delpiero10@studenti.unisa.it", "Alessandro", "DP99", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Cognome' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.10: Campo Matricola Vuoto
  @Test
  void testCampoMatricolaVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("For input string: \"\"", e.getMessage());
  }

  // Test case TC_GR_6.11: Campo Matricola troppo lungo
  @Test
  void testCampoMatricolaTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("1111111111111");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Matricola' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.12: Campo Matricola non rispetta il formato
  @Test
  void testCampoMatricolaFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("aaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("For input string: \"aaaaaaaaaa\"", e.getMessage());
  }

  // Test case TC_GR_6.13: Campo Data di Nascita vuoto
  @Test
  void testCampoDataNascitaVuoto() {
    when(requestMock.getParameter("facolta")).thenReturn("Informatica");
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Data di Nascita' &egrave vuoto", e.getMessage());
  }


  // Test case TC_GR_6.16: Campo Luogo di Nascita vuoto
  @Test
  void testCampoLuogoNascitaVuoto() {
    when(requestMock.getParameter("facolta")).thenReturn("Informatica");
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Luogo di Nascita' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.17: Campo Luogo di Nascita Troppo Lungo
  @Test
  void testCampoLuogoNascitaTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita"))
        .thenReturn("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Luogo di Nascita' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.18: Campo Luogo di Nascita formato Errato
  @Test
  void testCampoLuogoNascitaFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Sal2019");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Luogo di Nascita' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.19: Campo Cittadinanza vuoto
  @Test
  void testCampoCittadinanzaVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Cittadinanza' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.20: Campo Cittadinanza Troppo Lungo
  @Test
  void testCampoCittadinanzaTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza"))
        .thenReturn("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Cittadinanza' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.21: Campo Cittadinanza FormatoErrato
  @Test
  void testCampoCittadinanzaFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Ital99");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Cittadinanza' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.22: Campo Residenza vuoto
  @Test
  void testCampoResidenzaVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Residenza' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.23: Campo Residenza troppo lungo
  @Test
  void testCampoResidenzaTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza"))
        .thenReturn("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Residenza' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.24: Campo Residenza non rispetta il formato
  @Test
  void testCampoResidenzaFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("12039 @F");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Residenza' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.25: Campo CodiceFiscale vuoto
  @Test
  void testCampoCodiceFiscaleVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Codice Fiscale' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.26: Campo CodiceFiscale troppo lungo
  @Test
  void testCampoCodiceFiscaleTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("111111111DDDDDDDO");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Codice Fiscale' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.27: Campo CodiceFiscale formato errato
  @Test
  void testCampoCodiceFiscaleFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("111111111@111111");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Codice Fiscale' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.28: Campo Telefono vuoto
  @Test
  void testCampoTelefonoVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Telefono' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.29: Campo Telefono troppo lungo
  @Test
  void testCampoTelefonoTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("4444444444444444");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Telefono' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.30: Campo Telefono formato errato
  @Test
  void testCampoTelefonoFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("333ABCDEFG");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Telefono' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.31: Campo email vuoto
  @Test
  void testCampoEmailVuoto() {
    Student s = new Student("", "Alessandro", "Del Piero", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'E-mail' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.32: Campo email Troppo Lungo
  @Test
  void testCampoEmailTroppoLungo() {
    Student s = new Student("a.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa10@studenti.unisa.it",
        "Alessandro", "Del Piero", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'E-mail' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.33: Campo email formato errato
  @Test
  void testCampoEmailFormatoErrato() {
    Student s = new Student("a.delpiero10@2019", "Alessandro", "Del Piero", 'M', "password", 0);
    when(sessionMock.getAttribute("user")).thenReturn(s);
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'E-mail' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.34: Campo cfu vuoto
  @Test
  void testCampoCfuVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'CFU' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.35: Campo cfuPrevisti troppo Grande
  @Test
  void testCampoCfuTroppoGrande() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("190");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'CFU' supera la grandezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.36: Campo cfuPrevisti non rispetta il formato
  @Test
  void testCampoCfuFormatoErrato() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("ABC");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'CFU' non rispetta il formato", e.getMessage());
  }

  // Test case TC_GR_6.37: Campo competenze vuoto
  @Test
  void testCampoCompetenzeVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Competenze' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.38: Campo competenze troppo lungo
  @Test
  void testCampoCompetenzeTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn(
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Competenze' supera la lunghezza consentita", e.getMessage());
  }

  // Test case TC_GR_6.39: Campo competenze da acquisire vuoto
  @Test
  void testCampoCompetenzeAcquisireVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Competenze da Acquisire' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.40: Campo competenze da acquisire troppo lungo
  @Test
  void testCampoCompetenzeAcquisireTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn(
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Competenze da Acquisire' supera la lunghezza consentita",
        e.getMessage());
  }

  // Test case TC_GR_6.41: Campo svolgimento tirocinio vuoto
  @Test
  void testCampoSvolgimentoTirocinioVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn("Front End");
    when(requestMock.getParameter("modalitaTirocinio")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Modalit&agrave svolgimento tirocinio' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.42: Campo svolgimento tirocinio troppo lungo
  @Test
  void testCampoSvolgimentoTirocinioTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn("Front End");
    when(requestMock.getParameter("modalitaTirocinio")).thenReturn(
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Modalit&agrave svolgimento tirocinio' supera la lunghezza consentita",
        e.getMessage());
  }

  // Test case TC_GR_6.43: Campo Attivita previste vuoto
  @Test
  void testCampoAttivitaPrevisteVuoto() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn("Front End");
    when(requestMock.getParameter("modalitaTirocinio")).thenReturn("Tipologia A");
    when(requestMock.getParameter("attivitaPreviste")).thenReturn("");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Attivit&agrave Previste' &egrave vuoto", e.getMessage());
  }

  // Test case TC_GR_6.44: Campo Attivita previste troppo lungo
  @Test
  void testCampoAttivitaPrevisteTroppoLungo() {
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn("1999-10-25");
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn("Front End");
    when(requestMock.getParameter("modalitaTirocinio")).thenReturn("Tipologia A");
    when(requestMock.getParameter("attivitaPreviste")).thenReturn(
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        + "aaaaaaaaaaaa");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
    assertEquals("Il campo 'Attivit&agrave Previste' supera la lunghezza consentita",
        e.getMessage());
  }

  // Test case TC_GR_6.45: Richiesta inviata con successo
  @Test
  void testInvioRichiestaInizioTirocinioCorretto() throws ServletException, IOException {
	  /*
	   * Inserisco nel database un user di tipo studente (0) 
	   */
    try {
      Statement stmtSelect = conn.createStatement();
      String sql1 = ("INSERT INTO User VALUES('" + s.getEmail() + "','" + s.getName() + "','"
          + s.getSurname() + "','" + s.getSex() + "','" + s.getPassword() + "','0');");
      stmtSelect.executeUpdate(sql1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    when(requestMock.getParameter("matricolaTirocinante")).thenReturn("0512103333");
    when(requestMock.getParameter("dataDiNascita")).thenReturn((new Date(0)).toString());
    when(requestMock.getParameter("luogoDiNascita")).thenReturn("Salerno");
    when(requestMock.getParameter("cittadinanza")).thenReturn("Italiana");
    when(requestMock.getParameter("residenza")).thenReturn("Via Armando Diaz 1");
    when(requestMock.getParameter("codiceFiscale")).thenReturn("DLPLSD99N25I438I");
    when(requestMock.getParameter("telefono")).thenReturn("3331054900");
    when(requestMock.getParameter("cfu")).thenReturn("3");
    when(requestMock.getParameter("competenzePossedute")).thenReturn("Back End");
    when(requestMock.getParameter("competenzeDaAcquisire")).thenReturn("Front End");
    when(requestMock.getParameter("modalitaTirocinio")).thenReturn("Tipologia A");
    when(requestMock.getParameter("attivitaPreviste")).thenReturn("Programmazione Android");

    test.doPost(requestMock, responseMock);

    verify(responseMock)
        .sendRedirect(requestMock.getContextPath() + "/_areaStudent/HomeStudente.jsp?cod=1");
  }
}
