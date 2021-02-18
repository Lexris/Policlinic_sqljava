#in users
insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
('root'	 , '0192837465739', 	'Alexander','Stark', 	'0723456789', 	'astark@gmail.com', 	'217568923764982150498532',		123,	'2000/11/14', 	'DB'),
('user1' , '2937840520932', 	'Ricardo', 	'Kratos', 	'0763489966', 	'rkratos@gmail.com', 	'721386487912376589195691',		543, 	'2001/09/13',	'HR'),
('user2' , '1230718065442',  	'Bonnie',	'Snowball', '0744841486',	'bonbon@gmail.com', 	'582549283624110473621836',		229,	'2010/05/20',	'PR'),
('user3' , '1789234911283', 	'Nena', 	'Hera', 	'0748971871', 	'nhera@gmail.com', 		'238468912734689213764819',		634, 	'2002/05/12',	'F'	),
('user4' , '8349502704935',		'Mamasita', 'Hades', 	'0770459822', 	'mhades@gmail.com', 	'129387401237490172349598',		645,	'2003/07/11', 	'F'	),
('user5' , '2798304572058',	    'Maluma', 	'Baby', 	'0781237984', 	'mbaby@gmail.com', 		'532476592386789164897234',		867,	'2004/11/16', 	'M'	),
('user6' , '1027536120596', 	'Varga', 	'Robert', 	'0717395701', 	'vrobert@gmail.com', 	'293847532047529037459237',		155,	'2006/08/22', 	'M'	),
('user7' , '0453968048208', 	'Artemis', 	'Hunter', 	'0752357929', 	'ahunter@gmail.com', 	'918027350629581475023561',		156,	'2007/03/25', 	'M'	),
('user8' , '0250987218090', 	'Mircea', 	'Cojocaru', '0752116442', 	'mirceacojoc@gmail.com','871434173591735441810199',		129,	'2011/11/29', 	'M'	),
('user9' , '2181090735133', 	'Ted', 		'Mosby', 	'0767912543', 	'tmosby@gmail.com',		'543419463752012364528621',		331,	'2003/02/12', 	'M'	),
('user10', '2102099542618', 	'Natalie', 	'Royal', 	'0766983116', 	'natroyal@gmail.com', 	'735201654457900278287271',		231,	'2005/01/17', 	'M'	),
('user11', '0220505624171', 	'Matteo', 	'River', 	'0755917441', 	'matteoriv@gmail.com', 	'872413738915241689182268',		985,	'2008/04/24', 	'M'	),
('user12', '0019524518206', 	'Keridas', 	'Hermes', 	'0789320000', 	'khermes@gmail.com', 	'374205892357900237459272',		354,	'2005/01/17', 	'M'	),
('user13', '1252357897888', 	'Sperie', 	'Porumbei', '0757486249', 	'sporumbei@gmail.com', 	'012345678901234567890123',		111, 	'2006/03/18',	'DB');

#in super_admins
insert into super_admins(UserID)
(select UserID from users where CNP like '0192837465739');

#in admins
insert into admins(UserID)
(select UserID from users where CNP like '1252357897888');

#in employees
insert into employees(UserID, NegociatedIncome, WorkHours, PositionHeld) values
((select UserID from users where CNP like '2937840520932'), 12345, 80, 'Inspector'),
((select UserID from users where CNP like '1230718065442'), 14000, 80, 'Receptionist'),
((select UserID from users where CNP like '1789234911283'), 82473, 80, 'Accountant'),
((select UserID from users where CNP like '8349502704935'), 81732, 800, 'Accountant'),
((select UserID from users where CNP like '2798304572058'), 34567, 80, 'Medic'),
((select UserID from users where CNP like '1027536120596'), 15999, 80, 'Medic'),
((select UserID from users where CNP like '0453968048208'), 25000, 80, 'Medic'),
((select UserID from users where CNP like '0250987218090'), 30000, 80, 'Medic'),
((select UserID from users where CNP like '2181090735133'), 35000, 80, 'Medic'),
((select UserID from users where CNP like '0019524518206'), 12345, 80, 'Nurse'),
((select UserID from users where CNP like '0220505624171'), 12000, 80, 'Nurse'),
((select UserID from users where CNP like '2102099542618'), 13562, 80, 'Nurse');

#in time_table
call SET_TIME_TABLE('Ricardo' , 'Kratos'  , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Bonnie'  ,	'Snowball', Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Nena'    , 'Hera'    , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Mamasita',	'Hades'   , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Maluma'  , 'Baby'    , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Varga'   , 'Robert'  , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Artemis' , 'Hunter'  , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Mircea'  , 'Cojocaru', Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Ted'     , 'Mosby'   , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Natalie' , 'Royal'   , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Matteo'  , 'River'   , Date(NOW()), '09:00:00', '17:00:00');
call SET_TIME_TABLE('Keridas' , 'Hermes'  , Date(NOW()), '09:00:00', '17:00:00');


#in holiday
call SET_HOLIDAY('Ricardo', 	'Kratos', 	Date(NOW()), 	DATE_ADD(Date(NOW()), interval 1 week));
call SET_HOLIDAY('Nena', 		'Hera', 	Date(NOW()), 	DATE_ADD(Date(NOW()), interval 5 day));
call SET_HOLIDAY('Mamasita',	'Hades', 	Date(NOW()), 	DATE_ADD(Date(NOW()), interval 1 month));
call SET_HOLIDAY('Maluma', 		'Baby', 	Date(NOW()),	DATE_ADD(Date(NOW()), interval 2 week));
call SET_HOLIDAY('Varga', 		'Robert', 	Date(NOW()), 	DATE_ADD(Date(NOW()), interval 4 day));
call SET_HOLIDAY('Artemis', 	'Hunter', 	Date(NOW()),	DATE_ADD(Date(NOW()), interval 4 week));
call SET_HOLIDAY('Keridas', 	'Hermes',	Date(NOW()),	DATE_ADD(Date(NOW()), interval 9 day));


#in nurses
insert into nurses(UserID, Type, Grade) values
((select UserID from users where CNP like '0019524518206'), 'Registered', 'A'),
((select UserID from users where CNP like '0220505624171'), 'Registered', 'A'),
((select UserID from users where CNP like '2102099542618'), 'Registered', 'A');

#in medics
insert into medics(UserID, StampCode, MedicalTitle, AcademicPosition, Comission) values
((select UserID from users where CNP like '2798304572058'), 'Stamp@2675', 'Surgeon', 'Senior Lecturer', 21),
((select UserID from users where CNP like '1027536120596'), 'Stamp@1235', 'Family Medic', 'Associate Professor', 15),
((select UserID from users where CNP like '0453968048208'), 'Stamp@7654', 'Medical Laboratory Scientist', 'Assistant', 10),
((select UserID from users where CNP like '0250987218090'), 'Stamp@2376', 'Medical Laboratory Scientist', 'Assistant', 19),
((select UserID from users where CNP like '2181090735133'), 'Stamp@4175', 'Surgeon', 'Senior Lecturer', 22);

#in specializations
insert into specializations(Name) values
('Allergy & Immunology'), ('Anesthesiology'), ('Cardiology'), ('Dentistry'), ('Dermatology'), ('Diabetes & Nutrition'),
('Diagnostic Radiology'), ('Emergency Medicine'), ('Family Medicine'), ('Internal Medicine'), ('Medical Genetics'),
('Neurology'), ('Nuclear Medicine'), ('Obstetrics & Gynecology'), ('Ophthalmology'), ('Pathology'), ('Pediatrics'),
('Physical Medicine & Rehabilitation'), ('Preventive Medicine'), ('Psychiatry'), ('Radiation Oncology'), ('Surgery'), ('Urology');

#in competences(mai adaugi tu daca ai nevoie de ceva-in ordine topologica)
insert into competences(Name) values
('Bronchoscopy'), ('Chronic Medical Conditions Management'), ('Electrocardiography'), ('General Medical Tests'), ('Informatics'),
('Laboratory Experience'), ('Medical Imaging'), ('Physiotherapy Training'), ('Spirometry'), ('Surgeon Experience'), ('Emotional Intelligence'),
('Basic Medical Training');

#in medics_specializations
insert into medics_specializations(UserID, SpecializationID) values
((select UserID from users where CNP like '2798304572058'), (select SpecializationID from specializations where Name like 'Surgery')),
((select UserID from users where CNP like '1027536120596'), (select SpecializationID from specializations where Name like 'Family Medicine')),
((select UserID from users where CNP like '0453968048208'), (select SpecializationID from specializations where Name like 'Allergy & Immunology')),
((select UserID from users where CNP like '0250987218090'), (select SpecializationID from specializations where Name like 'Diagnostic Radiology')),
((select UserID from users where CNP like '2181090735133'), (select SpecializationID from specializations where Name like 'Surgery'));

#in medics_competences
insert into medics_competences(UserID, CompetenceID) values
((select UserID from users where CNP like '2798304572058'), (select CompetenceID from competences where Name like 'Surgeon Experience')),
((select UserID from users where CNP like '1027536120596'), (select CompetenceID from competences where Name like 'General Medical Tests')),
((select UserID from users where CNP like '0453968048208'), (select CompetenceID from competences where Name like 'Laboratory Experience')),
((select UserID from users where CNP like '0250987218090'), (select CompetenceID from competences where Name like 'Laboratory Experience')),
((select UserID from users where CNP like '2181090735133'), (select CompetenceID from competences where Name like 'Surgeon Experience'));

#in services
insert into services(Name, Price, Duration) values
('Acute Care', 99.99, '00:30:00'), 
('Bone Mineral Densitometry', 199.99, '01:00:00'),
('Care for Chronic Medical Conditions', 199.99, '01:00:00'),
('Care Management', 99.99, '00:30:00'), 
('Consultation', 99.99, '00:30:00'),
('Developmental Screening for Children', 99.99, '00:30:00'),
('Diabetic Foot Screening', 199.99, '00:30:00'),
('Diabetic Retinal Photography', 199.99, '00:30:00'),
('Dietetic Services', 199.99, '00:30:00'),
('Electrocardiogram', 199.99, '01:00:00'),
('General X-Ray', 199.99, '00:30:00'),
('Immunizations', 199.99, '00:15:00'),
('Laboratory Tests', 99.99, '01:00:00'),
('Physiotherapy Services', 199.99, '01:00:00'),
('Podiatry Services', 999.99, '00:30:00'),
('Psychology Services', 199.99, '01:00:00'),
('Quit Smoking Programme', 99.99, '00:10:00'),
('Screening Mammogram', 199.99, '00:30:00'),
('Spirometry', 199.99, '00:30:00'),
('Surgical Procedures', 2499.99, '01:00:00'),
('Ultrasound', 249.99, '01:00:00'),
('Vaccination', 199.99, '00:10:00'),
('Women’s Health Services', 199.99, '00:30:00');

#in services_specializations
insert into services_specializations(ServiceID, SpecializationID) values
((select ServiceID from services where Name like 'Consultation'), (select SpecializationID from specializations where Name like 'Family Medicine')),
((select ServiceID from services where Name like 'Laboratory Tests'), (select SpecializationID from specializations where Name like 'Pathology')),
((select ServiceID from services where Name like 'Bone Mineral Densitometry'), (select SpecializationID from specializations where Name like 'Family Medicine')),
((select ServiceID from services where Name like 'Electrocardiogram'), (select SpecializationID from specializations where Name like 'Cardiology')),
((select ServiceID from services where Name like 'General X-Ray'), (select SpecializationID from specializations where Name like 'Diagnostic Radiology')),
((select ServiceID from services where Name like 'Screening Mammogram'), (select SpecializationID from specializations where Name like 'Obstetrics & Gynecology')),
((select ServiceID from services where Name like 'Spirometry'), (select SpecializationID from specializations where Name like 'Internal Medicine')),
((select ServiceID from services where Name like 'Ultrasound'), (select SpecializationID from specializations where Name like 'Diagnostic Radiology')),
((select ServiceID from services where Name like 'Acute Care'), (select SpecializationID from specializations where Name like 'Family Medicine')),
((select ServiceID from services where Name like 'Care Management'), (select SpecializationID from specializations where Name like 'Internal Medicine')),
((select ServiceID from services where Name like 'Immunizations'), (select SpecializationID from specializations where Name like 'Allergy & Immunology')),
((select ServiceID from services where Name like 'Developmental Screening for Children'), (select SpecializationID from specializations where Name like 'Pediatrics')),
((select ServiceID from services where Name like 'Care for Chronic Medical Conditions'), (select SpecializationID from specializations where Name like 'Internal Medicine')),
((select ServiceID from services where Name like 'Diabetic Foot Screening'), (select SpecializationID from specializations where Name like 'Diabetes & Nutrition')),
((select ServiceID from services where Name like 'Diabetic Retinal Photography'), (select SpecializationID from specializations where Name like 'Diabetes & Nutrition')),
((select ServiceID from services where Name like 'Dietetic Services'), (select SpecializationID from specializations where Name like 'Diabetes & Nutrition')),
((select ServiceID from services where Name like 'Surgical Procedures'), (select SpecializationID from specializations where Name like 'Surgery')),
((select ServiceID from services where Name like 'Physiotherapy Services'), (select SpecializationID from specializations where Name like 'Physical Medicine & Rehabilitation')),
((select ServiceID from services where Name like 'Podiatry Services'), (select SpecializationID from specializations where Name like 'Surgery')),
((select ServiceID from services where Name like 'Psychology Services'), (select SpecializationID from specializations where Name like 'Psychiatry')),
((select ServiceID from services where Name like 'Quit Smoking Programme'), (select SpecializationID from specializations where Name like 'Psychiatry')),
((select ServiceID from services where Name like 'Women’s Health Services'), (select SpecializationID from specializations where Name like 'Obstetrics & Gynecology')),
((select ServiceID from services where Name like 'Vaccination'), (select SpecializationID from specializations where Name like 'Allergy & Immunology'));

#in services_competences
insert into services_competences(ServiceID, CompetenceID) values
((select ServiceID from services where Name like 'Consultation'), (select CompetenceID from competences where Name like 'General Medical Tests')),
((select ServiceID from services where Name like 'Laboratory Tests'), (select CompetenceID from competences where Name like 'Laboratory Experience')),
((select ServiceID from services where Name like 'Bone Mineral Densitometry'), (select CompetenceID from competences where Name like 'General Medical Tests')),
((select ServiceID from services where Name like 'Electrocardiogram'), (select CompetenceID from competences where Name like 'Electrocardiography')),
((select ServiceID from services where Name like 'General X-Ray'), (select CompetenceID from competences where Name like 'Medical Imaging')),
((select ServiceID from services where Name like 'Screening Mammogram'), (select CompetenceID from competences where Name like 'Medical Imaging')),
((select ServiceID from services where Name like 'Spirometry'), (select CompetenceID from competences where Name like 'Spirometry')),
((select ServiceID from services where Name like 'Ultrasound'), (select CompetenceID from competences where Name like 'Medical Imaging')),
((select ServiceID from services where Name like 'Acute Care'), (select CompetenceID from competences where Name like 'General Medical Tests')),
((select ServiceID from services where Name like 'Care Management'), (select CompetenceID from competences where Name like 'General Medical Tests')),
((select ServiceID from services where Name like 'Immunizations'), (select CompetenceID from competences where Name like 'Laboratory Experience')),
((select ServiceID from services where Name like 'Developmental Screening for Children'), (select CompetenceID from competences where Name like 'Medical Imaging')),
((select ServiceID from services where Name like 'Care for Chronic Medical Conditions'), (select CompetenceID from competences where Name like 'Chronic Medical Conditions Management')),
((select ServiceID from services where Name like 'Diabetic Foot Screening'), (select CompetenceID from competences where Name like 'Medical Imaging')),
((select ServiceID from services where Name like 'Diabetic Retinal Photography'), (select CompetenceID from competences where Name like 'Medical Imaging')),
((select ServiceID from services where Name like 'Dietetic Services'), (select CompetenceID from competences where Name like 'General Medical Tests')),
((select ServiceID from services where Name like 'Surgical Procedures'), (select CompetenceID from competences where Name like 'Surgeon Experience')),
((select ServiceID from services where Name like 'Physiotherapy Services'), (select CompetenceID from competences where Name like 'Physiotherapy Training')),
((select ServiceID from services where Name like 'Podiatry Services'), (select CompetenceID from competences where Name like 'Surgeon Experience')),
((select ServiceID from services where Name like 'Psychology Services'), (select CompetenceID from competences where Name like 'Emotional Intelligence')),
((select ServiceID from services where Name like 'Quit Smoking Programme'), (select CompetenceID from competences where Name like 'Emotional Intelligence')),
((select ServiceID from services where Name like 'Women’s Health Services'), (select CompetenceID from competences where Name like 'General Medical Tests'));

#in pacients
insert into patients(CNP, FirstName, LastName, PhoneNumber, Email) values
('7792785581988', 	'Unom', 	   'Saracudeel', 	'0738147597',	'usaracudeel@gmail.com'),
('1198257109257', 	'Altom', 	   'Vaideel', 		'0723689542',	'avaideel@gmail.com'),
('2569734690107', 	'Treileaom',   'Deeltambien',	'0712851277',	'tdeeltambien@gmail.com'),
('2110699462496',	'Robert',	   'Isaac',			'0756347221',	'risaac@gmai.com'),
('3171083651965',	'William',     'Notshakespeare','0722613883',	'willshake@yahoo.com'),
('1161298541852',	'Mary',		   'Padian',		'0744683116',	'padian_mary@gmail.com'),
('2230900761280',	'Lorette',	   'Smith',			'0765312889',	'lorettesmith@gmail.com'),
('1070899651328',	'Asher',	   'Millstone',		'0782586335',	'millstone.asher@gmail.com'),
('1231292591263',	'Annalise',	   'Keating',		'0795721554',	'annalise.keating@gmail.com');

#in polyclinics
insert into polyclinics(Name) values
('Dove Polyclinic Center'),
('Asustar Polyclinic Center');

#in medical_units_adresses
insert into polyclinics_adresses(PolyclinicID, Country, City, Street, Number) values
((select PolyclinicID from polyclinics where Name like 'Dove Polyclinic Center'), 'Romania', 'Cluj-Napoca', 'Ciprian Porumbescu', 2),
((select PolyclinicID from polyclinics where Name like 'Asustar Polyclinic Center'), 'Romania', 'Iasi', 'Strada Melodiei', 23);

#in polyclinics_services
insert into polyclinics_services(PolyclinicID, ServiceID) values
((select PolyclinicID from polyclinics where Name like 'Dove Polyclinic Center'), (select ServiceID from services where Name like 'Surgical Procedures')),
((select PolyclinicID from polyclinics where Name like 'Dove Polyclinic Center'), (select ServiceID from services where Name like 'Acute Care')),
((select PolyclinicID from polyclinics where Name like 'Asustar Polyclinic Center'), (select ServiceID from services where Name like 'Immunizations'));

#cream conturile pt toti userii de mai sus
drop user if exists 'user1'@'localhost';
create user 'user1'@'localhost' identified by 'user1';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user1'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_TIME_TABLE to 'user1'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_HOLIDAY to 'user1'@'localhost'; 
grant execute on procedure polyclinic_chain.SET_TIME_TABLE to 'user1'@'localhost';
grant execute on procedure polyclinic_chain.SET_HOLIDAY to 'user1'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_EMPLOYEES to 'user1'@'localhost';
#grant 'inspector'@'localhost' to 'user1'@'localhost';
#set default role 'inspector'@'localhost' to 'user1'@'localhost';

drop user if exists 'user2'@'localhost';
create user 'user2'@'localhost' identified by 'user2';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user2'@'localhost';
grant execute on procedure polyclinic_chain.ADD_PATIENT to 'user2'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user2'@'localhost';
grant execute on procedure polyclinic_chain.ADD_APPOINTMENT to 'user2'@'localhost';
#grant 'receptionist'@'localhost' to 'user2'@'localhost';
#set default role 'receptionist'@'localhost' to 'user2'@'localhost';

drop user if exists 'user3'@'localhost';
create user 'user3'@'localhost' identified by 'user3';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user3'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_TOTAL_PROFIT to 'user3'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MEDIC_PROFIT to 'user3'@'localhost';
#grant 'accountant'@'localhost' to 'user3'@'localhost';
#set default role 'accountant'@'localhost' to 'user3'@'localhost';

drop user if exists 'user4'@'localhost';
create user 'user4'@'localhost' identified by 'user4';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user4'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_TOTAL_PROFIT to 'user4'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MEDIC_PROFIT to 'user4'@'localhost';
#grant 'accountant'@'localhost' to 'user4'@'localhost';
#set default role 'accountant'@'localhost' to 'user4'@'localhost';

drop user if exists 'user5'@'localhost';
create user 'user5'@'localhost' identified by 'user5';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user5'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user5'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user5'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user5'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MY_MEDIC_SCHEDULE to 'user5'@'localhost';
#grant 'medic'@'localhost' to 'user5'@'localhost';
#set default role 'medic'@'localhost' to 'user5'@'localhost';

drop user if exists 'user6'@'localhost';
create user 'user6'@'localhost' identified by 'user6';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user6'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user6'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user6'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user6'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MY_MEDIC_SCHEDULE to 'user6'@'localhost';
#grant 'medic'@'localhost' to 'user6'@'localhost';
#set default role 'medic'@'localhost' to 'user6'@'localhost';

drop user if exists 'user7'@'localhost';
create user 'user7'@'localhost' identified by 'user7';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user7'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user7'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user7'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user7'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MY_MEDIC_SCHEDULE to 'user7'@'localhost';
#grant 'medic'@'localhost' to 'user7'@'localhost';
#set default role 'medic'@'localhost' to 'user7'@'localhost';

drop user if exists 'user8'@'localhost';
create user 'user8'@'localhost' identified by 'user8';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user8'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user8'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user8'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user8'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MY_MEDIC_SCHEDULE to 'user8'@'localhost';
#grant 'medic'@'localhost' to 'user8'@'localhost';
#set default role 'medic'@'localhost' to 'user8'@'localhost';

drop user if exists 'user9'@'localhost';
create user 'user9'@'localhost' identified by 'user9';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user9'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user9'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user9'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user9'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_MY_MEDIC_SCHEDULE to 'user9'@'localhost';
#grant 'medic'@'localhost' to 'user9'@'localhost';
#set default role 'medic'@'localhost' to 'user9'@'localhost';

drop user if exists 'user10'@'localhost';
create user 'user10'@'localhost' identified by 'user10';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user10'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user10'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user10'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user10'@'localhost';
#grant 'nurse'@'localhost' to 'user10'@'localhost';
#set default role 'nurse'@'localhost' to 'user10'@'localhost';

drop user if exists 'user11'@'localhost';
create user 'user11'@'localhost' identified by 'user11';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user11'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user11'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user11'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user11'@'localhost';
#grant 'nurse'@'localhost' to 'user11'@'localhost';
#set default role 'nurse'@'localhost' to 'user11'@'localhost';

drop user if exists 'user12'@'localhost';
create user 'user12'@'localhost' identified by 'user12';
grant execute on procedure polyclinic_chain.SHOW_MY_DATA to 'user12'@'localhost';
grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to 'user12'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to 'user12'@'localhost';
grant execute on procedure polyclinic_chain.SHOW_PATIENTS to 'user12'@'localhost';
#grant 'nurse'@'localhost' to 'user12'@'localhost';
#set default role 'nurse'@'localhost' to 'user12'@'localhost';

drop user if exists 'user13'@'localhost';
create user 'user13'@'localhost' identified by 'user13';
grant all privileges on polyclinic_chain.* to 'user13'@'localhost';
#grant 'admin'@'localhost' to 'user13'@'localhost';
#set default role 'admin'@'localhost' to 'user13'@'localhost';
