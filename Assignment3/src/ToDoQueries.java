import java.sql.*;
import java.util.ArrayList;

/**
 * Based on Fig. 24.31: PersonQueries.java PreparedStatements used by the ToDo
 * Application.
 */
/*
 * This ToDoQueries class is basically creating two Tables One for TODO list , and other one for the Category table with 
 * the category type of the Tasks from the TODO table and all the setters
 * and getters and other methods to manipulate the table in the database.
 */

public class ToDoQueries {

	// ===========================================================================
	// FIELDS
	// ===========================================================================

	private static final String URL = "jdbc:hsqldb:testdb";   // declaring a private instance for our database URL.
	private static final String USERNAME = "sa";			// declaring private instance variable String for the Username to access the database.
	private static final String PASSWORD = "";				// declaring private instance variable for the password to access the database.
	private ArrayList<ToDo> todoList;

	private Connection connection;                      // manages connection

	// Sql Statements For TODO GTable
	private PreparedStatement selectTaskByIDFromTodoTable;	// selecting task by ID from TODO table.
	private PreparedStatement insertNewTaskToTodoTable;		// statement for inserting New task TODO table
	private PreparedStatement updateTaskByIDFromTodoTable;	// statement for updating task by ID from TODO table.
    
	// - marking an item done - execute sql, and write function
	private PreparedStatement markTaskDoneByIDFromTodoTable;// statement for marking task by ID form the TODO table.
	// Sql Statements For CATEGORIES GTable
	private PreparedStatement insertNewTaskToCategoriesTable;// statement for inserting new task to Catagories table.

	// See all columns together
	private PreparedStatement selectAllTasks;// statement for selecting all task.

	// ===========================================================================
	// CONSTRUCTOR
	// ===========================================================================

	public ToDoQueries() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);// connecting with the database.

			Statement s;
			String sql;
			ResultSet rs;
			
			/*
			 * the code below is for dropping the TODO table and the Categories table. To avoid error
			 * This code must have to be COMMENTED OUT when the code is run for the first time because there 
			 * wouldn't be any table to drop. While running the code from the second time this code will work perfectly
			 * without any error.
			 */

			// DROP TODO TABLE
			sql = "DROP TABLE TODO;";
			try {
				s = connection.createStatement(); // creating statement
				rs = s.executeQuery(sql);// executing the sql statement and storing the result in the resultSet.
			} catch (SQLException e) {
				e.printStackTrace(); // printing if there is any Sql exception.
			}

			// DROP CATEGORIES TABLE
			sql = "DROP TABLE CATEGORIES;";
			try {
				s = connection.createStatement();// creating statement
				rs = s.executeQuery(sql);// executing the sql statement and storing the result in the resultset.
			} catch (SQLException e) {
				e.printStackTrace();// printing if there is any Sql exception.
			}

			// ************************* TODO GTable ********************************

			// CREATEING TODO TABLE
			
			sql = "CREATE TABLE TODO(ID INT, Task VARCHAR(255), Done BOOLEAN NOT NULL, Date DATE );";// sql command creating the table with four columns 
							// ID which unique number for all tasK,Tasks name of the task,boolean value DONE if the task is done, 
							//The date when the new task is created for sql table

			try {
				s = connection.createStatement(); // creating the statement 
				rs = s.executeQuery(sql);// executing the sql statement and storing the result in the resultset.
			} catch (SQLException e) {
				e.printStackTrace(); // printing sql exception if there is any.
			}

			// creating a query that selects entries with a specific ID
			sql = "SELECT * FROM TODO WHERE ID = ?;"; // saving the sql command for selecting all task by ID number.
			selectTaskByIDFromTodoTable = connection.prepareStatement(sql); // preparing the statement.

			// creating insert that adds a new task into the database
			sql = "INSERT INTO TODO (ID, Task, Done, Date) VALUES (?, ?, ?, ?);"; // saving the sql command in string for inserting new task 
																		// that will take new ID , new task, Done, and date.
			insertNewTaskToTodoTable = connection.prepareStatement(sql);//  preparing the statement for the inserting command.

			// creating  query that updates the given task in the database
			sql = "UPDATE TODO SET Task = ? WHERE ID = ?;"; // saving the sql command for updating the task by ID number.
			updateTaskByIDFromTodoTable = connection.prepareStatement(sql);// preparing the statement.

			// creating query that marks task as done in the database
			sql = "UPDATE TODO SET Done = NOT Done WHERE ID = ?;"; // saving the sql command in string for marking the task by ID number, if this is done.
			markTaskDoneByIDFromTodoTable = connection.prepareStatement(sql);// preparing the statement.

			// ******************* CATEGORIES TABLE **************************************

			// CREATING SECOND TABLE
			sql = "CREATE TABLE CATEGORIES (ID INT, Category VARCHAR(255));"; // SQL command for creating the table for category, 
															// with two columns ID number and the category type.
			try {
				s = connection.createStatement();// creating the statement.
				rs = s.executeQuery(sql);// executing the sql command and saving the result in the resultset.
			} catch (SQLException e) {
				e.printStackTrace();// printing the sql exception if there is any.
			}

			/*
			 *  creating query that selects all entries from  the TODO GTable and the CATEGORIES GTable.This command is saved in string like the 
			 *  previous ones. Here, it will select all the entries  from the TODO table and the Categories table. Note that the 
			 *  the ID number is same in TODO table and Categories table. That's why we used INNER JOIN in our command.
			 */
		
			sql = "SELECT TODO.ID, TODO.Task, CATEGORIES.Category, TODO.Done, TODO.Date FROM TODO INNER JOIN CATEGORIES ON "
					+ "TODO.ID=CATEGORIES.ID;"; 
			selectAllTasks = connection.prepareStatement(sql);// preparing the statement.

			// creating insert option that adds a new task into the database
			sql = "INSERT INTO CATEGORIES (ID, Category) VALUES (?, ?);";// sql command for inserting categories by IDs.
			insertNewTaskToCategoriesTable = connection.prepareStatement(sql);// prepare statement.

      // initializing our ArrayList
      this.todoList = getAllTasks(); // updating our class instance arrayList with all the tasks.

		} catch (SQLException e) {
			e.printStackTrace(); // printing out if there is any sql exception.
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();// printing out if there is any exception of Class not found.
		}

	}

	// ===========================================================================
	// METHODS
	// ===========================================================================
	// GETTERS
	// ---------------------------------------------------------------------------
    /*
     * this method searches through the TODO table and returns the task which is related to the ID number.
     */
	public ToDo getTaskByIDFromTodoTable(int ID) {
		ToDo results = null;   // creating a TODO class object results. 
		ResultSet resultSet = null;// initializing the result-set  to null.
		try {
			resultSet = selectTaskByIDFromTodoTable.executeQuery();// executing the statement with sql command that was prepared before.
			while (resultSet.next()) {
				results = new ToDo(resultSet.getString("Task"), null);// going through the all the entries in the table and stores the
																	// result in results object.
			}
		} catch (SQLException e) {
			e.printStackTrace(); // printing sql exception if there is any.
		} finally {
			try {
				resultSet.close(); // closing the result-set 
			} catch (SQLException e) {
				e.printStackTrace();// printing the sql exceptionn if there is any.
				close();
			}
		}

		return results;// returning the object.
	}

	/*
	 * This method returns and array list with ID number, tasks name, and category type of each entries
	 *  from the TODO table and Categories table.
	 */
	public ArrayList<ToDo> getAllTasks() {
		ArrayList<ToDo> results = null; // initializing array list of TODO class object null.
		ResultSet resultSet = null;// initializing resultset to null.

		try {
			// executeQuery returns ResultSet containing matching entries
			resultSet = selectAllTasks.executeQuery();// executing the command for selecting all the tasks from from the two table.
			results = new ArrayList<ToDo>();

			while (resultSet.next()) {
				results.add(
						new ToDo(resultSet.getInt("ID"), resultSet.getString("Task"), resultSet.getString("Category")));
						        // adding ID number, tasks name, Category type of each entries of the two table in the ArrayList.

			}
		} catch (SQLException e) {
			e.printStackTrace();// printing out sql exception.
		} finally {
			try {
				resultSet.close(); // closing the resulSet.
			} catch (SQLException e) {
				e.printStackTrace();// printing out sql exception.
				close();
			}
		}

		return results;// returning the arrayList.
	}
	
	/*
	 * this method is a getter method for getting our private instance arrayList.
	 */

	public ArrayList<ToDo> getTodoList() {
		return todoList;// returning arrayList.
	}

	// ---------------------------------------------------------------------------
	// SETTERS
	// ---------------------------------------------------------------------------
/* 
 * This method is going to add new entries to our Todo list.
 */
	private void addToTodos(ToDo toAdd) {
		this.todoList.add(toAdd);// changing the Todo list by adding new entries.
	}
/*
 * This method is for updating our todo list.
 */
	private void updateTodo(ToDo toUpdate) {
		this.todoList.set(toUpdate.getID() - 1, toUpdate);
	}

	// ---------------------------------------------------------------------------
	// OTHER METHODS
	// ---------------------------------------------------------------------------

	// adding a task
	
	/*
	 * This method is for adding a new task to our TODO table as well as to the Categories table. This method takes the two parameters
	 * of string one of them is the task name and the other one is category. I takes to object and run the sql command to set the
	 * elements for the new entries. It will also add the new ID and setting up the Date when the task is created.
	 * 
	 */
	public void addTask(String desc, String category) {

		// set parameters, then execute insertNewTask
		try {
			ToDo newTask = new ToDo(desc, category); // creating new object of TODO new task with the parameters.
			insertNewTaskToTodoTable.setInt(1, newTask.getID());// adding a new ID number by the getID method from the TODO class in TODO table.
			insertNewTaskToTodoTable.setString(2, newTask.getTask());// adding the new task by the getTask method from the TODO class in the TODO table.
			insertNewTaskToTodoTable.setBoolean(3, newTask.isTaskDone());// adding the boolean value if the task by the isTaskDone method from TODO class in the TODO table.
			insertNewTaskToTodoTable.setDate(4, newTask.getDateAdded());//adding a new date by the getDateAdded from the method from the TODO class in the TODO table.

			// ADDING TO CATEGORIES TABLE TOO
			insertNewTaskToCategoriesTable.setInt(1, newTask.getID());//adding ID by the getID method from the TODO class in the Category table.
			insertNewTaskToCategoriesTable.setString(2, newTask.getCategory());//adding a new Category by the getCategory method from the TODO class in Category table.

			// inserting the new entry; returns @ of rows updated
			insertNewTaskToTodoTable.executeUpdate(); // updating our queries in the TODO table.
			insertNewTaskToCategoriesTable.executeUpdate();// updating our queries in the Category table.

			// add to our todoList
			addToTodos(newTask); // updating our todoList for console.
		} catch (SQLException e) {
			e.printStackTrace();// printing out the sql exception if there is any.
			close();
		}

		// return result;
	}

	/*
	 * This method edit tasks in the sql queries. it takes two parameters Id and the task name. 
	 */
	public int editTask(int ID, String newDesc) {
		if (ID > getTodoList().size() || ID < 1) {			//checking if the ID is between the Todo list size and 1, to avoid index out
															// of bound exception.
      System.out.println("Sorry, that task doesn't exist");
      return -1; 											// error
    }
    try {
     
    		getTodoList().get(ID - 1).editTask(newDesc); // Update task on our TD object

			// Initialize our Prepared Statement
			updateTaskByIDFromTodoTable.setString(1, newDesc);// editing task name in the setString method in the Todo table.
			updateTaskByIDFromTodoTable.setInt(2, ID);// editing ID number by using setInt method in the Todo table.

			// Execute our statement
			updateTaskByIDFromTodoTable.executeUpdate(); // executing our updating statement.

		} catch (SQLException e) {
			e.printStackTrace(); //printing if there is any sql exception.
      return -1; // error
		}

    return 0; // success
	};

	// Marking Done as True in TODO GTable

	/*
	 * This method marks the ID if it is done or not. It takes the ID number as the parameter and update table if the task is 
	 * done or not done.
	 */
	public int markDone(int ID) {
		if (ID > getTodoList().size() || ID < 1) {               	//checking if the ID is between the Todo list size and 1, to avoid index out
																	//  index of bound exception.
			System.out.println("Sorry, that task doesn't exist");
      return -1; 													// error
		}
		try { 
			markTaskDoneByIDFromTodoTable.setInt(1, ID); 	//editing the ID number if its done or not.
			// Execute the statement
			markTaskDoneByIDFromTodoTable.executeUpdate(); // executing the update.

			
			getTodoList().get(ID - 1).markDone();     // update task on list
		} catch (SQLException e) {
			e.printStackTrace();
      return -1; // error
		}
    return 0; // success
	}

	/*
	 * This method closes the database connection.
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();  // printing sql exception.
		}
	}
} // end class ToDoQueries