/*
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package openjsip.proxy.Messages;

public class Reservation extends Message{
        private static final long serialVersionUID = 33L;
        private boolean isAck;
        
        public Reservation(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec,int debit, boolean classe, boolean isAck) {
                super(adresse_IP_src);
                this.isAck = isAck;
        }
        
        @Override
        public String toString() {
                return "Reservation from " + adresse_IP_src  +", isAck=" + isAck ;
        }

        public boolean isAck() {
                return isAck;
        }
        
        public void setIsAck(boolean isAck) {
                this.isAck = isAck;
        }
        
        
}
