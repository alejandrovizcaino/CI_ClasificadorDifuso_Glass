package algoritmoclasificacióndifuso;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class LineaDeDatos {

    private int numventrada;
    private int numvsalida;
    private ArrayList<Float> lineadatos = new ArrayList();

    public int getNumventrada() {
        return numventrada;
    }

    public void setNumventrada(int numventrada) {
        this.numventrada = numventrada;
    }

    public int getNumvsalida() {
        return numvsalida;
    }

    public void setNumvsalida(int numvsalida) {
        this.numvsalida = numvsalida;
    }

    public void cargarArray(ArrayList<Float> param) {

        for (int i = 0; i < param.size(); i++) {

            lineadatos.add(i, param.get(i));
        }

    }

    public double getPos(int i) {
        return lineadatos.get(i);
    }

    public float getSalida() {
        return lineadatos.get(numventrada);
    }

    public ArrayList<Float> getLineadatos() {
        return lineadatos;
    }

}
