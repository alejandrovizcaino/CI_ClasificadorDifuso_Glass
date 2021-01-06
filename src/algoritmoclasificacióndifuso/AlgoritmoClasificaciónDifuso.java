package algoritmoclasificacióndifuso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class AlgoritmoClasificaciónDifuso {

    private static BaseDeDatos bd = new BaseDeDatos();
    private static BaseDeReglas br = new BaseDeReglas(bd);

    public static void cargarFichero() {

        bd.setNumEtiquetas(3);
        BufferedReader objReader = null;
        try {
            String strCurrentLine;
            objReader = new BufferedReader(new FileReader("C:\\Users\\Alex\\Documents\\NetBeansProjects\\AlgoritmoClasificaciónDifuso\\glass.dat"));
            while ((strCurrentLine = objReader.readLine()) != null) {

                String[] splited = strCurrentLine.split(" ");
                ArrayList valores = new ArrayList();
                ArrayList datostemp = new ArrayList();

                for (String a : splited) {

                    if ((splited[0].equals("@relation")) && (a.equals(splited[0]))) {
                        bd.setNombre(splited[1]);

                    }
                    if (splited[0].equals("@attribute")) {

                        if (strCurrentLine.contains("[")) { //solo las variables de entrada tienen el símbolo [ en su declaración. La de salida {

                            if (a.equals(splited[0])) {
                                String temp1 = splited[3].replace("[", "");
                                temp1 = temp1.replace(",", "");
                                float inf = Float.parseFloat(temp1);
                                float sup = Float.parseFloat(splited[4].replace("]", ""));
                                bd.anadirVariableEntrada(splited[1], inf, sup);
                            }

                        } else { //variable de salida

                            if ((!a.equals(splited[0])) && (!a.equals(splited[1]))) {

                                String temp = a;
                                if (temp.contains("{")) {
                                    temp = temp.replace("{", "");
                                }
                                if (temp.contains("}")) {
                                    temp = temp.replace("}", "");
                                }
                                if (temp.contains(",")) {
                                    temp = temp.replace(",", "");
                                }

                                valores.add(Float.parseFloat(temp));
                                int num = splited.length - 1;

                                if (a.equals(splited[num])) {
                                    bd.anadirVariableSalida(splited[1], valores);
                                }
                            }
                        }
                    }
                    if (!splited[0].equals("@data") && !splited[0].equals("@relation")
                            && !splited[0].equals("@attribute") && !splited[0].equals("@inputs")
                            && !splited[0].equals("@outputs")) {

                        String temp = a;

                        if (temp.contains(",")) {
                            temp = temp.replace(",", "");
                        }
                        //System.out.println(temp);
                        datostemp.add(Float.parseFloat(temp));
                        int num = splited.length - 1;

                        if (a.equals(splited[num])) {
                            bd.anadirLineaDatos(datostemp);
                        }

                    }

                }
                //System.out.println(strCurrentLine);  
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objReader != null) {
                    objReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        cargarFichero();
        System.out.println("Nombre: " + bd.getNombre());
        System.out.println("Numero de ejemplos: " + bd.getNumEjemplos());
        System.out.println("Numero de variables: " + bd.getNumVariablesEntrada());
        System.out.println("Numero de clases: " + bd.getVar_salida().get(0).getNumValores());
        bd.mostrarVariablesEntrada();
        bd.mostrarVariablesSalida();
        //bd.mostrarDatos();
        bd.getParticiones();
        ArrayList<LineaDeDatos> train = bd.getTrainSet();
        ArrayList<LineaDeDatos> test = bd.getTestSet();
        bd.mostrarDatos(test);
        br.generarReglas(train);
        br.calcularGradoCerteza();
        br.mostrarReglas_Simple();
        br.procesoInferencia(test, 1, 0); // parámetros: (conjunto de test, grado de emparejamiento, función de ponderación). 0 para el mínimo y 1 para el producto.

    }

}
