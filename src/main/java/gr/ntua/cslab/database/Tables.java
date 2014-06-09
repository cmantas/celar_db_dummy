/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.cslab.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pro
 */
public class Tables  extends DBConnectable{
    
                
        	//init table objects
		public static UserTable usertable = new UserTable(true);
		public static ModuleTable moduleTable=new ModuleTable(true);
		public static ApplicationTable appTable=new ApplicationTable(true);
		public static ModuleDependencyTable modDep=new ModuleDependencyTable(true);
		public static ProvidedResourceTable provResTable=new ProvidedResourceTable(true);
		public static SpecsTable specsTable=new SpecsTable(true);
		public static ComponentTable componentTable=new ComponentTable(true);
		public static ComponentDependencyTable componentDependencyTable=new ComponentDependencyTable(true);
		public static DeploymentTable deplTable=new DeploymentTable(true);
                public static ResourceTypeTable resTypeTable=new ResourceTypeTable(true);
		public static ResourcesTable resTable=new ResourcesTable(true);
		public static ResizingActionsTable raTable=new ResizingActionsTable(true);
		public static DecisionsTable desTable=new DecisionsTable(true);
		public static MetricsTable metricsTable=new MetricsTable(true);
		public static MetricValueTable mvTable=new MetricValueTable(true);
                
    final static String killlerSQL = "DELETE FROM \"SPECS\" WHERE TRUE;\n"
            + "DELETE FROM \"METRIC_VALUES\" WHERE TRUE;\n"
            + "DELETE FROM \"METRICS\" WHERE TRUE;\n"
            + "DELETE FROM \"MODULE_DEPENDENCY\" WHERE TRUE;\n"
            + "DELETE FROM \"COMPONENT__DEPENDENCY\" WHERE TRUE;\n"
            + "DELETE FROM \"RESOURCES\" WHERE TRUE;\n"
            + "DELETE FROM \"DECISIONS\" WHERE TRUE;\n"
            + "DELETE FROM \"RESIZING_ACTIONS\" WHERE TRUE;\n"
            + "DELETE FROM \"COMPONENT\" WHERE TRUE;\n"
            + "DELETE FROM \"SPECS\" WHERE TRUE;\n"
            + "DELETE FROM \"PROVIDED_RESOURCE\" WHERE TRUE;\n"
            + "DELETE FROM \"RESOURCE_TYPE\" WHERE TRUE;\n"
            + "DELETE FROM \"MODULE\" WHERE TRUE;\n"
            + "DELETE FROM \"DEPLOYMENT\" WHERE TRUE;\n"
            + "DELETE FROM \"APPLICATION\" WHERE TRUE;\n"
            + "DELETE FROM \"USER\" WHERE TRUE;";   
                        
    public static void closeConnections() {
        	//close connections
		Tables.usertable.closeConnection();
		Tables.moduleTable.closeConnection();
		Tables.appTable.closeConnection();
		Tables.modDep.closeConnection();
		Tables.provResTable.closeConnection();
		Tables.componentTable.closeConnection();
		Tables.componentDependencyTable.closeConnection();
		Tables.deplTable.closeConnection();
		Tables.resTable.closeConnection();
		Tables.raTable.closeConnection();
		Tables.desTable.closeConnection();
		Tables.metricsTable.closeConnection();
		Tables.mvTable.closeConnection();
    }

    public static void clearDB() {
        try {
            
            Connection con = getConnection();

            //TODO: this was supposed to read the clear sql script from file
//            File file = new File("src/java/database/clear_celar_db.sql");
//
//            Statement statement;
//            statement = con.createStatement();
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                if(line.isEmpty() )continue;
//                statement.executeUpdate(line);
//            }
            
            
            {//TODO: this is a sloppy workaround
                List<String> lines = new java.util.LinkedList(Arrays.asList(killlerSQL.split("\n")));
                Statement statement;
                statement = con.createStatement();
                for (String line : lines) statement.executeUpdate(line);
            }
            
            
            
            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }           
        catch (Exception ex) {
                        ex.printStackTrace();
                    }
    }

}
