package main.InterfaceGrafica;

import main.Aeroportos.Aero;
import main.Dijsktra.Dijsktra;
import main.MySQL.Conexao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

//Aqui é feita a implementação da interface gráfica do programa
//Assim as seleções feitas na interface gráfica são os estopins que acionam a rotina restante dos programas
public class Tela {
    private final JTextField aero_origem = new JTextField();
    private final JTextField aero_destino = new JTextField();

    private static List<Integer> path ;
    private static int id_origem=-1;
    private static int id_destino=-1;
    private final JComboBox<String> Cidades_origem = new JComboBox<>();
    private final JComboBox<String> Estados_origem = new JComboBox<>();
    private final JComboBox<String> Cidades_destino = new JComboBox<>();
    private final JComboBox<String> Estados_destino = new JComboBox<>();

    private final List<Aero> aeroList;
    private final JFrame tela = new JFrame("Companhia Aerea");
    public Tela(List<Aero> aeroList, Vector<String> estados){

        //Declaração do Título e tornamos ele não editável
        JTextField Cabecalho =  new JTextField("ROTA:");
        Cabecalho.setEditable(false);

        //Como a lista de aeroporto é usada ao longo de todas as aplicações, aqui a definimos como uma variável estática
        //para que possamos referencia-la atráves de toda a classe sem nos preocupar em implementar uma funcionalidade
        //getter para esse atributo
        this.aeroList=aeroList;

        //Definição de configurações de tela que irá conter os demais componentes
        tela.setSize(400,480);
        //Fecha a aplicação quando a janela é fechada
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(null);
        tela.setLocationRelativeTo(null);
        JButton go = new JButton("Ir");

        //Declaração das legendas para indicar ao usuário como proceder
        JLabel legenda1 =  new JLabel("Insira o Estado e Cidade de origem");
        JLabel legenda2 =  new JLabel("Insira o Estado e Cidade de destino");


        //Definição de localização dos componentes na tela
        go.setBounds(100,320,100,50);

        Cabecalho.setBounds(100,25,100,50);

        legenda1.setBounds(100,80,180,20);
        Estados_origem.setBounds(100,100,150,20);
        Cidades_origem.setBounds(100,125,150,20);
        aero_origem.setBounds(100,150,150,20);

        legenda2.setBounds(100,200,180,20);
        Estados_destino.setBounds(100,225,150,20);
        Cidades_destino.setBounds(100,250,150,20);
        aero_destino.setBounds(100,275,150,20);

        //Definição do formato da fonte
        legenda1.setFont(new Font("Times New Roman",Font.BOLD,15));
        legenda2.setFont(new Font("Times New Roman",Font.BOLD,15));
        Cabecalho.setFont(new Font("Times New Roman",Font.BOLD,22));
        aero_origem.setFont(new Font("Times New Roman",Font.BOLD,15));
        aero_destino.setFont(new Font("Times New Roman",Font.BOLD,15));

        //Aqui para a JComboBox é adicionado os itens que ,,

        for (String str:estados){
            Estados_origem.addItem(str);
            Estados_destino.addItem(str);
        }
        aero_destino.setEditable(false);
        aero_origem.setEditable(false);
        Estados_origem.addActionListener(this::setCidades_origem);
        Estados_destino.addActionListener(this::setCidades_destino);
        go.addActionListener(this::caminho);
        Cidades_origem.addActionListener(this::setid_origem);
        Cidades_destino.addActionListener(this::setId_destino);


        //Adiciono na tela, os seus componentes
        tela.add(Cabecalho);
        tela.add(aero_destino);
        tela.add(aero_origem);
        tela.add(Cidades_origem);
        tela.add(Estados_origem);
        tela.add(Cidades_destino);
        tela.add(Estados_destino);
        tela.add(go);
        tela.add(legenda1);
        tela.add(legenda2);

        tela.setVisible(true);
    }
    private void setid_origem(ActionEvent actionEvent) {
        for (Aero p: aeroList){
            if (p.getCity().equals(Cidades_origem.getSelectedItem())){
                id_origem= p.getId()-1;
                aero_origem.setText(p.getIata());
            }
        }
    }
    private void setId_destino(ActionEvent actionEvent) {
        for (Aero p: aeroList){
            if (p.getCity().equals(Cidades_destino.getSelectedItem())){
                id_destino= p.getId()-1;
                aero_destino.setText(p.getIata());
            }
        }
    }
    private void caminho(ActionEvent actionEvent)  {
        Dijsktra caminho = new Dijsktra();
        path = caminho.graph((ArrayList<Aero>) aeroList,id_origem,id_destino);
        JOptionPane.showMessageDialog(null,aeroList.get(path.get(0)).getIata()+"->"+aeroList.get(path.get(1)).getIata()+"->"+aeroList.get(path.get(2)).getIata(),"MENOR ROTA",JOptionPane.WARNING_MESSAGE);
        try {
            fechar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fechar() throws SQLException, ClassNotFoundException {
            Conexao conn = new Conexao();
            conn.salvar_consultar(aeroList.get(path.get(0)).getIata(), aeroList.get(path.get(2)).getIata(), aeroList.get(path.get(1)).getIata());
            tela.dispose();
    }

    private void setCidades_origem(ActionEvent actionEvent) {
        Cidades_origem.removeAllItems();
        for (Aero p : aeroList) {
            if (Objects.requireNonNull(Estados_origem.getSelectedItem()).equals(p.getState())) {
                Cidades_origem.addItem(p.getCity());
            }
        }
    }
    private void setCidades_destino (ActionEvent actionEvent){
        Cidades_destino.removeAllItems();
        for (Aero p : aeroList) {
            if (Objects.equals(Estados_destino.getSelectedItem(), p.getState())) {
                Cidades_destino.addItem(p.getCity());
            }
        }


    }

}
