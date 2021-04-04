package com.ensta.librarymanager.dao.impl;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDaoImpl implements LivreDao {

    // Une implémentation possible du design pattern Singleton dite "lazy instanciation"
    private static LivreDaoImpl instance;
    private LivreDaoImpl() { }
    public static LivreDao getInstance() {
        if(instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL_QUERY = "SELECT id, titre, auteur, isbn FROM livre;";
    private static final String SELECT_ONE_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
    private static final String CREATE_QUERY = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM livre WHERE id = ?;";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre;";

    @Override
    public List<Livre> getList() throws DaoException {
        List<Livre> livres = new ArrayList<>();
        ResultSet res = null;
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
                Livre l = new Livre(res.getInt("id"),
                        res.getString("titre"),
                        res.getString("auteur"),
                        res.getString("isbn"));
                livres.add(l);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des livres", e);
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
        return livres;
    }

    @Override
    public Livre getById(int id) throws DaoException {
        Livre l = new Livre();
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
                l.setId(res.getInt("id"));
                l.setTitre(res.getString("titre"));
                l.setAuteur(res.getString("auteur"));
                l.setIsbn(res.getString("isbn"));
            }

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du livre: id=" + id, e);
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
        return l;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        ResultSet res = null;
        PreparedStatement preparedStatement = null;
        int id=-1;
        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);

            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }

            System.out.println("New book with name = " + titre + " created !");
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du livre", e);
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
    public void update(Livre livre) throws DaoException {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getId());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE: " + livre);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du membre: " + livre, e);
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
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("DELETE BOOK WITH ID : " + id);
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du livre d'ID : " + id, e);
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
            throw new DaoException("Problème lors de la récupération du nombre de livres", e);
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
