
CREATE TABLE cliente (
    id_cliente serial PRIMARY KEY,
    nome varchar(100) not null,
    telefone varchar(20) ,
    documento varchar(20) not null,
    ativo boolean not null
);

CREATE TABLE registro_abastecimento (
    id_atendimento serial PRIMARY KEY,
    data timestamp not null,
    quantidade_litros float not null,
    valor float not null,
    tipo_de_combustivel varchar(50) not null,
    forma_de_pagamento varchar(50) not null,
    desconto float,
    fk_cliente_id_cliente integer,
    fk_funcionarios_id_funcionario integer not null
);

CREATE TABLE funcionarios (
    id_funcionario serial PRIMARY KEY,
    nome varchar(100) not null,
    data_nascimento date,
    cpf varchar(11) not null,
    cargo varchar(50) not null,
    bairro varchar(255),
    cep varchar(255),
    cidade varchar(255),
    complemento varchar(255),
    logradouro varchar(255),
    numero varchar(255),
    uf varchar(255),
    ativo boolean not null
);
 

CREATE TABLE bomba (
    id_bomba serial PRIMARY KEY,
    quantidade_abastecida float not null,
    tipo_combustivel varchar(50) not null,
    ativo boolean not null,
    fk_Reservatorio_id_reservatorio integer not null
);
 
CREATE TABLE fornecedor (
    id_fornecedor serial PRIMARY KEY,
    nome varchar(50) not null,
    contato varchar(50),
    telefone varchar(50) not null,
    bairro varchar(255),
    cep varchar(255),
    cidade varchar(255),
    complemento varchar(255),
    logradouro varchar(255),
    numero varchar(255),
    uf varchar(255),
    cnpj varchar(30) not null,
    ativo boolean not null
);

CREATE TABLE reservatorio (
    id_reservatorio serial PRIMARY KEY,
    quantidade_combustivel float not null,
    tipo_combustivel varchar(50) not null,
    capacidade float not null
);

ALTER TABLE registro_abastecimento ADD CONSTRAINT FK_registro_abastecimento_2
    FOREIGN KEY (fk_cliente_id_cliente)
    REFERENCES cliente (id_cliente)
    ON DELETE SET NULL;

ALTER TABLE registro_abastecimento ADD CONSTRAINT FK_registro_abastecimento_3
    FOREIGN KEY (fk_funcionarios_id_funcionario)
    REFERENCES funcionarios (id_funcionario)
    ON DELETE RESTRICT;

ALTER TABLE bomba ADD CONSTRAINT FK_bomba_2
    FOREIGN KEY (fk_reservatorio_id_reservatorio)
    REFERENCES reservatorio (id_reservatorio)
    ON DELETE RESTRICT;