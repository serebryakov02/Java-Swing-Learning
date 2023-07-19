package controller;

import java.util.List;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {
	Database db = new Database();
	
	public List<Person> getPeople() {
		return db.getPeople();
	}
	
	public void addPerson(FormEvent e) {
		String name = e.getName();
		String occupation = e.getOccupation();
		int ageCat_id = e.getAgeCategory();
		String empCat = e.getEmpCat();
		boolean isUS = e.isUsCitizen();
		String taxID = e.getTaxID();
		String gender = e.getGender();
		
		AgeCategory ageCat = null;
		switch(ageCat_id) {
		case 0:
			ageCat = AgeCategory.child;
			break;
		case 1:
			ageCat = AgeCategory.adult;
			break;
		case 2:
			ageCat = AgeCategory.seniour;
			break;
		}
		
		EmploymentCategory eCat;
		if (empCat.equals("employed")) {
			eCat = EmploymentCategory.employed;
		}
		else if (empCat.equals("unemployed")) {
			eCat = EmploymentCategory.unemployed;
		}
		else if (empCat.equals("self-employed")) {
			eCat = EmploymentCategory.selfEmployed;
		}
		else {
			eCat = EmploymentCategory.other;
			System.err.print(empCat);
		}
		
		Gender gen = null;
		if (gender.equals("male")) {
			gen = Gender.male;
		}
		else if (gender.equals("female")) {
			gen = Gender.female;
		}
		
		Person person = new Person(name, occupation, ageCat,
				eCat, taxID, isUS, gen);
		
		db.addPerson(person);
	}
}
