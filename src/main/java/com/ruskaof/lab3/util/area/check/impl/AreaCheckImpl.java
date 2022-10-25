package com.ruskaof.lab3.util.area.check.impl;

import com.ruskaof.lab3.AttemptBean;
import com.ruskaof.lab3.util.area.check.api.AreaCheck;
import com.ruskaof.lab3.util.area.check.di.AreaCheckQualifier;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named("areaCheck")
@SessionScoped
@AreaCheckQualifier
public class AreaCheckImpl implements AreaCheck, Serializable {
    {
        System.out.println("AreaCheckImpl created");
    }

    @Override
    public void checkHit(AttemptBean attemptBean) {
        long startTime = System.currentTimeMillis();
        attemptBean.setAttemptTime(new Date(startTime));

        boolean hit = attemptIsInArea(attemptBean);
        attemptBean.setHit(hit);
        attemptBean.setProcessTime(System.currentTimeMillis() - attemptBean.getAttemptTime().getTime());
    }

    private boolean attemptIsInArea(AttemptBean attemptBean) {
        return attemptIsInRect(attemptBean) || attemptIsInTriangle(attemptBean) || attemptIsInSector(attemptBean);
    }

    private boolean attemptIsInRect(AttemptBean attemptBean) {
        double x = attemptBean.getX();
        double y = attemptBean.getY();
        int r = attemptBean.getR();

        return (x <= 0 && x >= -r && y >= 0 && y <= r / 2.0);
    }

    private boolean attemptIsInSector(AttemptBean attemptBean) {
        double x = attemptBean.getX();
        double y = attemptBean.getY();
        int r = attemptBean.getR();

        return (x >= 0 && y >= 0 && x * x + y * y <= r * r);
    }

    private boolean attemptIsInTriangle(AttemptBean attemptBean) {
        double x = attemptBean.getX();
        double y = attemptBean.getY();
        int r = attemptBean.getR();

        return (x >= 0 && y <= 0 && y >= x - r / 2.0);
    }
}
