package pnnl.backend.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;


/**
 * Servlet implementation class customServlet
 */


@WebServlet("/serverInput")
public class customServlet extends HttpServlet 
{

	private static final String Sender_id="AIzaSyDN647WaVGvIJdL82a1Xg-zs2UiRsjSO58";
	
	private static final String Device_id="APA91bF76-fi7_zTmiGzISMTFflRcWulZS5K0sICph3AVBSVDB2huuwsv7xuyNy"
			+ "yplVkbpd_xhQ6XdYl_N8SpguQ_NNnvWYbJTe-5p2AaMYAn0GLc_dvnSbK04ZSmihSgcefvCEYWIcx2"
			+ "SbCHIFSRFMl1Ex0LQ6V2B9oEZ627b-dm5pZCH4TJKI";
	
	private static final long serialVersionUID = 1L;
     
	private List<String> androidDevices=new ArrayList<String>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public customServlet() 
    {
        super();
    
        androidDevices.add(Device_id);

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	
	protected String ParseParameters(HttpServletRequest request)
	{
		StringBuilder dump=new StringBuilder();

		List<String> requestParameterNames = Collections.list(request.getParameterNames());
	
	    for ( String parameterName:requestParameterNames)
	    {
	            dump.append(parameterName+":"+request.getParameter(parameterName));
	    }
	   
	    return dump.toString();
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter r=response.getWriter();

		String message=this.ParseParameters(request);
		
		r.println("Your Message was Received!!!==>"+message);
			
	    doBroadcastMessage("HI from Server =>https://10.101.96.224:8443/backendServlets/serverInput\n"+message, request, response);
	    
	}

	 /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter r=response.getWriter();

		String message=this.ParseParameters(request);
		
		r.println("Your Message was Received!!!==>"+message);
	
		
	    doBroadcastMessage("HI from Server =>https://10.101.96.224:8443/backendServlets/serverInput\n"+message, request, response);
	}

	
	
	protected void doBroadcastMessage(String msg,HttpServletRequest request,HttpServletResponse response)
	{
		// We'll collect the "CollapseKey" and "Message" values from our JSP page
       // String collapseKey = "";
        String userMessage = msg;
         
        try 
        {
        //userMessage = request.getParameter("Message");
        // collapseKey = request.getParameter("CollapseKey");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            return;
        }
 
        // Instance of com.android.gcm.server.Sender, that does the
        // transmission of a Message to the Google Cloud Messaging service.
        Sender sender = new Sender(Sender_id);
         
        // This Message object will hold the data that is being transmitted
        // to the Android client devices.  For this demo, it is a simple text
        // string, but could certainly be a JSON object.
        Message message = new Message.Builder()
         
        // If multiple messages are sent using the same .collapseKey()
        // the android target device, if it was offline during earlier message
        // transmissions, will only receive the latest message for that key when
        // it goes back on-line.
        //.collapseKey(collapseKey)
        .timeToLive(30)
        .delayWhileIdle(true)
        .addData("messageServer", userMessage)
        .build();
         
        try 
        {
            // use this for multicast messages.  The second parameter
            // of sender.send() will need to be an array of register ids.
            MulticastResult result = sender.send(message, androidDevices, 1);
             
            if (result.getResults() != null) 
            {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) 
                {
                     
                }
            
                System.out.println("Broadcast Success: " + result.getSuccess());
                
            } 
            
            else 
            {
                int error = result.getFailure();
                System.out.println("Broadcast failure: " + error);
            }
             
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
 
        // We'll pass the CollapseKey and Message values back to index.jsp, only so
        // we can display it in our form again.
        //request.setAttribute("CollapseKey", collapseKey);
        // request.setAttribute("Message", userMessage);
         
        //request.getRequestDispatcher("index.jsp").forward(request, response);

	}
}
