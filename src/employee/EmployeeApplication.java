package employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import bean.Employee;
import jdbcconnection.JdbcConnection;

public class EmployeeApplication {
	private static ArrayList<Employee> employeeList = new ArrayList<Employee>();
	static Connection connection = JdbcConnection.getConnection();

	public static void readEmployees() throws IOException {
		String line = "";
		String splitBy = ",";

		try {
			BufferedReader br = new BufferedReader(new FileReader("employee.txt"));
			while ((line = br.readLine()) != null) {
				String[] employee = line.split(splitBy);

				int employee_id = Integer.parseInt(employee[0]);
				String Name = employee[1];
				String Department = employee[2];
				int basic_salary = Integer.parseInt(employee[3]);
				int hr_salary = Integer.parseInt(employee[4]);
				int special_salary = Integer.parseInt(employee[5]);
				int provitentfund_salary = Integer.parseInt(employee[6]);
				int experience = Integer.parseInt(employee[7]);
				String explevel;
				if (experience == 0) {
					explevel = "fresher";

				} else if (experience <= 3) {
					explevel = "junior";
				} else if (experience <= 6) {
					explevel = "senior";
				} else if (experience <= 9) {
					explevel = "lead";
				} else if (experience > 9) {
					explevel = "manager";
				} else {
					explevel = "";
				}
				int take_home_salary = basic_salary + hr_salary + special_salary - provitentfund_salary;
				java.sql.Date inserteddate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				
				String query = "insert into EmployeeDetails(emp_id,emp_name,department,take_home_salary,experience,experience_level,inserted_date) values (?,?,?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, employee_id);
				ps.setString(2, Name);
				ps.setString(3, Department);
				ps.setInt(4, take_home_salary);
				ps.setInt(5, experience);
				ps.setString(6, explevel);

				ps.setDate(7, (java.sql.Date) inserteddate);
				ps.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		String deletequery = "drop  table if exists EmployeeDetails";
		PreparedStatement ds;
		try {
			
			ds = connection.prepareStatement(deletequery);
			ds.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String createquery = "CREATE TABLE EmployeeDetails\r\n"
				+ "(\r\n"
				+ "emp_id int,\r\n"
				+ "emp_name varchar (20),\r\n"
				+ "department varchar (20),\r\n"
				+ "take_home_salary int,\r\n"
				+ "experience int,\r\n"
				+ "experience_level varchar(20),\r\n"
				+ "inserted_date date\r\n"
				+ ");";
		PreparedStatement cs;
		try {
			cs = connection.prepareStatement(createquery);
			cs.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		readEmployees();
		writeEmployees();
	}

	private static void writeEmployees() {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("select * from EmployeeDetails order by experience desc");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getInt("emp_id"));
				emp.setName(rs.getString("emp_name"));
				emp.setDepartment(rs.getString("department"));
				emp.setTake_home_salary(rs.getInt("take_home_salary"));
				emp.setExperience(rs.getInt("experience"));
				emp.setExp_level(rs.getString("experience_level"));
				emp.setInserteddate(rs.getDate("inserted_date"));
				employeeList.add(emp);
			}
			File fout = new File("out.txt");
			FileOutputStream fos = new FileOutputStream(fout);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			AtomicInteger index = new AtomicInteger(1);
			employeeList.stream().forEach(action -> {
				String output = index.getAndIncrement() + "," + action.getEmployee_id() + "," + action.getName() + ","
						+ action.getDepartment() + "," + action.getTake_home_salary() + "," + action.getExperience()
						+ "," + action.getExp_level() + "," + action.getInserteddate();
				try {
					bw.write(output);
					bw.newLine();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			bw.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}