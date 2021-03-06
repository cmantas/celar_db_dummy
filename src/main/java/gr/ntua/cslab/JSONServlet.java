package gr.ntua.cslab;

/**
 *
 * @author cmantas
 */
import gr.ntua.cslab.db_entities.DBException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public abstract class JSONServlet extends HttpServlet {

    //response types
    public final static byte JSON_TYPE = 1;
    public final static byte HTML_TYPE = 2;
    public final static byte INT_TYPE = 3;

    /**
     * indentation factor for the response
     */
    public final static byte INDENT = 2;

    /**
     * the output writer
     */
    private PrintWriter out;

    /**
     * the logger
     */
    public Logger logger;
    
    protected HttpServletRequest request;
    
    
    public JSONServlet(){
        this.logger = Logger.getLogger(this.getClass());
    }

   //======================= abstract  methods ===============================
    /**
     * @return the type of the response
     */
    public abstract byte getType();

    /**
     * @return the JSON parameters expected by this servlet as input
     */
    public abstract Iterable<String> requestJSONParameters();

    /**
     * @return the String parameters expected by this servlet as input
     */
    public abstract Iterable<String> requestStringParameters();

    /**
     * Processes the incoming request (as 2 maps of params-> values)
     *
     * @param inputJSONParameters the input JSON parameters
     * @param inputStringParameters the rest of the input parameters
     */
    public abstract void processRequest(Map<String, JSONObject> inputJSONParameters,
            Map<String, String> inputStringParameters) throws DBException;

    
  //========================  JSON Servlet core methods  ======================  
  
    /**
     * prints a json object in the output
     *
     * @param jo the JSON Object to print
     */
    public void print(JSONObject jo) {printString(jo.toString(INDENT));}
    public void print(JSONArray jo) {printString(jo.toString(INDENT));}

    /**
     * prints a string object in the output
     *
     * @param str
     */
    public void print(String str) { printString(str); }
    public void print(int i) { printString("" + i); }
    public void printString(String msg) {  out.println(msg); }

    /**
     * prints the info of this servlet in an HTML format
     */
    public void printInfo() {
        print("<html>\n<h2>servlet info</h2>");
        print(this.getServletInfo());
        print("<h3>JSON Parameters</h3>\n<ul>");
        for (String param : requestJSONParameters()) {
            print("\t<li>" + param + "</li>");
        }
        print("</ul>\n<h3>String parameters</h3>\n<ul>");
        for (String param : requestStringParameters()) {
            print("\t<li>" + param + "</li>");
        }
        print("</ul>\n<h3>return type</h3>");
        switch (getType()) {
            case JSON_TYPE: print("JSON"); break;
            case HTML_TYPE:  print("(X)HTML"); break;
            case INT_TYPE:  print("Integer"); break;
            default: print("text");
        }
        print("</html>");
    }

    /**
     * @param type the byte type of the servlet
     * @return The web MIME type of this page
     */
    protected String responseType(byte type) {
        switch (type) {
            case JSON_TYPE: return "text;charset=UTF-8";
            case HTML_TYPE: return "text/html;charset=UTF-8";
            default:        return "text;charset=UTF-8";
        }
    }

    /**
     *
     * @param parameter
     * @return
     */
    protected String getStringParameter(String parameter) {
        String param = request.getParameter(parameter);
        if (param == null){
            JSONObject rv = new JSONObject();
            rv.put("error_type", "missing parameter");
            rv.put("error_details", "parameter '"+parameter + "'is missing from string input");
            print(rv);
        }
        return param;
        

    }

    /**
     *
     * @param parameter
     * @return
     */
    public JSONObject getJSONParameter(String parameter) {
        try {
            String paramStr = request.getParameter(parameter);
            if (paramStr == null) {
                 JSONObject rv = new JSONObject();
                rv.put("error_type", "missing parameter");
                rv.put("error_details", "parameter '"+parameter + "' is missing from JSON input");
                print(rv);
                return null;
            }
            return new JSONObject(paramStr);
        } catch (JSONException je) {
            out.println("invalid json format for argument: " + parameter);
        }
        return null;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        response.setContentType(responseType(this.getType()));
        out = response.getWriter();

        try {
            //check if the info parameter is given and if so just print the info
            if (request.getParameter("info") != null) {
                response.setContentType(responseType(HTML_TYPE));
                printInfo();
                return;
            }
            //load all of the json parameters
            Map<String, JSONObject> inputJSONParameters = new java.util.HashMap(5);
            if (requestJSONParameters() != null) {
                for (String param : requestJSONParameters()) {
                    JSONObject j = getJSONParameter(param);
                    if (j == null) {
                        logger.error("found a missing param");
                        return;
                    }
                    inputJSONParameters.put(param, j);
                }
            }
            //load the rest of the parameters
            Map<String, String> inputStringParams = new java.util.HashMap(5);
            if (requestStringParameters() != null) {
                for (String param : requestStringParameters()) {
                    String p =getStringParameter(param);
                    if (p==null) return;
                    inputStringParams.put(param, p);
                }
            }
            
            try{
                //actually process the input parameters after parsing and checking them
                processRequest(inputJSONParameters, inputStringParams);
            }catch(DBException e){
                JSONObject rv = new JSONObject();
                rv.put("error_type", e.getType());
                rv.put("error_details", e.getDetail());
                print(rv);
            }
        } catch (JSONException e) {
            JSONObject rv = new JSONObject();
                rv.put("error_type", "invalid JSON");
                rv.put("error_details","error parsing JSON Input");
                print(rv);
        } catch (Exception e){
            logger.error("Uknown exception: \n"+e.toString());
            e.printStackTrace();
        }
        
        finally {
            out.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    abstract public String getServletInfo();

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. ">
	/**
	 * Handles the HTTP request
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
	}// </editor-fold>


}