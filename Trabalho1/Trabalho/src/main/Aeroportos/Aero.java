package main.Aeroportos;


//Classe para abstrair o conceito de Aeroporto, e armazenar de forma conjunta todas as suas informações
public class Aero  {
    //Como todos atributos são simplesmente colados do MySQL, eles são todos final
    //Pois não haverá necessidade de alteração
    private final int id;
    private final String iata;
    private final String State;
    private final String city;
    private final double latitude;
    private final double longitude;

    //Construtor
    public Aero(int id, String iata, String state,String city, double latitude, double longitude) {
        this.id = id;
        this.iata = iata;
        State = state;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    //Métodos getters dos atributos do objetos
    public int getId() {
        return id;
    }
    public String getIata() {
        return iata;
    }

    public String getState() {
        return State;
    }


    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }
    public String getCity() {
        return city;
    }

    //Fórmula que calcula distância entre dois aeroportos com base em suas diferenças de latitude
    // e  longitude
   public double calcular_distancia(Aero A){
        if(this == A){
            return 0;
        }
        double Raio = 6378.1,dist;
        double latA = Math.toRadians(A.getLatitude());
        double latB = Math.toRadians(this.getLatitude());
        double lonA = Math.toRadians(A.getLongitude());
        double lonB = Math.toRadians(this.getLongitude());
        double a = Math.sin((latA-latB)/2);
        double b = Math.cos(latA)*Math.cos(latB);
        double c = Math.sin((lonA-lonB)/2);
        dist = 2*Raio*Math.asin(Math.sqrt(a*a+b*c*c));

        return  dist;
   }



}
