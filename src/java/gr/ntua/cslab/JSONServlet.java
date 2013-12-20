package gr.ntua.cslab;

/**
 *
 * @author cmantas
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public abstract class JSONServlet extends HttpServlet {

    //response types
    public final static byte JSON_TYPE=1;
    public final static byte HTML_TYPE=2;

    /**
     * indentation factor for the response
     */
    public final static byte INDENT=2;

    /**
     *the output writer
     */
    private PrintWriter out;

    protected HttpServletRequest request;
       
   //================== abstract  methods ===============================

    /**
     * @return the type of the response
     */
           public  abstract byte getType();

    /**
     * @return the JSON parameters expected by this servlet as input
     */
    public abstract Iterable<String> requestJSONParameters();

    /**
     * @return the String parameters expected by this servlet as input
     */
    public abstract Iterable<String> requestStringParameters();

    /**
     *Processes the incoming request (as 2 maps of params-> values)
     * @param inputJSONParameters the input JSON parameters
     * @param inputStringParameters the rest of the input parameters
     */
    public abstract void processJSON(Map<String, JSONObject> inputJSONParameters, Map<String, String> inputStringParameters);


    /**
     * prints a json object in the output
     * @param jo 
     */
    public void print(JSONObject jo){
        printString(jo.toString(INDENT));
    }
    
    /**
     * prints a string object in the output
     * @param str 
     */
    public void print(String str){
        printString(str);
    }
    public void printString(String msg){
           out.println(msg);
       }

    /**
     *
     * @return
     */
    protected String responseType(){
           switch(getType()){
                   case JSON_TYPE: 
                       return "text;charset=UTF-8";
                   case HTML_TYPE:
                       return "text/html;charset=UTF-8";
                   default:
                       return "text;charset=UTF-8";
           }
       }

    /**
     *
     * @param parameter
     * @return
     */
    protected String getStringParameter(String parameter){
           String param = request.getParameter(parameter);
           if(param==null)
               print("input parameter: "+ parameter + "is missing");
           return param;
    
}

    /**
     *
     * @param parameter
     * @return
     */
    public JSONObject getJSONParameter( String parameter){
           try{
               String paramStr=request.getParameter(parameter);
               return new JSONObject(paramStr);
           }
           catch(JSONException je){
               out.println("invalid json format for argument: "+parameter);
           }
           return null;
       }

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
                this.request=request;
		response.setContentType(responseType());
		out = response.getWriter();
        
		try {
                        //load all of the json parameters
                        Map<String, JSONObject> inputJSONParameters=new java.util.HashMap(5);
                        if(requestJSONParameters()!=null)
                            for (String param:requestJSONParameters()){
                                inputJSONParameters.put(param, getJSONParameter(param));
                            }
                        //load the rest of the parameters
                        Map<String, String> inputStringParams=new java.util.HashMap(5);
                        if(requestStringParameters()!=null)
                            for (String param:requestStringParameters()) 
                                inputStringParams.put(param, getStringParameter(param));
                     
                        processJSON(inputJSONParameters, inputStringParams);
		} 
                catch (Exception e){
                    print("ERROR parsing input parameters - check their validity");
                    e.printStackTrace();
                }
                finally {			
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	abstract public String getServletInfo() ;
}