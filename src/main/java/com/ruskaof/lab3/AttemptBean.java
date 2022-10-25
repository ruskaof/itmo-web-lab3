package com.ruskaof.lab3;

import jakarta.persistence.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@ManagedBean
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
    private Long workTime;
    @Column
    private Date startTime;
    @Transient
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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

    public Long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Long workTime) {
        this.workTime = workTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void checkHit() {
        long start = System.currentTimeMillis();
        if (x >= 0 && y <= r/2 - x && y >= 0) hit = true;
        else if (x >= 0 && y<=0 && y >= -r && x <= r/2) hit = true;
        else if (x<=0 && y >= 0 && x*x+y*y <= r*r) hit = true;
        else hit = false;
        startTime = new Date(start);
        workTime = ((System.currentTimeMillis() - start)/1000);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttemptBean that = (AttemptBean) o;
        return attempt == that.attempt && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.r, r) == 0 && hit == that.hit && Objects.equals(workTime, that.workTime) && Objects.equals(startTime, that.startTime) && Objects.equals(simpleDateFormat, that.simpleDateFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, x, y, r, hit, workTime, startTime, simpleDateFormat);
    }
}