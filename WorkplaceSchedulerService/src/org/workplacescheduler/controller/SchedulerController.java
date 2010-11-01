package org.workplacescheduler.controller;

import org.workplacescheduler.dal.SchedulerServiceDal;

/**
 * @author last modified by: $Author$
 * @version $Revision$ $Date$
 */
public abstract class SchedulerController {
	private final SchedulerServiceDal _schedulerServiceDal;

	public SchedulerController( final SchedulerServiceDal schedulerServiceDal ) {
		_schedulerServiceDal = schedulerServiceDal;
	}

	protected SchedulerServiceDal getSchedulerServiceDal() {
		return _schedulerServiceDal;
	}

}
