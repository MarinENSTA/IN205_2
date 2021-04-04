package ensta;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

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
}
