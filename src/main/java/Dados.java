public class Dados {

	public Hourly hourly;
	public Daily daily;		
	
	public class Daily {
		public String[] time;
		public float[] temperature_2m_max;
		public float[] temperature_2m_min;
	}

	public class Hourly {
		public String[] time;
		public float[] temperature_2m;
	}
	
}
