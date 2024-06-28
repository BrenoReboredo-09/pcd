public class Cronometro {
	private long tempoIniciado;
	private long tempoCronometrado;
	
	public void iniciar() {
		tempoIniciado = System.currentTimeMillis();
	}
	
	public void parar() {
		tempoCronometrado = System.currentTimeMillis() - tempoIniciado;
	}
	
	public long getResultado() {
		return tempoCronometrado;
	}
}
