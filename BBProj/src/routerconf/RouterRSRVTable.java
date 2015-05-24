package routerconf;

import java.util.ArrayList;
import java.util.ListIterator;

import Log.Log;

import flow.FlowDescriptor;

public class RouterRSRVTable {
	private static final String TAG = "RouterRSVRTable";
	
	private RouterDescriptor rtrD;
	private ArrayList<FlowDescriptor> acceptedFlows;
	
	public RouterRSRVTable(RouterDescriptor rtrD) {
		super();
		this.rtrD = rtrD;
		this.acceptedFlows = new ArrayList<>();
	}
	
	public boolean isFlowAcceptable(FlowDescriptor fd) {
		return rtrD.isAvailRessEnough(fd.getTransmRate());
	}
	
	public boolean acceptFlow(FlowDescriptor fd) {
		if (isFlowAcceptable(fd)) {
			rtrD.subFromAvailRess(fd.getTransmRate());
			this.acceptedFlows.add(fd);
			return true;
		}
		else { return false; }
	}
	
	public boolean freeFlowRSRV(FlowDescriptor fd) {
		boolean foundFlow = this.acceptedFlows.remove(fd);
		boolean couldFreeRess = false;
		
		if (foundFlow) {
			couldFreeRess = this.rtrD.addToAvailRess(fd.getTransmRate());
			Log.d(TAG,"Found flow, could free ressources from rtr ? " + String.valueOf(couldFreeRess));
		}
		
		return foundFlow && couldFreeRess;
	}
}
