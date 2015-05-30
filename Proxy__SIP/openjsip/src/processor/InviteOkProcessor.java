package processor;

import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.sdp.SdpFactory;
import javax.sdp.SessionDescription;
import javax.sip.header.CSeqHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

import networking.Controller;
import networking.Ressources;
import messages.FlowDescriptor;

public class InviteOkProcessor {

	private FlowDescriptor f1;
	private FlowDescriptor f2;
	private Ressources r ;


	public InviteOkProcessor() {
		super();
		//create empty flow descriptors
		f1 = new FlowDescriptor(null, null, 0, 0, 0, "");
		f2 = new FlowDescriptor(null, null, 0, 0, 0, "");
		r = new Ressources();
		// TODO Auto-generated constructor stub
	}
	public FlowDescriptor getF1() {
		return f1;
	}
	public FlowDescriptor getF2() {
		return f2;
	}

	public void processInvite(Request request)
	{
		SIPRequest siprequest = (SIPRequest) request;
		String sdpContent = new String(siprequest.getRawContent());

		try
		{        		
			SdpFactory sdpf = SdpFactory.getInstance();
			SessionDescription requestSDP = sdpf.createSessionDescription(sdpContent); // recuperation du message SDP        		
			String chaine = requestSDP.toString(); // recuperation de la chaine a  parser
			System.out.println(chaine);       		

			String aux = new String();// declaration des variables auxiliaires
			Pattern p;
			Matcher m;

			//******************* RECUPERATION PORT SOURCE *************************//
			try
			{
				p = Pattern.compile("m=audio ([^ ]+)"); // recuperation du "media description"
				m = p.matcher(chaine);
				if (m.find())
				{
					f2.setpDst(Integer.parseInt(m.group(1)));
					//System.out.println(aux);
				}
				System.out.println("************ Port_source f2 = "+f2.getpSrc());


			}catch(PatternSyntaxException pse){
				System.out.println("ERROR [RECUPERATION PORT SOURCE]");
			}

			//******************** RECUPERATION ADRESSE IP SOURCE ***************************//
			try
			{
				p = Pattern.compile("c=IN IP4 ([0-9|.]+)"); // recuperation du "connection information"
				m = p.matcher(chaine);
				if(m.find())
				{
					f1.setIpSrc((Inet4Address) Inet4Address.getByName(m.group(1)));
					f2.setIpDst((Inet4Address) Inet4Address.getByName(m.group(1)));
					//System.out.println(aux);
				}

				System.out.println("************ IP_source f1 = "+f1.getIpSrc().getHostAddress());
				System.out.println("************ IP_desti f2 = "+f2.getIpDst().getHostAddress());

			}catch(PatternSyntaxException pse){
				System.out.println("ERROR [RECUPERATION ADRESSE IP SOURCE]");
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void processOk(Response response,CSeqHeader cseqHeader,int statusCode,Controller cont) {
		if(statusCode == 200 && cseqHeader.getMethod().equals(Request.INVITE))
		{
			SIPResponse sr = (SIPResponse)response;
			String sdpContent = new String(sr.getRawContent());
			String chaine = new String();
			String numCodec = new String();
			String extensionCodec = new String();
			Pattern p;
			Matcher m;

			try{        		
				SdpFactory sdpf = SdpFactory.getInstance(); // recuperation du message SDP
				SessionDescription requestSDP = sdpf.createSessionDescription(sdpContent);
				chaine=requestSDP.toString(); // recuperation de la chaine Ã  parser
				//System.out.println(chaine);

				//********************************* RECUPERATION PORT DEST***************************//
				try
				{
					p = Pattern.compile("m=audio ([^ ]+) ([^ ]+) ([0-9]+)"); // recuperation du "media description"
					m = p.matcher(chaine);
					if(m.find())
					{
						f1.setpDst(Integer.parseInt(m.group(1)));
						f1.setProto(m.group(2));
						f2.setProto(m.group(2));
						numCodec = m.group(3);
					}
						
					System.out.println("************ Port_dest f1 = "+ f1.getpDst());       			
				}
				catch(PatternSyntaxException pse){
					System.out.println("ERROR [RECUPERATION PORT DEST]");
				}

				//****************************** RECUPERATION DEBIT *****************************//
				try
				{
					p = Pattern.compile("a=rtpmap:"+numCodec+" ([^/]+)"); // recuperation du "more session attribute lines"
					m = p.matcher(chaine);
					if(m.find()){
						extensionCodec = m.group(1);
					}
				}
				catch(PatternSyntaxException pse){
					System.out.println("ERROR [RECUPERATION DEBIT]");
				}


				//**************************** RECUPERATION ADRESSE IP DEST ******************************//
				try
				{
					p = Pattern.compile("c=IN IP4 ([0-9|.]+)"); // recuperation du "connection information"
					m = p.matcher(chaine);
					if(m.find())
					{
						f1.setIpDst((Inet4Address) Inet4Address.getByName(m.group(1)));
						f2.setIpSrc((Inet4Address) Inet4Address.getByName(m.group(1)));
					}
					
					f1.setTransmRate(r.transmRate.get(extensionCodec));
					f2.setTransmRate(r.transmRate.get(extensionCodec));
					

					System.out.println("************ IP_desti f1 = "+f1.getIpDst().getHostAddress());
					System.out.println("************ IP_source f2 = "+f2.getIpSrc().getHostAddress());
					System.out.println("************ Codec is : " + numCodec + " " + extensionCodec);
					System.out.println("************ Rate is : " + f1.getTransmRate());

				}catch(PatternSyntaxException pse){
					System.out.println("ERROR [RECUPERATION ADRESSE IP DESTINATION]");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
			
			//*********** AFFICHAGE DU FLUX **********//
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< FLUX >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("flux 1 is : " + f1.toString());
			System.out.println("flux 2 is : " + f2.toString());
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			//*********** ENVOI DU MESSAGE AU BB **********//         	
            try
            {
            	cont.sendReservation(getF1(),getF2()); 
            }
  	        catch(UnknownHostException e){
                 System.out.println("ERROR [OK<== @IP]");
	        }         
			
		}
	}
	
	
	public void free_session(Controller cont)
	{	   
	    try
	    {   
	         cont.sendBye(getF1(),getF2()); 	
	    }
	    catch(UnknownHostException e){
	           System.out.println("ERROR [free_session<== @IP]");
	   } 	       
   }
}
