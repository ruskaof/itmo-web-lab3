package com.ruskaof.lab3.repository.impl;

import com.ruskaof.lab3.AttemptBean;
import com.ruskaof.lab3.repository.api.AttemptsRepository;
import com.ruskaof.lab3.util.area.check.api.AreaCheck;
import com.ruskaof.lab3.util.area.check.di.AreaCheckQualifier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Main implementation of AttemptsRepository interface
 */
@Named("attemptsRepository")
@SessionScoped
public class AttemptsRepositoryImpl implements Serializable, AttemptsRepository {
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AttemptBean.class).buildSessionFactory();
    private int id = 0;

    @Inject
    @AreaCheckQualifier
    private AreaCheck areaCheck;

    {
        System.out.println("DbManager created");
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

    public List<AttemptBean> getAttempts() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From AttemptBean ").list();
        }
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

}
