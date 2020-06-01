CREATE TABLE Customers (
	CustomerID VARCHAR(10) PRIMARY KEY NOT NULL,
	FirstName CHAR(50) NOT NULL,
	LastName CHAR(50) NOT NULL,
	Email string CHAR(90) NOT NULL,
	Address CHAR(90) NOT NULL,
	City CHAR(50) NOT NULL,
	PhoneNo VARCHAR NOT NULL,
	DateJoined date NOT NULL
	CHECK (length(CustomerID) = 10)
	
);


CREATE TABLE Membership (
	MembershipID STRING PRIMARY KEY NOT NULL,
MembershipTierID STRING(10) REFERENCES MembershipTier(MembershipTierID),
MembershipStart VARCHAR(10) NOT NULL,
MembershipEnd VARCHAR(10),
CustomerID STRING(10) REFERENCES Customers(CustomerID) NOT NULL
);


CREATE TABLE MembershipTier (
	MembershipTierID STRING(10) PRIMARY KEY,
	TierRate INTEGER(10)
);
   

CREATE TABLE Payment (
	InvoiceNo INTEGER(20) PRIMARY KEY,
	MembershipID INTEGER(10) REFERENCES Membership(MembershipID),
	CustomerID INTEGER(10) REFERENCES Customers(CustomerID),
	CreditCard INTEGER(30),
	PayForDate date,
	PaymentDue date,
	PaidDate date
	
);


-- behöver ändra så att alla string med address bli längre 
-- behöver ändra time så det står 06.00 och 20.00 
CREATE TABLE Gyms (
	GymID CHAR(20) PRIMARY KEY NOT NULL,
	GymPhoneNo VARCHAR NOT NULL,
	Address string CHAR(90) NOT NULL,
	City VARCHAR(50) NOT NULL,
	TimeOpen VARCHAR(5) NOT NULL,
	TimeClose VARCHAR(5) NOT NULL
);


CREATE TABLE Rooms (
	RoomID INTEGER(10) PRIMARY KEY NOT NULL,
	GymID CHAR(20) REFERENCES Gyms(GymID) NOT NULL,
	RoomName CHAR(50) NOT NULL
);



CREATE TABLE GymEquipment (
	GymEquipmentID STRING(40) PRIMARY KEY,
	GymID CHAR(20) REFERENCES Gyms(GymID) NOT NULL,
	EquipmentType CHAR(60) NOT NULL
);


CREATE TABLE Courses (
	CourseID STRING PRIMARY KEY,
	InstructorID INTEGER(10) REFERENCES Instructors(InstructorID) NOT NULL,
	CourseName CHAR(150) NOT NULL,
	Date VARCHAR(5) NOT NULL,
	Time VARCHAR(5) NOT NULL,
	RoomID INTEGER(10) REFERENCES Rooms(RoomID) NOT NULL,
	GymID VARCHAR(20) REFERENCES Gyms(GymID) NOT NULL
);


CREATE TABLE Instructors (
	InstructorID VARCHAR(10) PRIMARY KEY NOT NULL,
	FirstName CHAR(50) NOT NULL,
	LastName CHAR(50) NOT NULL
	CHECK (length(InstructorID) = 10)
);

CREATE TABLE CourseEnrollment (
	EnrollmentID VARCHAR (40) PRIMARY KEY,
	CourseID VARCHAR REFERENCES Courses(CourseID) NOT NULL,
	CustomerID INTEGER(10) REFERENCES Customers(CustomerID) NOT NULL
	
);

