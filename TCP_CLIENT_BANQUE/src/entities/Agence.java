package entities;

import java.io.*;

public class Agence implements Serializable {
	
	private int numero;
	private String nom;
	private String adresse;

	public Agence() {
		super();
	}

	public Agence(int numero, String nom, String adresse) {
		super();
		this.numero = numero;
		this.nom = nom;
		this.adresse = adresse;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}	

}
