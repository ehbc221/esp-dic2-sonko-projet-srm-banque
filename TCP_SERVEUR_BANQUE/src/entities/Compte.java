package entities;

import java.io.*;

public class Compte implements Serializable {

	private String numero;
	private String libelle;
	private double solde;
	private String  sens;
	private int  numeroClient;

	public Compte() {
		super();
	}

	public Compte(String numero, String libelle, double solde, String sens, int numeroClient) {
		super();
		this.numero = numero;
		this.libelle = libelle;
		this.solde = solde;
		this.sens = sens;
		this.numeroClient = numeroClient;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public void setSens(String sens) {
		this.sens = sens;
	}

	public String getSens() {
		return sens;
	}

	public void setNumeroClient(int numeroClient) {
		this.numeroClient = numeroClient;
	}

	public int getNumeroClient() {
		return numeroClient;
	}

}