package ensta;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    //Je teste en priorité les fonctions un peu particulières propres à chaque service

    @Test
    public void TestGetListDispo() throws ServiceException {
        LivreService livreDao = LivreServiceImpl.getInstance();
        List<Livre> livresdispo = new ArrayList<>();
        try {
            livresdispo = livreDao.getListDispo();
            //System.out.println(livresdispo);
            //System.out.println(livresdispo.size());
            assertEquals(livresdispo.size(), 7);
        }catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestEmpruntIsPossible() throws ServiceException {
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService= EmpruntServiceImpl.getInstance();

        try {
            Membre membre = membreService.getById(3);
            System.out.println(membre);
            assertTrue(empruntService.isEmpruntPossible(membre));
        }catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestLivreIsDisponible() throws ServiceException {
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService= EmpruntServiceImpl.getInstance();

        try {
            Livre livre= livreService.getById(2);
            System.out.println(livre);
            assertFalse(empruntService.isLivreDispo(livre.getId()));
        }catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestReturnBook() throws ServiceException {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> empruntsCurrent = new ArrayList<>();
        int count = -1;
        try {
            empruntsCurrent = empruntService.getListCurrent();
            count = empruntsCurrent.size();

            //On rend un livre encore non rendu (par exemple l'emprunt d'id : 5)
            empruntService.returnBook(5);
            empruntsCurrent = empruntService.getListCurrent();

            assertEquals(count - 1, empruntsCurrent.size());
        }catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

}
