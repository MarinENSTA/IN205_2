package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;

import java.time.LocalDate;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService {
    private static EmpruntServiceImpl instance = new EmpruntServiceImpl();
    private EmpruntServiceImpl() { }
    public static EmpruntService getInstance() {
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws ServiceException {
        return null;
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        return null;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        return null;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        return null;
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        return null;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {

    }

    @Override
    public void returnBook(int id) throws ServiceException {

    }

    @Override
    public int count() throws ServiceException {
        return 0;
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        return false;
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        return false;
    }
}
