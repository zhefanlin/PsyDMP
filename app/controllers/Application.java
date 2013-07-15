package controllers;

import java.sql.SQLException;

import org.json.JSONException;

import models.*;
import play.*;
import play.mvc.*;
import views.html.*;
import play.core.Router.Param;
import play.data.*;

public class Application extends Controller {

	// static Form<ShowData> taskForm = form(ShowData.class);

	public static Result index() {
		return ok("PsyDMP project");
	}

	public static Result segments() throws SQLException, JSONException {
		return ok(ShowData.showSegments());
	} // returns all segment names and the number of households in each

	public static Result segmentsTopN(int n) throws SQLException, JSONException {
		return ok(ShowData.showSegmentsTopN(n));
	}// returns top segment Names and number, descending values, limited
		// (default=10)

	public static Result segnameNumHouseScore(String segmentName, double s) throws SQLException,
			JSONException {
		return ok(ShowData.showNumScore(segmentName, s));
	}// returns households number in segment which score >= floor (s)

	public static Result ipSegnameScoreMore(String IPAddress, int n)
			throws SQLException, JSONException {
		return ok(ShowData.showIPMore(IPAddress, n));

	}

	public static Result ipSegnameScoreLess(String IPAddress, int n) throws SQLException,
			JSONException {

		return ok(ShowData.showIPLess(IPAddress, n));
	}
}
