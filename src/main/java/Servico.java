import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class Servico extends Thread{
	
	public String nome;
	public int quantas;
	public int deOnde;
	
	public Servico (String Nome, int Quantas, int DeOnde) {
		nome = Nome;
		quantas = Quantas;
		deOnde = DeOnde;
	}
	
    public void run() {   	
    	try {
	    	String[][] pedaco = new String[quantas][3];
	    	
	    	for (int i = 0; i < quantas; i++) {
	    		pedaco[i] = coordenadas[deOnde + i];
	    	}
	    	
	    	for (String[] coordenada : pedaco) {
	    		String latitude = coordenada[1];
	        	String longitude = coordenada[2];
	        	
	        	var request = HttpRequest.newBuilder();
	        	request.uri(new URI("https://historical-forecast-api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&start_date=2024-01-01&end_date=2024-01-31&hourly=temperature_2m&daily=temperature_2m_max,temperature_2m_min&timezone=America%2FSao_Paulo")).GET();
	        	
	        	HttpResponse<String> dadosClimaticos = HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
	        	
	        	Dados dados = new Gson().fromJson(dadosClimaticos.body(), Dados.class);
	        	
	        	String printFinal = nome + ": " + coordenada[0] + "\n"; //isso será impresso no final, para não conflitar com as outras threads
	        	for (int i = 0; i < dados.daily.time.length; i++) {
	        		float media = 0;
	        		float total = 0;
	        		
	        		for (int c = 0; c < dados.hourly.time.length; c++) {
	        			if (dados.hourly.time[c].contains(dados.daily.time[i] + "T")) {
	        				total += dados.hourly.temperature_2m[c];
	        			}
	        		}
	        		
	        		media = total / 24;
	        		printFinal +="Dia " + dados.daily.time[i].substring(8) + " | "
	        		+ "Max " + dados.daily.temperature_2m_max[i] + "°C | "
	        		+ "Min " + dados.daily.temperature_2m_min[i] + "°C | "
	        		+ "Med " + media + "°C\n";
	        	}
	        	System.out.println(printFinal);
	    	} 
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static String[][] coordenadas = {
	    {"Aracaju", "-10.9167", "-37.05"},
	    {"Belém", "-1.4558", "-48.5039"},
	    {"Belo Horizonte", "-19.9167", "-43.9333"},
	    {"Boa Vista", "2.81972", "-60.67333"},
	    {"Brasília", "-15.7939", "-47.8828"},
	    {"Campo Grande", "-20.44278", "-54.64639"},
	    {"Cuiabá", "-15.5989", "-56.0949"},
	    {"Curitiba", "-25.4297", "-49.2711"},
	    {"Florianópolis", "-27.5935", "-48.55854"},
	    {"Fortaleza", "-3.7275", "-38.5275"},
	    {"Goiânia", "-16.6667", "-49.25"},
	    {"João Pessoa", "-7.12", "-34.88"},
	    {"Macapá", "0.033", "-51.05"},
	    {"Maceió", "-9.66583", "-35.73528"},
	    {"Manaus", "-3.1189", "-60.0217"},
	    {"Natal", "-5.7833", "-35.2"},
	    {"Palmas", "-10.16745", "-48.32766"},
	    {"Porto Alegre", "-30.0331", "-51.23"},
	    {"Porto Velho", "-8.76194", "-63.90389"},
	    {"Recife", "-8.05", "-34.9"},
	    {"Rio Branco", "-9.97472", "-67.81"},
	    {"Rio de Janeiro", "-22.9111", "-43.2056"},
	    {"Salvador", "-12.9747", "-38.4767"},
	    {"São Luís", "-2.5283", "-44.3044"},
	    {"São Paulo", "-23.55", "-46.6333"},
	    {"Teresina", "-5.08917", "-42.80194"},
	    {"Vitória", "-20.2889", "-40.3083"}
	};
}