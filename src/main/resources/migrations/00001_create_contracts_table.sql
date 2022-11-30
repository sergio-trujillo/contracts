--liquibase formatted sql

--changeset timols:1
CREATE TABLE contracts
(
    id              INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    code            VARCHAR(100) NOT NULL,
    description     TEXT,
    vendor          TEXT,
    contract_amount NUMERIC      NOT NULL DEFAULT 0,
    invoiced_amount NUMERIC      NOT NULL DEFAULT 0,
    created_at      TIMESTAMP AS CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (code)
);



INSERT INTO contracts (code, description, vendor, contract_amount, invoiced_amount)
VALUES ('CON-001', 'Concrete', 'ACME Concrete', 10000.0, 1000.0);

INSERT INTO contracts (code, description, vendor, contract_amount, invoiced_amount)
VALUES ('LUM-001', 'Lumber', 'Pacific Lumber', 20000.0, 6000.0);

INSERT INTO contracts (code, description, vendor, contract_amount, invoiced_amount)
VALUES ('EQ-001', 'Crane Rental', 'Big Machine, Inc.', 5000.0, 1000.0);

INSERT INTO contracts (code, description, vendor, contract_amount, invoiced_amount)
VALUES ('PL-001', 'Plumbing', 'Pipeworks', 10000.0, 0.0);

--rollback DROP TABLE contracts;
