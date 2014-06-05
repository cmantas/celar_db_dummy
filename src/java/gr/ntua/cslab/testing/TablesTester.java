/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.testing;



import static gr.ntua.cslab.database.Tables.*;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author cmantas
 */
public class TablesTester {

	public static void main(String args[]){
            
            try{

		// insert data in the tables
		System.out.println("===================  INSERT  =====================");
		//create a user
		int userId=usertable.insertUser("christaras" );
		System.out.print("Inserted chris. exists: ");
		System.out.println(usertable.exists(userId));
                
		//create an application
                // TODO create "new" application
		String appId=appTable.insertApplication(1, 2, 3,"chris's app", userId);
		System.out.println("inserted app  app with id "+appId+" exists: "+appTable.exists(appId));
                
		//create a module
		int moduleId=moduleTable.insertModule(appId,"chris's apache" );
		System.out.println("inserted module exists: "+moduleTable.exists(moduleId));
                
//		//create a second module
		int module2Id=moduleTable.insertModule(appId,"chris's hbase" );
		System.out.println("inserted module chris's hbase"+moduleId);
                
//		//create a module dependency
		modDep.insertModuleDependency(moduleId, module2Id, "needs");
		System.out.println("chris's apache dependencies: "+modDep.findDependencies(moduleId));
                
                //create a resource type
                int resourceTypeID = resTypeTable.insertResourceType("VM");
		System.out.println("inserted resource Type. exists :"+resTypeTable.exists(resourceTypeID));
           
		//create a provided resource
		int providedResourceId=provResTable.insertProvidedResource(resourceTypeID, "dummy_vm");
		System.out.println("provided resource ("+providedResourceId+") exists: "+provResTable.exists(providedResourceId));
                
                //create a spec entry
		int specId=specsTable.insertSpecs(providedResourceId, "cores", "2");
		System.out.println("spec with id="+specId+" inserted. exists: "+specsTable.exists(specId));
                
		//create a component for an existing module with an existing provider resource
		int compId=componentTable.insertComponent("seed node", module2Id, resourceTypeID);
		System.out.println("Component with id="+compId+" inserted. exists="+componentTable.exists(compId));
                
		//create a component dependency and look it up
		componentDependencyTable.insertModuleDependency(compId, compId, "self");
		List componentDependencies=componentDependencyTable.findDependencies(compId);
		System.out.println("chris's component dependencies: "+componentDependencies);
                
		//create a deployment and check it exists
		int deplId=deplTable.insertDeployment(appId, new Timestamp(System.currentTimeMillis()), null);
		System.out.println("deployment ("+deplId+") inserted. exists="+deplTable.exists(deplId));
		//create a resource and check it exists
		int resourceId=resTable.insertResource(deplId, compId, 
			providedResourceId, new Timestamp(System.currentTimeMillis()), null);
		System.out.println("resource with id "+resourceId+" inserted"
			+" exists: "+resTable.exists(resourceId));
		//create a resizing action acn check it exists
		int raId=raTable.insertResizingAction(moduleId, compId, "add_vm");
		System.out.println("resizing action with id "+raId+" inserted"
			+" exists: "+raTable.exists(raId));
		//create a decision 
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		System.out.println("decision created="+desTable.insertDecision(ts, raId, 2));
		//create a metric for a previously created component
		int metricId=metricsTable.insertMetric(compId, ts);
		System.out.println("metric with id "+metricId+" inserted"
			+" exists: "+metricsTable.exists(metricId));
		//create a metric value for a previously created metric value, component, resource
		int mvId=mvTable.insertMetricValue(metricId, resourceId, 
			new Timestamp(System.currentTimeMillis()));
		System.out.println("metric value with id "+mvId+" inserted"
			+" exists: "+mvTable.exists(mvId));
		
		
	

		
		//delete the things you inserted
		System.out.println("\n===================  DELETE  =====================");
		mvTable.delete(mvId);
		System.out.println("Deleted metric value with id="+mvId+" exists="
			+mvTable.exists(mvId));
		metricsTable.delete(metricId);
		System.out.println("Deleted metric with id="+metricId+" exists="
			+metricsTable.exists(metricId));
		System.out.println("Deleted decision. rows affected: "+desTable.deleteDecision(ts));
		raTable.delete(raId);
		System.out.println("deleted resizing action with id="+raId
			+" exists="+raTable.exists(raId));
		resTable.delete(resourceId);
		System.out.println("deleted resource with id="+resourceId
			+" exists="+resTable.exists(resourceId));
		deplTable.delete(deplId);
		System.out.println("deleted deployment ("+deplId+") exists:"+deplTable.exists(deplId));
		componentDependencyTable.deleteDependency(compId, compId);
		System.out.println("chris's component dependencies after delete: "+componentDependencyTable.findDependencies(compId));
		componentTable.delete(compId);
		System.out.println("deleted component. exists: "+componentTable.exists(compId));
		specsTable.delete(specId);
		System.out.println("deleted sppec. exists:"+specsTable.exists(specId));
		provResTable.delete(providedResourceId);
		System.out.println("deleted prov resouce. exists: "+provResTable.exists(providedResourceId));
                resTypeTable.delete(resourceTypeID);
		System.out.println("deleted resouce type. exists: "+resTypeTable.exists(resourceTypeID));
		modDep.deleteDependency(moduleId, module2Id);
		System.out.println("chris's apache dependencies after delete: "+modDep.findDependencies(moduleId));
		moduleTable.delete(moduleId);
		System.out.println("deleted chris's hbase. exists: "+moduleTable.exists(moduleId));
		moduleTable.delete(module2Id);
		System.out.println("deleted chris's apache. exists: "+moduleTable.exists(module2Id));
		appTable.delete(appId);
		System.out.println("deleted chris's app. exists: "+appTable.exists(appId));
		usertable.delete(userId);
		System.out.println("deleted chris. exists:"+usertable.exists(userId));
		
                
            }
            catch (Exception e){ e.printStackTrace();}
            
                //positively clear the db
                clearDB();
		//close connections
		closeConnections();
		
	}
	
}
