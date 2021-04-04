package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.impl.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.MembreService;

import java.util.ArrayList;
import java.util.List;

public class MembreServiceImpl implements MembreService {

    private static MembreServiceImpl instance = new MembreServiceImpl();
    private MembreServiceImpl() { }
    public static MembreService getInstance() {
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membres;
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreDao.getList();

            int count = 0;
            while (membres.size() > count) {
                if (empruntService.isEmpruntPossible(membres.get(count)))
                    membres.remove(count);
                else
                    count++;
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membres;
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        Membre membre= new Membre();
        try {
            membre = membreDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return membre;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        int i = -1;
        try {
            if (nom.length() == 0 | prenom.length() == 0)
                throw new ServiceException("Prenom ou nom incorrect lors de la création d'un membre");
            else {
                //On met un abonnement BASIC par défaut lors de la création.
                i = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone, Abonnement.BASIC);
            }
        }  catch (DaoException |ServiceException e1) {
            System.out.println(e1.getMessage());
        }
        return i;
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        int i = -1;
        try {
            if (membre.getNom().length() == 0 | membre.getPrenom().length() == 0)
                throw new ServiceException("Prenom ou nom incorrect lors de la création d'un membre");
            else {
                membre.setNom(membre.getNom().toUpperCase());
                membreDao.update(membre);
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Erreur lors du parsing: id=" + membre.getId(), e2);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        MembreDao membreDao= MembreDaoImpl.getInstance();
        try {
            membreDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Erreur lors du parsing: id=" + id, e2);
        }
    }

    @Override
    public int count() throws ServiceException {
        MembreDao membreDao= MembreDaoImpl.getInstance();
        int count = -1;
        try {
            count = membreDao.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return count;
    }
}
