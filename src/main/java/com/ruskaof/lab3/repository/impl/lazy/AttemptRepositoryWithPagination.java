package com.ruskaof.lab3.repository.impl.lazy;

import com.google.gson.Gson;
import com.ruskaof.lab3.AttemptBean;
import com.ruskaof.lab3.util.area.check.api.AreaCheck;
import com.ruskaof.lab3.util.area.check.di.AreaCheckQualifier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("attemptRepository")
@ApplicationScoped
public class AttemptRepositoryWithPagination implements Serializable {
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AttemptBean.class).buildSessionFactory();
    private static final int LATEST_ATTEMPTS_COUNT = 10;
    private int id = 10_000_001;

    @Inject
    @AreaCheckQualifier
    private AreaCheck areaCheck;

    public List<AttemptBean> getAttemptsList(int start, int count) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<AttemptBean> data = session.createQuery("From AttemptBean ").setFirstResult(start).setMaxResults(count).list();
        session.getTransaction().commit();
        return data;
    }

    public List<AttemptBean> getLatestAttemptsList() {
        int attemptsCount = getAttemptsCount();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int firstResultIndex = Math.max(attemptsCount - LATEST_ATTEMPTS_COUNT, 0);
        List<AttemptBean> data = session.createQuery("From AttemptBean ").setFirstResult(firstResultIndex).setMaxResults(LATEST_ATTEMPTS_COUNT).list();
        session.getTransaction().commit();

        System.out.println("getLatestAttemptsList " + data.size());
        return data;
    }

    public void addOneThousandAttempts() {
        for (int i = 0; i < 1_000_00; i++) {
            System.out.println("addAttempt " + i);
            addAttempt(new AttemptBean());
        }

    }

    public void addAttempt(AttemptBean attemptBean) {
        areaCheck.checkHit(attemptBean);
        System.out.println("addAttempt");
        id++;
        attemptBean.setAttempt(id);
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(attemptBean);
        session.getTransaction().commit();
    }

    public int getAttemptsCount() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int count = session.createQuery("select count(*) from AttemptBean", Number.class).getSingleResult().intValue();
        session.getTransaction().commit();
        return count;
    }

    public void clearAttempts() {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().createQuery("delete from AttemptBean").executeUpdate();
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    public void addAttemptFromJsParams(int currentR) {
        System.out.println("addAttemptFromJsParams");
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            double xCoordinate = Double.parseDouble(params.get("x"));
            double yCoordinate = Double.parseDouble(params.get("y"));
            double graphR = Double.parseDouble(params.get("r"));
            final AttemptBean attemptBean = new AttemptBean(
                    xCoordinate / graphR * currentR,
                    yCoordinate / graphR * currentR,
                    currentR
            );
            addAttempt(attemptBean);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public String getX() {
        return new Gson().toJson(getLatestAttemptsList().stream().map(AttemptBean::getX).collect(Collectors.toList()));
    }

    public String getY() {
        return new Gson().toJson(getLatestAttemptsList().stream().map(AttemptBean::getY).collect(Collectors.toList()));
    }

    public String getR() {
        return new Gson().toJson(getLatestAttemptsList().stream().map(AttemptBean::getR).collect(Collectors.toList()));
    }

    public String getHit() {
        return new Gson().toJson(getLatestAttemptsList().stream().map(AttemptBean::isHit).collect(Collectors.toList()));
    }

    public String getDotsCoordinates() {
        return new Gson().toJson(getLatestAttemptsList().stream().map(AttemptBean::getCoordinates).collect(Collectors.toList()));
    }
}
