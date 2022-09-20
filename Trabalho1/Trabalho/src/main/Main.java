package main;

import main.Aeroportos.Aero;
import main.MySQL.Conexao;
import main.InterfaceGrafica.Tela;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class Main {

public static void main (String[] args) throws SQLException, ClassNotFoundException,NullPointerException {


    //É criado uma conexão com o MySQL
    Conexao conn = new Conexao();

    //Instaciada a Lista de aeroportos, a recebo a partir da conexão com o banco

    List<Aero> aeroList; //Declaração da Lista
    aeroList = conn.conectar(); //Atribuição da Lista de Aeroportos, a partir de dados do Banco

    //Como os Estados podem se repetir para mais Aeroportos, instancio esse vector a fim de
    //passar para o JComboBox um cada estado uma única vez
    Vector<String> Estados = new Vector<>();
    for (Aero p : aeroList) {
        if (!Estados.contains(p.getState())) {
            Estados.add(p.getState());
        }
    }
    //Todos os métodos estão dentro da interface gráfica, sendo chamados
    //por Listeners.
    new Tela(aeroList, Estados);
    }
}
