/*************************************************************************/
/**             SWE 642 - Software Engineering for the WWW              **/
/**                             Fall 2008                               **/
/**                                                                     **/
/**                     Airline Reservation Project                     **/
/**                                                                     **/
/**                           Customer table	                        **/
/*************************************************************************/;

CREATE TABLE customer (first_name CHAR(20), last_name CHAR(20), 
			street CHAR(50), city CHAR(20), state CHAR(2), zip INTEGER, 
			username CHAR(20), password CHAR(20), 
			cc_no BIGINT, expiration INTEGER,
			PRIMARY KEY(username));

INSERT INTO customer VALUES (NULL,NULL,NULL,NULL,NULL,NULL,'administrator', 'tomcat', NULL, NULL);
INSERT INTO customer VALUES ('Jack', 'Johnson', '1529 Main St', 'Fairfax', 'VA', 22030, 'jjohnson', 'harley', 2222222222222222, 1111);
INSERT INTO customer VALUES ('Samantha', 'Morris', '302 Cherry Hill Rd', 'Gaithersburg', 'MD', 20877, 'sammy', 'gmualum03', NULL, NULL);
