/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifcriodosul.servlet;

import br.edu.ifcriodosul.arduino.Arduino;
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
public class LogServlet extends HttpServlet implements SerialPortEventListener {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    GregorianCalendar calendar = new GregorianCalendar();
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
    private static final int TIME_OUT = 5000;
    private OutputStream output = null;

    private static final String verdeON = "1";
    private static final String verdeOFF = "0";
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;
    
    Arduino ard = new Arduino();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ex_jpaPU");
        em = emf.createEntityManager();
        try {

            String acao = "listar";
            String destino = "index.jsp";

            //testa se existe a acao
            if (request.getParameter("acao") != null) {
                acao = request.getParameter("acao");
            }
            if (acao.equalsIgnoreCase("listar")) {
                destino = listar(request, response);
            }
            /*else if (acao.equalsIgnoreCase("inserir")) {
            destino = inserir(request, response);
            destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("alterar")) {
            destino = alterar(request, response);
            destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("remover")) {
            destino = remover(request, response);
            destino = listar(request, response);
            } else if (acao.equalsIgnoreCase("selecionar")) {
            destino = selecionar(request, response);
            } else if (acao.equalsIgnoreCase("cancelar")) {
            destino = listar(request, response);
            }*/
            request.getRequestDispatcher(destino).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String saida = "";
        //JPAQL
        List<Log> leituraL = em.createQuery("FROM Log ORDER BY id desc LIMIT 100").getResultList();
        int quant = leituraL.size();

        //repassar para pagina (com Dispatcher)
        request.setAttribute("leituras", leituraL);
        request.setAttribute("tamanhoLista", quant);
        saida = "index.jsp";

        if (jaIniciou == false) {
            ard.initialize();
            jaIniciou = true;
        }

        return saida;

    }

    /*protected String selecionar(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    String saida = "";
    
    String idStr = request.getParameter("id");
    Long id = Long.parseLong(idStr);
    Localizacao l = em.find(Localizacao.class, id);
    
    //repassar para pagina (com Dispatcher)
    request.setAttribute("localizacao", l);
    saida = "localizacao.jsp";
    return saida;
    
    }
    
    protected String inserir(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    String saida = "localizacao_list.jsp";
    
    //criar objeto
    Localizacao l = new Localizacao();
    
    //preencher objeto
    l.setIdArduino(request.getParameter("idArduino"));
    l.setLatitude(request.getParameter("latitude"));
    l.setLongitude(request.getParameter("longitude"));
    
    //salvar
    EntityTransaction tx = em.getTransaction();
    try {
    tx.begin();
    em.persist(l);
    tx.commit();
    } catch (Exception e) {
    tx.rollback();
    }
    return saida;
    
    }
    
    protected String alterar(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    String saida = "localizacao_list.jsp";
    //criar objeto
    
    String idStr = request.getParameter("id");
    Long id = Long.parseLong(idStr);
    Localizacao l = em.find(Localizacao.class, id);
    
    //preencher objeto
    l.setIdArduino(request.getParameter("idArduino"));
    l.setLatitude(request.getParameter("latitude"));
    l.setLongitude(request.getParameter("longitude"));
    
    EntityTransaction tx = em.getTransaction();
    try {
    tx.begin();
    em.merge(l);
    tx.commit();
    } catch (Exception e) {
    tx.rollback();
    }
    
    return saida;
    
    }
    
    protected String remover(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    String saida = "localizacao_list.jsp";
    //criar objeto
    
    String idStr = request.getParameter("id");
    Long id = Long.parseLong(idStr);
    Localizacao l = em.find(Localizacao.class, id);
    
    int valida = 0;
    
    Query q = em.createQuery("FROM Usuario WHERE localizacao_id = :id");
    q.setParameter("id", id);
    valida = q.getResultList().size();
    
    EntityTransaction tx = em.getTransaction();
    
    if (valida == 0) {
    try {
    tx.begin();
    em.remove(l);
    tx.commit();
    } catch (Exception e) {
    tx.rollback();
    }
    } else {
    request.setAttribute("erro", "1");
    saida = "localizacao_list.jsp?erro=1";
    }
    saida = "localizacao_list.jsp?erro=1";
    
    return saida;
    
    }*/
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
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

                pegaIDArduino = "ArduinoUno16.000";
                localizacoes = null;
                localizacoes = em.createQuery("FROM Localizacao").getResultList();

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
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
}
