package bean;

import java.sql.Date;

public class Employee {
	private String Name;
	private String Department;
	private int take_home_salary;
	private int experience;
	private String exp_level;
	private Date inserteddate;

	private int employee_id;

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public int getTake_home_salary() {
		return take_home_salary;
	}

	public void setTake_home_salary(int take_home_salary) {
		this.take_home_salary = take_home_salary;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getExp_level() {
		return exp_level;
	}

	public void setExp_level(String exp_level) {
		this.exp_level = exp_level;
	}

	public Date getInserteddate() {
		return inserteddate;
	}

	public void setInserteddate(Date inserteddate) {
		this.inserteddate = inserteddate;
	}

	

}
