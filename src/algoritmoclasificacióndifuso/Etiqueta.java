
package algoritmoclasificacióndifuso;

/**
 *
 * @author Alex
 */
public class Etiqueta {
    
    private String nombre;
    private double rangoSuperior;
    private double rangoInferior;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getRangoSuperior() {
        return  rangoSuperior;
    }

    public void setRangoSuperior(double rangoSuperior) {
        this.rangoSuperior = rangoSuperior;
    }

    public double getRangoInferior() {
        return rangoInferior;
    }

    public void setRangoInferior(double rangoInferior) {
        this.rangoInferior = rangoInferior;
    }

    
}
