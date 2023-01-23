alter table reservatorio add ativo boolean;

alter table reservatorio add id_fornecedor Integer;

ALTER TABLE reservatorio add constraint fk_id_fornecedor foreign key(id_fornecedor) references fornecedor(id_fornecedor);


