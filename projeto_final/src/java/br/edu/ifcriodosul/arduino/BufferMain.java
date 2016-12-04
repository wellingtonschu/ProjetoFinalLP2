package br.edu.ifcriodosul.arduino;

import br.edu.ifcriodosul.conceitual.Configuracao;

/**
 *
 * @author Alex Manoel Coelho <alexma_coelho@hotmail.com>
 */
public class BufferMain {

    public static void main(String[] args) {
        
        arduino inp = new arduino(); // INSTANCIANDO CLASSE ENTRADA
        
        inp.initialize();           // INICIALIZANDO PORTA
        
        //inp.enviarDados("1");
    }
}

