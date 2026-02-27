CREATE GENERATOR GEN_PATIENT_ID;

CREATE GENERATOR GEN_VISIT_ID;

CREATE TABLE patient (
                         patient_id   INTEGER NOT NULL PRIMARY KEY,
                         first_name   VARCHAR(140) NOT NULL,
                         last_name    VARCHAR(140) NOT NULL,
                         pesel        CHAR(11) NOT NULL
);

SET TERM ^ ;

CREATE TRIGGER BI_PATIENT_ID FOR patient
BEFORE INSERT
AS
BEGIN
    IF (NEW.patient_id IS NULL) THEN
        NEW.patient_id = NEXT VALUE FOR GEN_PATIENT_ID;
END ^

SET TERM ; ^

CREATE TABLE visit (
                       visit_id     INTEGER NOT NULL PRIMARY KEY,
                       patient_id   INTEGER NOT NULL REFERENCES patient(patient_id),
                       visit_date   DATE NOT NULL,
                       diagnosis    BLOB SUB_TYPE 1,
                       amount       DECIMAL(18,2)
);

SET TERM ^ ;

CREATE TRIGGER BI_VISIT_ID FOR visit
BEFORE INSERT
AS
BEGIN
    IF (NEW.visit_id IS NULL) THEN
        NEW.visit_id = NEXT VALUE FOR GEN_VISIT_ID;
END ^

SET TERM ; ^