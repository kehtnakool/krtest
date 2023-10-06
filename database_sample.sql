BEGIN TRANSACTION;
DROP TABLE IF EXISTS "edetabel";
CREATE TABLE IF NOT EXISTS "edetabel" (
	"id"	INTEGER NOT NULL,
	"gameTime"	TEXT NOT NULL,
	"playerName"	TEXT NOT NULL,
	"missedLetters"	TEXT default "",
	"timeSeconds"	INTEGER NOT NULL,
	PRIMARY KEY("id")
);
DROP TABLE IF EXISTS "kategooriad";
CREATE TABLE IF NOT EXISTS "kategooriad" (
	"id"	INTEGER NOT NULL,
	"kategooria"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("id")
);
DROP TABLE IF EXISTS "sonad";
CREATE TABLE IF NOT EXISTS "sonad" (
	"id"	INTEGER NOT NULL,
	"sona"	TEXT NOT NULL UNIQUE,
	"kat_id"	INTEGER NOT NULL,
	FOREIGN KEY("kat_id") REFERENCES "kategooriad"("id"),
	PRIMARY KEY("id")
);

/*
select strftime('%s'); väljastab unix epoci
*/

/*
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (1,'1690329072','username','missitud_abc',20);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (2,'1690329101','username_1','missitud_abc'30);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (3,'1690329117','username_2','missitud_abc',40);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (3,'1690329117','username_2','missitud_abc',40);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (3,'1690329117','username_2','missitud_abc',40);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (3,'1690329117','username_2','missitud_abc',50);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (3,'1690329117','username_2','missitud_abc',50);
INSERT INTO "edetabel" ("id","gameTime","playerName","missedLetters","timeSeconds") VALUES (3,'1690329117','username_2','missitud_abc',50);
*/

INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329072','username','missitud_abc',20);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329101','username_1','missitud_abc',30);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',40);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',40);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',40);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',50);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','update_777','missitud_abc',51);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',50);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',50);
INSERT INTO "edetabel" ("gameTime","playerName","missedLetters","timeSeconds") VALUES ('1690329117','username_2','missitud_abc',50);



/*
UPDATE "edetabel" SET "gameTime"='1690329117',"playerName"='update_1',"missedLetters"='missitud_abc',"timeSeconds"=90 WHERE "id"=1;
*/


INSERT INTO "kategooriad" ("id","kategooria") VALUES (1,'Hoone');
INSERT INTO "kategooriad" ("id","kategooria") VALUES (2,'Elukutse');
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (1,'maja',1);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (2,'kuur',1);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (3,'kelder',1);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (4,'kasvuhoone',1);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (5,'kunstnik',2);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (6,'luuletaja',2);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (7,'kraanajuht',2);
INSERT INTO "sonad" ("id","sona","kat_id") VALUES (8,'plekksepp',2);
COMMIT;
