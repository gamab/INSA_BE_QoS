/**
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package messages;

import java.io.*;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 31400L;
    
    public Message() {
    	super();
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
    
}
