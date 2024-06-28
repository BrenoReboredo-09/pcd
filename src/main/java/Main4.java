public class Main4 {
    public static void main(String args[]) throws InterruptedException {
    	int quantidadeDeThreads = 27;
    	int capitaisPorThreads = 1;
    	
    	Cronometro cronometro = new Cronometro();
    	cronometro.iniciar();
    	
    	for (int p = 0; p < 10; p++) {
    		Servico[] servicos = new Servico[quantidadeDeThreads];
        	
        	for (int i = 0; i < quantidadeDeThreads; i++) {
        		servicos[i] = new Servico("#" + (i + 1), capitaisPorThreads, i * capitaisPorThreads);
        		servicos[i].start();
        	}
        	
        	for (Servico servico : servicos) { servico.join(); }
    	}
    	
    	cronometro.parar();
    	System.out.println("Tempo de execução: " + (cronometro.getResultado() / 1000) + " segundos");
    }
}