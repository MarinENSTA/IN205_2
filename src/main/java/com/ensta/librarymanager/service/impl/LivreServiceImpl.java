package com.ensta.librarymanager.service.impl;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.impl.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.LivreService;

import java.util.List;

public class LivreServiceImpl implements LivreService {
    @Override
    public List<Livre> getList() throws ServiceException {
        return null;
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        return null;
    }

    @Override
    public Livre getById(int id) throws ServiceException {
        return null;
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

    }

    @Override
    public int count() throws ServiceException {
        return 0;
    }
}
