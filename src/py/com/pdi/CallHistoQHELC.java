package py.com.pdi;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import py.com.pdi.QHELCprueba;

public class CallHistoQHELC{
	public static void main(String[] args) {
			// TODO Auto-generated method stub
			ImagePlus im= new ImagePlus("C:\\Users\\Familia Villegas\\Desktop\\2023\\DOCTORADO\\PDI 2\\Clases\\Images\\1.jpg");
			im.show();
			
			//ComputeHistogram ch= new ComputeHistogram();
			//Ejercicio1_T2 ch = new Ejercicio1_T2();
			//Ejercicio3_T2 ch = new Ejercicio3_T2();
			//ch.setup(null,im);
			
			ImageProcessor ip= im.getProcessor();
			
			//ch.run(ip);
			
		      // Para imagen con Math		
		    QHELCprueba ch1 = new QHELCprueba();
		    ch1.setup(null, im);
		    ch1.run(ip);
				

	}
}
