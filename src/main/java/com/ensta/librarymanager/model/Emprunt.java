package com.ensta.librarymanager.model;
import java.time.LocalDate;

public class Emprunt
{
	private int id;
	private Membre idMembre;
	private Livre idLivre;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;

	public Emprunt(int id, Membre idMembre, Livre idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
		this.id = id;
		this.idMembre = idMembre;
		this.idLivre = idLivre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}

	public Emprunt() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Membre getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(Membre idMembre) {
		this.idMembre = idMembre;
	}

	public Livre getIdLivre() {
		return idLivre;
	}

	public void setIdLivre(Livre idLivre) {
		this.idLivre = idLivre;
	}

	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public LocalDate getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
	}

	@Override
	public String toString() {
		return "Emprunt{" +
				"id=" + id +
				", idMembre= (" + idMembre +
				"), idLivre= (" + idLivre +
				"), dateEmprunt=" + dateEmprunt +
				", dateRetour=" + dateRetour +
				'}';
	}
}