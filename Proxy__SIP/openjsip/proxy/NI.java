/**
* Projet Chat System
* 
* @author Yann MBOUNGOU et Jean-Christophe VENDOME
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;
import java.io.File;
import java.io.IOException;
import java.net.*;

public class NI extends Thread
{
    private final int UDP_SERVER_PORT = 2015;
    private UDPServer server;
    private UDPClient client;
    private static Controller Cont;
    private static boolean active=false;
    private static boolean close=false;
        
    /**
    * README :
    * Cette classe est appelée 
    * à chaque fois qu'on intéragit avec le réseau
    * Elle nous permet de communiquer avec le systeme
    * distant et d'interagir avec nos serveurs TCP/UDP
    */
    
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public NI()
    {
        this.client=new UDPClient();   
        this.server= new UDPServer (UDP_SERVER_PORT); 
        this.start();
    }
    
    @Override
    public void run()
    {
        active=true;        
        while (active) {
            try{
                System.out.println("Waiting for data");
                getServer().receiveMessage();
                getServer().getSocketUDP_reception().receive(getServer().getPacket());                
                byte[] msg= getServer().getPacket().getData();                
                Message recu=Message.fromArray(msg); // message désérialisé
                InetAddress addr=getServer().getPacket().getAddress();                  
                String Addss=addr.getHostAddress();
                String hostAddr=""+InetAddress.getLocalHost().getHostAddress();
                
                if(recu instanceof Reservation)
                {
                    System.out.println("localhost : "+hostAddr+" adrrss :"+Addss);
                    Reservation reserv=(Reservation)recu;
                    if(reserv.isAck())
                    { 
                        
                       if(Addss.equals(hostAddr)==false) //<=== si mis à false, on refuse les demandes en provenances de notre machine
                       {
                           System.out.println("Data received : Reservation_Ack");
                           System.out.println("localhost : "+hostAddr+" adrrss :"+Addss+" @IP_s :"+reserv.getAdresse_IP_src());
                           this.Cont.responseMessage_Ack();
                       }                       
                    }
                    else
                    {
                        if(Addss.equals(hostAddr)==false) //<=== si mis à true, on accepte les demandes en provenances de notre machine
                        {
                            System.out.println("Data received : Reservation_NoAck");
                            this.Cont.responseMessage_NoAck();
                        }
                    }
                }
                else if(recu instanceof Bye)
                {
                    Bye bye=(Bye)recu;                    
                    if(Addss.equals(hostAddr)==true)
                    {
                        System.out.println("Data received : Bye");
                        this.Cont.receiveBye();  
                    }
                }                
                
            } 
            catch (IOException | ClassNotFoundException ex) 
            {
                active=false;
                System.err.println("!!NI: IOException | ClassNotFoundException ="+ex);
            }
            
        }
    }
    
    
    
    public void sendBroadcastMessage(Message message) throws IOException 
    {          
        try 
        {
            System.out.println("sendBroadcastMessage");
            this.sendMessage(message, InetAddress.getByName("255.255.255.255"));
            this.client.getSocketUDP_emission().setBroadcast(true);
            if(message instanceof Bye)
            {
                this.close();
            }            
        }
        catch (UnknownHostException e) 
        {
            System.err.println("!!NI: UnknownHostException ="+e);
        }
    }
    
    public void sendMessage(Message message, InetAddress address) throws IOException 
    {
        try 
        {       
            System.out.println("sendMessage to "+address);
            byte[] sendData = Message.toArray(message);
            DatagramPacket packetUDP = new DatagramPacket(sendData, sendData.length, address,UDP_SERVER_PORT);
            this.client.getSocketUDP_emission().send(packetUDP);
        } 
        catch (IOException e)
        {
            System.err.println("!!NI: IOException ="+e);
        }       
    }

    public void setCont(Controller Cont) {
        this.Cont = Cont;
    }

    public UDPServer getServer() {
        return server;
    }
    
    ///////////////////////////////////////////////
    public void close()
    {
        try
        {
            System.out.println("Fermeture de la connexion...");
            if (this.client.getSocketUDP_emission()!=null)
            {
                this.server.getSocketUDP_reception().close();
                this.client.getSocketUDP_emission().close();
            }
            active=false;
            setClose(true);
        }
        catch (Exception e)
        {
            System.err.println("!!ChatNI: Exception ="+e);
        }
    }
    ///////////////////////////////////////////////

    public void setServer(UDPServer server) {
        this.server = server;
    }

    public void setClient(UDPClient client) {
        this.client = client;
    }
    
    public static boolean isClose()
    {
        return close;
    }
    public static void setClose(boolean aClose) 
    {
        close = aClose;
    }
}
