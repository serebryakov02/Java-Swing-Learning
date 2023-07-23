package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	private List<Person> people;
	
	private Connection connection;
	private String jdbcUrl = "jdbc:mysql://localhost:3306/persons";
	private String username = "root";
	private String password = "Dk8vf4m5";
	
	public Database() {
		people = new LinkedList<Person>();
	}
	
	public void connect() {
		 if (connection == null) {
		        try {
		            // Load the MySQL JDBC driver
		            Class.forName("com.mysql.cj.jdbc.Driver");

		            // Establish the database connection
		            connection = DriverManager.getConnection(jdbcUrl, username, password);
		            System.out.println("Connected to MySQL database successfully.");
		        } catch (ClassNotFoundException e) {
		            System.err.println("MySQL JDBC driver not found.");
		            e.printStackTrace();
		        } catch (SQLException e) {
		            System.err.println("Error while connecting to MySQL database.");
		            e.printStackTrace();
		        }
		    } else {
		        System.out.println("Already connected to the database.");
		    }
	}
	
	public void disconnect() {
		try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from MySQL database.");
            }
        } catch (SQLException e) {
            System.err.println("Error while disconnecting from MySQL database.");
            e.printStackTrace();
        }
	}
	
	public void save() throws SQLException {
		String checkSql = "select count(*) as count from person where id =?";
		PreparedStatement checkStmt = connection.prepareStatement(checkSql);
		
		String insertSql = "insert into person(id, name, age, employment_status, "
				+ "tax_id, us_citizen, gender, occupation) values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStmt = connection.prepareStatement(insertSql);
		
		String updateSql = "update person set name=?, age=?, employment_status=?, "
				+ "tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStmt = connection.prepareStatement(updateSql);
		
		for(Person person : people) {
			int id = person.getId();
			String name = person.getName();
			String occupation = person.getOccupation();
			AgeCategory ageCat = person.getAgeCategory();
			EmploymentCategory empCat = person.getEmpCat();
			String taxID = person.getTaxID();
			boolean isUsCitizen = person.isUsCitizen();
			Gender gender = person.getGender();
			
			checkStmt.setInt(1, id);
			
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();
			
			int count = checkResult.getInt(1);
			if (count == 0) {
				System.out.println("Inserting person with ID " + id);
				
				int col = 1;
				insertStmt.setInt(col++, id);
				insertStmt.setString(col++, name);
				insertStmt.setString(col++, ageCat.name());
				insertStmt.setString(col++, empCat.name());
				insertStmt.setString(col++, taxID);
				insertStmt.setBoolean(col++, isUsCitizen);
				insertStmt.setString(col++, gender.name());
				insertStmt.setString(col++, occupation);
				
				insertStmt.executeUpdate();
			} else {
				System.out.println("Updating person with ID " + id);
				
				int col = 1;
				updateStmt.setString(col++, name);
				updateStmt.setString(col++, ageCat.name());
				updateStmt.setString(col++, empCat.name());
				updateStmt.setString(col++, taxID);
				updateStmt.setBoolean(col++, isUsCitizen);
				updateStmt.setString(col++, gender.name());
				updateStmt.setString(col++, occupation);
				updateStmt.setInt(col++, id);
				
				updateStmt.executeUpdate();
			}
		}
		
		updateStmt.close();
		insertStmt.close();
		checkStmt.close();
	}
	
	public void load() throws SQLException {
		people.clear();
		
		String sql = "select * from person order by name";
		Statement selectStmt = connection.createStatement();
		ResultSet results = selectStmt.executeQuery(sql);
		while(results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String tax = results.getString("tax_id");
			boolean isUs = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occupation = results.getString("occupation");
			
			Person person = new Person(id, name, occupation, AgeCategory.valueOf(age), 
					EmploymentCategory.valueOf(emp), tax, isUs, 
					Gender.valueOf(gender));
			people.add(person);
		}
		
		selectStmt.close();
	}
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(int index) {
		people.remove(index);
	}
	
	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file); 
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Person[] persons = people.toArray(new Person[people.size()]);
		oos.writeObject(persons);
		
		oos.close();
	}
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			Person[] persons = (Person[])ois.readObject();
			
			people.clear();
			people.addAll(Arrays.asList(persons));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
	}
}
