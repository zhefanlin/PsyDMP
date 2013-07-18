package controllers;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import models.*;
import play.libs.Json;
import play.mvc.*;
import views.*;

public class Application extends Controller {

	public static Result index() {
		return ok("PsyDMP project");
	}// Home Page

	public static Result segments() throws SQLException, JSONException {
			response().setContentType("application/json");
		    return ok(ShowData.showSegments());
	} // returns all segment names and the number of households in each

	public static Result segmentsTopN(int n) throws SQLException, JSONException {
		response().setContentType("application/json");
		return ok(ShowData.showSegmentsTopN(n));
	}// returns top segment Names and number, descending values, limited
		// (default=10)

	public static Result segnameNumHouseScore(String segmentName, double s)
			throws SQLException, JSONException {
		response().setContentType("application/json");
		return ok(ShowData.showNumScore(segmentName, s));
	}// returns households number in segment which score >= floor (s)

	public static Result ipSegnameScoreMore(String IPAddress, int n)
			throws SQLException, JSONException {
		response().setContentType("application/json");
		return ok(ShowData.showIPMore(IPAddress, n));
	}// returns all segment Names and scores
		// in which the household corresponding to the IP address given has score >= 0.5 in descending order of score
		//limited by maximum specified in top parameter, top parameter is optional with default value = 10

	public static Result ipSegnameScoreLess(String IPAddress, int n)
			throws SQLException, JSONException {
		response().setContentType("application/json");
		return ok(ShowData.showIPLess(IPAddress, n));
	}//returns all segment Names and scores
	  //in which the household corresponding to the IP address given has score < 0.5 in descending order of score
}
