package DAO;

import Config.ConfigDb;
import Model.Amigos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AmigosDAO {

    public static ArrayList<Amigos> MinhaLista = new ArrayList<>();
    public ConfigDb conexao;

    public AmigosDAO() {
        this.conexao = new ConfigDb();
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT MAX(id_a) id_a FROM amigos");
                res.next();
                maiorID = res.getInt("id_a");
            }

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public ArrayList getMinhaLista() {

        MinhaLista.clear();

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM amigos");
                while (res.next()) {

                    int id_a = res.getInt("Id_a");
                    String nome_a = res.getString("Nome");
                    String telefone = res.getString("Telefone");

                    Amigos objeto = new Amigos(id_a, nome_a, telefone);

                    MinhaLista.add(objeto);
                }
            }

        } catch (SQLException ex) {
        }

        return MinhaLista;
    }

    public boolean InsertAmigosBD(Amigos objeto) {
        String sql = "INSERT INTO amigos(id_a,nome,telefone) VALUES(?,?,?)";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setInt(1, objeto.getId_a());
                stmt.setString(2, objeto.getNome_a());
                stmt.setString(3, objeto.getTelefone());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean DeleteAmigosBD(int id_a) {
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                stmt.executeUpdate("DELETE FROM amigos WHERE id_a = " + id_a);
            }

        } catch (SQLException erro) {
        }

        return true;
    }

    public boolean UpdateAmigosBD(Amigos objeto) {

        String sql = "UPDATE amigos set nome = ? , telefone = ? WHERE id_a = ?";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setString(1, objeto.getNome_a());
                stmt.setString(2, objeto.getTelefone());
                stmt.setInt(3, objeto.getId_a());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public Amigos carregaAmigos(int id_a) {

        Amigos objeto = new Amigos();
        objeto.setId_a(id_a);

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM amigos WHERE id = " + id_a);
                res.next();

                objeto.setNome_a(res.getString("nome"));
                objeto.setTelefone(res.getString("telefone"));
            }

        } catch (SQLException erro) {
        }
        return objeto;
    }
}