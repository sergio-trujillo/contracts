package com.aconex.cost.contract.repositories;

import com.aconex.cost.contract.models.Contract;
import org.hibernate.SessionFactory;

import java.util.List;

public class ContractRepository {

    private final SessionFactory sessionFactory;

    public ContractRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contract> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Contract order by code", Contract.class)
                .list();
    }

    public Contract findByCode(String code){
        return sessionFactory.getCurrentSession()
                .createQuery("from Contract c where c.code=:code", Contract.class)
                .setParameter("code", code)
                .uniqueResult();
    }

    public Contract save(Contract contract) {
        sessionFactory.getCurrentSession().save(contract);
        return contract;
    }

    public Contract update(Contract contract) {
        sessionFactory.getCurrentSession().update(contract);
        return contract;
    }


    public Contract findById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Contract c where c.id=:id", Contract.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    public void delete(Contract contract) {
        sessionFactory.getCurrentSession()
                .delete(contract);
    }
}
