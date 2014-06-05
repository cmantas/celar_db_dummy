/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.testing;

import static gr.ntua.cslab.database.Tables.*;
import gr.ntua.cslab.database.entities.Application;
import static gr.ntua.cslab.database.entities.JSONTools.*;
import java.sql.Timestamp;
import org.json.JSONObject;

/**
 *A class whose main method tests  some functionalities of the JSONTools class
 * @author cmantas
 */
public class JSONTester extends EntitiesTester{
    
    
        private static void resetBaseline(){
            //clear the DB
            clearDB();
            //store the users and some dummy resources for an application
            vm.store();
            tinyVM.store();
            coreCount.store();
            ramSize.store();
            chris.store();
        }
        
        public static void main(String args[]){
        try{
            //create a DB structure using entities tester class methods
            createStructure();
            System.out.println("\n\n =================== JSON Utils =================================");
            
            //export the application description to a JSONObject
            JSONObject appDescriptionJson = exportApplicationDescription(test_app, new Timestamp(System.currentTimeMillis()));
            
            //System.out.println(appDescriptionJson.toString(3));
            
            //reset the db to a state containing some resources and a user so that you can insert new applications
            resetBaseline();
            
            //create again the application structure from its JSON description and store it
            System.out.println("\n --> Test Auto-generated JSON");
            parseApplicationDescription(appDescriptionJson, true);
            
            // export provided resources of a type
            //System.out.println(exportProvidedResources("VM").toString(3));
            
            resetBaseline();
            
            //create again the application structure from a JSON description loaded from a file
            System.out.println("\n --> Test JSON from file");
            appDescriptionJson = loadJSONObjectFromFile("resources/data_files/application_description.json");
            //System.out.println(appDescriptionJson.toString(3));
            //create again the application structure from its JSON description and store it
            Application app = parseApplicationDescription(appDescriptionJson, true);
            
            //add a deployment from a file
            System.out.println("\n --> Test Deployment Configuration from file");
            JSONObject appDeploymentConfig = loadJSONObjectFromFile("resources/data_files/application_configuration.json");
            deployment.store();
            parseApplicationDeploymentConfig(appDeploymentConfig, app, deployment, true);
            
        }catch(Exception e){ e.printStackTrace();}
        finally {
            clearDB();
            closeConnections();
        }
        
            
            
        }
    
}
