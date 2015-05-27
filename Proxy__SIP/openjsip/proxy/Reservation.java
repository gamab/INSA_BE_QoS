package openjsip.proxy;
/*
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/


public class Reservation extends Message{
        private static final long serialVersionUID = 33L;
        private boolean isAck;
        
        public Reservation(String adresse_IP_src, String adresse_IP_dest,int port_src, 
			int port_dest, String protocole, int codec,boolean isAck) {
                super(adresse_IP_src,adresse_IP_dest,port_src,port_dest,protocole,codec);
                this.isAck = isAck;
        }
        
        @Override
        public String toString() {
                return "Reservation from " + adresse_IP_src  +"to "+adresse_IP_dest+", isAck=" + isAck ;
        }

        public boolean isAck() {
                return isAck;
        }
        
        public void setIsAck(boolean isAck) {
                this.isAck = isAck;
        }
        
        
}
