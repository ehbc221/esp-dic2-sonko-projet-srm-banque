package service;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import dao.*;
import entities.*;

public class ApplicationServeur extends JFrame implements ActionListener {

	private JTextArea zoneReception;
	private JButton bQuitter;
	private JPanel panel1, panel2;

	public ApplicationServeur() {

		panel1 = new JPanel();
		panel2 = new JPanel();
		zoneReception = new JTextArea(15,40);
		bQuitter = new JButton("Quitter");
		bQuitter.addActionListener(this);

		panel1.add(new JScrollPane(zoneReception));
		panel2.add(bQuitter);

		add(panel1, BorderLayout.CENTER);
		add(panel2, BorderLayout.SOUTH);

		setTitle(" Serveur TCP Multiclients");
		setSize(700, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try {
			ServerSocket serv = new ServerSocket(8000);
			zoneReception.append("Le Serveur a demarré\n");
			int numclient = 1;
			while(true) {
				Socket socket = serv.accept();
				InetAddress adr = socket.getInetAddress();
				String ipclient = adr.getHostAddress();
				String nomclient= adr.getHostName();
				zoneReception.append("Client n:" + numclient + ", adresse ip:" + ipclient + "\n");
				zoneReception.append("Nom machine cliente: " + nomclient + "\n");
				Service s = new Service(socket);
				s.start();
				numclient++;
			}
		}
		catch(IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

	class Service extends Thread {

		private Socket socket;
		private AccesBD bd;
		public Service(Socket socket) {
			bd = new AccesBD();
			this.socket = socket;
		}

		public void run() {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String mode;
				do {
					mode = (String)ois.readObject();
					zoneReception.append("Mode en cours d\'exploitation!!!: " + mode + "\n");

					if (mode.equals("creerAgence")) {
						Agence agence = (Agence) ois.readObject();
						String message = bd.creerAgence(agence);
						oos.writeObject(message);
						oos.flush();
					}

					else if (mode.equals("listerAgences")) {
						ArrayList<Agence> listeAgences = new ArrayList<Agence>();
						listeAgences = bd.listerAgences(); 
						oos.writeObject(listeAgences);
						oos.flush();
					}

					else if(mode.equals("creerClient")) {
						Client c = (Client)ois.readObject();
						String message = bd.creerClient(c);
						oos.writeObject(message);
						oos.flush();
					}

					else if(mode.equals("listerClients")) {
						ArrayList<Client> listeClients = new ArrayList<Client>();
						listeClients = bd.listerClients(); 
						oos.writeObject(listeClients);
						oos.flush();
					}

					else if(mode.equals("creerCompte")) {
						Compte compte = (Compte) ois.readObject();
						String message = bd.creerCompte(compte);
						oos.writeObject(message);
						oos.flush();
					}

					else if(mode.equals("listerComptes")) {
						ArrayList<Compte> listeComptes = new ArrayList<Compte>();
						listeComptes = bd.listerComptes();
						oos.writeObject(listeComptes);
						oos.flush();
					}

					else if(mode.equals("listerComptesClient")) {
						ArrayList<Compte> listeComptesClient = new ArrayList<Compte>();
						String numeroClient = (String)ois.readObject();
						listeComptesClient = bd.listerComptesClient(numeroClient); 
						oos.writeObject(listeComptesClient);
						oos.flush();
					}

					else if(mode.equals("afficherReleveCompte")) {
						ArrayList<Operation> listeOperations = new ArrayList<Operation>();
						String libelle = (String)ois.readObject();
						listeOperations = bd.afficherReleveCompte(libelle); 
						oos.writeObject(listeOperations);
						oos.flush();
					}

					else if(mode.equals("passerOperation")) {
						Operation operation = (Operation) ois.readObject();
						String message = bd.passerOperation(operation);
						oos.writeObject(message);
						oos.flush();
					}

					else if (mode.equals("fin")) {
						zoneReception.append("Connexion terminée!!! pour un client" + "\n");
						oos.flush();
					}
				}
				while(true);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		dispose();
		System.exit(0);
	}

	public static void main(String args[]) {
		new ApplicationServeur();
	}

}
