package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//import play.api.libs.json.Json;
import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.db.*;

import javax.persistence.*;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.data.format.*;

@Entity
public class ShowData extends Model {

	
	// Constructor
	public ShowData() {
		super();
	}

	public static String getSegData(String query) throws SQLException, JSONException {
		String s = "{";
		Connection con = DB.getConnection();
		Statement sql_statement = con.createStatement();
		ResultSet result = sql_statement.executeQuery(query);
		while (result.next()) {
			int number = result.getInt("Number");
			String name = result.getString("SegmentName");
			int ID = result.getInt("SegmentID");

			// System.out.println(number+"  "+"  "+name+"  "+ID);

			// Generate a Json String for output
			Map obj = new LinkedHashMap();
			obj.put("SegmentID", ID);
			obj.put("SegmentName", name);
			obj.put("Number", number);
			s = s + "\n" + obj.toString();
		}
		sql_statement.close();
		con.close();
		return s + "\n"+ "}";
	}

	public static String getRankingData(String query) throws SQLException, JSONException {
		String str= "{";
		Connection con = DB.getConnection();
		Statement sql_statement = con.createStatement();
		ResultSet result = sql_statement.executeQuery(query);
		while (result.next()) {
			int count = result.getInt("Cnt");
			String SegmentName = result.getString("SegmentName");
			// Generate a Json String for output
			Map obj = new LinkedHashMap();
			obj.put("SegmentName", SegmentName);
			obj.put("NumOfHousehold", count);
			
			str = str + "\n" + obj.toString();
			
		}
		sql_statement.close();
		con.close();
		return str + "\n"+ "}";
	}
	
	public static String getHouseholdData(String query) throws SQLException, JSONException {
		String str= "{";
		Connection con = DB.getConnection();
		Statement sql_statement = con.createStatement();
		ResultSet result = sql_statement.executeQuery(query);
		while (result.next()) {
			String SegmentName = result.getString("SegmentName");
			Double Score = result.getDouble("Score");
			String IPAddress = result.getString("IPAddress");
			
			// Generate a Json String for output
			Map obj = new LinkedHashMap();
			obj.put("SegmentName", SegmentName);
			obj.put("Score", Score);
			obj.put("IPAddress", IPAddress);
			
			str = str + "\n" + obj.toString();
			
		}
		sql_statement.close();
		con.close();
		return str + "\n"+ "}";
	}
	
	public static String showSegments() throws SQLException, JSONException {
		String query = "select * from segments";
		String result = getSegData(query);
		return result;
	}

	public static String showSegmentsTopN(int n) throws SQLException, JSONException {
		String query = "select * from segments ORDER BY Number DESC LIMIT  "+n;
		String result = getSegData(query);
		return result;
	}

	public static String showNumScore(String segmentName, double s) throws SQLException, JSONException {
		String query = " SELECT SegmentName, COUNT(rankings.HouseholdID) as Cnt FROM segments LEFT JOIN rankings ON segments.SegmentID=rankings.SegmentID where rankings.Score  > "+s+" AND segments.SegmentName= '"+segmentName+"' GROUP BY segments.SegmentID";
        String result = getRankingData(query);
		return result;
	}
	
	public static String showIPMore(String IPAddress, int n) throws SQLException, JSONException{
		String query = "SELECT SegmentName, Score, IPAddress FROM segments LEFT JOIN rankings ON segments.SegmentID=rankings.SegmentID LEFT JOIN households on rankings.HouseholdID=households.HouseholdID where rankings.Score >=0.5 and households.IPAddress = '"+IPAddress+";' ORDER BY Score DESC LIMIT " + n;
		String result = getHouseholdData(query);
		return result;
	}
	
	public static String showIPLess(String IPAddress, int n) throws SQLException, JSONException{
		String query = "SELECT SegmentName, Score, IPAddress FROM segments LEFT JOIN rankings ON segments.SegmentID=rankings.SegmentID LEFT JOIN households on rankings.HouseholdID=households.HouseholdID where rankings.Score <0.5 and households.IPAddress =  '"+IPAddress+";' ORDER BY Score ASC LIMIT "+n;
		String result = getHouseholdData(query);
		return result;
	}
	
	
	//public static String showNumScore() throws SQLException, JSONException {
		//String query = "select * from rankings ORDER BY Score DESC LIMIT 10";
		//String result = getData(query);
		//return result;
	}


