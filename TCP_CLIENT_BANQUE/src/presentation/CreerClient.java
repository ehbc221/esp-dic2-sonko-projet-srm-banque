package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;

import entities.*;

public class CreerClient extends JFrame implements ActionListener {

	private ArrayList<Agence> listeAgences = new ArrayList<Agence>();
	private JButton bAjouter, bQuitter;
	private JComboBox<String> chListeAgences;
	private JLabel lNom, lPrenom, lAgence;
	private JPanel panel1, panel2;
	private JTextField chNom, chPrenom;
	private Socket socket;

	DataOutputStream out;
	DataInputStream in;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	private String nomAgence;

	public CreerClient() {

		lNom = new JLabel("Nom Client:");
		lPrenom = new JLabel("Prenom Client:");
		lAgence = new JLabel("Agence Client:");
		chNom = new JTextField(20);
		chPrenom = new JTextField(20);
		chListeAgences = new JComboBox<String>();
		bAjouter = new JButton("Enregistrer");
		bQuitter = new JButton("Quitter");

		chListeAgences.addActionListener(this);
		bAjouter.addActionListener(this);
		bQuitter.addActionListener(this);

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel1.setLayout(new GridLayout(4,2));
		panel1.add(lNom);
		panel1.add(chNom);
		panel1.add(lPrenom);
		panel1.add(chPrenom);
		panel1.add(lAgence);
		panel1.add(chListeAgences);
		panel2.add(bAjouter);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Créer Client");
		setSize(500, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try {
			socket= new Socket("localhost", 8000);
			oos=new ObjectOutputStream(socket.getOutputStream());
			ois=new ObjectInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			remplirListeAgences();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bAjouter) {
			Client client;
			String message = new String();
			try {
				oos.writeObject("creerClient");
				oos.flush();
				String nom = chNom.getText();
				String prenom = chPrenom.getText();
				client = new Client();
				client.setNom(nom);
				client.setPrenom(prenom);
				client.setNumeroAgence(recupererNumeroAgence(nomAgence));
				oos.writeObject(client);
				oos.flush();
				message = ois.readObject().toString();
				JOptionPane.showMessageDialog(null, message.equals("succes") ? "Client créé avec succès!" : "Echec de la création du client!");
				chNom.setText("");
				chPrenom.setText("");
				chListeAgences.setSelectedIndex(0);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		else if(e.getSource() == chListeAgences) {
			JComboBox cb = (JComboBox)e.getSource();
			nomAgence = (String)cb.getSelectedItem();
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

	public void remplirListeAgences()  {
		try {
			oos.writeObject("listerAgences");
			oos.flush();
			listeAgences = (ArrayList<Agence>) ois.readObject();
			chListeAgences.addItem("Choisir une banque");
			for(Agence agence : listeAgences) {
				chListeAgences.addItem(agence.getNom());
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public int recupererNumeroAgence(String nomAgence) {
		int num = 0;
		for(Agence agence : listeAgences) {
			if (agence.getNom().equals(nomAgence)) {
				num = agence.getNumero();
			}
		}
		return num;
	}
}
