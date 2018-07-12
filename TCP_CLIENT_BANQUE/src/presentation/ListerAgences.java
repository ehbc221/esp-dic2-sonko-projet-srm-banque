package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.*;

public class ListerAgences extends JFrame implements ActionListener {

	private ArrayList<Agence> listeAgences;
	private JButton bQuitter;
	private JPanel panel1 ,panel2;
	private JScrollPane sc;
	private JTable table;
	
	public ListerAgences(ArrayList <Agence> listeAgences) {
		panel1 = new JPanel();
		panel2 = new JPanel();
		bQuitter = new JButton("Quitter");
		sc = new JScrollPane();
		table = new JTable();
		sc.setViewportView(table);
		this.listeAgences = listeAgences;
		DefaultTableModel modele = (DefaultTableModel)table.getModel();
		modele.addColumn("Numero Agence");
		modele.addColumn("Nom Agence");
		modele.addColumn("Adresse Agence");

		int ligne = 0;
		for (Agence agence : listeAgences) {
			modele.addRow(new Object[0]);
			modele.setValueAt(String.valueOf(agence.getNumero()), ligne, 0);
			modele.setValueAt(agence.getNom(), ligne, 1);
			modele.setValueAt(agence.getAdresse(), ligne, 2);
			ligne++;
		}

		bQuitter.addActionListener(this); 
		panel1.add(sc);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.NORTH);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Liste Agences");
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
