package com.ruskaof.lab3.repository.impl.lazy;

import com.ruskaof.lab3.AttemptBean;
import org.primefaces.model.LazyDataModel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AttemptLazyModelHolder implements Serializable {
    private AttemptLazyDataModel model = new AttemptLazyDataModel();

    public LazyDataModel<AttemptBean> getLazyModel(AttemptService service) {
        model.setService(service);
        return model;
    }
}
