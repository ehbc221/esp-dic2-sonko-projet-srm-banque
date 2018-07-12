package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import entities.*;

public class AfficherReleveCompteRechercherCompte extends JFrame implements ActionListener {

	private JButton bQuitter, bValider;
	private JLabel lRecherche;
	private JPanel panel1, panel2;
	private JTextField chRecherche;
	private Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	DataOutputStream out;
	DataInputStream in;

	public AfficherReleveCompteRechercherCompte() {

		lRecherche = new JLabel("Entrer le libelle du compte du client:");
		chRecherche = new JTextField();		
		Connection con = null;
		Statement ps = null;
		ResultSet rs = null;	
		bValider = new JButton("Valider");
		bQuitter = new JButton("Quitter");
		bValider.addActionListener(this);
		bQuitter.addActionListener(this);
		panel1 = new JPanel();
		panel2 = new JPanel();

		panel1.setLayout(new GridLayout(5, 2));
		panel1.add(lRecherche);
		panel1.add(chRecherche);
		panel2.add(bValider);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Recherche Compte");
		setSize(400, 400);
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
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bValider) {
			try {
				String libelle = chRecherche.getText();
				oos.writeObject("afficherReleveCompte");	
				oos.flush();
				oos.writeObject(libelle);
				oos.flush();
				ArrayList<Operation> listeOperations = (ArrayList<Operation>)ois.readObject();
				new AfficherReleveCompte(listeOperations);
				this.chRecherche.setText("");
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
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

	public static void main(String args[]) {
		new AfficherReleveCompteRechercherCompte();
	}

}
