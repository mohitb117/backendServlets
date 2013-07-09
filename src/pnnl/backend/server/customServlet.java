package pnnl.backend.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class customServlet
 */
@WebServlet("/serverInput")
public class customServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public customServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
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

	    r.println("Your Message was Received!!!==>"+this.ParseParameters(request));
		
	}

	 /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter r=response.getWriter();
 
		r.println("Your Message was Received!!!==>"+this.ParseParameters(request));
	}

}
