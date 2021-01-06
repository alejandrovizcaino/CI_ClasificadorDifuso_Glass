package algoritmoclasificacióndifuso;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alex
 */
public class BaseDeDatos {

    private String nombre;
    private int numEjemplos;
    private int numVariablesEntrada;
    private int numVariablesSalida;
    private static int numEtiquetas;
    private final ArrayList<VariableEntrada> var_entrada = new ArrayList();
    private final ArrayList<VariableSalida> var_salida = new ArrayList();
    private final ArrayList<LineaDeDatos> ejemplos = new ArrayList();
    private ArrayList<LineaDeDatos> train_set = new ArrayList();
    private ArrayList<LineaDeDatos> test_set = new ArrayList();

    public Regla fuzzificar(ArrayList<LineaDeDatos> ln, int pos) { //ln los ejemplos del dataset elegidos (4/5 partes del training)

        Regla r = new Regla(numVariablesEntrada);

        for (int j = 0; j < numVariablesEntrada; j++) {  //hay que recorrer cada variable entrada de cada ejemplo. 

            int etq = var_entrada.get(j).buscarMejorEtiqueta(ln.get(pos).getPos(j));
            String e = "";
            if (etq == -1) {
                e = "nulo";
            } else {
                e = var_entrada.get(j).getEtiquetas().get(etq).getNombre();
            }
            Antecedente a = new Antecedente(e, var_entrada.get(j).getNombre()); //antecedente: etiqueta y nombrevariable
            a.setGrado_per(var_entrada.get(j).getGrado_per());
            r.setAntecedente(a);
            r.setSalida(ln.get(pos).getSalida());
        }
        return r;
    }

    public ArrayList<Antecedente> fuzzificar(LineaDeDatos ld) {

        ArrayList<Antecedente> result = new ArrayList();

        for (int j = 0; j < numVariablesEntrada; j++) {  //hay que recorrer cada variable entrada de cada ejemplo. 

            int etq = var_entrada.get(j).buscarMejorEtiqueta(ld.getPos(j));
            String e = "";
            if (etq == -1) {
                e = "nulo";
            } else {
                e = var_entrada.get(j).getEtiquetas().get(etq).getNombre();
            }
            Antecedente a = new Antecedente(e, var_entrada.get(j).getNombre()); //antecedente: etiqueta y nombrevariable
            a.setGrado_per(var_entrada.get(j).getGrado_per());
            result.add(a);

        }

        return result;
    }

    public ArrayList<LineaDeDatos> getTrainSet() {
        return train_set;
    }

    public ArrayList<LineaDeDatos> getTestSet() {
        return test_set;
    }

    public void getParticiones() {

        int num = numEjemplos / 5;
        // Partición 1
        int grupo1_inf = 0;
        int grupo1_sup = num - 1;
        // Partición 2
        int grupo2_inf = grupo1_sup + 1;
        int grupo2_sup = grupo2_inf + num;
        // Partición 3
        int grupo3_inf = grupo2_sup + 1;
        int grupo3_sup = grupo3_inf + num;
        // Partición 4
        int grupo4_inf = grupo3_sup + 1;
        int grupo4_sup = grupo4_inf + num;
        // Partición 5
        int grupo5_inf = grupo4_sup + 1;
        int grupo5_sup = grupo5_inf + num;

        Random random = new Random();
        ArrayList<Integer> milista = new ArrayList();
        for (int i = 0; i < 5; i++) {
            milista.add(i, i + 1);
        }
        int numero_rng = 0, contador = 0, inicial = 0, ultimo = 0;

        while (contador < 4) {

            numero_rng = milista.remove(random.nextInt(milista.size()));
            //System.out.println("RNG number= " + numero_rng);

            switch (numero_rng) {
                case 1:
                    inicial = grupo1_inf;
                    ultimo = grupo1_sup;
                    break;
                case 2:
                    inicial = grupo2_inf;
                    ultimo = grupo2_sup;
                    break;
                case 3:
                    inicial = grupo3_inf;
                    ultimo = grupo3_sup;
                    break;
                case 4:
                    inicial = grupo4_inf;
                    ultimo = grupo4_sup;
                    break;
                case 5:
                    inicial = grupo5_inf;
                    ultimo = grupo5_sup;
                    break;
                default:
                    break;
            }

            for (int i = inicial; i < ultimo; i++) {
                LineaDeDatos ln = new LineaDeDatos();
                ln.setNumventrada(ejemplos.get(i).getNumventrada());
                ln.setNumvsalida(ejemplos.get(i).getNumvsalida());
                ln.cargarArray(ejemplos.get(i).getLineadatos());
                train_set.add(train_set.size(), ln);
            }
            contador++;

        }
        System.out.println("el test set es: " + milista.get(0));
        switch (milista.get(0)) {
            case 1:
                inicial = grupo1_inf;
                ultimo = grupo1_sup;
                break;
            case 2:
                inicial = grupo2_inf;
                ultimo = grupo2_sup;
                break;
            case 3:
                inicial = grupo3_inf;
                ultimo = grupo3_sup;
                break;
            case 4:
                inicial = grupo4_inf;
                ultimo = grupo4_sup;
                break;
            case 5:
                inicial = grupo5_inf;
                ultimo = grupo5_sup;
                break;
            default:
                break;
        }

        for (int i = inicial; i < ultimo; i++) {
            LineaDeDatos ln = new LineaDeDatos();
            ln.setNumventrada(ejemplos.get(i).getNumventrada());
            ln.setNumvsalida(ejemplos.get(i).getNumvsalida());
            ln.cargarArray(ejemplos.get(i).getLineadatos());
            test_set.add(test_set.size(), ln);
        }
    }

    public void mostrarVariablesEntrada() {

        for (int i = 0; i < numVariablesEntrada; i++) {
            var_entrada.get(i).calculaTriangulos();
            ArrayList<Etiqueta> etiquetas = var_entrada.get(i).getEtiquetas();

            System.out.println("Nombre: " + var_entrada.get(i).getNombre() + " - Rangos ["
                    + var_entrada.get(i).getRangoInferior() + ", " + var_entrada.get(i).getRangoSuperior() + "] - ");
            for (int j = 0; j < numEtiquetas; j++) {
                System.out.println("\t" + etiquetas.get(j).getNombre() + " [" + etiquetas.get(j).getRangoInferior() + ", " + etiquetas.get(j).getRangoSuperior() + "]");
            }
        }
    }

    public void mostrarVariablesSalida() {

        for (int i = 0; i < numVariablesSalida; i++) {

            System.out.print("Nombre: " + var_salida.get(i).getNombre() + " {");
            ArrayList temp = var_salida.get(i).getValores();

            for (int j = 0; j < temp.size(); j++) {
                if (j != temp.size() - 1) {
                    System.out.print(temp.get(j) + ", ");
                } else {
                    System.out.println(temp.get(j) + "}");
                }
            }
        }
    }

    public void mostrarDatos() {

        for (int i = 0; i < numEjemplos; i++) {

            ArrayList temp = ejemplos.get(i).getLineadatos();

            for (int j = 0; j < temp.size(); j++) {
                if (j != temp.size() - 1) {
                    System.out.print(temp.get(j) + ", ");
                } else {
                    System.out.println(temp.get(j));
                }
            }
        }
    }

    public void mostrarDatos(ArrayList<LineaDeDatos> ln) {

        for (int i = 0; i < ln.size(); i++) {

            ArrayList temp = ln.get(i).getLineadatos();

            for (int j = 0; j < temp.size(); j++) {
                if (j != temp.size() - 1) {
                    System.out.print(temp.get(j) + ", ");
                } else {
                    System.out.println(temp.get(j));
                }
            }
        }
    }

    public void anadirVariableEntrada(String n, float inf, float sup) {
        VariableEntrada e = new VariableEntrada();
        VariableEntrada.setEtiquetas(numEtiquetas);
        e.setNombre(n);
        e.setRangoInferior(inf);
        e.setRangoSuperior(sup);
        var_entrada.add(e);
        numVariablesEntrada++;
    }

    public void anadirVariableSalida(String n, ArrayList valores) {
        VariableSalida s = new VariableSalida();
        s.setNombre(n);
        s.setNumValores(valores.size());
        s.cargarArray(valores);
        var_salida.add(s);
        numVariablesSalida++;
    }

    public void anadirLineaDatos(ArrayList param) {
        LineaDeDatos ld = new LineaDeDatos();
        ld.cargarArray(param);
        ld.setNumventrada(numVariablesEntrada);
        ld.setNumvsalida(numVariablesSalida);
        ejemplos.add(ld);
        numEjemplos++;
    }

    public ArrayList<VariableEntrada> getVar_entrada() {
        return var_entrada;
    }

    public ArrayList<VariableSalida> getVar_salida() {
        return var_salida;
    }

    public ArrayList<LineaDeDatos> getEjemplos() {
        return ejemplos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumEjemplos() {
        return numEjemplos;
    }

    public void setNumEjemplos(int numEjemplos) {
        this.numEjemplos = numEjemplos;
    }

    public int getNumVariablesEntrada() {
        return numVariablesEntrada;
    }

    public void setNumVariablesEntrada(int numVariablesEntrada) {
        this.numVariablesEntrada = numVariablesEntrada;
    }

    public int getNumVariablesSalida() {
        return numVariablesSalida;
    }

    public void setNumVariablesSalida(int numVariablesSalida) {
        this.numVariablesSalida = numVariablesSalida;
    }

    public int getNumEtiquetas() {
        return numEtiquetas;
    }

    public void setNumEtiquetas(int numEtiquetas) {
        BaseDeDatos.numEtiquetas = numEtiquetas;
    }

}
