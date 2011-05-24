DROP TABLE IF EXISTS "abbreviation";
CREATE TABLE "abbreviation" ("id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"abbreviation" VARCHAR NOT NULL ,"attribute_name" VARCHAR);
DROP TABLE IF EXISTS "beneficiary_category";
CREATE TABLE "beneficiary_category" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "name" VARCHAR NOT NULL );
DROP TABLE IF EXISTS "commune";
CREATE TABLE "commune" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "name" VARCHAR NOT NULL , "abbreviation" VARCHAR);
DROP TABLE IF EXISTS "commune_section";
CREATE TABLE "commune_section" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "name" VARCHAR NOT NULL , "commune_id" INTEGER, "abbreviation" VARCHAR);
DROP TABLE IF EXISTS "health_center";
CREATE TABLE "health_center" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "name" VARCHAR NOT NULL , "descriptor" VARCHAR);
DROP TABLE IF EXISTS "message_log";
CREATE TABLE "message_log" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "message_text" VARCHAR NOT NULL , "processed" BOOL NOT NULL , "timestamp" DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP);
