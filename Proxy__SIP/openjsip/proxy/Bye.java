/**
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy;

public class Bye extends Message{
        
        private static final long serialVersionUID = 4849L;
        public Bye(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec) {
                super(adresse_IP_src,adresse_IP_dest,port_src,port_dest,protocole,codec);                
        }
        @Override
        public String toString() {
                return "Bye from " + adresse_IP_src  ;
        }      
        
}
