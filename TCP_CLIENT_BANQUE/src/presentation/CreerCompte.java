package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;

import entities.*;

public class CreerCompte extends JFrame implements ActionListener {

	private ArrayList<Client> listeClients = new ArrayList<Client>();
	private JButton bAjouter, bQuitter;
	private JComboBox<String> chListeClients;
	private JLabel lNumero, lLibelle, lClient;
	private JPanel panel1, panel2;
	private JTextField chNumero, chLibelle;

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
		chNumero = new JTextField();
		chLibelle = new JTextField();
		chListeClients = new JComboBox<String>();
		bAjouter = new JButton("Enregistrer");
		bQuitter = new JButton("Quitter");
		String operation[] = {"Débiteur", "Créditeur"};
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
			String message = new String();
			try {
				oos.writeObject("creerCompte");
				oos.flush();
				String numero = chNumero.getText();
				String libelle = chLibelle.getText();
				compte = new Compte();
				compte.setNumero(numero);
				compte.setLibelle(libelle);
				compte.setNumeroClient(recupererNumeroClient(nomClient));
				oos.writeObject(compte);
				oos.flush();
				message = ois.readObject().toString();
				JOptionPane.showMessageDialog(null, message.equals("succes") ? "Compte créé avec succès!" : "Echec de la création du compte!");
				chNumero.setText("");
				chLibelle.setText("");
				chListeClients.setSelectedIndex(0);
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
