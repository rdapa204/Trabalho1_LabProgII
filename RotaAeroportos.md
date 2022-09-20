# Documentação

![**Instituto Militar de Engenharia** 
](Documentac%CC%A7a%CC%83o%2029141b3f34f0430ea662ea4d8eb9bd72/Untitled.png)

**Instituto Militar de Engenharia** 

### Al Paixão - 20060 - COMP24

## 1. Classes

Foram implementadas cinco classes:

- Conexão: responsável pela conexão com o MySQL usando JDBC Driver.
- Aero: utilizada para armazenar os dados dos aeroportos num objeto com o mesmos atributos que as colunas no Banco de Dados.
- Dijsktra: irá conter os métodos de construir a matriz de adjacência e calcular o caminho de custo mínimo dado o conjunto de entradas.
- Tela: implementa uma interface gráfica que será responsável por acionar os demais métodos a partir das seleções do usuária.
- Main: classe principal para rodar a rotina do código.

Contam com a seguinte organização:

![Untitled](Documentac%CC%A7a%CC%83o%2029141b3f34f0430ea662ea4d8eb9bd72/Untitled%201.png)

## 2. Métodos importantes

- Conexão com MySQL:

```java
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
```

- Salvar as consultas no MySQL:

```java
public void salvar_consultar(String origem,String destino,String percurso) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conectar = DriverManager.getConnection("jdbc:mysql://localhost/new_schema?user=root&password=mementomori");
        //Os parâmetros da função em questão são inseridas na query atráves da função StringFormat
        conectar.createStatement().execute(String.format("INSERT INTO consultas (origem,destino,escala) VALUES('%s','%s','%s');",origem,destino,percurso));
    }
```

- Algoritmo de Dijsktra:

```java
//A metodologia abaixo implementa DiJsktra, calculando a árvore de menor caminho, e retornando
    //um vetor que indica o pai de cada nó, a fim de refazer o caminho percorrido dada uma origem e um destino.
    public static int[] dijkstra(int V, ArrayList<ArrayList<AdjListNode>> graph, int src, int end) {
        //Vetor para identificar o pai de cada nó naquele percurso dado
        int[] parents = new int[V];
        //A origem não tem pai
        parents[src] = NO_PARENT;
        int[] distance = new int[V+1];
        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[src] = 0;

        //Fila de prioridade implementada para distingui a prioridade dos vértices para
        //encontrar o caminho de menor custo
        PriorityQueue<AdjListNode> pq = new PriorityQueue<>(
                (v1, v2) -> v1.getWeight() - v2.getWeight());
        pq.add(new AdjListNode(src, 0));

        while (pq.size() > 0) {
            AdjListNode current = pq.poll();
            for (AdjListNode n :
                    graph.get(current.getVertex())) {
               //Aqui é necessário que o vértice atual não seja o último vértice para garantir que
                // haverá pelo menos uma escala
                if (current.vertex != end) {
                    if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]) {
                        distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];
                        //Adiciona-se o pai daquele vértice na posição de mesmo índice do vértice no
                        //vetor parents
                        parents[n.vertex] = current.vertex;
                        pq.add(new AdjListNode(n.getVertex(), distance[n.getVertex()]));
                    }
                }
            }
        }
        return parents;
    }
```

## 3. Interface Gráfica

- Menu com estados e municípios para facilitar navegabilidade do usuário.

![Untitled](Documentac%CC%A7a%CC%83o%2029141b3f34f0430ea662ea4d8eb9bd72/Untitled%202.png)

Ao pressionar o botão “Ir”, o percurso é calculado e indicado para o usuário, ademais ao final da aplicação a consulta é armazenada no banco de dados.

![Untitled](Documentac%CC%A7a%CC%83o%2029141b3f34f0430ea662ea4d8eb9bd72/Untitled%203.png)

![Untitled](Documentac%CC%A7a%CC%83o%2029141b3f34f0430ea662ea4d8eb9bd72/Untitled%204.png)

## 4. MySQL

O Banco de dados utilizado para as aplicações, consta com o seguinte formato:

![Untitled](Documentac%CC%A7a%CC%83o%2029141b3f34f0430ea662ea4d8eb9bd72/Untitled%205.png)