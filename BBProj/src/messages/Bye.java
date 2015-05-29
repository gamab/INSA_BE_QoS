/**
* Projet BE QOS
* 
* @author RT-Groupe2
* ELEVES INGENIEUR - 4e Année - Informatique et Réseaux
* INSA TOULOUSE, Dept. GEI - 2014 - 2015
*/
package messages;

public class Bye extends Message{
        
        private static final long serialVersionUID = 4849L;
        
        private FlowDescriptor flow1;
        private FlowDescriptor flow2;
        
        public Bye(FlowDescriptor flow1, FlowDescriptor flow2) {
			super();
			this.flow1 = flow1;
			this.flow2 = flow2;
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
        
}
