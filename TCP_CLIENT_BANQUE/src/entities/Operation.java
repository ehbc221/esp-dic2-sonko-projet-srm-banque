package entities;

import java.io.*;

public class Operation implements Serializable {

	private String numero;
	private String libelle;
	private String date_operation;
	private String  sens;
	private double montant;
	private String  numeroCompte;

	public Operation() {
		super();
	}

	public Operation(String numero, String libelle, String date_operation, String sens, double montant, String numeroCompte) {
		super();
		this.numero = numero;
		this.libelle = libelle;
		this.date_operation = date_operation;
		this.sens = sens;
		this.montant = montant;
		this.numeroCompte = numeroCompte;
	}

	public String getNumero() {
		return numero;
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

	public String getDateOperation() {
		return date_operation;
	}

	public void setDateOperation(String date_operation) {
		this.date_operation = date_operation;
	}

	public String getSens() {
		return sens;
	}

	public void setSens(String sens) {
		this.sens = sens;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

}
