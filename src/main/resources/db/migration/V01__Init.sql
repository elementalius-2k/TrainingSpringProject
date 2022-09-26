CREATE TABLE Prod_group
(
    id              BIGSERIAL   PRIMARY KEY,
    name            TEXT        NOT NULL UNIQUE
);

CREATE TABLE Producer
(
    id              BIGSERIAL   PRIMARY KEY,
    name            TEXT        NOT NULL UNIQUE,
    address         TEXT        NOT NULL
);

CREATE TABLE Product
(
    id              BIGSERIAL    PRIMARY KEY,
    group_id        BIGSERIAL    NOT NULL REFERENCES Prod_group(id),
    producer_id     BIGSERIAL    NOT NULL REFERENCES Producer(id),
    name            TEXT         NOT NULL,
    description     TEXT,
    quantity        INTEGER      NOT NULL CHECK (quantity >= 0),
    income_price    REAL         NOT NULL CHECK (income_price > 0),
    outcome_price   REAL         NOT NULL CHECK (outcome_price > 0)
);

CREATE TABLE Worker
(
    id              BIGSERIAL   PRIMARY KEY,
    name            TEXT        NOT NULL UNIQUE,
    job             TEXT        NOT NULL
);

CREATE TABLE Partner
(
    id              BIGSERIAL   PRIMARY KEY,
    name            TEXT        NOT NULL UNIQUE,
    address         TEXT        NOT NULL,
    email           TEXT        NOT NULL,
    requisites      TEXT        NOT NULL UNIQUE
);

CREATE TABLE Invoice
(
    id              BIGSERIAL   PRIMARY KEY,
    partner_id      BIGSERIAL   NOT NULL REFERENCES Partner(id),
    worker_id       BIGSERIAL   NOT NULL REFERENCES Worker(id),
    type            INTEGER     NOT NULL,
    date            DATE        NOT NULL
);

CREATE TABLE Item
(
    id              BIGSERIAL   PRIMARY KEY,
    invoice_id      BIGSERIAL   NOT NULL REFERENCES Invoice(id),
    product_id      BIGSERIAL   NOT NULL REFERENCES Product(id),
    quantity        INTEGER     NOT NULL CHECK (quantity > 0),
    price           REAL        NOT NULL CHECK (price > 0)
);