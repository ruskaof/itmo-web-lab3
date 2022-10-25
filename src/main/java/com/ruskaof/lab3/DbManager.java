package com.ruskaof.lab3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class DbManager {
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AttemptBean.class).buildSessionFactory();
    private int id = 0;

    static {
        System.out.println("DbManager created");
    }

    public void addAttempt(AttemptBean attemptBean) {
        attemptBean.checkHit();
        System.out.println("addAttempt");
        id++;
        attemptBean.checkHit();
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
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        System.out.println("addAttemptFromJsParams");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        try {
            double xCoordinate = Double.parseDouble(params.get("x"));
            double yCoordinate = Double.parseDouble(params.get("y"));
            double graphR = Double.parseDouble(params.get("r"));
            final AttemptBean attemptBean = new AttemptBean(
                    xCoordinate / graphR,
                    yCoordinate / graphR,
                    currentR
            );
            addAttempt(attemptBean);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
