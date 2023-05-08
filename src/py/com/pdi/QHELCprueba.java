package py.com.pdi;

import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;



import ij.*;
import ij.gui.*;
import ij.plugin.filter.*;
import ij.process.*;

public class QHELCprueba implements PlugInFilter {
    private ImagePlus imp;

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        // Calcular el histograma
        int[] histograma = ip.getHistogram();

        // Calcular el número de píxeles en la imagen
        int n = ip.getPixelCount();

        // Calcular los límites de los 4 subhistogramas
        int[] limites = new int[5];
        limites[0] = 0;
        limites[4] = 255;
        for (int i = 1; i < 4; i++) {
            int sum = 0;
            int j = limites[i - 1] + 1;
            while (sum < n / 4 && j <= 255) {
                sum += histograma[j];
                j++;
            }
            limites[i] = j - 1;
        }
        
		//int K= histograma.length;
		//	
		System.out.println(Arrays.toString(histograma));

        // Crear las imágenes para los subhistogramas
        ImagePlus[] subimagenes = new ImagePlus[4];
        for (int i = 0; i < 4; i++) {
            subimagenes[i] = NewImage.createByteImage("Subhistograma " + (i + 1), ip.getWidth(), ip.getHeight(), 1, NewImage.FILL_BLACK);
        }

        // Segmentar el histograma en los 4 subhistogramas
        for (int y = 0; y < ip.getHeight(); y++) {
            for (int x = 0; x < ip.getWidth(); x++) {
                int valor = ip.get(x, y);
                if (valor <= limites[1]) {
                    subimagenes[0].getProcessor().set(x, y, valor);
                } else if (valor <= limites[2]) {
                    subimagenes[1].getProcessor().set(x, y, valor);
                } else if (valor <= limites[3]) {
                    subimagenes[2].getProcessor().set(x, y, valor);
                } else {
                    subimagenes[3].getProcessor().set(x, y, valor);
                }
            }
        }

        // Mostrar las imágenes de los subhistogramas
        for (int i = 0; i < 4; i++) {
            subimagenes[i].show();
        }
    }
}
