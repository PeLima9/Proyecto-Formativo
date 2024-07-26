ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER PFLimaPeraza IDENTIFIED BY "LimaPeraza";
GRANT CONNECT TO PFLimaPeraza;

CREATE TABLE tbPacientes(
    idPaciente INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    uuid VARCHAR2 (255) NOT NULL,
    nombres VARCHAR2 (25) NOT NULL,
    apellidos VARCHAR2 (25) NOT NULL,
    edad NUMBER(2) NOT NULL,
    enfermedad VARCHAR2 (50) NOT NULL,
    numeroHabitacion INT NOT NULL,
    numeroCama INT NOT NULL,
    medicamentoAsignado VARCHAR2 (50),
    fechaNacimiento DATE,
    horaMedicamento TIMESTAMP
);

INSERT INTO tbPacientes (idPaciente, uuid, nombres, apellidos, edad, enfermedad, numeroHabitacion, numeroCama, medicamentoAsignado, fechaNacimiento, horaMedicamento) VALUES ('', '', '', '', '', '', '', '', '')