package mdc.client;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import javax.swing.JOptionPane;
import mdc.client.parse.DoctorListParser;
import mdc.client.parse.FapListParser;
import mdc.client.parse.PatientListParser;
import mdc.client.parse.ServiceParamsParser;
import mdc.client.update.PatientServerStatusChange;
import mdc.engine.ReadConfigXml;
import mdc.gui.JFrame;
import org.apache.log4j.Logger;

public class MdcClient {
    
    private Connection connection;
    private Channel channel;
    private String replyQueueName ;
    private QueueingConsumer consumer;
    private GetResultsForMdcServer patientResult ;
    private boolean flag ;
    private java.sql.Connection con;
    private String CrbId;
    private PatientListParser list;
    private ServiceParamsParser serv;
    private FapListParser fapList;
    private DoctorListParser doctorList;
    private PatientServerStatusChange status;   
    private ReadConfigXml config = new ReadConfigXml();
    private JFrame frame;
    private static final Logger log = Logger.getLogger(MdcClient.class);
        
    public MdcClient(GetResultsForMdcServer xml,java.sql.Connection con,
            PatientListParser list,ServiceParamsParser serv,String CrbId,FapListParser fapList,
            DoctorListParser doc,PatientServerStatusChange status,boolean flag , JFrame frame) {
            this.patientResult = xml;
            this.con = con;
            this.list = list;
            this.serv = serv;
            this.CrbId = CrbId;
            this.fapList = fapList;
            this.doctorList = doc;
            this.status = status;
            this.flag = flag;       
            this.frame = frame;
    }
    
    private String username  = "";
    private String pass  = ""; 
    private String hostName  = ""; 
    private String port  = ""; 
    private String exchangeName = "";
    private String routingKey = "";
    private String vh = "";
    private String queue = "";
    
    private void readPropertyFile(){
        config.parse();
        username = config.getRabbitUser();
        pass = config.getRabbitPassword();
        hostName = config.getRabbitHost();
        port = config.getRabbitPort();  
        vh = config.getRabbitVH(); 
        exchangeName = config.getRabbitExchange();
        routingKey = config.getRabbitRouting();  
    }
    
    public MdcClient init() throws Exception {
        readPropertyFile();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        
         factory.setUsername(username);                  
         factory.setPassword(pass);
         factory.setVirtualHost(vh);
         factory.setHost(hostName);
         factory.setPort(Integer.parseInt(port));
        connection = factory.newConnection();
        channel = connection.createChannel();
        return this;
    }

    public MdcClient setupConsumer()throws Exception {       
        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, false, consumer);
        return this;
    }
    
    public void call() throws Exception {
        //отправляем код ЦРБ для дальнейших действий
        String msg = "Crb ID [" +CrbId+"]";
                channel.basicPublish(
                exchangeName,
                routingKey,
                getRequestProperties(),
                msg.getBytes()
                );
        
       
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            byte [] arr = delivery.getBody();            
            String value = new String(arr,"UTF-8");            
            System.out.println(value);
            
            // получаем код ЦРБ  и отправляем результаты диспы
            if(value.equals("Crb ID received")){                
                channel.basicPublish(exchangeName,
                routingKey,
                getRequestProperties(),
                patientResult.getArr()
                );
                patientResult.getIdList().forEach((idList) -> {
                    status.UpdateStatus(idList);
                });
                   System.out.println("Patient result sent... Waiting ");
            }
            
            //  изменяем статус, что он на сервере - "YES"
            if(value.equals("Patient_result received")){                                             
                String execute = "";
                if(flag){
                    execute = "Server status updated - true";
                }
                if(!flag){
                    execute = "Server status updated - false";
                } 
                channel.basicPublish(
                exchangeName,
                routingKey,
                getRequestProperties(),
                execute.getBytes()
                ); 
            }
            // получили акт. список пациентов
            if(value.contains("PATIENT_LIST")){
              list.parse(arr);  
              String execute = "PatientList received";               
              channel.basicPublish(
                exchangeName,
                routingKey,
                getRequestProperties(),
                execute.getBytes()
                );              
            }
            // получили список услуг
            if(value.contains("SERVICE_OPTIONS")){
              //serv.parse(arr);                
              String execute = "ServiceList received";
              channel.basicPublish(
                exchangeName,
                routingKey,
                getRequestProperties(),
                execute.getBytes()
                );
                }  
            
            //  получили список ФАПов 
            if(value.contains("FAP_LIST")){
                fapList.parse(arr);
                String execute = "FAPList received";
                channel.basicPublish(
                exchangeName,
                routingKey,
                getRequestProperties(),
                execute.getBytes()
                );
            }
            // получили акт. список врачей
            if(value.contains("DOCTOR_LIST")){
                doctorList.parse(arr);
                String execute = "DoctorList received";
                channel.basicPublish(
                exchangeName,
                routingKey,
                getRequestProperties(),
                execute.getBytes()
                );
            frame.synchronBut.setEnabled(true);
            frame.synchronBut1.setEnabled(true);
            frame.synchronBut2.setEnabled(true);  
            JOptionPane.showMessageDialog(null, 
            "Обмен данными с сервером завершен. Для обновления списка врачей и фапов перезапустите программу.");
                }
            }
        }
       
    private BasicProperties   getRequestProperties() {
        return new BasicProperties
        .Builder()
        .replyTo(replyQueueName)
        .build();
    }
       
    public void close() throws Exception {        
        connection.close();             
        }

    public  void run() throws Exception {    
        try {       
            init().setupConsumer();
            call();
            System.out.println("message receive");
	}
	catch (Exception e) {
            log.info(e ,e );
	}
	finally {		
                    try {
                        close();
                    }
	catch (Exception ex) {
            log.info(ex , ex);
            }           
        }
    }    
}