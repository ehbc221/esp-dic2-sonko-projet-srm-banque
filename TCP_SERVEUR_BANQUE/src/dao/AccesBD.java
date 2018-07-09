package dao;

import java.sql.*;
import java.util.ArrayList;

import entities.*;

public class AccesBD {
	
	private Connection con = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;
	
	public AccesBD() {
		final String host = "jdbc:mysql://localhost:3306/banque_tcp", username = "phpmyadmin", password = "phpmyadmin";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, username, password);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void creerAgence(Agence agence) {
		try {
			st = con.prepareStatement("insert into agence(nom, adresse) values(?, ?)");
			st.setString(1, agence.getNom());
			st.setString(2, agence.getAdresse());
			st.executeUpdate();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	 public ArrayList<Agence> listerAgences() {
		ArrayList <Agence> listeAgences = new ArrayList <Agence>();
		try {
			st = con.prepareStatement("select * from agence");
			rs = st.executeQuery();
			while(rs.next()) {
				Agence agence = new Agence();
				agence.setNumero(rs.getInt("numero"));
				agence.setNom(rs.getString("nom"));
				agence.setAdresse(rs.getString("adresse"));
				listeAgences.add(agence);
			}			
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return listeAgences;
	}

	public void creerClient(Client client) {
		try {
			st= con.prepareStatement("insert into client(nom, prenom, numero_agence) values(?, ?, ?)");
			st.setString(1, client.getNom());
			st.setString(2, client.getPrenom());
			st.setInt(3, client.getNumeroAgence());
			st.executeUpdate();          
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public ArrayList<Client> listerClients() {
		ArrayList <Client> listeClients = new ArrayList <Client>();
		try {
			st = con.prepareStatement("select * from client");
			rs = st.executeQuery();
			while(rs.next()) {
				Client client = new Client();
				client.setNumero(rs.getInt("numero"));
				client.setNom(rs.getString("nom"));
				client.setPrenom(rs.getString("prenom"));
				listeClients.add(client);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return listeClients;
	}

	public void creerCompte(Compte compte) {
		try {
			st = con.prepareStatement("insert into compte(numero, libelle, sens, solde, numero_client) values(?, ?, ?, ?, ?)");
			st.setString(1, compte.getNumero());
			st.setString(2, compte.getLibelle());
			st.setString(3, compte.getSens());
			st.setDouble(4, compte.getSolde());
			st.setInt(5, compte.getNumeroClient());
			st.executeUpdate();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public ArrayList<Compte> listerComptes() {
		ArrayList <Compte> listeComptes = new ArrayList <Compte>();
		try {
			st = con.prepareStatement("select * from compte");
			rs = st.executeQuery();
			while(rs.next()) {
				Compte compte = new Compte();
				compte.setNumero(rs.getString("numero"));
				compte.setLibelle(rs.getString("libelle"));   
				compte.setSens(rs.getString("sens"));
				compte.setSolde(rs.getDouble("solde"));   
				compte.setNumeroClient(rs.getInt("numero_client"));
				listeComptes.add(compte);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return listeComptes;
	}

	public ArrayList<Compte> listerComptesClient(String numeroClient) {
		ArrayList <Compte> listeComptesClient = new ArrayList <Compte>();
		try {
			st = con.prepareStatement("select * from compte where numero_client=" + "'" + numeroClient + "'");
			rs = st.executeQuery();
			while(rs.next()) {
				Compte compte = new Compte();
				compte.setNumero(rs.getString("numero"));
				compte.setLibelle(rs.getString("libelle"));
				compte.setSens(rs.getString("sens"));
				compte.setSolde(rs.getDouble("solde"));
				compte.setNumeroClient(rs.getInt("numero_client"));
				listeComptesClient.add(compte);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return listeComptesClient;
	}

	public ArrayList<Operation> afficherReleveCompte(String numeroCompte) {
		ArrayList <Operation> listeOperations = new ArrayList <Operation>();
		try {
			st = con.prepareStatement("select * from operation where numero_compte=" + "'" + numeroCompte + "'");
			rs = st.executeQuery();
			while(rs.next()) {
				Operation operation = new Operation();
				operation.setNumero(rs.getString("numero"));
				operation.setLibelle(rs.getString("libelle"));
				operation.setDateOperation(rs.getString("date_operation"));
				operation.setMontant(rs.getDouble("montant"));
				operation.setSens(rs.getString("sens"));
				operation.setNumeroCompte(rs.getString("numero_compte"));
				listeOperations.add(operation);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return listeOperations;
	}

	public void passerOperation(Operation operation)	{
		try {
			st = con.prepareStatement("insert into operation(libelle, date_operation, sens, montant, numero_compte) values(?, ?, ?, ?, ?)");
			st.setString(1, operation.getLibelle());
			st.setString(2, operation.getDateOperation());
			st.setString(3, operation.getSens());
			st.setDouble(4, operation.getMontant());
			st.setString(5, operation.getNumeroCompte());
			st.executeUpdate();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
