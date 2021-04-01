package ensta;

import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ModeleTest {
    Membre TestMembre = new Membre(0, "STAMM", "Marin", "Ensta Paris", "marin.stamm@ensta-paris.fr", "+33615269117", Membre.Abonnement.VIP );
    Livre TestLivre = new Livre(0, "Boule de Foudre", "Liu Cixin", "12345");
    Emprunt TestEmprunt = new Emprunt(0,TestMembre, TestLivre, LocalDate.now(), LocalDate.now());

    @Test
    public void TestEmprunt0()
    {
        assertEquals(0, TestEmprunt.getId());
    }

    @Test
    public void TestEmprunt1()
    {
        assertNotEquals(42, TestEmprunt.getIdLivre());
        //System.out.println(TestEmprunt.toString());
    }

    @Test
    public void TestMembre0()
    {
        assertEquals(Membre.Abonnement.VIP, TestMembre.getAbonnement());
    }

    @Test
    public void TestMembre1()
    {
        assertNotEquals(TestMembre.getAdresse(), "Ensta Bretagne");
    }

    @Test
    public void TestLivre0()
    {
        assertNotEquals("Léonard de Vinci", TestLivre.getAuteur());
    }
}
