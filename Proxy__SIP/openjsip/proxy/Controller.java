/**
* Projet BE QOS
* 
* @author Yann MBOUNGOU 
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;
import Messages.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {
    private static NI ni;
    private static int id=1;
    
    /**
    * README :
    * Cette classe est appelée 
    * à chaque fois qu'on lance notre appli, 
    * dans le cas où notre appli émet un signal
    * vers le système distant, ou même lorsque   
    * une des fonctions du chatNI ou du ChatGUI 
    * est appelée par le chatGUI ou chatNI.Elle sert 
    * en réalité d'intermediaire entre les deux 
    * classes.Cf pattern MVC
    */
    
    public Controller(NI ni) 
    {
       this.ni = ni;
    }
    

    //***************SEND Reservationr********************//
    public void performConnect(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec,int debit, boolean classe,InetAddress addr) throws UnknownHostException  
    {
        System.out.println("performConnect");
        if(NI.isClose())
        { 
            this.setNI(new  NI());            
        }
        this.sendReservation(adresse_IP_src,adresse_IP_dest,port_src,port_dest,protocole,codec,debit,classe,addr);
    }

    public void sendReservation(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec, int debit, boolean classe,InetAddress addr) throws UnknownHostException 
    {
        Reservation reserv = new Reservation(adresse_IP_src,adresse_IP_dest,port_src,port_dest,protocole,codec,debit,classe,false); 
        try 
        {
            System.out.println("sendReservation");
            this.NI.sendMessage(reserv,addr); 
        } 
        catch (IOException ex) 
        {
           System.err.println("!!Cont: IOException ="+ex);
        }
    }
    

    //*************** SEND Bye********************//    
    public void sendBye(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec, int debit, boolean classe,InetAddress addr) throws UnknownHostException 
    {
        Bye bye = new Bye(adresse_IP_src,adresse_IP_dest,port_src,port_dest,protocole,codec,debit,classe);
        try 
        {
            System.out.println("sendBye");
            this.NI.sendMessage(bye,addr);  
        } 
        catch (IOException ex) 
        {
            System.err.println("!!Cont: IOException ="+ex);
        }
    }     
    
    
    //***************Receive Ack********************//
         public void responseMessage_Ack(String login)
    {
        System.out.println("responseMessage_Ack" + login);
        InetAddress addr=this.NI.getServer().getPacket().getAddress();         
    }
         
    //***************Receive No_ACK********************//      
      public void responseMessage_NoAck(String login)
    {
        System.out.println("responseMessage_NoAck "+login);
        InetAddress addr=this.NI.getServer().getPacket().getAddress();   
    }  
      
    //***************Receive Bye********************//     
      public void receiveBye(String login)
      {
          System.out.println("receiveBye");
      }    
      

    public void setNI(NI ni)
    {
        this.ni = ni;
    }
      
      
}
