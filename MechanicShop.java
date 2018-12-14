/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class MechanicShop{
	//reference to physical database connection
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public MechanicShop(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
	        System.out.println("Make sure you started postgres on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned result set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query result as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned result set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
		int rowCount = 0; 
	 
		//iterates through the result set and saves the data returned by the query. 
		boolean outputHeader = false;
		List<List<String>> result  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			result.add(record); 
		}//end while 
		stmt.close (); 
		return result; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		if(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	public int executeQuery2 (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		while(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	public int executeQuery3 (String query) throws SQLException {
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);
		/*
		 ** obtains the metadata object for the returned result set.  The metadata
		 ** contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
    int numCol = rsmd.getColumnCount ();
		int rowCount = 0;

		// iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
				}
				System.out.println();
				outputHeader = false;
			}
 	 	  for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}
		stmt.close();
		return rowCount;
	}
	
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
		
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/**
	 * The main execution method
	 * 
	 * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
	 */
	public static void main (String[] args) {
		if (args.length != 3) {
			System.err.println (
				"Usage: " + "java [-classpath <classpath>] " + MechanicShop.class.getName () +
		            " <dbname> <port> <user>");
			return;
		}//end if
		
		MechanicShop esql = null;
		
		try{
			System.out.println("(1)");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(Exception e){

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				return;
			}
			
			System.out.println("(2)");
			String dbname = args[0];
			String dbport = args[1];
			String user = args[2];
			
			esql = new MechanicShop (dbname, dbport, user, "");
			
			boolean keepon = true;
			while(keepon){
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. AddCustomer");
				System.out.println("2. AddMechanic");
				System.out.println("3. AddCar");
				System.out.println("4. InsertServiceRequest");
				System.out.println("5. CloseServiceRequest");
				System.out.println("6. ListCustomersWithBillLessThan100");
				System.out.println("7. ListCustomersWithMoreThan20Cars");
				System.out.println("8. ListCarsBefore1995With50000Milles");
				System.out.println("9. ListKCarsWithTheMostServices");
				System.out.println("10. ListCustomersInDescendingOrderOfTheirTotalBill");
				System.out.println("11. < EXIT");
				
				/*
				 * FOLLOW THE SPECIFICATION IN THE PROJECT DESCRIPTION
				 */
				switch (readChoice()){
					case 1: AddCustomer(esql); break;
					case 2: AddMechanic(esql); break;
					case 3: AddCar(esql); break;
					case 4: InsertServiceRequest(esql); break;
					case 5: CloseServiceRequest(esql); break;
					case 6: ListCustomersWithBillLessThan100(esql); break;
					case 7: ListCustomersWithMoreThan20Cars(esql); break;
					case 8: ListCarsBefore1995With50000Milles(esql); break;
					case 9: ListKCarsWithTheMostServices(esql); break;
					case 10: ListCustomersInDescendingOrderOfTheirTotalBill(esql); break;
					case 11: keepon = false; break;
				}
			}
		}catch(Exception e){
			System.err.println (e.getMessage ());
		}finally{
			try{
				if(esql != null) {
					System.out.print("Disconnecting from database...");
					esql.cleanup ();
					System.out.println("Done\n\nBye !");
				}//end if				
			}catch(Exception e){
				// ignored.
			}
		}
	}

	public static int readChoice() {
		int input;
		// returns only if a correct value is given.
		do {
			System.out.print("Please make your choice: ");
			try { // read the integer, parse it and break.
				input = Integer.parseInt(in.readLine());
				break;
			}catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}//end try
		}while (true);
		return input;
	}//end readChoice
	public static int getRandNum(){
 
     int upperBound = (int) Math.pow(2, 32) - 1;
     Random rand = new Random();
     int n = (int) (Math.random() * upperBound);
 
     return n;
	}
 
	public static int getCID(MechanicShop esql){
	   try{
	 
	       boolean flag = true;
	 
	       while (flag) {
	 
	         String query = "Select id FROM Customer WHERE id = ";
	 
	         int randNum = getRandNum();
	 
	         query += randNum + ";";
	 
	         int rowCount = esql.executeQuery2(query);
	 
	         if(rowCount == 0){
	           //System.out.println("Worked");
	           //System.out.println(randNum);
	           flag = false;
	           return randNum;//add 1 later
	         } else{
	           //System.out.println("FAiled");
	         }
	       }
	 
       return -99999;
 
         // return totalID;//add 1 later
     } catch(Exception e){
         System.err.println(e.getMessage());
	         return -912833;
	}
 }

	public static int getMID(MechanicShop esql){
	   try{
	 
	       boolean flag = true;
	 
	       while (flag) {
	 
	         String query = "Select id FROM Mechanic WHERE id = ";
	 
	         int randNum = getRandNum();
	 
	         query += randNum + ";";
	 
	         int rowCount = esql.executeQuery2(query);
	 
	         if(rowCount == 0){
	           //System.out.println("Worked");
	           //System.out.println(randNum);
	           flag = false;
	           return randNum;//add 1 later
	         } else{
	           //System.out.println("FAiled");
	         }
	       }
	 
       return -99999;

         // return totalID;//add 1 later
     } catch(Exception e){
         System.err.println(e.getMessage());
	         return -912833;
	}
 }
	public static int getOID(MechanicShop esql){
	   try{
	 
	       boolean flag = true;
	 
	       while (flag) {
	 
	         String query = "Select ownership_id FROM Owns WHERE ownership_id = ";
	 
	         int randNum = getRandNum();
	 
	         query += randNum + ";";
	 
	         int rowCount = esql.executeQuery2(query);
	 
	         if(rowCount == 0){
	           //System.out.println("Worked");
	           //System.out.println(randNum);
	           flag = false;
	           return randNum;//add 1 later
	         } else{
	           //System.out.println("FAiled");
	         }
	       }
	 
       return -99999;
 
         // return totalID;//add 1 later
     } catch(Exception e){
         System.err.println(e.getMessage());
	         return -912833;
	}
 }
	public static int getRID(MechanicShop esql){
	   try{
	 
	       boolean flag = true;
	 
	       while (flag) {
	 
	         String query = "Select rid FROM Service_Request WHERE rid = ";
	 
	         int randNum = getRandNum();
	 
	         query += randNum + ";";
	 
	         int rowCount = esql.executeQuery2(query);
	 
	         if(rowCount == 0){
	          // System.out.println("Worked");
	          // System.out.println(randNum);
	           flag = false;
	           return randNum;//add 1 later
	         } else{
	          // System.out.println("Failed");
	         }
	       }
	 
       return -99999;
 
         // return totalID;//add 1 later
     } catch(Exception e){
         System.err.println(e.getMessage());
	         return -912833;
		}
 	}

	public static void AddCustomer(MechanicShop esql){//1
		try {
				String query = "INSERT INTO Customer(id, fname, lname, phone, address) VALUES('";
				int cid = getCID(esql);
				query += cid + "','";

				System.out.println("First Name: ");
				String fname = in.readLine();
				query += fname + "','";

				System.out.println("Last Name: ");
				String lname = in.readLine();
				query += lname + "','";

				System.out.println("Phone #: ");
				String phone = in.readLine();
				query += phone + "','";

				System.out.println("Address: ");
				String address = in.readLine();
				query += address + "');";

				esql.executeUpdate(query); //updated Customer table
				//display result
				System.out.println("------------------------------------------------");
				System.out.println("Success! New customer info shown below");
				query = "SELECT * FROM Customer WHERE id=";
				query+= cid + ";";
				int x = esql.executeQueryAndPrintResult(query);
				System.out.println("------------------------------------------------");
		} catch (Exception e){
				System.err.println(e.getMessage());
		}
	}
	
	public static void AddMechanic(MechanicShop esql){//2
		try {
				String query = "INSERT INTO Mechanic(id, fname, lname, experience) VALUES('";
				int mid = getMID(esql);
				query += mid + "','";

				System.out.println("First Name: ");
				String fname = in.readLine();
				query += fname + "','";

				System.out.println("Last Name: ");
				String lname = in.readLine();
				query += lname + "','";

				System.out.println("Experience Years: ");
				String experience = in.readLine();
				query += experience + "');";

				esql.executeUpdate(query);
				//display result
				System.out.println("------------------------------------------------");
				System.out.println("Success! New mechanic info shown below");
				query = "SELECT * FROM Mechanic WHERE id=";
				query+= mid + ";";
				int x = esql.executeQueryAndPrintResult(query);
				System.out.println("------------------------------------------------");
		} catch (Exception e){
				System.err.println(e.getMessage());
		}
	}
	
	public static void AddCar(MechanicShop esql){//3
		try {
				
				System.out.println("Enter customer_id for who owns the car: ");
				String customerID = in.readLine();
			
				String query = "Select * FROM Customer WHERE id=";
				query+= customerID + ";";
				int customerExists = esql.executeQuery(query); //number of customers with this id

				if(customerExists!=0){ //id exists
					System.out.println("Welcome Back! To add your car, please enter the following information");

					query = "INSERT INTO Car(vin, make, model, year) VALUES('";

					System.out.println("VIN: ");
					String vin = in.readLine();
					query += vin + "','";

					System.out.println("Make: ");
					String make = in.readLine();
					query += make + "','";

					System.out.println("Model: ");
					String model = in.readLine();
					query += model + "','";

					System.out.println("Year: ");
					String year = in.readLine();
					query += year + "');";

					esql.executeUpdate(query); //added to Car
					
					query = "INSERT INTO Owns(ownership_id, customer_id, car_vin) VALUES('";
					int ownID = getOID(esql);
					query += ownID + "', '";
					query += customerID + "', '";
					query += vin + "');";
					esql.executeUpdate(query); //added to Owns
					
					//display result
					System.out.println("------------------------------------------------");
					System.out.println("Success! New car and ownership info shown below");
					query = "SELECT * FROM Car WHERE vin='";
					query+= vin + "';";
					int x = esql.executeQueryAndPrintResult(query);
					
					System.out.println("------------------------------------------------");
					query = "SELECT * FROM Owns WHERE ownership_id=";
					query+= ownID + ";";
					x = esql.executeQueryAndPrintResult(query);
					System.out.println("------------------------------------------------");
					
				}
				else { //id doesn't exist
					 // customer doesn't exist; ask NEW Customer?
					//ask if new customer
					System.out.println("Wrong ID. Are you a new customer? [y/n]");
					String answer = in.readLine();
					boolean flag = true;
					//String yes = "y"; String no = "n";
					if (answer.equals("y")) {flag=false;}
					if (answer.equals("n")) {flag=false;}
					//if (flag){
					//	System.out.println("answer was y");
					//}
					
					//if (flag) {
					while(flag){
						System.out.println("Please enter [y/n]:");
						answer = in.readLine();
						if (answer.equals("y")) {flag=false;}
						if (answer.equals("n")) {flag=false;}
					}
					//}
					
					if (answer.equals("y")){ //new customer
						//add customer
						//int result = AddCustomer(esql);		
						System.out.println("-----------------------------------------");
						System.out.println("Please add customer first");
						System.out.println("-----------------------------------------");
						return;
					}
					else { //not new customer
						do { //keep rechecking customer id
								//make sure said person is entering their own id.
								//even though they forgot it		
								System.out.println("Please enter correct customer id:");
								customerID = in.readLine();
								
								query = "Select * FROM Customer WHERE id=";
								query += customerID + ";";

								customerExists = esql.executeQuery(query);
								
								} while(customerExists == 0);//end of while; finally got id
						//insert
						System.out.println("Welcome! To add your car, please enter the following information");

						query = "INSERT INTO Car(vin, make, model, year) VALUES('";

						System.out.println("VIN: ");
						String vin = in.readLine();
						query += vin + "','";

						System.out.println("Make: ");
						String make = in.readLine();
						query += make + "','";

						System.out.println("Model: ");
						String model = in.readLine();
						query += model + "','";

						System.out.println("Year: ");
						String year = in.readLine();
						query += year + "');";

						esql.executeUpdate(query); //added to Car
					
						query = "INSERT INTO Owns(ownership_id, customer_id, car_vin) VALUES('";
						int ownID = getOID(esql);
						query += ownID + "', '";
						query += customerID + "', '";
						query += vin + "');";
						esql.executeUpdate(query); //added to Owns
						//display result
						
						System.out.println("------------------------------------------------");
						System.out.println("Success! New car and ownership info shown below");
						query = "SELECT * FROM Car WHERE vin='";
						query+= vin + "';";
						int x = esql.executeQueryAndPrintResult(query);
					
						System.out.println("------------------------------------------------");
						query = "SELECT * FROM Owns WHERE ownership_id=";
						query+= ownID + ";";
						x = esql.executeQueryAndPrintResult(query);
						System.out.println("------------------------------------------------");
						
					}//end of id doesn't exist else
				}
		} catch (Exception e){
				System.err.println(e.getMessage());
		}	//end of catch
	}//end of addcar function
	
	public static void InsertServiceRequest(MechanicShop esql){//4
		try{
			int x = 0; //dummy integer for the result of executeQueryAndPrintResult
			String customerID="";
			String carID="";
			String query = "SELECT * FROM Customer WHERE lname='";
			System.out.println("Last Name: ");
			String lname = in.readLine();
			query += lname + "';";

			int customerExists = esql.executeQuery(query); //return number of customers with this last name
			if (customerExists != 0){ //if customer exists
				System.out.println("---------------------------------------------------");	
				x = esql.executeQueryAndPrintResult(query); //display the customer table with this lname
				
				//Prompt the user to choose her customer id; check if id is valid???
				System.out.println("---------------------------------------------------");	
				System.out.println("Choose your customer id: ");
				customerID = in.readLine();
			}
			else{ //customer doesn't exist
				System.out.println("No customer with that last name exists. Please follow the instruction below to add new customer");
				AddCustomer(esql);
				System.out.println("Input your customer id shown above: ");
				customerID = in.readLine();
			}	
			
			//find out how many cars owned by this customer
			query = "SELECT car_vin FROM Owns WHERE customer_id='";
			query += customerID + "';";
				
			int carExists = esql.executeQuery(query); //number of cars owned by the chosen customer
				
			if (carExists !=0){ //if this customer has cars to display 
				System.out.println("---------------------------------------------------");	
				x = esql.executeQueryAndPrintResult(query); //display cars(just car_vin) owned by the customer
					//Prompt the user to choose her customer id;
				System.out.println("---------------------------------------------------");
				System.out.println("Choose your car_vin: ");
				carID = in.readLine();
			}
			else { //this customer has no cars
				System.out.println("This customer has no car. Please follow the instruction below to add new car");
				AddCar(esql);
				System.out.println("Input your car VIN(shown above): ");
				carID = in.readLine();
			}	
			//verifies if this customer owns this car
			query = "SELECT * FROM Owns WHERE car_vin='";
			query += carID + "' AND customer_id='";
			query += customerID + "';";
				
			int ownershipExists = esql.executeQuery(query);
			if (ownershipExists != 0){
				query = "INSERT INTO Service_Request(rid, customer_id, car_vin, date, odometer, complain) VALUES ('";
				int rid = getRID(esql);
				query += rid + "', '";
				query += customerID + "', '" + carID + "', " + "CURRENT_DATE" + ", '";
				System.out.println("Enter odometer:");
				String odometer = in.readLine();
				query += odometer + "', '";
				System.out.println("Enter complain:");
				String complain = in.readLine();
				query += complain + "');";
						
				esql.executeUpdate(query);
		
				System.out.println("------------------------------------------------");
				System.out.println("Success! New service request info shown below");
				query = "SELECT * FROM Service_Request WHERE rid='";
				query+= rid + "';";
				x = esql.executeQueryAndPrintResult(query);
				System.out.println("------------------------------------------------");
	
			}
			else { //this customer doesn't own this car
				System.out.println("this customer doesn't own this car");
			}
		} catch(Exception e){
				System.err.println(e.getMessage());
		}//end of catch
	}//end of initiate service request
	
	public static void CloseServiceRequest(MechanicShop esql) throws Exception{//5
	    try {
		System.out.println("Closing a service request.");
		System.out.println("Please enter the service request number:");
		String serReqNum = in.readLine();
		
		String checkReqNumQuery = "SELECT * FROM Service_Request WHERE rid = '";
		checkReqNumQuery += serReqNum + "';";
		
		int x = esql.executeQuery(checkReqNumQuery);
		while(x == 0){
		    checkReqNumQuery = "";
		    serReqNum = "";
		    System.out.println("Not a valid service request number");
		    System.out.println("Enter a service request number:");
		    checkReqNumQuery = "SELECT * FROM Service_Request WHERE rid = '";
		    serReqNum = in.readLine();
		    checkReqNumQuery += serReqNum + "';";
		    x = esql.executeQuery(checkReqNumQuery);
		}

		System.out.println("Valid service request");

		System.out.println("Enter Employee Id who worked on the request");
		String employeeId = in.readLine();
		String checkEIDQuery = "SELECT * FROM Mechanic WHERE id = '";
		checkEIDQuery += employeeId + "';";
		
		x = esql.executeQuery(checkEIDQuery);
		
		while (x == 0){
		    employeeId = "";
		    checkEIDQuery = "";
		    System.out.println("Not a valid employee id num");
		    System.out.println("Enter a employee id num:");
		    employeeId = in.readLine();
		    checkEIDQuery = "SELECT * FROM Mechanic WHERE id = '";
		    checkEIDQuery += employeeId + "';";
		    x = esql.executeQuery(checkEIDQuery);
		}

		System.out.println("Valid employee id");
		System.out.println("Deleting the service request!");

		String deleteQuery = "DELETE FROM Service_Request WHERE rid = serReqNum;";
		    
		esql.executeQuery(deleteQuery);
		
		
	    }
	    catch(Exception e){
		System.err.println(e.getMessage()); 
	    }
	}
	
	public static void ListCustomersWithBillLessThan100(MechanicShop esql){//6
		try{
			String query = "SELECT date,comment,bill FROM Closed_Request WHERE bill < 100;";
			int rowCount = esql.executeQueryAndPrintResult(query);
			System.out.println("total row(s): " + rowCount);
		}
	 	catch(Exception e){
			System.err.println(e.getMessage());
		}	
	}
	
	public static void ListCustomersWithMoreThan20Cars(MechanicShop esql){//7
		try{
			String query = "SELECT fname,lname FROM Customer AS C, (SELECT customer_id, COUNT(customer_id) AS cnt FROM Owns GROUP BY customer_id HAVING COUNT(customer_id) > 20) AS N WHERE N.customer_id=C.id;";
			int rowCount = esql.executeQueryAndPrintResult(query);
			System.out.println("total row(s): " + rowCount);
		}
	 	catch(Exception e){
			System.err.println(e.getMessage());
		}	
	}
	
	public static void ListCarsBefore1995With50000Milles(MechanicShop esql){//8
		try{
			String query = "SELECT DISTINCT make, model, year FROM Car AS C, Service_Request AS S WHERE year < 1995 and S.car_vin = C.vin and S.odometer < 50000;";
			int rowCount = esql.executeQueryAndPrintResult(query);
			System.out.println("total row(s): " + rowCount);
		}
	 	catch(Exception e){
			System.err.println(e.getMessage());
		}	
	}
	
	public static void ListKCarsWithTheMostServices(MechanicShop esql){//9
		try{
			String query = "SELECT make, model, S.num FROM Car AS C, (SELECT car_vin, COUNT(rid) AS num FROM Service_Request GROUP BY car_vin ) AS S WHERE S.car_vin = C.vin ORDER BY S.num DESC LIMIT ";
			
			System.out.println("First Number of Cars to List(>0): ");
			String num = in.readLine();
			query += num + ";";

			int rowCount = esql.executeQueryAndPrintResult(query);
			System.out.println("total row(s): " + rowCount);
		}
	 	catch(Exception e){
			System.err.println(e.getMessage());
		}	
	}
	
	public static void ListCustomersInDescendingOrderOfTheirTotalBill(MechanicShop esql){//9
		try{
			String query = "SELECT C.fname, C.lname, total FROM Customer AS C,(SELECT SR.customer_id, SUM(CR.bill) AS total FROM Closed_Request AS CR, Service_Request AS SR WHERE CR.rid = SR.rid GROUP BY SR.customer_id) AS B WHERE C.id=B.customer_id ORDER BY B.total DESC;";
			int rowCount = esql.executeQueryAndPrintResult(query);
			System.out.println("total row(s): " + rowCount);
		}
	 	catch(Exception e){
			System.err.println(e.getMessage());
		}	
	}
	
}//end of Mechanic shop class
