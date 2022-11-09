package com.ruskaof.lab3.repository.impl.lazy;

import com.ruskaof.lab3.AttemptBean;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;


@Named("attemptsList")
@SessionScoped
public class AttemptLazyDataModel extends LazyDataModel<AttemptBean> {

    @Inject
    private AttemptRepositoryWithPagination service;

    public void setService(AttemptRepositoryWithPagination service) {
        this.service = service;
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        System.out.println("Using count");
        return service.getAttemptsCount();
    }

    @Override
    public List<AttemptBean> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
        System.out.println("Using load with first = " + first + " and pageSize = " + pageSize);
        return service.getAttemptsList(first, pageSize);

    }
}
