package org.workplacescheduler.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias( "Shift" )
public class Shift {

	@XStreamAlias( "ShiftId" )
	private int _shiftId;

	@XStreamAlias( "Employee" )
	private Employee _employee;

	@XStreamAlias( "Job" )
	private Job _job;

	@XStreamAlias( "StartTime" )
	private Date _startTime;

	@XStreamAlias( "StopTime" )
	private Date _endTime;

	public Shift( final int shiftId, final Employee employee, final Job job, final Date startTime, final Date endTime ) {
		setShiftId( shiftId );
		_employee = employee;
		_job = job;
		_startTime = startTime;
		_endTime = endTime;
	}

	public int getShiftId() {
		return _shiftId;
	}

	public void setShiftId( final int shiftId ) {
		if ( shiftId >= 0 ) {
			_shiftId = shiftId;
		}
	}

	public Employee getEmployee() {
		return _employee;
	}

	public void setEmployee( final Employee employee ) {
		_employee = employee;
	}

	public Job getJob() {
		return _job;
	}

	public void setJob( final Job job ) {
		_job = job;
	}

	public Date getStartTime() {
		return _startTime;
	}

	public void setStartTime( final Date startTime ) {
		_startTime = startTime;
	}

	public Date getEndTime() {
		return _endTime;
	}

	public void setEndTime( final Date endTime ) {
		_endTime = endTime;
	}

}
