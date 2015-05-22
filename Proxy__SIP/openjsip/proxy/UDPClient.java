/**
* Projet BE QOS
* 
* @author Yann MBOUNGOU et Jean-Christophe VENDOME
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;
import java.net.DatagramSocket;
import java.net.SocketException;

public final class UDPClient {
    
    private DatagramSocket socketUDP_emission;    
    
    public UDPClient()
    {
        try 
        {
            socketUDP_emission = new DatagramSocket();    
            System.out.println("Connexion from "+this.getSocketUDP_emission().getLocalAddress().getHostAddress()+" : "+this.getSocketUDP_emission().getPort()+"...");
        } 
        catch (SocketException e) 
        {
            System.out.println("SocketException");
        }
    }

    public DatagramSocket getSocketUDP_emission() {
        return socketUDP_emission;
    }

    
}
