package database;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;



public class DecisionsTable extends Table {
	
	public DecisionsTable() {super();}
	
	
	public DecisionsTable(boolean connect) {super(connect);}


	@Override
	protected String getTableName() {
		return "DECISIONS";
	}
	
	
	/**
	 * Inserts a decision  with the specified data. 
	 * It returns true if everything went smoothly, else it returns false.
	 * @param timestamp the timestamp the decision was made
	 * @param RECISING_ACTION_id the resizing action taken by this decision
	 * @param size the size/multiplicity of this action
	 */
	public boolean insertDecision(Date timestamp, int RECISING_ACTION_id, int size){
		Map<String, String> data = new java.util.TreeMap<String, String>();
		data.put("timestamp", timestamp.toString());
		data.put("RECISING_ACTION_id", Integer.toString(RECISING_ACTION_id));
		data.put("size", Integer.toString(size));
		return	this.insertData(data);
	}


	
	/**
	 * Deletes the decisions with the specified timestamp 
	 * @param timestamp
	 * @return # of rows affected 
	 */
	public int deleteDecision(Date timestamp){
		String query= "DELETE FROM "+this.getTableName()+""
			+" WHERE timestamp='"+timestamp.toString()+"';";
		System.out.println(query);
		Statement statement;
		int result=0;
		try {
			statement = this.connection.createStatement();
			result=statement.executeUpdate(query);
			statement.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	



}
