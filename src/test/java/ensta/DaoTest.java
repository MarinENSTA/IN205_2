package ensta;


import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.impl.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.impl.LivreDaoImpl;
import com.ensta.librarymanager.dao.impl.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DaoTest {

    @Test
    public void TestCountLivres() throws DaoException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        int count = -1;
        try {
            count = livreDao.count();
            //System.out.println(count);
            livres = livreDao.getList();
            assertEquals(count, livres.size());

        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestIdLivre() throws DaoException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int count = -1;
        try {
            //System.out.println(count);
            Livre livre = livreDao.getById(5);
            //System.out.println(livre.toString());
            assertEquals(livre.getAuteur(), "Jurgen MULLER");
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void TestCountEmprunts() throws DaoException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        int count = -1;
        try {
            count = empruntDao.count();
            //System.out.println(count);
            emprunts = empruntDao.getList();
            assertEquals(count, emprunts.size());

        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestIdEmprunts() throws DaoException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int count = -1;
        try {
            //System.out.println(count);
            Emprunt emprunt = empruntDao.getById(3);
            //System.out.println(emprunt.toString());
            assertEquals(emprunt.getIdMembre().getId(), 5);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestCountMembres() throws DaoException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        List<Membre> emprunts = new ArrayList<>();
        int count = -1;
        try {
            count = membreDao.count();
            //System.out.println(count);
            emprunts = membreDao.getList();
            assertEquals(count, emprunts.size());

        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestIdMembres() throws DaoException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        int count = -1;
        try {
            //System.out.println(count);
            Membre membre = membreDao.getById(1);
            //System.out.println(membre.toString());
            assertEquals(membre.getEmail(), "kcherif@email.com");
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }
}