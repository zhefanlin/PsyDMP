package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//import play.api.libs.json.Json;
import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.*;

import javax.persistence.*;

import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.data.format.*;
import play.libs.Json;

@Entity
public class ShowData extends Model {

	// Constructor
	public ShowData() {
		super();
	}

	public static String getSegData(String query) throws SQLException,
			JSONException {
		String str = "";
		JSONArray array = new JSONArray();
		// Connect to Database
		Connection con = DB.getConnection();
		Statement sql_statement = con.createStatement();

		// Read Data
		ResultSet result = sql_statement.executeQuery(query);
		while (result.next()) {
			int number = result.getInt("Number");
			String name = result.getString("SegmentName");
			int ID = result.getInt("SegmentID");

			// Generate a Json String for output
			JSONObject obj = new JSONObject();
			obj.put("SegmentID",ID);
			obj.put("SegmentName", name);
			obj.put("Number", number);
			array.put(obj);
		}
		str =array.toString();
		sql_statement.close();
		con.close();
		return str;
	}

	public static String getRankingData(String query) throws SQLException,
			JSONException {
		String str = "";
		JSONArray array = new JSONArray();
		// Connect to Database
		Connection con = DB.getConnection();
		Statement sql_statement = con.createStatement();

		// Read Data
		ResultSet result = sql_statement.executeQuery(query);
		while (result.next()) {
			int count = result.getInt("Cnt");
			String SegmentName = result.getString("SegmentName");

			// Generate a Json String for output
			JSONObject obj = new JSONObject();
			obj.put("SegmentName", SegmentName);
			obj.put("NumOfHousehold", count);
			array.put(obj);
		}
		str =array.toString();
		sql_statement.close();
		con.close();
		return str ;
	}

	public static String getHouseholdData(String query) throws SQLException,
			JSONException {
		String str = "";
		JSONArray array = new JSONArray();
		// Connect to Database
		Connection con = DB.getConnection();
		Statement sql_statement = con.createStatement();

		// Read Data
		ResultSet result = sql_statement.executeQuery(query);
		while (result.next()) {
			String SegmentName = result.getString("SegmentName");
			Double Score = result.getDouble("Score");
			String IPAddress = result.getString("IPAddress");

			// Generate a Json String for output
			JSONObject obj = new JSONObject();
			obj.put("SegmentName", SegmentName);
			obj.put("Score", Score);
			obj.put("IPAddress", IPAddress);
			array.put(obj);
		}
		str =array.toString();
		sql_statement.close();
		con.close();
		return str ;
	}

	public static String showSegments() throws SQLException, JSONException {
		// Create Database Query
		String query = "SELECT * FROM segments";
		String result = getSegData(query);
		return result;
	}

	public static String showSegmentsTopN(int n) throws SQLException,
			JSONException {
		// Create Database Query
		String query = "SELECT * FROM segments ORDER BY Number DESC LIMIT  "
				+ n;
		String result = getSegData(query);
		return result;
	}

	public static String showNumScore(String segmentName, double s)
			throws SQLException, JSONException {
		// Create Database Query
		String query = " SELECT SegmentName, COUNT(rankings.HouseholdID) as Cnt FROM segments LEFT JOIN rankings ON segments.SegmentID=rankings.SegmentID where rankings.Score  > "
				+ s
				+ " AND segments.SegmentName= '"
				+ segmentName
				+ "' GROUP BY segments.SegmentID";
		String result = getRankingData(query);
		return result;
	}

	public static String showIPMore(String IPAddress, int n)
			throws SQLException, JSONException {
		// Create Database Query
		String query = "SELECT SegmentName, Score, IPAddress FROM segments LEFT JOIN rankings ON segments.SegmentID=rankings.SegmentID LEFT JOIN households on rankings.HouseholdID=households.HouseholdID where rankings.Score >=0.5 and households.IPAddress = '"
				+ IPAddress + ";' ORDER BY Score DESC LIMIT " + n;
		String result = getHouseholdData(query);
		return result;
	}

	public static String showIPLess(String IPAddress, int n)
			throws SQLException, JSONException {
		// Create Database Query
		String query = "SELECT SegmentName, Score, IPAddress FROM segments LEFT JOIN rankings ON segments.SegmentID=rankings.SegmentID LEFT JOIN households on rankings.HouseholdID=households.HouseholdID where rankings.Score <0.5 and households.IPAddress =  '"
				+ IPAddress + ";' ORDER BY Score ASC LIMIT " + n;
		String result = getHouseholdData(query);
		return result;
	}

}
