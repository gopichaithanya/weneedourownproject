/*************************************************************************/
/**		SWE 642 - Software Engineering for the WWW		**/
/**				Fall 2008				**/
/**									**/
/**			Airline Reservation Project			**/
/**									**/
/**				Airline table				**/
/*************************************************************************/


DROP TABLE airline;

CREATE TABLE airline  ( code CHAR(2), name CHAR(20), PRIMARY KEY(code));

INSERT INTO airline VALUES ('AS', 'Alaska Airlines');
INSERT INTO airline VALUES ('AQ', 'Aloha Air');
INSERT INTO airline VALUES ('HP', 'America West Airlines');
INSERT INTO airline VALUES ('AA', 'American Airlines');
INSERT INTO airline VALUES ('AP', 'Air One');
INSERT INTO airline VALUES ('CO', 'Continental Airlines');
INSERT INTO airline VALUES ('DL', 'Delta Airlines');
INSERT INTO airline VALUES ('HA', 'Hawaiian Airlines');
INSERT INTO airline VALUES ('YX', 'Midwest Express');
INSERT INTO airline VALUES ('NW', 'Northwest Airlines');
INSERT INTO airline VALUES ('WN', 'Southwest Airlines');
INSERT INTO airline VALUES ('FF', 'Tower Air');
INSERT INTO airline VALUES ('TW', 'Trans World Airlines');
INSERT INTO airline VALUES ('UA', 'United Airlines');
INSERT INTO airline VALUES ('US', 'US Airways');
INSERT INTO airline VALUES ('FL', 'AirTran');