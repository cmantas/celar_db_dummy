/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.database.entities;

import gr.ntua.cslab.database.Tables;
import static gr.ntua.cslab.database.Tables.clearDB;
import gr.ntua.cslab.database.entities.*;
import static gr.ntua.cslab.database.entities.Entities.chris;
import java.sql.Timestamp;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author cmantas
 */


// important that the tests run in this order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class EntitiesTest {

    public EntitiesTest() {
    }
    
    @After
    public void destroy(){
        clearDB();
    }

    @Test
    public void test_01_basic() throws Exception {
        
        User chris;
        Application test_app;
        Module cassandra;
        Module apache;
        Deployment deployment;
        ProvidedResource tinyVM, bigVM;
        Spec coreCount, ramSize;
        Component component;
        ResizingAction ra;
        Resource resource1, resource2;
        Metric cpuLoad;
        MetricValue metricValue;
        Dependency moduleDependency;
        ResourceType vm;

        System.out.println("\n=================  TEST Dummy Entities Structure==========================");

        //create, save and retrieve a user
        int userId = Tables.usertable.insertUser("chris");
        chris = new User(userId);
        System.out.println("User: " + chris);


        //TODO application id as string
        //create, save and retrieve an app
        test_app = new Application("test_application", new Timestamp(System.currentTimeMillis()), chris);
        test_app.store();
        System.out.println("Application: " + new Application(test_app.getId()));


        //create, save and retrieve a module
        cassandra = new Module("Cassandra store", test_app);
        cassandra.store();

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
            System.out.println("Deployment: " + deployment);

        //create, save and retrieve a resource type
        vm = new ResourceType("VM");
        vm.store();
        System.out.println(new ResourceType(vm.getId()));

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
        component = new Component(cassandra, "seed node", vm);
        component.store();
            System.out.println("Component: " + new Component(component.getId()));

        //create, save and retreive a resizing action
        ra = new ResizingAction(cassandra, component, "add a vm");
        ra.store();
            System.out.println(new ResizingAction(ra.getId()));
        //create, save and retreive a resource
        resource1 = new Resource(deployment, component, tinyVM, new Timestamp(System.currentTimeMillis()), null);
        resource1.store();
        System.out.println("Resource: " + new Resource(resource1.getId()));
        clearDB();
    }
    
    @Test
    public void test_02_entityClasses_JSON(){
                try{
        
        System.out.println("\n\n=================  TEST Entities basic JSON==========================");
        
        //create a User
        chris = new User("chris");
        chris.store();
        Application test_app = new Application("test_application", new Timestamp(System.currentTimeMillis()), chris);
        test_app.store();
        //create, a module
        Module cassandra = new Module("Cassandra store", test_app);
        cassandra.store();
        //create another module
        Module apache = new Module("Appache Webserver", test_app);
        apache.store();
        //crete a deployment
        Deployment deployment = new Deployment(test_app, new Timestamp(System.currentTimeMillis()), null);
        deployment.store();
        //create resource type
        ResourceType vm = new ResourceType("VM");
        vm.store();
        System.out.println(new ResourceType(vm.getId()));
        //create, save and retrieve a provided resource
        ProvidedResource tinyVM = new ProvidedResource(vm.getId(), "tiny VM");
        tinyVM.store();
        System.out.println(new ProvidedResource(tinyVM.getId()));
        //create and retreive a resource spec
        Spec coreCount = new Spec(tinyVM, "cores", "2");
        coreCount.store();
        Spec ramSize = new Spec(tinyVM, "ram", "2048");
        ramSize.store();
        //create save and retreive a component
        Component component = new Component(cassandra, "seed node", vm);
        component.store();
        //create, save and retreive a resizing action
        ResizingAction ra = new ResizingAction(cassandra, component, "add a vm");
        ra.store();
        //create, save and retreive a resource
        Resource resource1 = new Resource(deployment, component, tinyVM, new Timestamp(System.currentTimeMillis()), null);
        resource1.store();
        System.out.println("Resource: " + new Resource(resource1.getId()));
        
        
        //recreate all entities based on their JSON output
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
        
        }catch(Exception e){
            e.printStackTrace();
            fail("failed to recreate Entities from JSON");
        }
    }

}
