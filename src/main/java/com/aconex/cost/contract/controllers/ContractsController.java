package com.aconex.cost.contract.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import com.aconex.cost.contract.models.Contract;
import com.aconex.cost.contract.repositories.ContractRepository;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.WILDCARD)
public class ContractsController {

    private final ContractRepository contractRepository;

    public ContractsController(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @GET
    @UnitOfWork
    public Response index() {
        return Response.ok(contractRepository.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getContract(@PathParam("id") Long id) {
        return Response.ok(contractRepository.findById(id)).build();
    }

    @POST
    @UnitOfWork
    public Response postContract(@Valid Contract contract) {
        if (Optional.ofNullable(contractRepository.findByCode(contract.getCode())).isPresent()) {
            final Map<String, String> result = new HashMap<>();
            result.put("result", "A contract with this code already exists.");
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).entity(result).build();
        }

        return Response.status(HttpStatus.CREATED_201)
                .entity(contractRepository.save(contract))
                .build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response putContract(@PathParam("id") Long id, Contract contract) {
        if (Optional.ofNullable(contractRepository.findById(id)).isPresent()) {
            final Map<String, String> result = new HashMap<>();
            result.put("result", "The contract with this code not exists.");
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).entity(result).build();
        }
        return Response.status(HttpStatus.BAD_REQUEST_400).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteContract(@PathParam("id") Long id) {
        if (Optional.ofNullable(contractRepository.findById(id)).isPresent()) {
            contractRepository.delete(contractRepository.findById(id));
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/totals")
    @UnitOfWork
    public Response getTotalContractAmount() throws InterruptedException, ExecutionException {
        Map<String, Double> response = new HashMap<>();
        List<Contract> contracts = contractRepository.findAll();
        final Double totalContractAmount = contracts.stream().mapToDouble(contract -> contract.getContractAmount()).sum();
        final Double totalInvoicedAmount = contracts.stream().mapToDouble(contract -> contract.getInvoicedAmount()).sum();
        final Double percentInvoiced = totalInvoicedAmount / totalContractAmount * 100;

        response.put("totalContractAmount", totalContractAmount);                                                        
        response.put("totalInvoicedAmount", totalInvoicedAmount);                                                       
        response.put("percentInvoiced", percentInvoiced);                                                        
;
        return Response.ok(response).build();
    }
}
