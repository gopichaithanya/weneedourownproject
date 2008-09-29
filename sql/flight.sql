/*************************************************************************/
/**             SWE 642 - Software Engineering for the WWW              **/
/**                             Fall 2008                               **/
/**                                                                     **/
/**                     Airline Reservation Project                     **/
/**                                                                     **/
/**                           Flight table	                            **/
/*************************************************************************/;

DROP TABLE flight;

CREATE TABLE flight   (	flight_no INTEGER, airline_code CHAR(2), 
			departure_location CHAR(3), departure_time TIMESTAMP 
			arrival_location CHAR(3), arrival_time TIMESTAMP,
			economy_seats INTEGER, economy_price REAL,
			business_seats INTEGER, business_price REAL,
			PRIMARY KEY(flight_no),
			FOREIGN KEY airline_code REFERENCES airline
			FOREIGN KEY departure_location REFERENCES airport,
			FOREIGN KEY arrival_location REFERENCES airport);

INSERT INTO flight VALUES (157,  'AA', 'DCA', TIMESTAMP '2008-12-20 07:45:00', 'SFO', TIMESTAMP '2008-12-20 13:34:00', 30, 479.00, 10, 1,357.00);
INSERT INTO flight VALUES (1069, 'NW', 'BWI', TIMESTAMP '2008-12-22 18:10:00', 'DEN', TIMESTAMP '2008-12-22 20:10:00', 30, 240.50, 10, 780.00);
INSERT INTO flight VALUES (67,   'NW', 'BWI', TIMESTAMP '2008-12-22 14:50:00', 'DEN', TIMESTAMP '2008-12-22 16:57:00', 30, 240.50, 10, 780.00);
INSERT INTO flight VALUES (378,  'CO', 'IAD', TIMESTAMP '2008-12-26 20:05:00', 'LAX', TIMESTAMP '2008-12-22 22:55:00', 30, 350.00, 10, 927.50);
INSERT INTO flight VALUES (1825, 'AA', 'IAD', TIMESTAMP '2008-12-26 08:10:00', 'LAX', TIMESTAMP '2008-12-22 11:00:00', 30, 350.00, 10, 927.50);
INSERT INTO flight VALUES (533,  'DL', 'MIA', TIMESTAMP '2009-01-02 10:46:00', 'ATL', TIMESTAMP '2009-01-02 12:42:00', 30, 185.50, 10, 644.00);
INSERT INTO flight VALUES (2927, 'US', 'PHL', TIMESTAMP '2009-01-04 07:35:00', 'ORD', TIMESTAMP '2009-01-04 09:20:00', 30, 286.00, 10, 793.75);
INSERT INTO flight VALUES (483,  'FL', 'JFK', TIMESTAMP '2009-01-04 19:29:00', 'DFW', TIMESTAMP '2009-01-04 20:54:00', 30, 412.50, 10, 1083.50);
INSERT INTO flight VALUES (54, 	 'NW', 'DEN', TIMESTAMP '2009-01-04 10:50:00', 'BWI', TIMESTAMP '2009-01-04 16:03:00', 30, 205.00, 10, 824.00);

