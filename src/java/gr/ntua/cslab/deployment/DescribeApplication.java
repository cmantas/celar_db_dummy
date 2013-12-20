package gr.ntua.cslab.deployment;

import gr.ntua.cslab.JSONServlet;
import gr.ntua.cslab.database.entities.Application;
import static gr.ntua.cslab.database.entities.JSONTools.parseApplication;
import static gr.ntua.cslab.database.entities.JSONTools.exportApplication;
import static gr.ntua.cslab.database.entities.JSONTools.parseApplicationConfiguration;
import java.util.Map;
import java.util.Arrays;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
public class DescribeApplication extends JSONServlet {

   

    @Override
    public byte getType() {return JSON_TYPE;}

    @Override
    public Iterable<String> requestJSONParameters() {
        String[] params={"application"};
        return Arrays.asList(params);
    }

    @Override
    public Iterable<String> requestStringParameters() {return null;}

    @Override
    public void processJSON(Map<String, JSONObject> inputJSONParameters, Map<String, String> inputStringParameters) {
                Application app=parseApplicationConfiguration(inputJSONParameters.get("application"),true);
                JSONObject app_json=exportApplication(app);
                print(app_json);
    }

    @Override
    public String getServletInfo() {return "inserts an application in the DB";}

}
