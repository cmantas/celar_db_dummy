package gr.ntua.cslab.deployment;

import gr.ntua.cslab.JSONServlet;
import gr.ntua.cslab.database.entities.Application;
import gr.ntua.cslab.database.entities.Deployment;
import static gr.ntua.cslab.database.entities.JSONTools.parseApplicationDeploymentConfig;
import gr.ntua.cslab.database.entities.NotInDBaseException;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import org.json.JSONObject;

/**
 *
 * @author cmantas
 */
@WebServlet(name = "Deploy", urlPatterns = {"/deployment/deploy"})
public class Deploy extends JSONServlet {

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Deploys the requested application using the provided configuration (allocation of resources to components)";
    }

    @Override
    public Iterable<String> requestJSONParameters() {
        String[] params = {"deployment_configuration"};
        return Arrays.asList(params);
    }

    @Override
    public Iterable<String> requestStringParameters() {
        String[] params = {"ApplicationId"};
        return Arrays.asList(params);
    }

    @Override
    public void processRequest(Map<String, JSONObject> inputJSONParams, Map<String, String> inputStringParams) {
        String appId = inputStringParams.get("ApplicationId").trim();
        try {
            JSONObject in_configuration = inputJSONParams.get("deployment_configuration");
            Application app = new Application(appId);
            Deployment depl = new Deployment(app, new java.sql.Timestamp(System.currentTimeMillis()), null);
            depl.store();
            parseApplicationDeploymentConfig(in_configuration, app, depl, true);
            print(depl.getId());
        } catch (NotInDBaseException ndb) {
            print("there is no app with id: " + appId + " in the DB");
        }

    }

    @Override
    public byte getType() {
        return INT_TYPE;
    }

}
