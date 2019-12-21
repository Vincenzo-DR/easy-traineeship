package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import controller.DbConnection;
import model.DAO.EnteConvenzionatoDAO;
import model.DAO.TirocinanteDAO;
import model.EnteConvenzionato;
import model.Tirocinante;

class TirocinanteDAOTest {
	
	Connection conn  = new DbConnection().getInstance().getConn();
	TirocinanteDAO tirocinanteDao = new TirocinanteDAO();
	
	//Test del metodo allTirocinante di TirocinanteDAO 
	@Test
	void testAllTirocinante() throws SQLException 
	{
		boolean inserito = false;
		int i;
		
		
		try 
		{
			Date data = new SimpleDateFormat("yyyyMMdd").parse("19700101");
			String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(data);
	    	Statement stmtSelect = conn.createStatement();
	    	String sql1 = ("INSERT INTO User VALUES('azienda@email.it','Cap Gemini','NA','N','password','3');");
	    	stmtSelect.executeUpdate(sql1);
	    	String sql2 = ("INSERT INTO Tirocinante VALUES('0512103313','" + modifiedDate + "','Salerno','Italiana','Salerno','QVGXRV78A52H443B','3491494900','azienda@email.it');");
	    	stmtSelect.executeUpdate(sql2);
	    	conn.commit();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		ArrayList<Tirocinante> listaTirocinanti = tirocinanteDao.allTirocinante();
		for( i = 0; i < listaTirocinanti.size(); i++) {
			if (listaTirocinanti.get(i).getEmail().equals("azienda@email.it")) {
				inserito = true;
			}
		}
		assertEquals(inserito,true);
		
		try 
		{
		    Statement stmtSelect = conn.createStatement();
		    String sql1 = ("DELETE FROM User WHERE EMAIL='azienda@email.it';");
		    stmtSelect.executeUpdate(sql1);
		    conn.commit();
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	/*
	//Test del metodo inserisciTirocinante di TirocinanteDAO
	@Test
	void testInserisciTirocinante() throws SQLException
	{
		
		EnteConvenzionato ente = new EnteConvenzionato("azienda@email.it","Cap Gemini","NA",' ',"password",3,"25/12/1980","99999999999","Salerno","Giacomo","Carmine","3401414140",8,"Pino","TE","Molto interessante");
		assertEquals(enteConDao.inserisciEnte(ente),true);
		
		try {
		    Statement stmtSelect = conn.createStatement();
		    String sql1 = ("DELETE FROM User WHERE EMAIL='azienda@email.it';");
		    stmtSelect.executeUpdate(sql1);
		    conn.commit();
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	/*
	//Test del metodo ModificaEnte di EnteConvenzionatoDAO quando l'elemento da modificare non � presente nel database
	@Test
	void testModificaEnteCampoNonPresente() throws SQLException
	{
		
		EnteConvenzionato ente = new EnteConvenzionato("azienda@email.it","Cap Gemini","NA",' ',"password",3,"25/12/1980","99999999999","Salerno","Giacomo","Carmine","3401414140",8,"Pino","TE","Molto interessante");
		
		assertEquals(enteConDao.modificaEnte(ente),false);
		
		try 
		{
		    Statement stmtSelect = conn.createStatement();
		    String sql1 = ("DELETE FROM User WHERE EMAIL='azienda@email.it';");
		    stmtSelect.executeUpdate(sql1);
		    conn.commit();
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}

	//Test del metodo ModificaEnte di EnteConvenzionatoDAO quando l'elemento da modificare � presente nel database
	@Test
	void testModificaEnteCampoPresente() throws SQLException
	{
		
		try 
		{
	    	Statement stmtSelect = conn.createStatement();
	    	String sql1 = ("INSERT INTO User VALUES('azienda@email.it','Cap Gemini','NA','N','password','3');");
	    	stmtSelect.executeUpdate(sql1);
	    	String sql2 = ("INSERT INTO EnteConvenzionato VALUES('99999999999','Salerno','Giacomo','3490000141','100','Carmine','Giuseppe','12/10/1975','Molto interessante','azienda@email.it');");
	    	stmtSelect.executeUpdate(sql2);
	    	conn.commit();
	    }
	    catch (Exception e) 
		{
	    	e.printStackTrace();
	    }
		
		EnteConvenzionato ente = new EnteConvenzionato("azienda@email.it","Cap Gemini","NA",' ',"password",3,"25/12/1980","99999999999","Salerno","Giacomo","Carmine","3401414140",8,"Pino","TE","Molto interessante");
		assertEquals(enteConDao.modificaEnte(ente),true);
		
		try 
		{
		    Statement stmtSelect = conn.createStatement();
		    String sql1 = ("DELETE FROM User WHERE EMAIL='azienda@email.it';");
		    stmtSelect.executeUpdate(sql1);
		    conn.commit();
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}
	
	// Test del metodo eliminaEnte di EnteConvenzionatoDAO
		@Test
		void testEliminaEnte() throws SQLException {

			try {
				Statement stmtSelect = conn.createStatement();
				String sql1 = ("INSERT INTO User VALUES('azienda@email.it','Cap Gemini','NA','N','password','3');");
				stmtSelect.executeUpdate(sql1);
				String sql2 = ("INSERT INTO EnteConvenzionato VALUES('99999999999','Salerno','Giacomo','3490000141','100','Carmine','Giuseppe','12/10/1975','Molto interessante','azienda@email.it');");
				stmtSelect.executeUpdate(sql2);
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			EnteConvenzionato ente = new EnteConvenzionato("azienda@email.it", "Cap Gemini", "NA", ' ', "password", 3,
					"25/12/1980", "99999999999", "Salerno", "Giacomo", "Carmine", "3401414140", 8, "Pino", "TE",
					"Molto interessante");
			assertEquals(enteConDao.eliminaEnte(ente), true);

			try {
				Statement stmtSelect = conn.createStatement();
				String sql1 = ("DELETE FROM User WHERE EMAIL='azienda@email.it';");
				stmtSelect.executeUpdate(sql1);
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	//Test del metodo updatePassword di EnteConvenzionatoDAO 
	@Test
	void testUpdatePassword() throws SQLException
	{
		
		try 
		{
	    	Statement stmtSelect = conn.createStatement();
	    	String sql1 = ("INSERT INTO User VALUES('azienda@email.it','Cap Gemini','NA','N','password','3');");
	    	stmtSelect.executeUpdate(sql1);
	    	String sql2 = ("INSERT INTO EnteConvenzionato VALUES('99999999999','Salerno','Giacomo','3490000141','100','Carmine','Giuseppe','12/10/1975','Molto interessante','azienda@email.it');");
	    	stmtSelect.executeUpdate(sql2);
	    	conn.commit();
	    }
	    catch (Exception e) 
		{
	    	e.printStackTrace();
	    }
		
		assertEquals(enteConDao.updatePassword("azienda@email.it", "123456"),true);
		
		try 
		{
		    Statement stmtSelect = conn.createStatement();
		    String sql1 = ("DELETE FROM User WHERE EMAIL='azienda@email.it';");
		    stmtSelect.executeUpdate(sql1);
		    conn.commit();
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}
	
	*/
}