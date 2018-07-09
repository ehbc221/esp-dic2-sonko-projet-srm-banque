package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.*;

public class ListerComptesClient extends JFrame implements ActionListener {

	private ArrayList<Compte> listeComptes;
	private JButton bQuitter;
	private JPanel panel1, panel2;
	private JScrollPane sc;
	private JTable table;

	public ListerComptesClient(ArrayList <Compte> listeComptes) {

		panel1 = new JPanel();
		panel2 = new JPanel();
		bQuitter = new JButton("Quitter");
		this.listeComptes = listeComptes;
		sc = new JScrollPane();
		table = new JTable();
		sc.setViewportView(table);
		DefaultTableModel modele = (DefaultTableModel)table.getModel();
		modele.addColumn("Numero Compte");
		modele.addColumn("Libelle Compte");
		modele.addColumn("Solde Compte");
		modele.addColumn("Sens Compte");

		int ligne=0;
		for (Compte compte : listeComptes) {
			modele.addRow( new Object[0]);
			modele.setValueAt(String.valueOf(compte.getNumeroClient()), ligne, 0);
			modele.setValueAt(compte.getLibelle(), ligne, 1);
			modele.setValueAt(String.valueOf(compte.getSolde()), ligne, 2);
			modele.setValueAt(compte.getSens(), ligne, 3);
			ligne++;
		}

		bQuitter.addActionListener(this); 

		panel1.add(sc);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.NORTH);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Lister Comptes Clients");
		setSize(550, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bQuitter) {
			dispose();
		}
	}

}
