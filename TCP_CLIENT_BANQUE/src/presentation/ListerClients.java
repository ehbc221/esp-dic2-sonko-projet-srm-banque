package presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.*;

public class ListerClients extends JFrame implements ActionListener {

	private JTable table;
	private ArrayList<Client> listeClients;
	private JScrollPane sc;
	private JPanel panel1, panel2;
	private JButton bQuitter;

	public ListerClients(ArrayList <Client> listeClients) {

		panel1 = new JPanel();
		panel2 = new JPanel();
		bQuitter = new JButton("Quitter");
		this.listeClients = listeClients;
		sc = new JScrollPane();
		table = new JTable();
		sc.setViewportView(table);
		DefaultTableModel modele = (DefaultTableModel)table.getModel();
		modele.addColumn("Numéro Client");
		modele.addColumn("Nom Client");
		modele.addColumn("Prénom Client");

		int ligne=0;
		for (Client client : listeClients) {
			modele.addRow( new Object[0]);
			modele.setValueAt(client.getNumero(), ligne, 0);
			modele.setValueAt(client.getNom(), ligne, 1);
			modele.setValueAt(client.getPrenom(), ligne, 2);
			ligne++;
		}

		bQuitter.addActionListener(this);

		panel1.add(sc);
		panel2.add(bQuitter);

		add(panel1,BorderLayout.NORTH);
		add(panel2,BorderLayout.SOUTH);

		setTitle("Liste des client");
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
