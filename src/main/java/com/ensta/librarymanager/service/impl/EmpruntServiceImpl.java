package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.impl.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.impl.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService {
    private static EmpruntServiceImpl instance = new EmpruntServiceImpl();
    private EmpruntServiceImpl() { }
    public static EmpruntService getInstance() {
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrent();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            //On met un abonnement BASIC par défaut lors de la création.
            Membre membre = membreDao.getById(idMembre);
            if (isEmpruntPossible(membre))
                empruntDao.create(idMembre,idLivre,dateEmprunt);
        }  catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
    }

    @Override
    //IMPORTANT : returnBook raisonne sur l'ID d'un EMPRUNT et non d'un LIVRE.
    //Ceci peut porter à confusion.
    public void returnBook(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Erreur lors du parsing: id=" + id, e2);
        }
    }

    @Override
    public int count() throws ServiceException {
        EmpruntDao empruntDao= EmpruntDaoImpl.getInstance();
        int count = -1;
        try {
            count = empruntDao.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return count;
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDao empruntDao= EmpruntDaoImpl.getInstance();
        boolean isAvailable = false;
        try {
            isAvailable = empruntDao.getListCurrentByLivre(idLivre).size() == 0;
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return isAvailable;
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        int limit = membre.getAbonnement().getSimultaneous();
        EmpruntDao empruntDao= EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByMembre(membre.getId());
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        boolean isPossible = emprunts.size() < limit;
        return isPossible;
    }
}
