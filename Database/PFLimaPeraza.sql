ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER PFLimaPeraza IDENTIFIED BY "LimaPeraza";
GRANT CONNECT TO PFLimaPeraza;

CREATE TABLE tbPacientes(
    idPaciente INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
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