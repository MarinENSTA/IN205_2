package com.ensta.librarymanager.dao.impl;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDaoImpl implements EmpruntDao {
    // Une implémentation possible du design pattern Singleton dite "lazy instanciation"
    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl() { }
    public static EmpruntDao getInstance() {
        if(instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, " +
            "dateRetour " +
            "FROM emprunt AS e " +
            "INNER JOIN membre ON membre.id = e.idMembre " +
            "INNER JOIN livre ON livre.id = e.idLivre " +
            "ORDER BY dateRetour DESC;";
    private static final String SELECT_CURRENT_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, " +
            "dateRetour " +
            "FROM emprunt AS e " +
            "INNER JOIN membre ON membre.id = e.idMembre " +
            "INNER JOIN livre ON livre.id = e.idLivre " +
            "WHERE dateRetour IS NULL;";
    private static final String SELECT_CURRENT_BY_MEMBER_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, " +
            "dateRetour " +
            "FROM emprunt AS e " +
            "INNER JOIN membre ON membre.id = e.idMembre " +
            "INNER JOIN livre ON livre.id = e.idLivre " +
            "WHERE dateRetour IS NULL AND membre.id = ?;";
    private static final String SELECT_CURRENT_BY_BOOK_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, " +
            "dateRetour " +
            "FROM emprunt AS e " +
            "INNER JOIN membre ON membre.id = e.idMembre " +
            "INNER JOIN livre ON livre.id = e.idLivre " +
            "WHERE dateRetour IS NULL AND livre.id = ?;";
    private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, " +
            "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, " +
            "dateRetour " +
            "FROM emprunt AS e " +
            "INNER JOIN membre ON membre.id = e.idMembre " +
            "INNER JOIN livre ON livre.id = e.idLivre " +
            "WHERE e.id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) " +
            "VALUES (?, ?, ?, ?);";

    private static final String UPDATE_QUERY = "UPDATE emprunt " +
            "SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? " +
            "WHERE id = ?;";

    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";

    @Override
    public List<Emprunt> getList() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                //System.out.println(res.getDate("dateRetour").toLocalDate());
                Emprunt e = new Emprunt();
                e.setId(res.getInt("id"));
                e.setIdMembre(new Membre(
                            res.getInt("idMembre"),
                            res.getString("nom"),
                            res.getString("prenom"),
                            res.getString("adresse"),
                            res.getString("email"),
                            res.getString("telephone"),
                            Abonnement.fromString(res.getString("abonnement"))
                ));
                e.setIdLivre(new Livre(
                        res.getInt("idLivre"),
                        res.getString("titre"),
                        res.getString("auteur"),
                        res.getString("isbn")
                ));
                e.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
                if (res.getDate("dateRetour")!=null)
                    e.setDateRetour(res.getDate("dateRetour").toLocalDate());
                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CURRENT_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                //System.out.println(res.getDate("dateRetour").toLocalDate());
                Emprunt e = new Emprunt();
                e.setId(res.getInt("id"));
                e.setIdMembre(new Membre(
                        res.getInt("idMembre"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("adresse"),
                        res.getString("email"),
                        res.getString("telephone"),
                        Abonnement.fromString(res.getString("abonnement"))
                ));
                e.setIdLivre(new Livre(
                        res.getInt("idLivre"),
                        res.getString("titre"),
                        res.getString("auteur"),
                        res.getString("isbn")
                ));
                e.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
                if (res.getDate("dateRetour")!=null)
                    e.setDateRetour(res.getDate("dateRetour").toLocalDate());
                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts encore non rendus", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_MEMBER_QUERY);
            preparedStatement.setInt(1, idMembre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                //System.out.println(res.getDate("dateRetour").toLocalDate());
                Emprunt e = new Emprunt();
                e.setId(res.getInt("id"));
                e.setIdMembre(new Membre(
                        res.getInt("idMembre"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("adresse"),
                        res.getString("email"),
                        res.getString("telephone"),
                        Abonnement.fromString(res.getString("abonnement"))
                ));
                e.setIdLivre(new Livre(
                        res.getInt("idLivre"),
                        res.getString("titre"),
                        res.getString("auteur"),
                        res.getString("isbn")
                ));
                e.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
                if (res.getDate("dateRetour")!=null)
                    e.setDateRetour(res.getDate("dateRetour").toLocalDate());
                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts pour un utilisateur", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_BOOK_QUERY);
            preparedStatement.setInt(1, idLivre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                //System.out.println(res.getDate("dateRetour").toLocalDate());
                Emprunt e = new Emprunt();
                e.setId(res.getInt("id"));
                e.setIdMembre(new Membre(
                        res.getInt("idMembre"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("adresse"),
                        res.getString("email"),
                        res.getString("telephone"),
                        Abonnement.fromString(res.getString("abonnement"))
                ));
                e.setIdLivre(new Livre(
                        res.getInt("idLivre"),
                        res.getString("titre"),
                        res.getString("auteur"),
                        res.getString("isbn")
                ));
                e.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
                if (res.getDate("dateRetour")!=null)
                    e.setDateRetour(res.getDate("dateRetour").toLocalDate());
                emprunts.add(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts pour un livre", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        Emprunt em = new Emprunt();
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                em.setId(res.getInt("id"));
                em.setIdMembre(new Membre(
                        res.getInt("idMembre"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("adresse"),
                        res.getString("email"),
                        res.getString("telephone"),
                        Abonnement.fromString(res.getString("abonnement"))
                ));
                em.setIdLivre(new Livre(
                        res.getInt("idLivre"),
                        res.getString("titre"),
                        res.getString("auteur"),
                        res.getString("isbn")
                ));
                em.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
                if (res.getDate("dateRetour")!=null)
                    em.setDateRetour(res.getDate("dateRetour").toLocalDate());
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de l'emprunt id : " + id, e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return em;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id=0;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3, Date.valueOf(dateEmprunt));
            preparedStatement.setDate(3, null);

            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }

            System.out.println("New emprunt with id = " + id + " created !");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création de l'emprunt", e);
        } finally {
            // Ici pour bien faire les choses on doit fermer les objets utilisés dans
            // des blocs séparés afin que les exceptions levées n'empèchent pas la fermeture des autres !
            // la logique est la même pour les autres méthodes. Pour rappel, le bloc finally sera toujours exécuté !
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, emprunt.getIdMembre().getId());
            preparedStatement.setInt(2, emprunt.getIdLivre().getId());
            preparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + emprunt);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour de l'emprunt : " + emprunt, e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int count() throws DaoException {
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_QUERY);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du nombre d'emprunts total", e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
