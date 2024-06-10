package com.honeybee.work_log.util;

import com.honeybee.work_log.dto.WorkLogsViewResponse;

import java.util.Comparator;

public class WorkLogsViewResponseComparator implements Comparator<WorkLogsViewResponse> {
    @Override
    public int compare(WorkLogsViewResponse o1, WorkLogsViewResponse o2) {

        if (o1.getId() < o2.getId()) {
            return 1;
        } else if (o1.getId() > o2.getId()) {
            return -1;
        }
        return 0;
    }
}
