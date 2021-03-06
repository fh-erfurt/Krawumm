CREATE TABLE IF NOT EXISTS EXPERIMENT (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    LOGGEDIN ENUM('T','F') NOT NULL,
    USERNAME VARCHAR(255) NOT NULL,
    ISCREATOR ENUM('T','F'),
    ISADMIN ENUM('T','F'),
    PRIMARY KEY (ID)
    );


CREATE TABLE IF NOT EXISTS EXPERIMENT (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    EXPERIMENTNAME VARCHAR(255),
    DESCRIPTION VARCHAR(500),
    INDOOROUTDOOR ENUM('I','O'),
    REGISTEREDUSER BIGINT NOT NULL,
    DIFFICULTY INT NOT NULL,
    VIDEO VARCHAR(500) NULL,
    AGE INT NOT NULL,
    DURATION FLOAT NOT NULL,
    IS_RELEASED ENUM('T','F'),
    PRIMARY KEY (ID)
    );


CREATE TABLE IF NOT EXISTS COMMENT (
	ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
	UPDATEDAT TIMESTAMP NULL,
	TEXT VARCHAR(1000),
	REGISTEREDUSERID BIGINT NOT NULL,
	EXPERIMENTID BIGINT NOT NULL,
	PRIMARY KEY (ID)
);


CREATE TABLE IF NOT EXISTS PICTURES (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    PICTURENAME VARCHAR(255) NOT NULL,
    COMMENTID BIGINT NULL,
    EXPERIMENTID BIGINT NULL,
    PRIMARY KEY (ID)
);


CREATE TABLE IF NOT EXISTS MATERIAL (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    MATERIALNAME VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID)
);


CREATE TABLE IF NOT EXISTS EXPERIMENT_HAS_MATERIAL (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    MATERIALID BIGINT NOT NULL,
    EXPERIMENTID BIGINT NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS INSTRUCTION (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    TEXT VARCHAR(500) NOT NULL,
    EXPERIMENTID BIGINT NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS RATING (
    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATEDAT TIMESTAMP AS CURRENT_TIMESTAMP() NOT NULL,
    UPDATEDAT TIMESTAMP NULL,
    RATINGVALUE INT NOT NULL,
    EXPERIMENTID BIGINT NOT NULL,
    PRIMARY KEY (ID)
);