ALTER TABLE registro_abastecimento DROP COLUMN tipo_de_combustivel;

ALTER TABLE registro_abastecimento ADD id_bomba Integer;

ALTER TABLE registro_abastecimento add constraint fk_id_fornecedor foreign key(id_bomba) references bomba(id_bomba);