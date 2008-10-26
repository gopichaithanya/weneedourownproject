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
			PRIMARY KEY(flight_no, username),
			FOREIGN KEY(flight_no) REFERENCES flight,
			FOREIGN KEY(username) REFERENCES customer);

INSERT INTO itinerary VALUES (106, 'jjohnson', 'booked');
INSERT INTO itinerary VALUES (154, 'jjohnson', 'booked');
