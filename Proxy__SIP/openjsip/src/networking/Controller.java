package networking;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import messages.Bye;
import messages.FlowDescriptor;
import messages.Reservation;
import openjsip.proxy.Proxy;

public class Controller {
    private static Proxy prox;
    public static final int MAX_SIZE = 1024;
	public static final byte[] addrBB = {(byte)192,(byte)168,(byte)1,(byte)101};
	public static final int port = 2015;
    
    /**
    * README :
    * Cette classe est appelée à chaque fois qu'on lance notre appli, 
    * dans le cas où notre appli émet un signal vers le système distant
    */
    
    public Controller(Proxy prox) 
    {
       this.prox = prox;
    }
    

    //***************SEND Reservationr********************//
    public void sendReservation(FlowDescriptor un, FlowDescriptor deux) throws UnknownHostException 
    {
    	System.out.println("sendReservation");
    	Reservation reserv = new Reservation(un,deux,false);                    
        ProxyClient.resa_Flux(port,MAX_SIZE,this,addrBB,reserv);
    }
    

    //*************** SEND Bye********************//    
    public void sendBye(FlowDescriptor un, FlowDescriptor deux) throws UnknownHostException 
    {
    	System.out.println("sendBye");
    	Bye bye = new Bye(un,deux);       
        ProxyClient_Bye.resa_Flux(port,MAX_SIZE,this,addrBB,bye);  
    }     
    
    
    //***************Receive Ack********************//
         public void responseMessage_Ack()
    {        
        System.out.println("receive : Message[OK] from BB");
        this.prox.setVerifRessources(true);        
    }
         
    //***************Receive No_ACK********************//      
      public void responseMessage_NoAck()
    { 
        System.out.println("receive : Message[NOK] from BB "); 
        this.prox.setVerifRessources(false);         
    }  
          
    public void setProxy(Proxy prox)
    {
        this.prox = prox;
    }      
      
}

