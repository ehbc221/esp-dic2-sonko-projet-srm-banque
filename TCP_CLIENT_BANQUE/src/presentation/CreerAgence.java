package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JOptionPane;

import entities.*;

public class CreerAgence extends JFrame implements ActionListener {

	private JPanel panel1, panel2;
	private JLabel lNom, lAdresse;
	private JTextField chNom, chAdresse;
	private JButton bAjouter, bQuitter;
	private Socket socket;
	DataOutputStream out;
	DataInputStream in;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public CreerAgence() {

		lNom = new JLabel("Nom Agence:");
		lAdresse = new JLabel("Adresse Agence:");
		chNom = new JTextField();
		chAdresse = new JTextField();
		bAjouter = new JButton("Enregistrer");
		bQuitter = new JButton("Quitter");

		bAjouter.addActionListener(this);
		bQuitter.addActionListener(this);

		panel1 = new JPanel();
		panel2 = new JPanel();

		panel1.setLayout(new GridLayout(2,2));
		panel1.add(lNom);
		panel1.add(chNom);
		panel1.add(lAdresse);
		panel1.add(chAdresse);
		panel2.add(bAjouter);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.CENTER);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Créer Agence");
		setSize(500, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try {
			socket= new Socket("localhost", 8000);
			oos=new ObjectOutputStream(socket.getOutputStream());;
			ois=new ObjectInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bAjouter) {
			Agence agence;
			String message = new String();
			try {
				oos.writeObject("creerAgence");
				oos.flush();
				String nom = chNom.getText();
				String adresse = chAdresse.getText();
				agence = new Agence();
				agence.setNom(nom);
				agence.setAdresse(adresse);
				oos.writeObject(agence);
				oos.flush();
				message = ois.readObject().toString();
				JOptionPane.showMessageDialog(null, message.equals("succes") ? "Agence créée avec succès!" : "Echec de la création de l'agence!");
				chNom.setText("");
				chAdresse.setText("");
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
				//System.exit(0);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}	

	}

	public static void main(String args[]) {
		new CreerAgence();
	}

}
