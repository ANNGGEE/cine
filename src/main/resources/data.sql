-- ================== TABLA SALA ==================
CREATE TABLE IF NOT EXISTS SALA (
                                    ID_SALA BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    NUMERO INT NOT NULL,
                                    DESCRIPCION VARCHAR(255),
                                    CAPACIDAD INT NOT NULL
);

-- ================== TABLA BUTACA ==================
CREATE TABLE IF NOT EXISTS BUTACA (
                                      ID_BUTACA BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      FILA VARCHAR(5) NOT NULL,
                                      NUMERO INT NOT NULL,
                                      POSICION VARCHAR(10) NOT NULL,
                                      ID_SALA BIGINT NOT NULL,
                                      CONSTRAINT FK_SALA_BUTACA FOREIGN KEY (ID_SALA) REFERENCES SALA(ID_SALA)
);
