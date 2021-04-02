package com.ensta.librarymanager.dao.impl;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.utils.EstablishConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDaoImpl implements MembreDao {

    private static final String SELECT_ALL_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement " +
            "FROM membre " +
            "ORDER BY nom, prenom;";
    private static final String SELECT_ONE_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement " +
            "FROM membre " +
            "WHERE id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone, " +
            "abonnement) " +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE membre " +
            "SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, " +
            "abonnement = ? " +
            "WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM membre WHERE id = ?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";

    public MembreDaoImpl() {
    }

    @Override
    public List<Membre> getList() throws DaoException {
        List<Membre> members = new ArrayList<>();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = EstablishConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                Membre m = new Membre(res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("adresse"),
                        res.getString("email"),
                        res.getString("telephone"),
                        Abonnement.fromString(res.getString("abonnement")));
                members.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des films", e);
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
        return members;
    }

    @Override
    public Membre getById(int id) throws DaoException {
        Membre m = new Membre();
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = EstablishConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                m.setId(res.getInt("id"));
                m.setNom(res.getString("nom"));
                m.setPrenom(res.getString("prenom"));
                m.setAdresse(res.getString("adresse"));
                m.setEmail(res.getString("email"));
                m.setTelephone(res.getString("telephone"));
                m.setAbonnement(Abonnement.fromString(res.getString("abonnement")));
            }

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du membre: id=" + id, e);
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
        return m;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws DaoException {
        ResultSet res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = EstablishConnection.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, abonnement.getName());

            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }

            System.out.println("New member with name = " + nom + " created !");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du membre", e);
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
        return id;
    }

    @Override
    public void update(Membre membre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = EstablishConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().name());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + membre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du membre: " + membre, e);
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
    public void delete(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = EstablishConnection.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("DELETE MEMBER WITH ID : " + id);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du membre d'ID : " + id, e);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = EstablishConnection.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_QUERY);
            res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du nombre de membres", e);
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
