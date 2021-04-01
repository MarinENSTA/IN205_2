package ensta;

import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ModeleTest {

    @Test
    public void TestEmprunt0()
    {
        Emprunt testEmprunt = new Emprunt(0,0, 42, LocalDate.now(), LocalDate.now());
        assertEquals(0, testEmprunt.getId());
    }

    @Test
    public void TestEmprunt1()
    {
        Emprunt testEmprunt = new Emprunt(0,0, 42, LocalDate.now(), LocalDate.now());
        assertEquals(42, testEmprunt.getIdLivre());
    }

    @Test
    public void TestMembre0()
    {
        Membre TestMembre = new Membre(0, "STAMM", "Marin", "Ensta Paris", "marin.stamm@ensta-paris.fr", "+33615269117", Membre.Abonnement.VIP );
        assertEquals(Membre.Abonnement.VIP, TestMembre.getAbonnement());
    }

    @Test
    public void TestMembre1()
    {
        Membre TestMembre = new Membre(0, "STAMM", "Marin", "Ensta Paris", "marin.stamm@ensta-paris.fr", "+33615269117", Membre.Abonnement.VIP );
        assertNotEquals(TestMembre.getAdresse(), "Ensta Bretagne");
    }

    @Test
    public void TestLivre0()
    {
        Livre TestLivre = new Livre(0, "Boule de Foudre", "Liu Cixin", "12345");
        assertNotEquals("Léonard de Vinci", TestLivre.getAuteur());
    }
}
