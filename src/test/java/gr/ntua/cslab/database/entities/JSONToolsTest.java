/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.database.entities;

import gr.ntua.cslab.database.DBException;
import static gr.ntua.cslab.database.Tables.clearDB;
import static gr.ntua.cslab.database.entities.JSONTools.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author cmantas
 */
// important that the tests run in this order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JSONToolsTest {
    
        
    public static User chris;
    public static ProvidedResource tinyVM, bigVM;
    public static Spec coreCount, ramSize;
    public static ResourceType vm;
    
    public JSONToolsTest() {
    }
    
    @Before
    public void createBaseline(){
        try{
            
        chris = new User("chris");
        chris.store();        
        //create, save and retrieve a resource type
        vm = new ResourceType("VM");
        vm.store();
        //create, save and retrieve a provided resource
        tinyVM = new ProvidedResource(vm.getId(), "tiny VM");
        tinyVM.store();
        //create and retreive a resource spec
        coreCount = new Spec(tinyVM, "cores", "2");
        coreCount.store();
        ramSize = new Spec(tinyVM, "ram", "2048");
        ramSize.store();
        
        } catch(Exception e){
            e.printStackTrace();
            fail("failed to create baseline entities to before testing JSON Tools");
        }
    }
    
    @After
    public void clear(){
        System.out.println("------> Clearing the DB ");
        clearDB();
    }

    static JSONObject appDescriptionJson;
    
    @Test
    public void test_01_ExportApplicationDescription() throws DBException {
        try {
            //create, save and retrieve an app
            Application test_app = new Application("test_application", new Timestamp(System.currentTimeMillis()), chris);
            test_app.store();
            //export the application description to a JSONObject
            appDescriptionJson = exportApplicationDescription(test_app, new Timestamp(System.currentTimeMillis()));
            
        } catch (NotInDBaseException ex) {
            fail("failed to export an application");
        }
    }
    
    @Test
    public void test_02_ParseApplicationDescription() {
        //create again the application structure from its JSON description and store it
        System.out.println("\n ================  TEST Auto-generated JSON  ===========================");
        try{
            parseApplicationDescription(appDescriptionJson, true);
        }catch(Exception e){
            System.out.println("Error Message: "+ e.getMessage());
            e.printStackTrace();
            fail("failed to import an application description from JSON");
        }
    }
    
    static Application app;
    static Deployment deployment;
    
    @Test
    public void test_03_ParseApplicationDescription_from_file() {
        
        //create again the application structure from its JSON description and store it
        System.out.println("\n ================== Test Description JSON from file  ===========================");
        try{
            appDescriptionJson = loadJSONObjectFromFile("src/main/resources/data_files/application_description.json");
            //System.out.println(appDescriptionJson.toString(3));
            //create again the application structure from its JSON description and store it
            app = parseApplicationDescription(appDescriptionJson, true);
            System.out.println(app);
            assertTrue("app exists after parsing description", !app.id.equals("-1"));
            
        }catch(Exception e){
            e.printStackTrace();
            fail("failed to import an application description from JSON");
        }finally{
            //Do not clear for the next test
        }
    }
    
    
    
    @Test
    public void test_04_ParseApplicationDeploymentConfig() throws DBException {
        try {
            //need to parse the Application Description before you parse the configuration
            test_03_ParseApplicationDescription_from_file();
            deployment = new Deployment(app, new Timestamp(System.currentTimeMillis()), null);
            deployment.store();
            //add a deployment from a file
            System.out.println("\n ======================= Test Deployment Configuration from file  ========================== ");
            JSONObject appDeploymentConfig = loadJSONObjectFromFile("src/main/resources/data_files/application_configuration.json");
            parseApplicationDeploymentConfig(appDeploymentConfig, app, deployment, true);
            
            JSONObject config = exportApplicationConfiguration(app, new Timestamp(System.currentTimeMillis()));
            System.out.println(config.toString(3));
            
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
            fail("failed to import an application description from JSON");
        } catch (NotInDBaseException ex) {
            System.err.println(ex.getMessage());
            fail("failed to import an application description from JSON");
        }
    }

    
}
