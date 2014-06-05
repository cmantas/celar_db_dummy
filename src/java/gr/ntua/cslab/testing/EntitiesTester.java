/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.testing;

import gr.ntua.cslab.database.Tables;
import gr.ntua.cslab.database.entities.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import org.json.JSONObject;


/**
 *
 * @author cmantas
 */
public class EntitiesTester {
    
    public static User chris;
    public static Application test_app;
    public static Module cassandra;
    public static Module apache;
    public static Deployment deployment;
    public static ProvidedResource tinyVM, bigVM;
    public static Spec coreCount, ramSize;
    public static Component component;
    public static ResizingAction ra;
    public static Resource resource1, resource2;
    public static Metric cpuLoad;
    public static MetricValue metricValue;
    public static Dependency moduleDependency;
    public static ResourceType vm;
    
    static boolean DEBUG=true;
    
    public static void createStructure(){
        try {
            
        System.out.println("=================  Create Dummy Structure==========================");
            
        //create, save and retrieve a user
        int userId = Tables.usertable.insertUser("chris");
        chris = new User(userId);
        if(DEBUG) System.out.println("User: " + chris);

        //TODO application id as string
        //create, save and retrieve an app
        test_app = new Application("test_application", new Timestamp(System.currentTimeMillis()), chris);
        test_app.store();
        if(DEBUG) System.out.println("Application: " + new Application(test_app.getId()));
        
        //create, save and retrieve a module
        cassandra = new Module("Cassandra store", test_app);
        cassandra.store();
        if(DEBUG) System.out.println("Module: " + new Module(cassandra.getId()));
        
        //create another module
        apache = new Module("Appache Webserver", test_app);
        apache.store();
        
        //create a module Dependency
//        moduleDependency=new Dependency(apache,cassandra,"depends on storage",Dependency.MODULE);
//        moduleDependency.store();
        //TODO retreive module Dependencies
        
        //create, save and retrieve a deployment (alternative retreive)
        deployment = new Deployment(test_app, new Timestamp(System.currentTimeMillis()), null);
        deployment.store();
        deployment = Tables.deplTable.getDeployment(deployment.getId());
        if(DEBUG) System.out.println("Deployment: " + deployment);
        
        //create, save and retrieve a resource type
        vm = new ResourceType("VM");
        vm.store();
        System.out.println(new ResourceType( vm.getId() ));
        
        //create, save and retrieve a provided resource
        tinyVM = new ProvidedResource(vm.getId(), "tiny VM");
        tinyVM.store();
        System.out.println(new ProvidedResource(tinyVM.getId()));
        
        
        //create and retreive a resource spec
        coreCount = new Spec(tinyVM, "cores", "2");
        coreCount.store();
        System.out.println(new Spec(coreCount.getId()));
        ramSize = new Spec(tinyVM, "ram", "2048");
        ramSize.store();
        
        //create save and retreive a component
        component = new Component(cassandra, "seed node",vm);
        component.store();
        if(DEBUG) System.out.println("Component: " + new Component(component.getId()));
        
        //create, save and retreive a resizing action
        ra = new ResizingAction(cassandra, component, "add a vm");
        ra.store();
        if(DEBUG) System.out.println(new ResizingAction(ra.getId()));
        
        //create, save and retreive a resource
        resource1 = new Resource(deployment, component, tinyVM,  new Timestamp(System.currentTimeMillis()), null);
        resource1.store();
        System.out.println("Resource: "+new Resource(resource1.getId()));
        
//        //create, save and retreive a metric
//        cpuLoad = new Metric(component, new Timestamp(System.currentTimeMillis()));
//        cpuLoad.store();
//        cpuLoad = Tables.metricsTable.getMetric(cpuLoad.getId());
        
//        if(DEBUG) System.out.println("Metric cpu load: " + cpuLoad);
//        //create, save and retreive a metric Value
//        metricValue = new MetricValue(cpuLoad, resource1,
//                new Timestamp(System.currentTimeMillis()));
//        metricValue.store();
//        metricValue = Tables.mvTable.getMetricValue(metricValue.getId());
//        if(DEBUG) System.out.println("Metric value: " + metricValue);
             
        
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
}
    
    public static void testJSON() throws NotInDBaseException{
        
        try{
        
        System.out.println("\n\n=================  JSON Entities ==========================");
        
        
        chris =new User(chris.toJSONObject());
        test_app = new Application(test_app.toJSONObject());
        cassandra = new Module( cassandra.toJSONObject());
        deployment = new Deployment( deployment.toJSONObject());
        vm = new ResourceType(vm.toJSONObject());
        
        resource1=new Resource(resource1.toJSONObject());
        ra=new ResizingAction(ra.toJSONObject());        
        component = new Component(component.toJSONObject());        
        coreCount = new Spec(coreCount.toJSONObject());
        tinyVM = new ProvidedResource(tinyVM.toJSONObject());
        
        }catch(Exception e){e.printStackTrace();}
    }
    
    public static void deleteStructure(){
        
            resource1.delete();
            ra.delete();
            component.delete();
            //TODO Delete module dependency
            apache.delete();
            cassandra.delete();
            coreCount.delete();
            ramSize.delete();
            tinyVM.delete();
            vm.delete();
            deployment.delete();
            test_app.delete();
            chris.delete();
    }
    

    


    public static void main(String args[]) throws NotInDBaseException, FileNotFoundException, IOException {

        //create a dummy structure of entities in the database
        createStructure();
        
        //test the JSON functionalities of the Entity Classes
        testJSON();
        
        Tables.clearDB();
 

    }//main

   
    


}
