package org.workplacescheduler.model;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias( "Job" )
public class Job {

	@XStreamAlias( "JobId" )
	private int _jobId;

	@XStreamAlias( "JobName" )
	private String _jobName = "";

	public Job( final int jobId, final String jobName ) {
		setJobId( jobId );
		setJobName( jobName );
	}

	public int getJobId() {
		return _jobId;
	}

	public void setJobId( final int jobId ) {
		if ( jobId >= 0 ) {
			_jobId = jobId;
		}
	}

	public String getJobName() {
		return _jobName;
	}

	public void setJobName( final String jobName ) {
		if ( StringUtils.isNotBlank( jobName ) ) {
			_jobName = jobName;
		}
	}

}
