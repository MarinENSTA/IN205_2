package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.impl.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.impl.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.LivreService;

import java.util.ArrayList;
import java.util.List;

public class LivreServiceImpl implements LivreService {

    private static LivreServiceImpl instance = new LivreServiceImpl();
    private LivreServiceImpl() { }
    public static LivreService getInstance() {
        return instance;
    }

    @Override
    public List<Livre> getList() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreDao.getList();

            int count = 0;
            while (livres.size() > count) {
                if (empruntDao.getListCurrentByLivre(livres.get(count).getId()).size()>0)
                    livres.remove(count);
                else
                    count++;
            }

        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public Livre getById(int id) throws ServiceException {
        LivreDao filmDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        try {
            livre = filmDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return livre;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int i = -1;
        try {
            i = livreDao.create(titre,auteur,isbn);
        }  catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return i;
    }

    @Override
    public void update(Livre livre) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int i = -1;
        try {
            livreDao.update(livre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Erreur lors du parsing: id=" + livre.getId(), e2);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livreDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2) {
            throw new ServiceException("Erreur lors du parsing: id=" + id, e2);
        }
    }

    @Override
    public int count() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int count = -1;
        try {
             count = livreDao.count();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return count;
    }
}
