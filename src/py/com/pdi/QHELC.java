package py.com.pdi;

import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;


public class QHELC implements PlugInFilter {
	ImagePlus imp;
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int[] h = new int[256];
		//int[] h_mod = new int[256];
		int M = ip.getWidth();
		int N = ip.getHeight();
		int i1= 0, i2=0, i3=0, i4=0;
		int c1=0, c2=0, c3=0, c4=0;
		int prom1, prom2, prom3, prom4;
		for(int v = 0; v < M; v++){
			for(int u = 0; u < N; u++){
				int i = ip.getPixel(u, v);
				if (i>=0 && i<=63){
					i1= i1 + i;
					c1= c1+1;
				}
				else if (i>=64 && i<=128){
					i2= i2 + i;
					c2= c2+1;
				}
				else if (i>=129 && i<=191){
					i3= i3 + i;
					c3= c3+1;
				}
				else if (i>=192 && i<255){
					i4= i4 + i;
					c4= c4+1;
				}
			}
		}
		prom1= (int) (1.0*i1/c1);
		prom2= (int) (1.0*i2/c2);
		prom3= (int) (1.0*i3/c3);
		prom4= (int) (1.0*i4/c4);
		//System.out.print("Prueba");
		//System.out.print(prom4);
		//System.out.print(prom3);
		//System.out.print(prom4);
		
		for(int v = 0; v < M; v++){
			for(int u = 0; u < N; u++){
				int i = ip.getPixel(u, v);
				if (i>=0 && i<=63){
					if (i>prom1){
						ip.putPixel(u, v, prom1);
						i = ip.getPixel(u, v);						
					}
				}
				else if (i>=64 && i<=128){
					if (i>prom2){
						ip.putPixel(u, v, prom2);
						i = ip.getPixel(u, v);						
					}
				}
				else if (i>=129 && i<=191){
					if (i>prom3){
						ip.putPixel(u, v, prom3);
						i = ip.getPixel(u, v);						
					}
				}
				else if (i>=192 && i<=255){
					if (i>255){
						ip.putPixel(u, v, 255);
						i = ip.getPixel(u, v);						
					}
				}
				h[i] = h[i] + 1;
			}
		}
				
		//System.out.println(Arrays.toString(h));
		// ... histogram h can now be used

		// create the histogram image:
		ImageProcessor hip = new ByteProcessor(255, 100);
		hip.setValue(255); // white = 255
		hip.fill();

		// draw the histogram values as black bars in hip here,
		// for example, using hip.putpixel(u, v, 0)
		// ...
		
		//Obtener la frecuencia mas alta del vector h
		int ma = maximo(h);
		for(int x = 0; x < 256; x++){
			int esc = (h[x]*100)/ma;
			for(int y = 0; y<=esc; y++){
				hip.putPixel(x, 100-y, 0);
			}
		}
		
		// compose a nice title:
		String imTitle = imp.getShortTitle();
		String histTitle = "Histo de " + imTitle;

		// display the histogram image:
		ImagePlus him = new ImagePlus(histTitle, hip);
		him.show();
	}
	
	private int maximo(int[] H){
		int maxi = H[0];
		int ta = H.length; //Tamanho del vector o arreglo unidimensional
		for(int t = 0; t < ta; t++){  //Recorrer mi vector
			if(H[t]>maxi){
				maxi = H[t];
			}
		}
		return maxi;
	}
}
