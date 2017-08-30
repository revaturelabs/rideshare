-- POI types
INSERT INTO point_of_interest_type VALUES(1, 'office');
INSERT INTO point_of_interest_type VALUES(2, 'housing');
INSERT INTO point_of_interest_type VALUES(3, 'other');
/
-- POIs
INSERT INTO points_of_interest VALUES(1,'11730 Plaza America Drive','','Reston',38.9533932,-77.35044779,'Revature Office','VA',20190,1);
INSERT INTO points_of_interest VALUES(2,'2150 Astoria Cir','','Herndon',38.967903,-77.413994,'Dulles Greene','VA',20170,2);
INSERT INTO points_of_interest VALUES(3,'2341 Dulles Station Blvd','','Herndon',38.9566975,-77.4186998,'Icon at Dulles','VA',20171,2);
INSERT INTO points_of_interest VALUES(4,'1815-A Sycamore Valley Dr','','Reston',38.9575877,-77.35125349,'The Sycamores','VA',20190,2);
INSERT INTO points_of_interest VALUES(5,'2320 Dulles Station Blvd','','Herndon',38.9582449,-77.4174213,'Camden Dulles Station','VA',20171,2);
INSERT INTO points_of_interest VALUES(6,'603 Elden St','','Herndon',38.96863539,-77.38133429,'Walgreens','VA',20170,3);
/
-- Users
INSERT INTO users VALUES(1,'jchickson@gmail.com','Jack','Jak Chickson',1,'Chickson','982jlsk3',3,4);
INSERT INTO users VALUES(2,'mholler@gmail.com','Michael','Michael Holler',0,'Holler','982jlsk3',4,3);
INSERT INTO users VALUES(3,'bhammer@gmail.com','Bob','Bob Hammer',0,'Hammer','982jlsk3',2,3);
INSERT INTO users VALUES(4,'mstu@gmail.com','Mary','Mary Stu',0,'Stu','982jlsk3',2,3);
INSERT INTO users VALUES(5,'dmaxwell@gmail.com','David','David Maxwell',0,'Maxwell','982jlsk3',4,3);
INSERT INTO users VALUES(6,'dmark@gmail.com','Dwayne','Dwayne Mark',0,'Mark','982jlsk3',5,3);
INSERT INTO users VALUES(7,'fseraferaranowitz4829@gmail.com','Frank','Frankie Dee',0,'Seraferaranowitz','982jlsk3',3,4);
INSERT INTO users VALUES(8,'spowers@gmail.com','Sarah','Sarah Powers',0,'Powers','982jlsk3',5,3);
INSERT INTO users VALUES(9,'kholmes@gmail.com','Katie','Katie Holmes',0,'Holmes','982jlsk3',4,3);
INSERT INTO users VALUES(10,'otwixel@gmail.com','Oliver','Oliver Twixel',0,'Twixel','982jlsk3',2,3);
/
-- Cars
-- For example, the U.S. state of Virginia allows up to 7.5 characters (a space or hyphen is counted as 0.5 character) 
-- on a standard-issue plate, but only up to 6 characters on many of its optional plates.
INSERT INTO cars VALUES(1,'Honda','white','4AQJ668','Civic','no note',1,1);
INSERT INTO cars VALUES(2,'Toyota','grey','24681','Prius','back passenger window not working',1,2);
INSERT INTO cars VALUES(3,'Ford','red','107-005','Ranger','dont care about smoking',0,3);
INSERT INTO cars VALUES(4,'Toyota','blue','GSF SMS','Yaris','no a/c, sorry!',1,4);
INSERT INTO cars VALUES(5,'Dodge','purple','M4XCH4R5','Challenger','nooo note',0,5);
/
-- Available Rides
INSERT INTO available_rides VALUES(1, 1, '', 3, TO_DATE('2017/07/03 17:05:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 5, 1);
INSERT INTO available_rides VALUES(2, 1, 'can not wait more than 2-3 minutes past time!', 2, TO_DATE('2017/07/03 17:10:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 2, 1);
INSERT INTO available_rides VALUES(3, 0, 'will wait 5 minutes past', 1, TO_DATE('2017/07/03 17:05:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 4, 1);
INSERT INTO available_rides VALUES(4, 0, '', 1, TO_DATE('2017/07/03 17:15:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 3, 1);
INSERT INTO available_rides VALUES(5, 1, '', 2, TO_DATE('2017/07/03 17:30:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 2, 1);
INSERT INTO available_rides VALUES(6, 1, 'may stop for drive-thru', 3, TO_DATE('2017/07/03 18:00:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 3, 1);
INSERT INTO available_rides VALUES(7, 1, '', 3, TO_DATE('2017/07/03 8:15:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 1, 5);
INSERT INTO available_rides VALUES(8, 1, 'need gas first', 2, TO_DATE('2017/07/03 7:00:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 1, 2);
INSERT INTO available_rides VALUES(9, 1, 'going in early!', 2, TO_DATE('2017/07/03 6:30:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 1, 3);
INSERT INTO available_rides VALUES(10, 1, '', 1, TO_DATE('2017/07/03 17:00:00', 'yyyy/mm/dd hh24:mi:ss'), 1, 4, 1);
/
-- Ride Requests
INSERT INTO ride_request VALUES(1,'Can we stop for drive-thru?', 0, TO_DATE('2017/07/03 17:30:00', 'yyyy/mm/dd hh24:mi:ss'),4,1,1);
INSERT INTO ride_request VALUES(2,'', 2, TO_DATE('2017/07/03 17:15:00', 'yyyy/mm/dd hh24:mi:ss'),4,1,1);
INSERT INTO ride_request VALUES(3,'I require A/C', 0, TO_DATE('2017/07/03 8:30:00', 'yyyy/mm/dd hh24:mi:ss'),1,3,3);
INSERT INTO ride_request VALUES(4,'will give $3 for gas!', 0, TO_DATE('2017/07/03 8:40:00', 'yyyy/mm/dd hh24:mi:ss'),1,3,2);
INSERT INTO ride_request VALUES(5,'', 0, TO_DATE('2017/07/03 8:10:00', 'yyyy/mm/dd hh24:mi:ss'),1,5,5);
INSERT INTO ride_request VALUES(6,'Can we listen to my iPod?', 0, TO_DATE('2017/07/03 9:00:00', 'yyyy/mm/dd hh24:mi:ss'),1,2,4);
INSERT INTO ride_request VALUES(7,'I have 4 bags, is this okay?', 0, TO_DATE('2017/07/03 18:30:00', 'yyyy/mm/dd hh24:mi:ss'),2,1,6);
INSERT INTO ride_request VALUES(8,'Can we stop at the store on the way?', 2, TO_DATE('2017/07/03 17:05:00', 'yyyy/mm/dd hh24:mi:ss'),3,1,7);
INSERT INTO ride_request VALUES(9,'', 2, TO_DATE('2017/07/03 17:45:00', 'yyyy/mm/dd hh24:mi:ss'),3,1,8);
INSERT INTO ride_request VALUES(10,'', 0, TO_DATE('2017/07/03 19:00:00', 'yyyy/mm/dd hh24:mi:ss'),2,1,9);
INSERT INTO ride_request VALUES(11,'', 0, TO_DATE('2017/07/03 7:35:00', 'yyyy/mm/dd hh24:mi:ss'),1,2,10);
INSERT INTO ride_request VALUES(12,'Please drive safely!', 0, TO_DATE('2017/07/03 8:25:00', 'yyyy/mm/dd hh24:mi:ss'),1,4,4);
INSERT INTO ride_request VALUES(13,'', 2, TO_DATE('2017/07/03 7:30:00', 'yyyy/mm/dd hh24:mi:ss'),1,5,6);
INSERT INTO ride_request VALUES(14,'', 2, TO_DATE('2017/07/03 16:50:00', 'yyyy/mm/dd hh24:mi:ss'),2,1,1);
INSERT INTO ride_request VALUES(15,'making coffee should I bring one for you?', 0, TO_DATE('2017/07/03 8:00:00', 'yyyy/mm/dd hh24:mi:ss'),1,4,7);
/
-- Rides
INSERT INTO rides VALUES(1, '', 1, 2, 14);
INSERT INTO rides VALUES(2, '', 0, 3, 2);
INSERT INTO rides VALUES(3, '', 1, 4, 8);
INSERT INTO rides VALUES(4, '', 1, 6, 9);
INSERT INTO rides VALUES(5, '', 1, 7, 13);
/
SELECT * FROM point_of_interest_type;
SELECT * FROM points_of_interest;
SELECT * FROM users;
SELECT * FROM cars;
SELECT * FROM available_rides;
SELECT * FROM ride_request;
SELECT * FROM rides;

commit;