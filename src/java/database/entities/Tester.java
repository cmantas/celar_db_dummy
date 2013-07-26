/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import database.ApplicationTable;
import database.ComponentDependencyTable;
import database.ComponentTable;
import database.DecisionsTable;
import database.DeploymentTable;
import database.MetricValueTable;
import database.MetricsTable;
import database.ModuleDependencyTable;
import database.ModuleTable;
import database.ProvidedResourceTable;
import database.ResizingActionsTable;
import database.ResourcesTable;
import database.SpecsTable;
import database.UserTable;
import java.sql.Timestamp;

/**
 *
 * @author cmantas
 */
public class Tester {

public static void main(String args[]) throws NotInDBaseException{

		//init table objects
		UserTable usertable = new UserTable(true);
		ModuleTable moduleTable=new ModuleTable(true);
		ApplicationTable appTable=new ApplicationTable(true);
		ModuleDependencyTable modDep=new ModuleDependencyTable(true);
		ProvidedResourceTable provResTable=new ProvidedResourceTable(true);
		SpecsTable specsTable=new SpecsTable(true);
		ComponentTable compTable=new ComponentTable(true);
		ComponentDependencyTable compDep=new ComponentDependencyTable(true);
		DeploymentTable deplTable=new DeploymentTable(true);
		ResourcesTable resTable=new ResourcesTable(true);
		ResizingActionsTable raTable=new ResizingActionsTable(true);
		DecisionsTable desTable=new DecisionsTable(true);
		MetricsTable metricsTable=new MetricsTable(true);
		MetricValueTable mvTable=new MetricValueTable(true);

		int userId=usertable.insertUser("christaras" );
		System.out.print("Inserted chris. exists: ");
		System.out.println(usertable.exists(userId));
		//create and retrieve some users	
		User chris=new User(userId,usertable);
		System.out.println("The of user you inserted="+chris);
		User john=new User("John", usertable);
		john.store();
		System.out.println("John exists:"+usertable.exists(john.id));
		//create and retrieve an app
		Application facebook=new Application("social net", 
			new Timestamp(System.currentTimeMillis()), john, appTable);
		facebook.store();
		facebook=new Application(facebook.id, appTable);
		System.out.println("Your app: "+facebook);
		//create and retrieve a module
		Module cassandra=new Module("Cassandra store",facebook, moduleTable);
		cassandra.store();
		cassandra=new Module(cassandra.id,moduleTable);
		System.out.println("A module: "+cassandra);
		//create and retrieve a deployment (alternative retreive)
		Deployment dep=new Deployment(facebook,	
			new Timestamp(System.currentTimeMillis()),null, deplTable);
		dep.store();
		dep=deplTable.getDeployment(dep.id);
		System.out.println("Deployment: "+dep);
		//create and retrieve a provided resource
		ProvidedResource provRes=new ProvidedResource("VM","tiny", provResTable);
		provRes.store();
		provRes=provResTable.getProvidedResource(provRes.id);
		System.out.println("Prov. Resource: "+provRes);
		//create and retrieve a spec
		Spec spec=new Spec(provRes,"dual core machine", specsTable);
		spec.store();
		spec=specsTable.getSpec(spec.id);
		System.out.println("Spec: "+spec);
		//create and save a component
		Component component=new Component(cassandra, provRes,compTable);
		component.store();
		component=compTable.getComponent(component.id);
		System.out.println("Component: "+component);
		//create, save and retreive a resizing action
		ResizingAction ra=new ResizingAction(cassandra,component,"add a vm",raTable);
		ra.store();
		ra=raTable.getResizingAction(ra.id);
		System.out.println("Resizing action: "+ ra);
		//create, save and retreive a resource
		Resource resource =new Resource(dep,component, provRes,
			new Timestamp(System.currentTimeMillis()), null, resTable);
		resource.store();
		resource=resTable.getResource(resource.id);
		System.out.println("Resource: "+resource);
		//create, save and retreive a metric
		Metric cpuLoad=new Metric(component, new Timestamp(System.currentTimeMillis()), metricsTable);
		cpuLoad.store();
		cpuLoad=metricsTable.getMetric(cpuLoad.id);
		System.out.println("Metric cpu load: "+cpuLoad);
		//create, save and retreive a metric Value
		MetricValue metricValue=new MetricValue(cpuLoad,resource, 
			new Timestamp(System.currentTimeMillis()), mvTable);
		metricValue.store();
		metricValue=mvTable.getMetricValue(metricValue.id);
		System.out.println("Metric value: "+metricValue);
		

		//Delete the entries created
		metricValue.delete();
		cpuLoad.delete();
		resource.delete();
		ra.delete();
		component.delete();
		spec.delete();
		provRes.delete();
		dep.delete();
		cassandra.delete();
		facebook.delete();
		usertable.delete(userId);
		john.delete();
		System.out.println("Deleted john. Exists:"+usertable.exists(john.id));


		//close connections
		usertable.closeConnection();
		moduleTable.closeConnection();
		appTable.closeConnection();
		modDep.closeConnection();
		provResTable.closeConnection();
		compTable.closeConnection();
		compDep.closeConnection();
		deplTable.closeConnection();
		resTable.closeConnection();
		raTable.closeConnection();
		desTable.closeConnection();
		metricsTable.closeConnection();
		mvTable.closeConnection();

}//main
	
}
