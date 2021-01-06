package algoritmoclasificacióndifuso;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class VariableSalida {

    private String nombre;
    private int numValores;
    private final ArrayList valores = new ArrayList();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumValores() {
        return numValores;
    }

    public void setNumValores(int numValores) {
        this.numValores = numValores;
    }

    public ArrayList getValores() {
        return valores;
    }
    
    public void cargarArray(ArrayList param) {

        for (int i = 0; i < param.size(); i++) {

            valores.add(i, param.get(i));
        }
    }

}
