package br.edu.ifcriodosul.arduino;

import br.edu.ifcriodosul.conceitual.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import static java.awt.image.ImageObserver.ERROR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OrderBy;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex Manoel Coelho <alexma_coelho@hotmail.com>
 */
public class Arduino implements SerialPortEventListener {

    private EntityManager em = null;
    List<Configuracao> configuracoes = null;
    private boolean jaIniciou = false;
    List<Localizacao> localizacoes = null;
    Configuracao c = new Configuracao();
    Localizacao localizacao = null;
    Sensor s = null;
    TipoLeitura tipoLeitura = null;
    Leitura leitura = null;
    Log log = null;
    Situacao sit = null;
    String idStr = "";
    //GregorianCalendar calendar = new GregorianCalendar();
    Date data = new Date();
    String entrada[];
    String pegaIDArduino;
    String pegaUmidadeSolo[];
    String pegaUmidadeAr[];
    String pegaTemperatura[];
    int situacao = 0; //1 Irrigando; 2 Não irrigando
    String inputLine; //arduino inp = new arduino(); // INSTANCIANDO CLASSE ENTRADA
    SerialPort serialPort;
    //updateDB up = new updateDB();

    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
        "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Raspberry Pi
        "/dev/ttyUSB0", // Linux
        "COM5", "COM3", "COM4" // Windows
    };
    /**
     * A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent
     */
    private BufferedReader input;
    /**
     * The output stream to the port
     */
    //private OutputStream output;  // CASO QUEIRA ENVIAR DADOS DEVOLTA
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 3000;
    private OutputStream output = null;

    private static final String verdeON = "1";
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    public void initialize() {

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public void enviarDados(String dados) {
        try {
            output.write(dados.getBytes());
        } catch (Exception e) {
            System.exit(ERROR);
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("ex_jpaPU");
                em = emf.createEntityManager();
                inputLine = input.readLine();
                entrada = inputLine.split(";");
                System.out.println(".." + inputLine);

                //Umidade solo
                pegaUmidadeSolo = null;
                pegaUmidadeSolo = entrada[0].split(":");
                System.out.println(pegaUmidadeSolo[1]);
                //Umidade do ar
                pegaUmidadeAr = null;
                pegaUmidadeAr = entrada[1].split(":");
                System.out.println(pegaUmidadeAr[1]);
                //temperatura
                pegaTemperatura = null;
                pegaTemperatura = entrada[2].split(":");
                System.out.println(pegaTemperatura[1]);

                /*a principio como esta sendo avaliado apenas um arduino (localizacao) a localizacao já foi atribuida a 
                variavel pegaIDArduino, mas se caso estiver controlando mais de uma localizacao essa variavel teria que 
                ser atribuida pelo banco de dados
                A ideia de que se tivesse mais localizacoes seria passado o id do arduino atraves de uma string, a partir dai seria
                tomado as decisoes*/
                pegaIDArduino = "ArduinoUno16.000";
                localizacoes = null;
                localizacoes = em.createQuery("FROM Localizacao").getResultList();

                //se a lista de localizações estiver vazia, não sera cadastrado nenhuma leitura no banco
                if (!localizacoes.isEmpty()) {
                    //a partir do idPassado acha a localização
                    for (int i = 0; i < localizacoes.size(); i++) {
                        if (localizacoes.get(i).getIdArduino().equals(pegaIDArduino)) {
                            localizacao = em.find(Localizacao.class, localizacoes.get(i).getId());
                            break;
                        }
                    }
                    configuracoes = null;
                    configuracoes = em.createQuery("FROM Configuracao").getResultList();
                    c = new Configuracao();
                    //a partir do id da localizacao achado - acha a configuracao que corresponde a localizacao
                    for (int i = 0; i < configuracoes.size(); i++) {
                        if (configuracoes.get(i).getLocalizacao().getId() == localizacao.getId()) {
                            c = em.find(Configuracao.class, configuracoes.get(i).getId());
                            break;
                        }
                    }

                    //condicoes para ligar
                    if ((Float.parseFloat(pegaUmidadeSolo[1]) > c.getUmidadeDoSoloMax())
                            && (Float.parseFloat(pegaUmidadeAr[1]) > c.getUmidadeDoArMax())
                            && (Float.parseFloat(pegaTemperatura[1]) > c.getTemperaturaMax())) {
                        situacao = 1;
                        try {
                            output.write("1".getBytes());
                        } catch (Exception e) {
                            System.exit(ERROR);
                        }
                        System.out.println("LIGA! -> Dados Arduino: " + pegaUmidadeAr[1] + " - Dados banco: " + c.getUmidadeDoArMax());
                    } else {
                        situacao = 2;
                        try {
                            output.write("0".getBytes());
                        } catch (Exception e) {
                            System.exit(ERROR);
                        }
                        System.out.println("DESLIGA!");
                    }

                    //salvando log no banco
                    log = new Log();
                    sit = new Situacao();

                    sit.setId(situacao);

                    data = new Date();
                    log.setSituacao(sit);
                    log.setData(data);
                    log.setHorario(data);
                    log.setLocalizacao(localizacao);

                    //salvar
                    EntityTransaction tx = em.getTransaction();
                    try {
                        tx.begin();
                        em.persist(log);
                        tx.commit();
                    } catch (Exception e) {
                        tx.rollback();
                    }

                    //salvando leitura do sensor Moisture sensor (Umidade do solo)
                    leitura = new Leitura();
                    tipoLeitura = new TipoLeitura();
                    s = new Sensor();

                    tipoLeitura.setId(1);
                    s.setId(1);

                    //preencher objeto
                    leitura.setTipoLeitura(tipoLeitura);
                    leitura.setLog(log);
                    leitura.setSensor(s);
                    leitura.setDadosLeitura(Float.parseFloat(pegaUmidadeSolo[1]));

                    //salvar
                    tx = em.getTransaction();
                    try {
                        tx.begin();
                        em.persist(leitura);
                        tx.commit();
                    } catch (Exception e) {
                        tx.rollback();
                    }

                    //salvando leitura do sensor DHT-22 sensor (Umidade do ar)
                    leitura = new Leitura();
                    tipoLeitura = new TipoLeitura();
                    s = new Sensor();

                    tipoLeitura.setId(2);
                    s.setId(2);

                    //preencher objeto
                    leitura.setTipoLeitura(tipoLeitura);
                    leitura.setLog(log);
                    leitura.setSensor(s);
                    leitura.setDadosLeitura(Float.parseFloat(pegaUmidadeAr[1]));

                    //salvar
                    tx = em.getTransaction();
                    try {
                        tx.begin();
                        em.persist(leitura);
                        tx.commit();
                    } catch (Exception e) {
                        tx.rollback();
                    }

                    //salvando leitura do sensor DHT-22 sensor (Temperatura)
                    leitura = new Leitura();
                    tipoLeitura = new TipoLeitura();
                    s = new Sensor();

                    tipoLeitura.setId(3);
                    s.setId(2);

                    //preencher objeto
                    leitura.setTipoLeitura(tipoLeitura);
                    leitura.setLog(log);
                    leitura.setSensor(s);
                    leitura.setDadosLeitura(Float.parseFloat(pegaTemperatura[1]));

                    //salvar
                    tx = em.getTransaction();
                    try {
                        tx.begin();
                        em.persist(leitura);
                        tx.commit();
                    } catch (Exception e) {
                        tx.rollback();
                    }

                    System.out.println(c.getNomePlantacao());
                    System.out.println("ID -> " + localizacao.getId());
                    c = new Configuracao();
                    System.out.println(c.getNomePlantacao());
                    //up.updateDados(entrada);
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

}
