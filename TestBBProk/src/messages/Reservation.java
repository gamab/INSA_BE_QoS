package messages;
/*
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/


public class Reservation extends Message{
        private static final long serialVersionUID = 33L;
        private FlowDescriptor flow1;
        private FlowDescriptor flow2;
        private boolean isAck;
        
        public Reservation(FlowDescriptor flow1, FlowDescriptor flow2,
				boolean isAck) {
			super();
			this.flow1 = flow1;
			this.flow2 = flow2;
			this.isAck = isAck;
		}

		public FlowDescriptor getFlow1() {
			return flow1;
		}

		public void setFlow1(FlowDescriptor flow1) {
			this.flow1 = flow1;
		}

		public FlowDescriptor getFlow2() {
			return flow2;
		}

		public void setFlow2(FlowDescriptor flow2) {
			this.flow2 = flow2;
		}

		public void setAck(boolean isAck) {
			this.isAck = isAck;
		}       
		
		public boolean getAck() {
			return this.isAck;
		}
        
}
