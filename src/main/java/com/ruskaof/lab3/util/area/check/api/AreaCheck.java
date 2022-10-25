package com.ruskaof.lab3.util.area.check.api;

import com.ruskaof.lab3.AttemptBean;

import java.io.Serializable;


public interface AreaCheck extends Serializable {
    /**
     * Checks if the point is in the area and sets the hit field of the bean
     * Also calculates the process time
     */
    void checkHit(AttemptBean attemptBean);
}
