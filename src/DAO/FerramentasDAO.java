package DAO;

import Config.ConfigDb;
import Model.Ferramentas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FerramentasDAO {

    public static ArrayList<Ferramentas> MinhaLista = new ArrayList<>();
    public ConfigDb conexao;

    public FerramentasDAO() {
        this.conexao = new ConfigDb();
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT MAX(id_f) id_f FROM ferramentas");
                res.next();
                maiorID = res.getInt("id_f");
            }

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public ArrayList getMinhaLista() {

        MinhaLista.clear();

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM ferramentas");
                while (res.next()) {

                    int id_f = res.getInt("Id_f");
                    String nome = res.getString("Nome");
                    String status = res.getString("Status");
                    String marca = res.getString("Marca");
                    String aquisicao = res.getString("Aquisicao");

                    Ferramentas objeto = new Ferramentas(id_f, nome, status, marca, aquisicao);

                    MinhaLista.add(objeto);
                }
            }

        } catch (SQLException ex) {
        }

        return MinhaLista;
    }

    public boolean InsertFerramentasBD(Ferramentas objeto) {
        String sql = "INSERT INTO ferramentas(id_f,nome,status,marca,aquisicao) VALUES(?,?,?,?,?)";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setInt(1, objeto.getId_f());
                stmt.setString(2, objeto.getNome_f());
                stmt.setString(3, objeto.getStatus());
                stmt.setString(4, objeto.getMarca());
                stmt.setString(5, objeto.getAquisicao());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean DeleteFerramentasBD(int id_f) {
        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                stmt.executeUpdate("DELETE FROM ferramentas WHERE id_f = " + id_f);
            }

        } catch (SQLException erro) {
        }

        return true;
    }

    public boolean UpdateFerramentasBD(Ferramentas objeto) {

        String sql = "UPDATE ferramentas set nome = ?, status = ? ,marca = ? ,aquisicao = ? WHERE id_f = ?";

        try {
            try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
                stmt.setString(1, objeto.getNome_f());
                stmt.setString(2, objeto.getStatus());
                stmt.setString(3, objeto.getMarca());
                stmt.setString(4, objeto.getAquisicao());
                stmt.setInt(5, objeto.getId_f());

                stmt.execute();
            }

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public Ferramentas carregaFerramentas(int id_f) {

        Ferramentas objeto = new Ferramentas();
        objeto.setId_f(id_f);

        try {
            try (Statement stmt = conexao.getConexao().createStatement()) {
                ResultSet res = stmt.executeQuery("SELECT * FROM ferramentas WHERE id_f = " + id_f);
                res.next();

                objeto.setNome_f(res.getString("Nome"));
                objeto.setStatus(res.getString("Status"));
                objeto.setMarca(res.getString("Marca"));
                objeto.setAquisicao(res.getString("Aquisicao"));
            }

        } catch (SQLException erro) {
        }
        return objeto;
    }
}