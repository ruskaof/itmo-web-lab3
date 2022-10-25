package com.ruskaof.lab3;

import jakarta.persistence.*;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Named("attemptBean")
@SessionScoped
@Table(name = "attempts")
@Entity
public class AttemptBean implements Serializable {
    @Id
    private int attempt = 0;
    @Column
    private double x;
    @Column
    private double y;
    @Column
    private int r = 1;
    @Column
    private boolean hit;
    @Column
    private Long processTime;
    @Column
    private Date attemptTime;
    @Transient
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public AttemptBean() {
    }

    public AttemptBean(double x, double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getId() {
        return attempt;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Long workTime) {
        this.processTime = workTime;
    }

    public Date getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(Date startTime) {
        this.attemptTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttemptBean that = (AttemptBean) o;
        return attempt == that.attempt && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.r, r) == 0 && hit == that.hit && Objects.equals(processTime, that.processTime) && Objects.equals(attemptTime, that.attemptTime) && Objects.equals(simpleDateFormat, that.simpleDateFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, x, y, r, hit, processTime, attemptTime, simpleDateFormat);
    }
}