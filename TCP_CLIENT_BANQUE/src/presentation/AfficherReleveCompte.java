package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.*;

public class AfficherReleveCompte extends JFrame implements ActionListener {

	private ArrayList<Operation> listeOperations;
	private JButton bQuitter;
	private JPanel panel1, panel2;
	private JScrollPane sc;
	private JTable table;

	public AfficherReleveCompte(ArrayList <Operation> listeOperations) {

		panel1 = new JPanel();
		panel2 = new JPanel();
		bQuitter = new JButton("Quitter");
		this.listeOperations = listeOperations;
		sc = new JScrollPane();
		table = new JTable();
		sc.setViewportView(table);
		DefaultTableModel modele = (DefaultTableModel)table.getModel();
		modele.addColumn("Numero Compte");
		modele.addColumn("Numero Operation");
		modele.addColumn("Libelle Operation");
		modele.addColumn("Date Operation");
		modele.addColumn("Montant Operation");
		modele.addColumn("Sens Operation");

		int ligne=0;
		for (Operation operation : listeOperations) {
			modele.addRow( new Object[0]);
			modele.setValueAt(String.valueOf(operation.getNumeroCompte()), ligne, 0);
			modele.setValueAt(String.valueOf(operation.getNumero()), ligne, 1);
			modele.setValueAt(operation.getLibelle(), ligne, 2);
			modele.setValueAt(operation.getDateOperation(), ligne, 3);
			modele.setValueAt(String.valueOf(operation.getMontant()), ligne, 4);
			modele.setValueAt((operation.getSens().equals("CR") ? "Crédit" : "Débit"), ligne, 5);
			ligne++;
		}

		bQuitter.addActionListener(this); 

		panel1.add(sc);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.NORTH);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Afficher Releve Compte");
		setSize(550, 500);
		setResizable(true);
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
