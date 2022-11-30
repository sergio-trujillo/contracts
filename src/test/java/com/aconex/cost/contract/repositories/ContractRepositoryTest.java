package com.aconex.cost.contract.repositories;


import com.aconex.cost.contract.models.Contract;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ContractRepositoryTest {

    private final DAOTestExtension database = DAOTestExtension.newBuilder().addEntityClass(Contract.class).build();

    private ContractRepository contractRepository;

    @BeforeEach
    public void setUp() {
        contractRepository = new ContractRepository(database.getSessionFactory());
    }

    @Test
    void save_successfully_saves_contract() {
        Contract contract = new Contract();
        contract.setCode("1-00");

        database.inTransaction(() -> contractRepository.save(contract));

        assertThat(contract.getId(), notNullValue());
        assertEquals(contract.getCode(), "1-00");
    }

    @Test
    public void findAll() {
        fail();
    }

}
