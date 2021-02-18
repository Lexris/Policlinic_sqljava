drop database if exists polyclinic_chain;
create database if not exists polyclinic_chain;
use polyclinic_chain;

create table if not exists users (
	UserID int unique not null auto_increment primary key,
    Username varchar(20),
	CNP varchar(13) unique not null,
	FirstName varchar(20),
	LastName varchar(20),
	PhoneNumber varchar(10),
	Email varchar(50),
	IBAN varchar(24),
	ContractNumber int unique not null,
	HiringDate date,
	UserType varchar(11)	#ce departament 
);

create table if not exists super_admins (
	UserID int unique not null,
    foreign key (UserID) references users(UserID) on delete cascade
);

create table if not exists admins (
	UserID int unique not null,
    foreign key (UserID) references users(UserID) on delete cascade
);

create table if not exists employees (
	UserID int unique not null,
    foreign key (UserID) references users(UserID) on delete cascade,
    NegociatedIncome double,
    WorkHours int,
    PositionHeld varchar(50)
);

create table if not exists holiday (
	UserID int unique not null,
    foreign key (UserID) references employees(UserID) on delete cascade,
    StartingDate date,
    EndingDate date
);

create table if not exists nurses (
	UserID int unique not null,
    foreign key (UserID) references employees(UserID) on delete cascade,
    Type varchar(50),
    Grade varchar(1)					#de la A la I
);

create table if not exists medics (
	UserID int unique not null,
    foreign key (UserID) references employees(UserID) on delete cascade,
    StampCode varchar(10) unique,
    MedicalTitle varchar(50),		#Physician, Surgeon, Dentist, and Veterinarian. 
    AcademicPosition varchar(50),	#Assistant / Associate Professor, Engineer, Lecturer / Senior Lecturer, Management / Leadership, Other, PhD ,Postdoc, Professor, Research assistant, Researcher, Tenure Track
    Comission smallint				#0->100
);

create table if not exists specializations (
	SpecializationID int unique not null auto_increment primary key,
	Name varchar(50) unique not null
);

create table if not exists competences (
	CompetenceID int unique not null auto_increment primary key,
	Name varchar(50) unique not null
);

create table if not exists medics_specializations (
	UserID int not null,
	foreign key (UserID) references medics(UserID) on delete cascade,
    SpecializationID int not null,
	foreign key (SpecializationID) references specializations(SpecializationID) on delete cascade
);

create table if not exists medics_competences (
	UserID int not null,
	foreign key (UserID) references medics(UserID) on delete cascade,
    CompetenceID int not null,
	foreign key (CompetenceID) references competences(CompetenceID) on delete cascade
);

create table if not exists polyclinics (
	PolyclinicID int unique not null auto_increment primary key,
    Name varchar(50)
);

create table if not exists polyclinics_adresses (
	PolyclinicID int unique not null,
	foreign key (PolyclinicID) references polyclinics(PolyclinicID) on delete cascade,
    Country varchar(20),
    City varchar(20),
    Street varchar(50),
    Number smallint
);

create table if not exists time_table (
	UserID int unique not null,
	foreign key (UserID) references employees(UserID) on delete cascade,
    #PolyclinicID int unique not null,		#don't remember why i've put this here
    Date date,
	BeginsAt time,
    EndsAt time
);

create table if not exists services (
	ServiceID int unique not null auto_increment primary key,
	Name varchar(50),
    Price double,
    Duration time
);

create table if not exists services_specializations (
	ServiceID int not null,
	foreign key (ServiceID) references services(ServiceID) on delete cascade,
    SpecializationID int not null,
	foreign key (SpecializationID) references specializations(SpecializationID) on delete cascade
);

create table if not exists services_competences (
	ServiceID int not null,
	foreign key (ServiceID) references services(ServiceID) on delete cascade,
    CompetenceID int not null,
	foreign key (CompetenceID) references competences(CompetenceID) on delete cascade
);

create table if not exists polyclinics_services (
	PolyclinicID int not null,
	foreign key (PolyclinicID) references polyclinics(PolyclinicID) on delete cascade,
	ServiceID int not null,
	foreign key (ServiceID) references services(ServiceID) on delete cascade
);

create table if not exists patients (
	PatientID int unique not null auto_increment primary key,
	CNP varchar(13) unique not null,
	FirstName varchar(20),
	LastName varchar(20),
	PhoneNumber varchar(10),
	Email varchar(50)
);

create table if not exists appointments ( 
	AppointmentID int unique not null auto_increment primary key,
	PatientID int not null,
	foreign key (PatientID) references patients(PatientID) on delete cascade,
    ServiceID int not null,
	foreign key (ServiceID) references services(ServiceID) on delete cascade,
    PolyclinicID int not null,
	foreign key (PolyclinicID) references polyclinics(PolyclinicID) on delete cascade,
    UserID int not null,	# AKA MedicID
	foreign key (UserID) references medics(UserID) on delete cascade,
	Date date,
	BeginsAt time,
    EndsAt time	##deductibil cu AppointmentBegins + Service.Duration
);

create table if not exists receipts (
    ReceiptID int unique not null auto_increment primary key,
	AppointmentID int not null,
    foreign key (AppointmentID) references appointments(AppointmentID) on delete cascade
);

create table if not exists patient_history (
	PatientID int unique not null,
	foreign key (PatientID) references patients(PatientID) on delete cascade,
    AppointmentID int unique not null,
    foreign key (AppointmentID) references appointments(AppointmentID) on delete cascade,
    MedicalReport blob
);

CREATE TABLE `test_table` (
  `name` varchar(20) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `name2` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




