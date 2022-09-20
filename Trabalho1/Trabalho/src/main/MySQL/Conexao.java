package main.MySQL;

import main.Aeroportos.Aero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Class;
import java.lang.ClassNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Conexao {
    //As exceções possíveis em geral não foram tratadas, e foram lançadas
    //Método conectar retorna a lista de aeroportos, necessária para toda a rotina desse programa
    public List<Aero> conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Estabelecendo a conexão
        Connection conectar = DriverManager.getConnection("jdbc:mysql://localhost/new_schema?user=root&password=mementomori");
        List<Aero> aeroList;

        //Tratamento de Erros e Exceções
        try (ResultSet set = conectar.createStatement().executeQuery("SELECT * FROM airportdata")) {
            aeroList = new ArrayList<>();
            while (set.next()) {
                //A leitura é feita linha por linha
                //E a cada coluna está associado um atributo do objeto Aero
                int id = set.getInt("id");
                String iata = set.getString("iata");
                String State = set.getString("state");
                String City = set.getString("city");
                double latitude = set.getDouble("latitude");
                double longitude = set.getDouble("longitude");
                Aero aero = new Aero(id, iata,State,City, latitude, longitude);
                aeroList.add(aero);
            }
        return  aeroList;
        }
        catch (Exception exception){
        }
        //No caso de Exceção ou falha de conexão é retornado uma lista nula
       return null;
    }

    //Função que automatiza a inserção de cada consulta no MySQL, com auxilio do StringFormat.
    public void salvar_consultar(String origem,String destino,String percurso) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conectar = DriverManager.getConnection("jdbc:mysql://localhost/new_schema?user=root&password=mementomori");
        //Os parâmetros da função em questão são inseridas na query atráves da função StringFormat
        conectar.createStatement().execute(String.format("INSERT INTO consultas (origem,destino,escala) VALUES('%s','%s','%s');",origem,destino,percurso));
    }

}
