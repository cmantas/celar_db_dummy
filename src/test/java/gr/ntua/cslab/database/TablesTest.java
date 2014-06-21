/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.ntua.cslab.database;

import static gr.ntua.cslab.database.Tables.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author cmantas
 */
public class TablesTest {
    
    public TablesTest() {
    }
    
    @After
    public void clear(){
        System.out.println("Clearing Db");
        clearDB();
    }

    /**
     * Test of closeConnections method, of class Tables.
     */
    @Test
    @Ignore
    public void testTables() throws SQLException {
        	// insert data in the tables
		System.out.println("===================  TEST TABLES  =====================");
		//create a user
		int userId=usertable.insertUser("christaras" );
		assertTrue("User exists in table", usertable.exists(userId));
                
		//create an application
                // TODO create "new" application
		String appId=appTable.insertApplication(1, 2, 3,"chris's app", userId);    
                assertTrue("Application exists in table", appTable.exists(appId));
                
		//create a module
		int moduleId=moduleTable.insertModule(appId,"chris's apache" );
		assertTrue("module apache exists in table",moduleTable.exists(moduleId));
                
//		//create a second module
		int module2Id=moduleTable.insertModule(appId,"chris's hbase" );
		assertTrue("module hbase exists in table",moduleTable.exists(module2Id));
                
//		//create a module dependency
		modDep.insertModuleDependency(moduleId, module2Id, "needs");
		System.out.println("chris's apache dependencies: "+modDep.findDependencies(moduleId));
                
                //create a resource type
                int resourceTypeID = resTypeTable.insertResourceType("VM");
		assertTrue("resource type exists in table",resTypeTable.exists(resourceTypeID));
           
		//create a provided resource
		int providedResourceId=provResTable.insertProvidedResource(resourceTypeID, "dummy_vm");
		assertTrue("provided resource exists in table",provResTable.exists(providedResourceId));
                
                //create a spec entry
		int specId=specsTable.insertSpecs(providedResourceId, "cores", "2");
		assertTrue("spec exists in table",specsTable.exists(specId));
                
		//create a component for an existing module with an existing provider resource
		int compId=componentTable.insertComponent("seed node", module2Id, resourceTypeID);
		assertTrue("component exists in table",componentTable.exists(compId));
                
                //TODO
		//create a component dependency and look it up
		componentDependencyTable.insertModuleDependency(compId, compId, "self");
		List componentDependencies=componentDependencyTable.findDependencies(compId);
		System.out.println("chris's component dependencies: "+componentDependencies);
                
		//create a deployment and check it exists
		int deplId=deplTable.insertDeployment(appId, new Timestamp(System.currentTimeMillis()), null);
		assertTrue("deployment exists in table",deplTable.exists(deplId));
                
		//create a resource and check it exists
		int resourceId=resTable.insertResource(deplId, compId, 
			providedResourceId, new Timestamp(System.currentTimeMillis()), null);
		assertTrue("resource exists in table", resTable.exists(resourceId));
                
		//create a resizing action acn check it exists
		int raId=raTable.insertResizingAction(moduleId, compId, "add_vm");
		assertTrue("resizing action exists in table",raTable.exists(raId));
                
		//create a decision 
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		desTable.insertDecision(ts, raId, 2);
                
		//create a metric for a previously created component
		int metricId=metricsTable.insertMetric(compId, ts);
		assertTrue("metric exists in table", metricsTable.exists(metricId));
                
		//create a metric value for a previously created metric value, component, resource
		int mvId=mvTable.insertMetricValue(metricId, resourceId, 
			new Timestamp(System.currentTimeMillis()));
		assertTrue("metric value exists in table",mvTable.exists(mvId));
    }

    


}
    

