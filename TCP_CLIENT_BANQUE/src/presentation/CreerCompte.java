package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import entities.*;

public class CreerCompte extends JFrame implements ActionListener {

	private ArrayList<Client> listeClients = new ArrayList<Client>();
	private JButton bAjouter, bQuitter;
	private JComboBox<String> listeSens, chListeClients;
	private JLabel lNumero, lLibelle, lClient, lSolde, lSens;
	private JPanel panel1, panel2;
	private JTextField chNumero, chLibelle, chSolde;

	private Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	DataOutputStream out;
	DataInputStream in;

	private String nomClient;

	public CreerCompte() {

		lNumero = new JLabel("Numero Compte:");
		lLibelle = new JLabel("Libelle Compte:");
		lClient = new JLabel("Client Compte:");
		lSolde = new JLabel("Solde Compte:");
		lSens = new JLabel("Sens Compte:");
		chNumero = new JTextField();
		chLibelle = new JTextField();
		chListeClients = new JComboBox<String>();
		chSolde = new JTextField();
		listeSens = new JComboBox<String>();
		bAjouter = new JButton("Enregistrer");
		bQuitter = new JButton("Quitter");
		String operation[] = {"Débiteur", "Créditeur"};
		listeSens = new JComboBox<String>(operation);
		panel1 = new JPanel();
		panel2 = new JPanel();

		chListeClients.addActionListener(this);
		bAjouter.addActionListener(this);
		bQuitter.addActionListener(this);

		panel1.setLayout(new GridLayout(5, 2));
		panel1.add(lNumero);
		panel1.add(chNumero);
		panel1.add(lLibelle);
		panel1.add(chLibelle);
		panel1.add(lClient);
		panel1.add(chListeClients);
		panel1.add(lSens);
		panel1.add(listeSens);
		panel1.add(lSolde);
		panel1.add(chSolde);
		panel2.add(bAjouter);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Créer Compte");
		setSize(800, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try {
			socket = new Socket("localhost", 8000);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			remplirListeClients();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bAjouter) {
			Compte compte;
			try {
				oos.writeObject("creerCompte");
				oos.flush();
				String numero = chNumero.getText();
				String libelle = chLibelle.getText();
				String sens = listeSens.getSelectedItem().toString();
				int solde = Integer.parseInt(chSolde.getText());
				compte = new Compte();
				compte.setNumero(numero);
				compte.setLibelle(libelle);
				compte.setSolde(solde);
				compte.setSens(sens.equals("Débiter")? "DB" : "CR");
				compte.setNumeroClient(recupererNumeroClient(nomClient));
				oos.writeObject(compte);
				oos.flush();
				chNumero.setText("");
				chLibelle.setText("");
				chSolde.setText("");
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		else if(e.getSource() == chListeClients) {
			JComboBox cb = (JComboBox)e.getSource();
			nomClient = (String)cb.getSelectedItem();
		}
		else if (e.getSource() == bQuitter) {
			try {
				oos.writeObject("fin");
				oos.flush();
				socket.close();
				dispose();
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}	
	}

	public void remplirListeClients()  {
		try {
			oos.writeObject("listerClients");
			oos.flush();
			listeClients = (ArrayList<Client>) ois.readObject();
			chListeClients.addItem("Choisir un client");
			for(Client client : listeClients) {
				chListeClients.addItem(client.getPrenom() + " " + client.getNom());
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public int recupererNumeroClient(String prenomNomClient) {
		int num = 0;
		for(Client client : listeClients) {
			String nomComplet = client.getPrenom() + " " + client.getNom();
			if (nomComplet.equals(prenomNomClient)) {
				num = client.getNumero();
			}
		}
		return num;
	}

	public static void main(String args[]) {
		new CreerCompte();
	}

}
