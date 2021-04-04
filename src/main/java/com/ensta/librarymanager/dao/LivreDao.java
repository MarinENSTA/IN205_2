package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;

import java.util.List;

public interface LivreDao {
	public List<Livre> getList() throws DaoException;
	public Livre getById(int id) throws DaoException;
	public int create(String titre, String auteur, String isbn) throws DaoException;
	public void update(Livre livre) throws DaoException;
	public void delete(int id) throws DaoException;
	public int count() throws DaoException;
}
