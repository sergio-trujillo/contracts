package com.aconex.cost.contract.views;

import com.aconex.cost.contract.models.Contract;
import io.dropwizard.views.View;

import java.util.List;

public class ContractsView extends View {

    private final List<Contract> contracts;

    public ContractsView(List<Contract> contracts) {
        super("index.mustache");
        this.contracts = contracts;
    }

    public List<Contract> getContracts() {
        return contracts;
    }
}
