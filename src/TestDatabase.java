import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class TestDatabase {
	public static void main(String[] args) {
		System.out.println("Running database test");
		
		Database db = new Database();
		db.connect();
		
		db.addPerson(new Person("Mykyta", "C++/Qt/Java Developer", AgeCategory.adult, 
				EmploymentCategory.selfEmployed, "4444", true, Gender.male));
		db.addPerson(new Person("Olya", "English teacher", AgeCategory.adult, 
				EmploymentCategory.selfEmployed, null, false, Gender.female));
		
		try {
			db.save();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			db.load();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.disconnect();
	}
}
