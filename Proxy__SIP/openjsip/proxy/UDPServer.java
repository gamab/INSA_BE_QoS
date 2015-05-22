/**
* Projet BE QOS
* 
* @author Yann MBOUNGOU et Jean-Christophe VENDOME
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    private int port;
    private DatagramSocket socketUDP_reception;
    private DatagramPacket packet;
    
    public UDPServer(int port)
    {
        this.port=port;
        try 
        {   
            socketUDP_reception= new DatagramSocket (port);
        } 
        catch (SocketException e) 
        {
            System.out.println("SocketException");
        }
    }

    public DatagramSocket getSocketUDP_reception() {
        return socketUDP_reception;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void receiveMessage() 
    {
        // Creating a DatagramPacket to be received
        byte[] buf = new byte[1000]; //<===== tableau de 1000 octets qui contiendra les données        
        packet = new DatagramPacket(buf,0, buf.length);        
    }
    
}
