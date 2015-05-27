package controller;

import messages.Bye;
import messages.Reservation;
import ressources.FlowDescriptor;

public class Controller {

	public boolean processReservation(Reservation msg) {
		//Extract flow from reservation
		FlowDescriptor flow = null;
		
		//Identify Router in charge of the flow
		
		
		//Check if routeur can accept the flow
		
		//Connect to routeur and exec script of reservation
		
		return false;
	}

	public void processBye(Bye msg) {
		// TODO Auto-generated method stub
		
	}

}
