package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.MembreService;

import java.util.List;

public class MembreServiceImpl implements MembreService {

    private static MembreServiceImpl instance = new MembreServiceImpl();
    private MembreServiceImpl() { }
    public static MembreService getInstance() {
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        return null;
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        return null;
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        return null;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
        return 0;
    }

    @Override
    public void update(Membre membre) throws ServiceException {

    }

    @Override
    public void delete(int id) throws ServiceException {

    }

    @Override
    public int count() throws ServiceException {
        return 0;
    }
}
