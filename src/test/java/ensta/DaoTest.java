package ensta;


import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.impl.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class DaoTest {

    @Test
    public void TestCountMembres() throws DaoException{
        MembreDao m = new MembreDaoImpl();
        try {
            int count = m.count();
            assertNotEquals(m, 0);

        } catch (DaoException e) {
            e.printStackTrace();
        }

    }
}
