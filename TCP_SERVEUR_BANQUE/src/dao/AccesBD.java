package dao;

import java.sql.*;
import java.util.ArrayList;

import entities.*;

public class AccesBD {
	
	private Connection con = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;
	private final String host = "jdbc:mysql://localhost:3306/banque_tcp", username = "phpmyadmin", password = "phpmyadmin", sensDefaut="CR";
	private final double soldeDefaut = 0;
	
	public AccesBD() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(host, username, password);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public String creerAgence(Agence agence) {
		String message = new String("succes");
		try {
			st = con.prepareStatement("insert into agence(nom, adresse) values(?, ?)");
			st.setString(1, agence.getNom());
			st.setString(2, agence.getAdresse());
			st.executeUpdate();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			message = new String("echec");
		}
		return message;
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

	public String creerClient(Client client) {
		String message = new String("succes");
		try {
			st= con.prepareStatement("insert into client(nom, prenom, numero_agence) values(?, ?, ?)");
			st.setString(1, client.getNom());
			st.setString(2, client.getPrenom());
			st.setInt(3, client.getNumeroAgence());
			st.executeUpdate();          
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			message = new String("echec");
		}
		return message;
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

	public String creerCompte(Compte compte) {
		String message = new String("succes");
		try {
			st = con.prepareStatement("insert into compte(numero, libelle, sens, solde, numero_client) values(?, ?, ?, ?, ?)");
			st.setString(1, compte.getNumero());
			st.setString(2, compte.getLibelle());
			st.setString(3, this.sensDefaut);
			st.setDouble(4, this.soldeDefaut);
			st.setInt(5, compte.getNumeroClient());
			st.executeUpdate();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			message = new String("echec");
		}
		return message;
	}

	public Compte recupererCompteParNumero(String string) {
		Compte compte = new Compte();
		try {
			st = con.prepareStatement("select * from compte where numero='" + string + "'");
			rs = st.executeQuery();
			if(rs.next()) {
				compte.setNumero(rs.getString("numero"));
				compte.setLibelle(rs.getString("libelle"));
				compte.setSens(rs.getString("sens"));
				compte.setSolde(rs.getDouble("solde"));   
				compte.setNumeroClient(rs.getInt("numero_client"));
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return compte;
	}

	public Compte recupererCompteParLibelle(String libelle) {
		Compte compte = new Compte();
		try {
			st = con.prepareStatement("select * from compte where libelle='" + libelle + "'");
			rs = st.executeQuery();
			if(rs.next()) {
				compte.setNumero(rs.getString("numero"));
				compte.setLibelle(rs.getString("libelle"));
				compte.setSens(rs.getString("sens"));
				compte.setSolde(rs.getDouble("solde"));   
				compte.setNumeroClient(rs.getInt("numero_client"));
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return compte;
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

	public ArrayList<Operation> afficherReleveCompte(String libelleCompte) {
		Compte compte = new Compte();
		compte = recupererCompteParLibelle(libelleCompte);
		ArrayList <Operation> listeOperations = new ArrayList <Operation>();
		try {
			st = con.prepareStatement("select * from operation where numero_compte='" + compte.getNumero() + "'");
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

	public String passerOperation(Operation operation) {
		String message = new String("succes");
		Compte compte = new Compte();
		compte = recupererCompteParNumero(operation.getNumeroCompte());
		if(compte.getNumero().equals("") || operation.getMontant() < 0) {
			message = new String("echec");
		}
		else {
			try {
				st = con.prepareStatement("insert into operation(libelle, date_operation, sens, montant, numero_compte) values(?, ?, ?, ?, ?)");
				st.setString(1, operation.getLibelle());
				st.setString(2, operation.getDateOperation());
				st.setString(3, operation.getSens());
				st.setDouble(4, operation.getMontant());
				st.setString(5, operation.getNumeroCompte());
				st.executeUpdate();
				// Mise à jour du compte
				double nouveauSolde = (operation.getSens().equals("CR")) ? (compte.getSolde() + operation.getMontant()) : (compte.getSolde() - operation.getMontant());
				String nouveauSens = (nouveauSolde > 0) ? new String("CR") : new String("DB");
				nouveauSolde = Math.abs(nouveauSolde);
				st = con.prepareStatement("update compte set solde=?, sens=? where numero=?");
				st.setDouble(1,  nouveauSolde);
				st.setString(2, nouveauSens);
				st.setString(3, operation.getNumeroCompte());
				st.executeUpdate();
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
				message = new String("echec");
			}
		}
		return message;
	}

}
