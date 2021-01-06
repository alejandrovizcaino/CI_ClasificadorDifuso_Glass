package algoritmoclasificacióndifuso;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class VariableEntrada {

    private String nombre;
    private double rangoInferior;
    private double rangoSuperior;
    private double grado_per;
    static int numEtiquetas;
    private final ArrayList<Etiqueta> etiquetas = new ArrayList();

    public void calculaTriangulos() {

        double mitad = (rangoSuperior + rangoInferior)/2;
        
        if (numEtiquetas == 3) {
            Etiqueta e = new Etiqueta();
            e.setNombre("Bajo");
            e.setRangoInferior(rangoInferior);
            e.setRangoSuperior(mitad);
            etiquetas.add(e);
            
            e = new Etiqueta();
            e.setNombre("Medio");
            e.setRangoInferior(rangoInferior);
            e.setRangoSuperior(rangoSuperior);
            etiquetas.add(e);
            
            e = new Etiqueta();
            e.setNombre("Alto");
            e.setRangoInferior(mitad);  
            e.setRangoSuperior(rangoSuperior);
            etiquetas.add(e);
        }

    }

    public double getGrado_per() {
        return grado_per;
    }

    public void setGrado_per(double grado_per) {
        this.grado_per = grado_per;
    }
    
    public int buscarMejorEtiqueta(double value){
        
        double max = 0.0;
        int etq = -1;
        double per;        
        //hay que calcular el per para cada etiqueta y quedarme con el per mayor        
        for (int j = 0; j < numEtiquetas; j++) {
                per = grado_pertenencia(value, j);
                if (per > max) {
                    max = per;
                    etq = j;
                }
            }
        grado_per = max;
        
        return etq;      
    }

    public boolean dentroDelRango(double val) {
        boolean dentro = true;
        if (val < etiquetas.get(0).getRangoInferior() || val > etiquetas.get(2).getRangoSuperior()) {
            dentro = false;
        }
        return dentro;
    }

    public double grado_pertenencia(double X, int etiqueta) { 
        double y = 1.0;
   
        if (etiqueta==0){
            
           double m = etiquetas.get(etiqueta).getRangoInferior();
           double b = etiquetas.get(etiqueta).getRangoSuperior();
           
            if (X <= m){
                y = 1.0;
            } 

            if(X > m  && X < b){
                y = (b - X) / (b - m);
            }     
        }    
        if (etiqueta==1){ //Etiqueta Media// Triangulo verde
            
            double a = etiquetas.get(etiqueta).getRangoInferior();
            double b = etiquetas.get(etiqueta).getRangoSuperior();
            double m = (a + b)/2;
            
            if (X <= m && X > a){

                y = (X - a)/(m - a);
            } // si x está entre rango inferior y la mitad (x - rangoinferior)/(mitad - rangoinferior)
        
            if (X > m && X < b){
                y = (b - X) / (b - m);
            } // si x esta entre la mitad y rango superior:  (rango superior - x)/(rango superior - mitad)
        }       
        if (etiqueta==2){
           double a = etiquetas.get(etiqueta).getRangoInferior();
           double m = etiquetas.get(etiqueta).getRangoSuperior();
           
           if (X >= m){
               y = 1.0;
           }
            
            if (X <= etiquetas.get(0).getRangoSuperior()){

                y = (X - a)/(m - a);
            }
        }
        return y;
    }

    public ArrayList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public static void setEtiquetas(int etiquetas) {
        numEtiquetas = etiquetas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getRangoInferior() {
        return rangoInferior;
    }

    public void setRangoInferior(double rangoInferior) {
        this.rangoInferior = rangoInferior;
    }

    public double getRangoSuperior() {
        return rangoSuperior;
    }

    public void setRangoSuperior(double rangoSuperior) {
        this.rangoSuperior = rangoSuperior;
    }

}
