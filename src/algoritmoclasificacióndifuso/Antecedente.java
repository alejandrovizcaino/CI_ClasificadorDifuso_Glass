package algoritmoclasificacióndifuso;

/**
 *
 * @author Alex
 */
public class Antecedente {

    String etq;
    String nombreVariable;
    double grado_per;

    public Antecedente(String str, String nombreVariable) {
        this.etq = str;
        this.nombreVariable = nombreVariable;
    }

    public double getGrado_per() {
        return grado_per;
    }

    public void setGrado_per(double grado_per) {
        this.grado_per = grado_per;
    }

    public String getE() {
        return etq;
    }

    public void setE(String e) {
        this.etq = e;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

}
