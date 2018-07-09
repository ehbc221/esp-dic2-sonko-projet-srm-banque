package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import entities.*;

public class ApplicationClient extends JFrame implements ActionListener {

	private JMenuBar bMenuBar;
	private JMenu mAgence, mClient, mCompte, mOperation;
	private JMenuItem iCreerAgence, iListerAgences, iCreerClient, iListerClients, iCreerCompte, iListerComptesClient, iAfficherReleveCompte, iPasserOperation;
	private JPanel panel1, panel2;
	private JLabel lMessageAccueil;
	private JButton bQuitter;
	protected Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private DataOutputStream out;
	private DataInputStream in;

	public ApplicationClient() {

		bMenuBar = new JMenuBar();
		mAgence = new JMenu("Agence");
		mClient = new JMenu("Client");
		mCompte = new JMenu("Compte");
		mOperation = new JMenu("Opération");

		iCreerAgence = new JMenuItem("Créer Agence");
		iListerAgences = new JMenuItem("Lister Agences");
		iCreerClient = new JMenuItem("Créer Client");
		iListerClients = new JMenuItem("Lister Clients");
		iCreerCompte = new JMenuItem("Créer Compte");
		iListerComptesClient = new JMenuItem("Lister Comptes Client");
		iAfficherReleveCompte = new JMenuItem("Afficher Relevé Compte");
		iPasserOperation = new JMenuItem("Passer Opération");

		bMenuBar.add(mAgence);
		bMenuBar.add(mClient);
		bMenuBar.add(mCompte);
		bMenuBar.add(mOperation);

		mAgence.add(iCreerAgence);
		mAgence.add(iListerAgences);
		mClient.add(iCreerClient);
		mClient.add(iListerClients);
		mCompte.add(iCreerCompte);
		mCompte.add(iListerComptesClient);
		mCompte.add(iAfficherReleveCompte);
		mOperation.add(iPasserOperation);

		panel1 = new JPanel();
		panel2 = new JPanel();
		lMessageAccueil = new JLabel("Sélectionnez un menu pour continuer.");
		bQuitter = new JButton("Quitter");
		bQuitter.addActionListener(this);

		panel1.add(lMessageAccueil);
		panel2.add(bQuitter);

		add(panel1, BorderLayout.CENTER);
		add(panel2, BorderLayout.SOUTH);

		setTitle("Banque - ESP");
		setJMenuBar(bMenuBar);
		setSize(700, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		initEvent();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bQuitter) {
			try {
				dispose();
				System.exit(0);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public void initEvent() {

		iCreerAgence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreerAgence();
			}
		});

		iListerAgences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket("localhost", 8000);
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
					oos.writeObject("listerAgences");
					oos.flush();
					ArrayList<Agence> listeAgences = (ArrayList<Agence>)ois.readObject();
					new ListerAgences(listeAgences);
				}
				catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		iCreerClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreerClient();
			}
		});

		iListerClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket("localhost", 8000);
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());
					oos.writeObject("listerClients");
					oos.flush();
					ArrayList<Client> listeClients = (ArrayList<Client>)ois.readObject();
					new ListerClients(listeClients);
				}
				catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		iCreerCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreerCompte();
			}
		});

		iListerComptesClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListerComptesClientRechercherClient();
			}
		});

		iAfficherReleveCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AfficherReleveCompteRechercherCompte();
			}
		});

		iPasserOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PasserOperation();
			}
		});

	}

	public static void main(String[] args) {
		new ApplicationClient();
	}

}
