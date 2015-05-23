/**
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy.Messages;

public class Bye extends Message{
        
        private static final long serialVersionUID = 4849L;
        private String adresse_IP_src;
        public Bye(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec,int debit, boolean classe) {
                super(adresse_IP_src);
                this.adresse_IP_src=adresse_IP_src;
        }
        @Override
        public String toString() {
                return "Bye from " + adresse_IP_src  ;
        }      
        
}
