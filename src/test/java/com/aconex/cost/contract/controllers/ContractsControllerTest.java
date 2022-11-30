package com.aconex.cost.contract.controllers;

import com.aconex.cost.contract.models.Contract;
import com.aconex.cost.contract.repositories.ContractRepository;
import com.codahale.metrics.MetricRegistry;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.dropwizard.views.ViewMessageBodyWriter;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ContractsControllerTest {

    private static final ContractRepository contractRepository = mock(ContractRepository.class);

    public static final ResourceExtension resources = ResourceExtension.builder()
            .addProvider(new ViewMessageBodyWriter(new MetricRegistry(), Collections.singleton(new MustacheViewRenderer())))
            .addResource(new ContractsController(contractRepository))
            .build();

    @BeforeEach
    public void setup() {
        when(contractRepository.findAll()).thenReturn(Lists.newArrayList(
                new Contract().setCode("CON-01").setCreatedAt(DateTime.now()).setContractAmount(10000).setInvoicedAmount(10000)
        ));
    }

    @AfterEach
    public void tearDown() {
        reset(contractRepository);
    }

    @Test
    public void testGetIndex() throws Exception {
        Response response = resources.client().target("/contracts").request().get();
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

        String html = new String(ByteStreams.toByteArray((ByteArrayInputStream) response.getEntity()));
        assertTrue(html.contains("CON-01"));
        verify(contractRepository).findAll();
    }
}
