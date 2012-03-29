package com.wallofshame.service;

import com.wallofshame.domain.*;
import com.wallofshame.repository.MissingTimeSheetRepository;
import com.wallofshame.utils.DateTimeUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Since: 3/16/12
 */
@Service("updateWallOfShameService")
public class UpdateWallOfShameService {

    private MissingTimeSheetRepository repo;

    @Autowired
    public UpdateWallOfShameService(MissingTimeSheetRepository repo) {
        this.repo = repo;
    }

    //scheduled at every 2 hours
    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void pullUpdates() {
        Employees employees = repo.lookUp(DateTimeUtils.lastSunday(new DateTime()), companyId());
        PeopleMissingTimeSheet.getInstance().replaceAll(employees);
    }

    private String companyId() {
        //TCH mean China
        return "TCH";
    }
}
