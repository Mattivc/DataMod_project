DROP DATABASE IF EXISTS Trening;
CREATE DATABASE IF NOT EXISTS Trening;
USE Trening;

DROP USER IF EXISTS 'user'@'localhost';
CREATE USER 'user'@'localhost' IDENTIFIED BY '12345678';
GRANT ALL PRIVILEGES ON Trening.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE GRUPPE (
    GruppeID            INT            NOT NULL,
    Navn                VARCHAR(30)    NOT NULL,
    TilhørerGruppeID    INT,
    PRIMARY KEY(GruppeID),
    FOREIGN KEY(TilhørerGruppeID) REFERENCES Gruppe(GruppeID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ØVELSE (
    ØvelseID        INT            NOT NULL,
    Navn            VARCHAR(30)    NOT NULL,
    Beskrivelse     VARCHAR(200),
    Erstatning      INT,
    GruppeID        INT,
    PRIMARY KEY(ØvelseID),
    FOREIGN KEY(Erstatning) REFERENCES ØVELSE(ØvelseID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(GruppeID) REFERENCES GRUPPE(GruppeID) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(Navn)
);

CREATE TABLE TRENINGSØKT  (
    TreningsØktID    INT         NOT NULL,
    Dato_tid         DATETIME    NOT NULL,
    Form             INT,
    Prestasjon       INT,
    Notat            VARCHAR(200),
    Antall_tilskuere INT,
    ØktMalID         INT,
    PRIMARY KEY(TreningsØktID),
    FOREIGN KEY(ØktMalID) REFERENCES TRENINGSØKT(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE TRENINGSØKTØVELSE (
    TreningsØktID   INT            NOT NULL,
    ØvelseID        INT            NOT NULL,
    PRIMARY KEY(TreningsØktID, ØvelseID),
    FOREIGN KEY(TreningsØktID) REFERENCES TRENINGSØKT(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(ØvelseID)      REFERENCES ØVELSE(ØvelseID) ON DELETE CASCADE ON UPDATE CASCADE
);  

CREATE TABLE RESULTAT   (
    ØvelseID        INT        NOT NULL,
    TreningsØktID   INT        NOT NULL,
    PRIMARY KEY(ØvelseID, TreningsØktID),
    FOREIGN KEY(ØvelseID)        REFERENCES ØVELSE(ØvelseID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(TreningsØktID)   REFERENCES TreningsØkt(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE MÅL   (
    ØvelseID        INT        NOT NULL,
    TreningsØktID   INT        NOT NULL,
    Dato            DATE       NOT NULL,
    Oppnådd	BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY(ØvelseID, TreningsØktID),
    FOREIGN KEY(ØvelseID)        REFERENCES ØVELSE(ØvelseID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(TreningsØktID)   REFERENCES TreningsØkt(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE STYRKE (
    ØvelseID            INT    NOT NULL,
    TreningsØktID       INT    NOT NULL,
    Belastning          FLOAT,
    Antall_sett         INT,
    Antall_reps         INT,
    FOREIGN KEY(ØvelseID)         REFERENCES RESULTAT(ØvelseID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(TreningsØktID)    REFERENCES RESULTAT(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE KONDISJON (
    ØvelseID            INT    NOT NULL,
    TreningsØktID       INT    NOT NULL,
    Lengde              FLOAT,
    Tid                 TIME,
    FOREIGN KEY(ØvelseID)         REFERENCES RESULTAT(ØvelseID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(TreningsØktID)    REFERENCES RESULTAT(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE UTENDØRS (
    TreningsØktID        INT    NOT NULL,
    Vær                  VARCHAR(30),
    Temp                 FLOAT,
    FOREIGN KEY(TreningsØktID)    REFERENCES TreningsØkt(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE INNENDØRS(
    TreningsØktID        INT    NOT NULL,
    Luftkvalitet         VARCHAR(30),
    FOREIGN KEY(TreningsØktID)    REFERENCES TreningsØkt(TreningsØktID) ON DELETE CASCADE ON UPDATE CASCADE
);
