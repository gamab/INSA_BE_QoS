package routerconf;

import java.util.ArrayList;
import java.util.ListIterator;


import log.Log;
import messages.FlowDescriptor;



public class RouterRSRVTable {
	private static final String TAG = "RouterRSVRTable";

	private float maxTransmRate; 					//the maximum bandwith that can be reserved
	private float availRess; 						//the bandwith that is currently unused and could be reserved
	private ArrayList<FlowDescriptor> acceptedFlows;//the list of flows that have been accepted
	
	public RouterRSRVTable(float maxTransmRate) {
		super();
		this.availRess = maxTransmRate;
		this.maxTransmRate = maxTransmRate;
		this.acceptedFlows = new ArrayList<>();
	}
	
	public boolean isFlowAcceptable(FlowDescriptor fd) {
		return this.availRess - fd.getTransmRate() >= 0.0f;
	}
	
	public boolean acceptFlow(FlowDescriptor fd) {
		if (isFlowAcceptable(fd)) {
			Log.d(TAG,"Accepting " + fd.toString());
			this.subFromAvailRess(fd.getTransmRate());
			this.acceptedFlows.add(fd);
			return true;
		}
		else { return false; }
	}
	
	public boolean freeFlowRSRV(FlowDescriptor fd) {
		Log.d(TAG,"Removing " + fd.toString() + " from " + acceptedFlows);
		boolean foundFlow = this.acceptedFlows.remove(fd);
		
		if (foundFlow) {
			addToAvailRess(fd.getTransmRate());
			Log.d(TAG,"Found flow, assuming we can free the ressources that we accepted");
		}
		
		return foundFlow;
	}
	
	public float getAvailRess() {
		return availRess;
	}

	private boolean addToAvailRess(float freedRess) {
		if (this.availRess + freedRess <= this.maxTransmRate) {
			this.availRess += freedRess;
			return true;
		}
		else {
			Log.e(TAG,"Could not add " + freedRess 
					+ " to available ressources it is greater than the maximum !");
			return false;
		}
	}

	private boolean subFromAvailRess(float neededRess) {
		if (this.availRess - neededRess >= 0.0d) {
			this.availRess -= neededRess;
			return true;
		}
		else {
			Log.e(TAG,"Could not sub " + neededRess 
					+ " from available ressources it is lower than 0 !");
			return false;
		}
	}
}
