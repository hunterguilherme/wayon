CREATE TABLE transferencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conta_origem VARCHAR(10),
    conta_destino VARCHAR(10),
    valor DECIMAL(10, 2),
    taxa DECIMAL(5, 2),
    data_transferencia TIMESTAMP,
    data_agendamento TIMESTAMP
);

CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    conta VARCHAR(10),
    saldo DECIMAL(10, 2)
);
