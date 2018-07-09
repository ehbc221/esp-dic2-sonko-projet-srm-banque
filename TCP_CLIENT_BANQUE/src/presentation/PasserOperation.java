package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;

import entities.*;

public class PasserOperation extends JFrame implements ActionListener {

	private ArrayList<Compte> listeComptes = new ArrayList<Compte>();
	private JButton bAjouter, bQuitter;
	private JComboBox<String> listeSens, chlisteComptes;
	private JLabel lLibelle, lMontant, lSens, lCompte;
	private JPanel panel1, panel2;
	private JTextField chLibelle, chMontant;

	private Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	DataOutputStream out;
	DataInputStream in;

	private String libelleCompte;

	public PasserOperation() {

		lLibelle = new JLabel("Libelle Opération:");
		lCompte = new JLabel("Compte Opération:");
		lMontant = new JLabel("Montant Opération:");
		lSens = new JLabel("Sens Opération:");
		chLibelle = new JTextField();
		chlisteComptes = new JComboBox<String>();
		chMontant = new JTextField();
		listeSens = new JComboBox<String>();
		bAjouter = new JButton("Enregistrer");
		bQuitter = new JButton("Quitter");
		String operation[] = {"Débit", "Crédit"};
		listeSens = new JComboBox<String>(operation);
		panel1 = new JPanel();
		panel2 = new JPanel();

		chlisteComptes.addActionListener(this);
		bAjouter.addActionListener(this);
		bQuitter.addActionListener(this);

		panel1.setLayout(new GridLayout(5, 2));
		panel1.add(lLibelle);
		panel1.add(chLibelle);
		panel1.add(lCompte);
		panel1.add(chlisteComptes);
		panel1.add(lSens);
		panel1.add(listeSens);
		panel1.add(lMontant);
		panel1.add(chMontant);
		panel2.add(bAjouter);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Passer Operation");
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
			remplirlisteComptes();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bAjouter) {
			Operation operation;
			try {
				oos.writeObject("passerOperation");
				oos.flush();
				String libelle = chLibelle.getText();
				String sens = listeSens.getSelectedItem().toString();
				int montant = Integer.parseInt(chMontant.getText());
				operation = new Operation();
				operation.setLibelle(libelle);
				operation.setDateOperation(LocalDateTime.now().toString());
				operation.setMontant(montant);
				operation.setSens(sens.equals("Débit")? "DB" : "CR");
				operation.setNumeroCompte(recupererNumeroCompte(libelleCompte));
				oos.writeObject(operation);
				oos.flush();
				chLibelle.setText("");
				chMontant.setText("");
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		else if(e.getSource() == chlisteComptes) {
			JComboBox cb = (JComboBox)e.getSource();
			libelleCompte = (String)cb.getSelectedItem();
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

	public void remplirlisteComptes()  {
		try {
			oos.writeObject("listerComptes");
			oos.flush();
			listeComptes = (ArrayList<Compte>) ois.readObject();
			chlisteComptes.addItem("Choisir un compte");
			for(Compte compte : listeComptes) {
				chlisteComptes.addItem(compte.getLibelle());
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public String recupererNumeroCompte(String libelleCompte) {
		String num = new String("");
		for(Compte compte : listeComptes) {
			if (compte.getLibelle().equals(libelleCompte)) {
				num = compte.getNumero();
			}
		}
		return num;
	}

	public static void main(String args[]) {
		new PasserOperation();
	}

}
