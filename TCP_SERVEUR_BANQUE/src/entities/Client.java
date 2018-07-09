package entities;

import java.io.Serializable;

public class Client implements Serializable {

	private int numero;
	private String nom;
	private String prenom;
	private int numeroAgence;

	public Client() {
		super();
	}

	public Client(int numero, String nom, String prenom, int numeroAgence) {
		super();
		this.numero = numero;
		this.nom = nom;
		this.prenom = prenom;
		this.numeroAgence = numeroAgence;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getNumeroAgence() {
		return numeroAgence;
	}

	public void setNumeroAgence(int numeroAgence) {
		this.numeroAgence = numeroAgence;
	}

}
