/**
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;

import java.io.*;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 31400L;
    protected String adresse_IP_src; 
    protected String adresse_IP_dest ;
    protected int port_src ;
    protected int port_dest ;
    protected String protocole ;
    protected int codec ;

    
     /**
     * README :
     * Cette classe contient tous nos messages et
     * implémente les fonctions de sérialisation et 
     * de désirialisation quelque soit le type de 
     * message (Hello,Bye,etc...)
     */
    public Message(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec) { //login source
        this.adresse_IP_src = adresse_IP_src;
        this.adresse_IP_dest = adresse_IP_dest;
	this.port_src = port_src;
	this.port_dest = port_dest;
	this.protocole = protocole;
        this.codec= codec;
    }
    @Override
    public String toString() {
        return "Message from " + this.adresse_IP_src;
    }   

    public void setAdresse_IP_src(String adresse_IP_src) {
        this.adresse_IP_src = adresse_IP_src;
    }
        
    public static byte[] toArray(Message msg) throws IOException{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(msg);
        objectOut.close(); 
        
        byte[] flux = byteOut.toByteArray();
        
        //objectOut.close();
        byteOut.close();
        return flux; // retourne un tableau d'octet
    }
    
    public static Message fromArray(byte[] array) throws IOException, ClassNotFoundException{
        ByteArrayInputStream byteIn = new ByteArrayInputStream(array);
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        
        Message msg;
        msg = (Message)objectIn.readObject();
        
        return msg; 
    }
    	
        public String getAdresse_IP_src() {
        return this.adresse_IP_src;
    }
	public String getAdresse_IP_dest() {
		return this.adresse_IP_dest;
	    }
	public int getPort_src() {
		return this.port_src;
	    }
	public int getPort_dest() {
		return this.port_dest;
	    }
	public String getProtocole() {
		return this.protocole;
	    }
	public int getCodec() {
		return this.codec;
	    }
    
}
