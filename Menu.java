package Bus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu {
	static Scanner sc = new Scanner(System.in);	
	static ResultSet rs;
	
	public static void menu(Connection conn, Statement stmt) {
		
		System.out.println("----------------------- KNU BUS SERVICE -----------------------");
		
		while(true) {
			
			System.out.println("----------------------- MENU -----------------------");
			System.out.println("<Login stage>");
			System.out.println(" 1. Login");	
			System.out.println(" 2. Find ID(email)\n");
			
			System.out.println("<Search bus information>");
			System.out.println(" 3. Query possible arrivals (arriving station) with departure (departing station)");	// document에서 11번 query
			System.out.println(" 4. Query bus type with departing/arriving station");	
			System.out.println(" 5. Query detailed bus information with departing/arriving station");	// document에서 6번 query
			System.out.println(" 6. Query bus seat information with departing/arriving station, date and time\n");	// document에서 5번 query
			
			System.out.println("<Find my reservation information>");
			System.out.println(" 7. Create reservation");	
			System.out.println(" 8. Update reservation ");
			System.out.println(" 9. Delete reservation ");
			System.out.println("10. Find my reservation information");	// document에서 3번 query
			System.out.println("11. Show detailed information of my ticket\n");	// document에서 10번 query
			
			System.out.println("12. Exit BUS SERVICE");
			System.out.println("----------------------------------------------------");
			
			System.out.print("Select menu and input a number of menu: ");
			
			switch(sc.nextInt()) {
				case 1:
					funct1(conn, stmt);
					break;
				case 2:
					funct2(conn, stmt);
					break;
				case 3:
					funct3(conn, stmt);
					break;
				case 4:
					funct4(conn, stmt);
					break;
				case 5:
					funct5(conn, stmt);
					break;
				case 6:
					funct6(conn, stmt);
					break;
				case 7:
					funct7(conn, stmt);
					break;
				case 8:
					funct8(conn, stmt);
					break;
				case 9:
					funct9(conn, stmt);
					break;
				case 10:
					funct10(conn, stmt);
					break;
				case 11:
					funct11(conn, stmt);
					break;
				case 12:
					System.out.println("KNU_BUS service is terminated");
					System.exit(0);
					break;
			}
	
		}
	}
	
	public static void funct1(Connection conn, Statement stmt) {
		String email = sc.next();
		String password = sc.next();
		
		String query = "SELECT AID" +
		" FROM ACCOUNT" + 
		" WHERE EMAIL='" + email + "'" + 
		" AND PW='" + password + "'";
		
		try {
			String AID = "";
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i =1;i<=cnt;i++){
				System.out.println(rsmd.getColumnName(i));
			}
			if (rs.next()){
				AID = rs.getString(1);
			}
			System.out.println(!AID.isEmpty() ? AID : "Login Failed");
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void funct2(Connection conn, Statement stmt) {
		// input your code
		System.out.println("func2\n\n");
	}
	
	public static void funct3(Connection conn, Statement stmt) {
		// input your code
		System.out.println("func3\n\n");
	}
	
	public static void funct4(Connection conn, Statement stmt) {
		// input your code
		System.out.println("func4\n\n");
	}
	
	public static void funct5(Connection conn, Statement stmt) {
		// input your code
		System.out.println("func5\n\n");
	}
	
	
	public static void funct6(Connection conn, Statement stmt) {
		
		String arr_station, dep_station, dep_date, dep_time, query, sid = "", row = "", col = "";
		
		System.out.print("Input departing station(ex: chungryangri): ");
		dep_station = sc.next();
		
		System.out.print("Input arriving station(ex: donghae): ");
		arr_station = sc.next();
		
		System.out.print("Input departing date(format: 22/10/07): ");
		dep_date = sc.next();
		
		System.out.print("Input departing time(format: 05:15): ");
		dep_time = sc.next();
		
		query = "SELECT * FROM SEAT" +
				" WHERE SID NOT IN (" +
				"SELECT DISTINCT RE.RSID FROM ROUTE RO, TIMETABLE T, BUS B, RESERVATION RE" +
				" WHERE RO.DSTATION = '" + dep_station +
				"' AND RO.ASTATION = '" + arr_station +
				"' AND T.TRID = RO.RID AND T.TDATE = TO_DATE('" + dep_date + "', 'yy/mm/dd')" +
				" AND T.DEPART_TIME = TO_DATE('" + dep_date + " " + dep_time +
				"', 'yy/mm/dd HH24:MI') AND T.TID = RE.RTID)" +
				" ORDER BY SID ASC";
			
		System.out.println("Seat list you can reserve");
		
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("	");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	   " + row + "		   " + col);
				System.out.println();
			}
			System.out.println(!sid.isEmpty() ? "" : "Can not find a bus from " + dep_station + " to " + arr_station + " for that time slot");
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static void funct7(Connection conn, Statement stmt) {
		String sid, tid, aid, query, row, col;
		
		System.out.print("Input your account number(ex: 692633736): ");
		aid = sc.next();
		
		System.out.println(aid + "'s reservation");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			System.out.println();
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		
		System.out.print("Input timetable number(ex: 61): ");
		tid = sc.next();

		System.out.print("Input seat number you want to seat(ex: F3): ");
		sid = sc.next();
		
		query = "INSERT INTO RESERVATION VALUES('" + aid +
				"', '" + sid + "', " + tid + ")";				
		
		try {
			int res = stmt.executeUpdate(query);
			System.out.println(res + " row inserted\n");
				
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(aid + "'s reservation after insert");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			conn.rollback();
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static void funct8(Connection conn, Statement stmt) {
		String sid, tid, aid, query, row, col, sid1, sid2;
		
		System.out.print("Input your account number(ex: 547937757): ");
		aid = sc.next();
		
		System.out.println(aid + "'s reservation");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			System.out.println();
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		
		System.out.print("Input timetable number(ex: 32): ");
		tid = sc.next();

		System.out.print("Input seat number you want to change(ex: J3): ");
		sid1 = sc.next();
		
		System.out.print("Input seat number you want to seat(ex: J4): ");
		sid2 = sc.next();
		
		query = "UPDATE RESERVATION SET RSID = '" + sid2 +
				"' WHERE RAID = '" + aid +
				"' AND RTID = '" + tid +
				"' AND RSID = '" + sid1 + "'";
				
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			System.out.println(cnt + " row updated");
			
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(aid + "'s reservation after update");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			conn.rollback();
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static void funct9(Connection conn, Statement stmt) {
		String sid, tid, aid, query, row, col;
		
		System.out.print("Input your account number(ex: 547937757): ");
		aid = sc.next();
		
		System.out.println(aid + "'s reservation");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			System.out.println();
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		
		System.out.print("Input timetable number(ex: 32): ");
		tid = sc.next();

		System.out.print("Input seat number you want to delete(ex: J3): ");
		sid = sc.next();
		
		query = "DELETE FROM RESERVATION WHERE RAID = '" + aid +
				"' AND RTID = '" + tid +
				"' AND RSID = '" + sid + "'";
				
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			System.out.println(cnt + " row deleted");
			
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(aid + "'s reservation after delete");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			conn.rollback();
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static void funct10(Connection conn, Statement stmt) {
		String aid, query, date="", dep_time, arr_time, bid, sid, d_station, d_platform, a_station, a_platform;
		
		System.out.print("Input your account number(ex: 246306950): ");
		aid = sc.next();
		
		query = "SELECT TO_CHAR(T.TDATE, 'YYYY/MM/DD') AS DATES," + 
				" TO_CHAR(T.DEPART_TIME, 'HH24:MI') AS DEPART_TIME," +
				" TO_CHAR(T.ARRIVE_TIME, 'HH24:MI') AS ARRIVE_TIME," +
				" T.TBID AS BUS_ID, RE.RSID AS SEAT, RO.DSTATION AS DEPART_STATION," +
				" RO.DPLATFORM AS DEPART_PLATFORM, RO.ASTATION AS ARRIVE_STATION," +
				" RO.APLATFORM AS ARRIVE_PLATFORM FROM RESERVATION RE, TIMETABLE T, ROUTE RO" +
				" WHERE RE.RAID = '" + aid + "'" + " AND RE.RTID = T.TID AND T.TRID = RO.RID";
				
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("	    ");
			}
			System.out.println();
			
			while (rs.next()){
				date = rs.getString(1);
				dep_time = rs.getString(2);
				arr_time = rs.getString(3);
				bid = rs.getString(4);
				sid = rs.getString(5);
				d_station = rs.getString(6);
				d_platform = rs.getString(7);
				a_station = rs.getString(8);
				a_platform = rs.getString(9);
				
				
				System.out.print(date + "    " + dep_time + "	  	" + arr_time + "	     " + bid + "	     "
						+ sid + "	 	     " + d_station + "  	           " + d_platform + "	                 " + a_station + "	          10 " + a_platform);
				System.out.println();
			}
			System.out.println(!date.isEmpty() ? "" : "Can not find your reservation");
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static void funct11(Connection conn, Statement stmt) {
		String tid, dlname = "", dfname, bcompany, query, aid, row, col, sid;
		
		System.out.print("Input your account number(ex: 790210786): ");
		aid = sc.next();
		
		System.out.println(aid + "'s reservation");
		query = "SELECT * FROM RESERVATION WHERE RAID = '" + aid + "'";
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				sid = rs.getString(1);
				row = rs.getString(2);
				col = rs.getString(3);
				
				System.out.print(sid + "	" + row + "		" + col);
				System.out.println();
			}
			System.out.println();
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.print("Input tid you want to show details(ex: 137): ");
		tid = sc.next();
		
		query = "SELECT E.LNAME AS DRIVER_LNAME, E.FNAME AS DRIVER_FNAME, B.BCOMPANY AS BUS_COMPANY" +
				" FROM TIMETABLE T, DRIVES D, BUS B, EMPLOYEE E" +
				" WHERE T.TID = " + tid +
				" AND D.DBID = T.TBID AND B.BID = T.TBID AND E.SSN = D.DSSN";
		
		try {
			rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int cnt = rsmd.getColumnCount();
			for (int i = 1; i <= cnt; i++){
				System.out.print(rsmd.getColumnName(i));
				System.out.print("		");
			}
			System.out.println();
			
			while (rs.next()){
				dlname = rs.getString(1);
				dfname = rs.getString(2);
				bcompany = rs.getString(3);
				
				System.out.print(dlname + "		         " + dfname + "	       " + bcompany);
				System.out.println();
			}
			System.out.println(!dlname.isEmpty() ? "" : "Can not find detail information");
			
		} catch (SQLException e) {
			System.err.println("sql error = " + e.getMessage());
			e.printStackTrace();
		}
	}
}
