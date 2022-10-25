package com.ruskaof.lab3.repository.api;

import com.ruskaof.lab3.AttemptBean;

import java.io.Serializable;
import java.util.List;

/**
 * Use the "attemptsRepository" name to access this interface implementation
 */
public interface AttemptsRepository extends Serializable {
    void addAttempt(AttemptBean attemptBean);
    List<AttemptBean> getAttempts();
    void clearAttempts();
}
