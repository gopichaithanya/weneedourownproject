/*************************************************************************/
/**             SWE 642 - Software Engineering for the WWW              **/
/**                             Fall 2008                               **/
/**                                                                     **/
/**                     Airline Reservation Project                     **/
/**                                                                     **/
/**                           Itinerary table                           **/
/*************************************************************************/;

CREATE TABLE itinerary(	flight_no INTEGER,
			username CHAR(20),
			status CHAR(20),
			numOfSeats INTEGER,
			seatClass CHAR(20),
			ticketNo VARCHAR,
			PRIMARY KEY(flight_no, username),
			FOREIGN KEY(flight_no) REFERENCES flight,
			FOREIGN KEY(username) REFERENCES customer);

INSERT INTO itinerary VALUES (106, 'jjohnson', 'BOOKED', 1, 'ECONOMY', 'NW-106-JJOHNSON-000');
INSERT INTO itinerary VALUES (154, 'jjohnson', 'CANCELED', null, null, null);
INSERT INTO itinerary VALUES (167, 'jjohnson', 'BOOKED', 1, 'ECONOMY', 'NW-167-JJOHNSON-000');
INSERT INTO itinerary VALUES (292, 'jjohnson', 'RESERVED', 1, 'ECONOMY', null);
