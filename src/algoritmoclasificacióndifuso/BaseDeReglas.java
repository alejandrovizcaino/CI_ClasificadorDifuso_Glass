package algoritmoclasificacióndifuso;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Alex
 */
public class BaseDeReglas {

    BaseDeDatos bd;
    ArrayList<Regla> reglas = new ArrayList();
    int operadorImplicacion;

    public BaseDeReglas(BaseDeDatos bd) {
        this.bd = bd;
    }

    public void setOperadorImplicacion(int operadorImplicacion) {
        this.operadorImplicacion = operadorImplicacion;
    }

    public boolean duplicado(Regla r) {
        int i = 0;
        boolean found = false;
        while ((i < reglas.size()) && (!found)) {
            found = reglas.get(i).existeRegla(r);
            i++;
        }
        return found;
    }

    public void generarReglas(ArrayList<LineaDeDatos> train) {

        System.out.println("train size es " + train.size());

        for (int i = 0; i < train.size(); i++) {

            Regla r = bd.fuzzificar(train, i);

            if (!duplicado(r)) {

                reglas.add(r);
            }
        }

    }

    public void mostrarReglas_Detallado() {

        if (reglas.isEmpty()) {
            System.out.println("La base de reglas está vacía");
        } else {
            System.out.println("Hay " + reglas.size() + " reglas");
            for (int i = 0; i < reglas.size(); i++) {
                System.out.print("Regla " + (i + 1) + ": ");
                for (int j = 0; j < reglas.get(i).antecedentes.size(); j++) {

                    if (!reglas.get(i).antecedentes.get(j).getE().equals("nulo")) {
                        System.out.print(reglas.get(i).antecedentes.get(j).getNombreVariable() + "(" + reglas.get(i).antecedentes.get(j).getE() + " "
                                + reglas.get(i).antecedentes.get(j).getGrado_per()
                                + "). ");
                    }
                }
                System.out.print("--> " + reglas.get(i).getSalida() + " Peso: " + reglas.get(i).pesoRegla);
                System.out.println("");
            }
        }

    }
    
    public void mostrarReglas_Simple(){
        
        if (reglas.isEmpty()) {
            System.out.println("La base de reglas está vacía");
        } else {
            System.out.println("Hay " + reglas.size() + " reglas");
            for (int i = 0; i < reglas.size(); i++) {
                System.out.print("R" + (i + 1) + ": ");
                for (int j = 0; j < reglas.get(i).antecedentes.size(); j++) {

                    if (!reglas.get(i).antecedentes.get(j).getE().equals("nulo")) {
                        System.out.print(" "+reglas.get(i).antecedentes.get(j).getE());
                    }
                }
                System.out.print("--> " + reglas.get(i).getSalida() + " Peso: " + reglas.get(i).pesoRegla);
                System.out.println("");
            }
        }
        
    }

    /*
    d) Calcular el Grado de Certeza de cada regla generada mediante
    el cociente:
    
    ri= Sj / S 
    i = 1, ..., L (#reglas), j = 1, ..., M (#clases)
    Donde:
    
    – Sj es la suma del grado de pertenencia de los ejemplos de
    entrenamiento de la clase Cj a la región difusa determinada
    por los antecedentes de la regla
    
    – S es la suma del grado de pertenencia a la misma región
    de todos los ejemplos de cualquier clase

     
    Regla 1: RI(Bajo 1.0). Na(Medio 0.90). Mg(Bajo 1.0). Al(Bajo 0.862). --> 1.0

    Regla 2: RI(Medio 0.86). Na(Medio 0.87). Mg(Bajo 1.0). Al(Medio 0.503).  --> 2.0

    Regla 3: RI(Medio 0.55). Na(Medio 0.62). Mg(Bajo 1.0). Al(Medio 0.748).  --> 1.0

    Regla 4: RI(Medio 0.96). Na(Bajo 1.0). Mg(Bajo 1.0). Al(Bajo 0.88).  --> 1.0

    sumar solo que coincida Elemento y grado_per con la regla en cuestion
    s_1 (primera regla) =1+(0.9 +0.87...+0.62...)+(1.0+1.0+1.0+1.0+1.0)+(0.862+0.88..)+...
           
    Sj:
    1- buscar reglas que tengan misma salida
    2- sumar grado_per de regla con esas otras reglas si coincide con (etiqueta, grado_per) de la regla que estamos comparando).
    
    S: suma de todas las Sj*/
    
    public void calcularGradoCerteza() {

        ArrayList sumatotal = new ArrayList(reglas.size());
        ArrayList sj = new ArrayList(reglas.size());

        for (int i = 0; i < reglas.size(); i++) { //para iterar cada Sj

            sj.add(i, 0.0);
            sumatotal.add(i, 0.0);

            for (int j = 0; j < reglas.size(); j++) { //para buscar otras reglas con misma salida

                for (int k = 0; k < bd.getNumVariablesEntrada(); k++) {

                    //Aqui vamos sumando todos los valores, es decir, la S
                    double suma = (double) sumatotal.get(i) + reglas.get(i).antecedentes.get(k).grado_per;
                    sumatotal.set(i, suma);

                    if (reglas.get(i).antecedentes.get(k).etq.equals(reglas.get(j).antecedentes.get(k).etq)) {
                        suma = (double) sumatotal.get(i) + reglas.get(j).antecedentes.get(k).grado_per;
                        sumatotal.set(i, suma);
                    }

                    if ((i != j) && (reglas.get(i).getSalida() == reglas.get(j).getSalida())) { // en este if solo sumamos que coincida la clase, es decir, el Sj

                        double sum = (double) sj.get(i) + reglas.get(i).antecedentes.get(k).grado_per;
                        sj.set(i, sum);

                        if (reglas.get(i).antecedentes.get(k).etq.equals(reglas.get(j).antecedentes.get(k).etq)) {
                            sum = (double) sj.get(i) + reglas.get(j).antecedentes.get(k).grado_per;
                            sj.set(i, sum);
                        }
                    }

                }

            }

        }

        for (int i = 0; i < reglas.size(); i++) {
            //System.out.println("sumaTotal de " + i + " es: " + sumatotal.get(i));
            //System.out.println("sj(" + i + ") es " + (double) sj.get(i));
            double peso = (double) sj.get(i) / (double) sumatotal.get(i);
            reglas.get(i).setPeso(peso);

        }

    }

    public void procesoInferencia(ArrayList<LineaDeDatos> test, int tipo_empar, int tipo_pond) { //este array tiene en cada linea un ejemplo con sus 9 valores de entrada y la salida.

        double[][] g_asociacion = new double[test.size()][reglas.size()];
        ArrayList ganadoras = new ArrayList();
        int acertadas = 0;

        for (int i = 0; i < test.size(); i++) {

            double max = 0.0;
            int indice = -1;

            //fuzzificamos el ejemplo i
            ArrayList<Antecedente> temp = bd.fuzzificar(test.get(i));

            for (int j = 0; j < reglas.size(); j++) {

                //comparamos etiquetas en un nuevo bucle con las etiquetas de los antecedentes de la regla rj
                boolean iguales = true;
                int k = 0;
                double min = 1.0;

                while (iguales && k < temp.size()) {
                    if (!temp.get(k).etq.equals(reglas.get(j).antecedentes.get(k).etq)) {
                        iguales = false;
                        min = 0.0;// si no coinciden el T_MIN es 0
                    } else {
                        if (tipo_empar==0){
                            if (temp.get(k).grado_per < min) {
                                min = temp.get(k).grado_per;
                                // si coinciden calculamos el T_MIN de los matching de cada antecedente
                            }
                        }
                        else if (tipo_empar==1){
                            min = min * temp.get(k).grado_per;
                        }
                    }
                    k++;
                }

                //Regla Ganadora
                //calcular grado de asociación
                //producto algebraico entre el hi calculado antes y el ri(peso de la regla ri)
                if (tipo_pond==1){
                    g_asociacion[i][j] = min * reglas.get(j).pesoRegla;
                }
                else if (tipo_pond==0){
                    if (min < reglas.get(j).pesoRegla){
                        g_asociacion[i][j] = min;
                    }
                    else if (reglas.get(j).pesoRegla < min){
                        g_asociacion[i][j] = reglas.get(j).pesoRegla; 
                    }
                }
                if (g_asociacion[i][j] > max) {
                    max = g_asociacion[i][j];
                    indice = j;
                }
            }
            
            if (indice == -1) {
                System.out.println("No hay ninguna regla que coincida con el ejemplo " + i);
            } else {
                
                
                System.out.print("La regla ganadora para el ejemplo " + i + ": ");
                for (int j = 0; j < test.get(i).getNumventrada(); j++) {
                   System.out.print("("+temp.get(j).getNombreVariable() + " " + test.get(i).getLineadatos().get(j) + " " + temp.get(j).etq + "). ");
                  
                }
                System.out.println(") es la regla " + (indice + 1) + ", con GA=" + g_asociacion[i][indice]);
                 if (test.get(i).getSalida()==reglas.get(indice).getSalida()){
                       System.out.println(test.get(i).getSalida()+" es igual a "+reglas.get(indice).getSalida());
                       acertadas++;
                   }
                   else System.out.println(test.get(i).getSalida()+" no es igual a "+reglas.get(indice).getSalida());
                if (!ganadoras.contains(indice)){
                    ganadoras.add(indice);
                }
                
            }
            
        }
        int tam = test.size();
        float div = acertadas*100/tam;
        System.out.println("El porcentaje de acierto es: "+acertadas+"/"+tam+" = "+ div+"%");
        System.out.println("Las reglas ganadoras para este conjunto de test son: ");
        Collections.sort(ganadoras);
        for (int i = 0; i < ganadoras.size(); i++) {
            System.out.print("Regla "+ganadoras.get(i)+", ");
        }
        System.out.println("");

        //nos quedamos con el máximo de cada ejemplo
        //si hay 47 ejemplos saldrán 47 reglas ganadoras como máximo. 
    }

}
