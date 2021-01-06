package algoritmoclasificacióndifuso;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Regla {

    ArrayList<Antecedente> antecedentes;
    double pesoRegla;
    double salida;

    Regla(int numVariablesEntrada) {
        antecedentes = new ArrayList(numVariablesEntrada);
    }

    public void setAntecedente(Antecedente a) {
        antecedentes.add(a);
    }

    public boolean existeRegla(Regla r) {
        int contador = 0;

        for (int j = 0; j < this.antecedentes.size(); j++) {
          if (this.antecedentes.get(j).getE().equals(r.antecedentes.get(j).getE())) {
                //System.out.println("son iguales");

                contador++;

            }
        }
        if (contador == antecedentes.size()&& this.salida == r.salida) {
            return true;
        }
        return false;
    }

    public double getSalida() {
        return salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    void setPeso(double pesoRegla) {
        this.pesoRegla = pesoRegla;
    }

    public double getPesoRegla() {
        return pesoRegla;
    }
    
    

}
