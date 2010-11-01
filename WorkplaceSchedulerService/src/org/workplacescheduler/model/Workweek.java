package org.workplacescheduler.model;

import java.util.ArrayList;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias( "Workweek" )
public class Workweek {
	@XStreamAlias( "WorkweekId" )
	private int _workweekId;

	@XStreamAlias( "WeekStartDate" )
	private Date _weekStartDate;

	@XStreamAlias( "Shifts" )
	private ArrayList< Shift > _shifts;

	public int getWorkweekId() {
		return _workweekId;
	}

	public Workweek( final int workweekId, final Date weekStartDate, final ArrayList< Shift > shifts ) {
		_workweekId = workweekId;
		_weekStartDate = weekStartDate;
		_shifts = shifts;
	}

	public Workweek() {}

	public void setWorkweekId( final int workweekId ) {
		_workweekId = workweekId;
	}

	public ArrayList< Shift > getShifts() {
		return _shifts;
	}

	public void setShifts( final ArrayList< Shift > shifts ) {
		_shifts = shifts;
	}

	public Date getWeekStartDate() {
		return _weekStartDate;
	}

	public void setWeekStartDate( final Date weekStartDate ) {
		_weekStartDate = weekStartDate;
	}

}
