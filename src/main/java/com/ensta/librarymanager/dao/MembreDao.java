package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;

import java.util.List;

public interface MembreDao {

	public List<Membre> getList() throws DaoException;
	public Membre getById(int id) throws DaoException;
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws DaoException;
	public void update(Membre membre) throws DaoException;
	public void delete(int id) throws DaoException;
	public int count() throws DaoException;
}
