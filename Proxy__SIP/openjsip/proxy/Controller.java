/**
* Projet BE QOS
* 
* @author Yann MBOUNGOU 
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Inet4Address;
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
			int port_dest, String protocole, float transmRate,InetAddress addr) throws UnknownHostException  
    {
        System.out.println("performConnect");
        if(NI.isClose())
        { 
            this.setNI(new  NI());            
        }
        this.sendReservation(adresse_IP_src,adresse_IP_dest,port_src,port_dest,protocole,transmRate,addr);
    }

    public void sendReservation(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, float transmRate,InetAddress addr) throws UnknownHostException 
    {
	    FlowDescriptor un= new FlowDescriptor((Inet4Address)InetAddress.getByName(adresse_IP_src), (Inet4Address)InetAddress.getByName(adresse_IP_dest),port_src,port_dest,transmRate, protocole);
		FlowDescriptor deux= new FlowDescriptor((Inet4Address)InetAddress.getByName(adresse_IP_dest),(Inet4Address)InetAddress.getByName(adresse_IP_src),port_dest,port_src,transmRate,protocole);
        Reservation reserv = new Reservation(un,deux,false); 
        try 
        {
            System.out.println("sendReservation");
            this.ni.sendMessage(reserv,addr); 
        } 
        catch (IOException ex) 
        {
           System.err.println("!!Cont: IOException ="+ex);
        }
    }
    

    //*************** SEND Bye********************//    
    public void sendBye(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, float transmRate,InetAddress addr) throws UnknownHostException 
    {
		FlowDescriptor un= new FlowDescriptor((Inet4Address)InetAddress.getByName(adresse_IP_src), (Inet4Address)InetAddress.getByName(adresse_IP_dest),port_src,port_dest,transmRate, protocole);
		FlowDescriptor deux= new FlowDescriptor((Inet4Address)InetAddress.getByName(adresse_IP_dest),(Inet4Address)InetAddress.getByName(adresse_IP_src),port_dest,port_src,transmRate,protocole);
        Bye bye = new Bye(un,deux);
        try 
        {
            System.out.println("sendBye");
            this.ni.sendMessage(bye,addr);  
        } 
        catch (IOException ex) 
        {
            System.err.println("!!Cont: IOException ="+ex);
        }
    }     
    
    
    //***************Receive Ack********************//
         public void responseMessage_Ack()
    {        
        InetAddress addr=this.ni.getServer().getPacket().getAddress(); 
        System.out.println("receive : Message[OK] from " + addr.getHostAddress());        
    }
         
    //***************Receive No_ACK********************//      
      public void responseMessage_NoAck()
    {
		InetAddress addr=this.ni.getServer().getPacket().getAddress();  
        System.out.println("receive : Message[NOK] from "+addr.getHostAddress());         
    }  
      
    //***************Receive Bye********************//     
      public void receiveBye()
      {
          System.out.println("receiveBye");
      }    
      

    public void setNI(NI ni)
    {
        this.ni = ni;
    }
      
      
}
