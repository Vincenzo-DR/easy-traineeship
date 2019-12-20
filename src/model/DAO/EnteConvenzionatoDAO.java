package model.DAO;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DbConnection;
import model.EnteConvenzionato;

public class EnteConvenzionatoDAO {
	
	/*
	 * Metodo che interroga il DB e restituisce tutti gli 'EntiConvenzionati' 
	 * persenti all' interono
	 * 
	 * @return listaEnti
	 * */
	public synchronized ArrayList allEnte()
	{
		
		Connection con = null; //variabile per la connesione del DB
		PreparedStatement ps = null;// Creazione oggetto Statement
		//ArrayLista di tipo EnteConvenzionato
		ArrayList<EnteConvenzionato> listaEnti = new ArrayList<EnteConvenzionato>();
		try 
		{
			//Connessione con il DB
			con= new DbConnection().getInstance().getConn();
			//Query Sql per prelevare gli 'EntiConvenzionati'
			ps= con.prepareStatement("SELECT * "
									+ "FROM ENTECONVENZIONATO, USER "
									+ "WHERE ENTECONVENZIONATO.EMAIL=USER.EMAIL");
			ResultSet res = ps.executeQuery();
			//Ciclo che inserisce all' interno della lista gli 'EntiConvenzionati'
			//restituiti dalla query
			while(res.next())
			{
				
				EnteConvenzionato purchase= new EnteConvenzionato();
				purchase.setEmail(res.getString("EMAIL"));
				purchase.setName(res.getString("NAME"));
				purchase.setPartitaIva(res.getString("PARTITAIVA"));
				purchase.setSede(res.getString("SEDE"));
				purchase.setRappresentante(res.getString("RAPPRESENTANTE"));
				purchase.setTelefono(res.getString("TELEFONO"));
				purchase.setDipendenti(res.getShort("DIPENDENTI"));
				purchase.setDotRiferimento(res.getString("DOTRIFERIMENTO"));
				purchase.setDescrizioneAttivita(res.getString("DESCRIZIONEATTIVITA"));
				
				listaEnti.add(purchase);//listaEnti
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				ps.close();// Chiusura oggetto Statement 
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return listaEnti;	
	}
	
	/*
	 * Metodo che interagisce con il DB e inserisce in esso l' 'EnteConvenzionato' 
	 * passato come parametro
	 * 
	 * @param enteConvenzionato
	 * @return boolean
	 * */
	public synchronized boolean inserisciEnte(EnteConvenzionato enteConvenzionato)
	{
		
		Connection con = null; //variabile per la connessione al DB
		PreparedStatement psEnteConvenzionato = null;// Creazione oggetto Statement per l'EnteConvenzioanto
		PreparedStatement psUser = null;// Creazione oggetto Statement per l' User
		try 
		{
			//Connessione con il DB
			con= new DbConnection().getInstance().getConn();
			
			//Insert per l'inserimento in 'User' dei dati parziali di 'EnteConvenzionato'
			psUser= con.prepareStatement("INSERT INTO USER(EMAIL, NAME, SURNAME, SEX, PASSWORD, USER_TYPE) "
					+ "VALUES (?, ?, ?, ?, ?, 3)");
			psUser.setString(1, enteConvenzionato.getEmail());
			psUser.setString(2, enteConvenzionato.getName());
			psUser.setString(3, enteConvenzionato.getSurname());
			psUser.setString(4, " ");
			psUser.setString(5, enteConvenzionato.getPassword());
			
			//Insert per l'inserimento in 'EnteConvenzionato' dei dati parziali di 'EnteConvenzionato'
			psEnteConvenzionato= con.prepareStatement("INSERT INTO ENTECONVENZIONATO(PARTITAIVA, SEDE, RAPPRESENTANTE,"
														+ " TELEFONO, DIPENDENTI, DOTRIFERIMENTO,"
														+ "DESCRIZIONEATTIVITA, EMAIL) "
														+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			psEnteConvenzionato.setString(1, enteConvenzionato.getPartitaIva());
			psEnteConvenzionato.setString(2, enteConvenzionato.getSede());
			psEnteConvenzionato.setString(3, enteConvenzionato.getRappresentante());
			psEnteConvenzionato.setString(4, enteConvenzionato.getTelefono());	
			psEnteConvenzionato.setInt(5, enteConvenzionato.getDipendenti());
			psEnteConvenzionato.setString(6, enteConvenzionato.getDotRiferimento());
			psEnteConvenzionato.setString(7, enteConvenzionato.getDescrizioneAttivita());
			psEnteConvenzionato.setString(8, enteConvenzionato.getEmail());	
			
			//Se l'inserimento va a buon fine restituisce true
			if((psUser.executeUpdate()==1)&&(psEnteConvenzionato.executeUpdate()==1))
			{
				con.commit();
				return true;
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				psEnteConvenzionato.close();// Chiusura oggetto Statement dell' 'EnteConvenzionato'
				psUser.close();// Chiusura oggetto Statement dell' 'User'
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		//Ritorna false se l'insert non � andato a buon fine 
		return false;	
	}
	
	/*
	 * Metodo che interagisce con il DB e modifica l' 'EnteConvenzionato' 
	 * di riferimento
	 * 
	 * @param enteConvenzionato
	 * @return boolean
	 * */
	public synchronized boolean modificaEnte(EnteConvenzionato enteConvenzionato)
	{
		
		Connection con = null;//variabile per la connessione al DB
		PreparedStatement psEnteConvenzionato = null;// Creazione oggetto Statement per l'EnteConvenzioanto
		PreparedStatement psUser = null;// Creazione oggetto Statement per l' User
		try 
		{
			//Connessione con il DB
			con= new DbConnection().getInstance().getConn();
			//inserimento di un EnteConvenzionato
			boolean update = inserisciEnte(enteConvenzionato);
			//se l'operazione di inserimento non � andata a buon fine
			//faccio la modifica perch� l' 'EnteConvenzionato' esiste
			if(update==false)	
			{
				//Update per la modifica in 'User' dei dati parziali di 'EnteConvenzionato'
				psUser= con.prepareStatement("UPDATE USER "
						+ ("SET EMAIL=?, NAME=?, SURNAME=?, SEX=?, PASSWORD=? ")
						+ ("WHERE email = ?;"));
				psUser.setString(1, enteConvenzionato.getEmail());
				psUser.setString(2, enteConvenzionato.getName());
				psUser.setString(3, enteConvenzionato.getSurname());
				psUser.setString(4, " ");
				psUser.setString(5, enteConvenzionato.getPassword());
				psUser.setString(6, enteConvenzionato.getEmail());
				//Update per la Modifica in 'EnteConvenzionato' dei dati parziali di 'EnteConvenzionato'
				psEnteConvenzionato= con.prepareStatement("UPDATE ENTECONVENZIONATO "
														+ "SET SEDE = ?, RAPPRESENTANTE = ?, TELEFONO = ?,"
															+ "DIPENDENTI = ?, DOTRIFERIMENTO = ?, "
															+ "DESCRIZIONEATTIVITA = ?, EMAIL = ? "
															+ "WHERE PARTITAIVA=?;");
				
				psEnteConvenzionato.setString(1, enteConvenzionato.getSede());
				psEnteConvenzionato.setString(2, enteConvenzionato.getRappresentante());
				psEnteConvenzionato.setString(3, enteConvenzionato.getTelefono());	
				psEnteConvenzionato.setInt(4, enteConvenzionato.getDipendenti());
				psEnteConvenzionato.setString(5, enteConvenzionato.getDotRiferimento());
				psEnteConvenzionato.setString(6, enteConvenzionato.getDescrizioneAttivita());
				psEnteConvenzionato.setString(7, enteConvenzionato.getEmail());	
				psEnteConvenzionato.setString(8, enteConvenzionato.getPartitaIva());
				//Se la modifica va a buon fine restituisce true
				if(((psUser.executeUpdate()==1)&&psEnteConvenzionato.executeUpdate()==1))
				{
					con.commit();
					return true;
				}
				
				try 
				{
					psEnteConvenzionato.close();// Chiusura oggetto Statement dell' 'EnteConvenzionato'
					psUser.close();// Chiusura oggetto Statement dell' 'User'
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				return false;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		//Ritorna false se l'insert non � andato a buon fine 
		return false;	
	}
	
	/*
	 * Metodo che interagisce con il DB e modifica la 'password' dell' 'EnteConvenzionato' 
	 * di riferimento
	 * 
	 * @param email
	 * @param password
	 * @return boolean
	 * */
	public synchronized boolean updatePassword(String email, String password)
	{
		
		Connection con = null;//variabile per la connessione al DB
		PreparedStatement psUser = null;// Creazione oggetto Statement per l' User
		try 
		{
			//Connessione con il DB
			con= new DbConnection().getInstance().getConn();
			//Update per la modifica della 'Password' in 'User' dell' 'EnteConvenzionato'
			psUser= con.prepareStatement("UPDATE USER "
										+ "SET PASSWORD ="+password 
										+ " WHERE EMAIL = '"+email+"';");
			//Se la modifica va a buon fine restituisce true
			if(psUser.executeUpdate()==1)
			{
				con.commit();
				return true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				psUser.close();// Chiusura oggetto Statement dell' 'User'
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		//Ritorna false se la modifica non � andata a buon fine 
		return false;	
	}
}
