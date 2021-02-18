#cu asta vede userul datele sale dintr-o tabela
drop procedure if exists SHOW_MY_DATA;
delimiter //
create procedure SHOW_MY_DATA(TableName varchar(30))
begin
	declare SQLStatement varchar(500) default null;
    declare CurrentUserID int;
    set CurrentUserID:=(select users.UserID from users where (select user()) like CONCAT(users.username, '@localhost'));#like in =
    set @SQLStatement:=CONCAT('select * from ', TableName, ' where UserID = ', CurrentUserID);
    prepare stmt from @SQLStatement;
    execute stmt;
end; //
delimiter ;

#cu asta vede inspectorul workhours-urile
drop procedure if exists SHOW_TIME_TABLE;#done
delimiter //
create procedure SHOW_TIME_TABLE(FirstName varchar(50), LastName varchar(50))   
begin
	select users.FirstName, users.LastName, time_table.date, time_table.BeginsAt, time_table.EndsAt from users, time_table
    where users.FirstName = FirstName and users.LastName = LastName and users.userID = time_table.userID;
end; //
delimiter ;

#cu asta modifica inspectorul workhours-urile
drop procedure if exists SET_TIME_TABLE;#done
delimiter //
create procedure SET_TIME_TABLE(FirstName varchar(50), LastName varchar(50), Date date, BeginsAt time, EndsAt time)   
begin
	if exists(select time_table.UserID from time_table where time_table.UserID = (select UserID from users where users.FirstName like FirstName and users.LastName like LastName)) then
		begin
			update time_table set
				time_table.Date=Date,
                time_table.BeginsAt=BeginsAt,
                time_table.EndsAt=EndsAt
            where time_table.UserID=(select UserID from users where users.FirstName like FirstName and users.LastName like LastName);
		end;
	else 
		insert into time_table(UserID, Date, BeginsAt, EndsAt)
        values((select UserID from users where users.FirstName like FirstName and users.LastName like LastName), Date, BeginsAt, EndsAt);
    end if;
end; //
delimiter ;

#cu asta vedem ce vacanta are cineva
drop procedure if exists SHOW_HOLIDAY;#done
delimiter //
create procedure SHOW_HOLIDAY(FirstName varchar(50), LastName varchar(50))   
begin
	select users.FirstName, users.LastName, holiday.StartingDate, holiday.EndingDate from users, holiday
    where users.FirstName = FirstName and users.LastName = LastName and users.userID = holiday.userID;
end; //
delimiter ;


#cu asta modifica inspectorul holiday-urile
drop procedure if exists SET_HOLIDAY; #done
delimiter //
create procedure SET_HOLIDAY(FirstName varchar(50), LastName varchar(50), StartingDate date, EndingDate date)   
begin
	#declare UserID int;
    #set UserID:=(select UserID from users where users.FirstName like FirstName and users.LastName like LastName);
    #select @UserID;
    #select UserID from users where users.FirstName like FirstName and users.LastName like LastName;
	if exists(select holiday.UserID from holiday where holiday.UserID = (select UserID from users where users.FirstName like FirstName and users.LastName like LastName)) then
		begin
			update holiday set
				holiday.StartingDate=StartingDate,
                holiday.EndingDate=EndingDate
            where holiday.UserID=(select UserID from users where users.FirstName like FirstName and users.LastName like LastName);
		end;
	else 
		insert into holiday(UserID, StartingDate, EndingDate)
        values((select UserID from users where users.FirstName like FirstName and users.LastName like LastName), StartingDate, EndingDate);
    end if;
end; //
delimiter ;

#cu asta vede receptionerul ce unitati medicale ale policlinicii ofera serviciul(alege locatie)
drop procedure if exists SHOW_SUITABLE_UNITS;#done
delimiter //
create procedure SHOW_SUITABLE_UNITS(ServiceName varchar(50))
begin
	select polyclinics.Name from polyclinics
    where polyclinics.PolyclinicID=(
		select distinct polyclinics_services.PolyclinicID from polyclinics_services, services
        where services.Name=ServiceName and polyclinics_services.ServiceID=services.ServiceID
	);
end; //
delimiter;

#cu asta vede receptionerul ce doctori pot efectua seriviciul(alege doctor)nike swooosh
drop procedure if exists SHOW_QUALIFIED_MEDICS;#done
delimiter //
create procedure SHOW_QUALIFIED_MEDICS(ServiceName varchar(50))
begin
	#DE FORMA ((CAZ FARA SPECIALIZ) OR (CAZ CU SPECIALIZ)) AND ((CAZ FARA COMPETENTE) OR (CAZ CU COMPETENCE))
	#iau medicii care au specializ necesare si cei care au comp necesare, se face produs cartezian, si se vor lua doar cand sunt egale pt ca trb sa apara ambele
	select distinct users.FirstName, users.LastName from users, medics
    where
		medics.UserID = users.UserID and
		((select distinct services_specializations.specializationID from services_specializations, services
		where services_specializations.ServiceID = services.ServiceID and#si aici
        services.Name = ServiceName) is null OR
        medics.UserID in (
			select distinct medics_specializations.UserID from medics_specializations, specializations, services_specializations, services
			where specializations.specializationID=medics_specializations.specializationID and
			services_specializations.serviceID=services.ServiceID and#aici am modificat
            services.Name = ServiceName and#si aici
			services_specializations.specializationID=medics_specializations.specializationID
        )) AND
		((select distinct services_competences.competenceID from services_competences, services
		where services_competences.ServiceID = services.ServiceID and#si aici
        services.Name = ServiceName) is null OR
        medics.UserID in (
			select distinct medics_competences.UserID from medics_competences, competences, services_competences, services
			where competences.competenceID=medics_competences.competenceID and
			services_competences.serviceID=services.ServiceID and#aici am modificat
            services.Name = ServiceName and#si aici
			services_competences.competenceID=medics_competences.competenceID
		));
end; //
delimiter ;

#cu asta vede receptionerul cand un medic are deja programari(putem face pe interfata interzise datele astea pt alte programari)
drop procedure if exists SHOW_MEDIC_SCHEDULE;
delimiter //
create procedure SHOW_MEDIC_SCHEDULE(FirstName varchar(50), LastName varchar(50))
begin
	select appointments.Date, polyclinics.Name, appointments.BeginsAt, appointments.EndsAt from appointments, polyclinics 
    where appointments.UserID=(select UserID from users where users.FirstName like FirstName and users.LastName like LastName)#aka medicID
    and polyclinics.polyclinicID=appointments.polyclinicID;	
end; //
delimiter ;

#cu asta vede medicul ce program are
drop procedure if exists SHOW_MY_MEDIC_SCHEDULE;
delimiter //
create procedure SHOW_MY_MEDIC_SCHEDULE()
begin
	select appointments.Date, polyclinics.Name, appointments.BeginsAt, appointments.EndsAt from appointments, polyclinics 
    where appointments.UserID=(select users.UserID from users where (select user()) like CONCAT(users.username, '@localhost'))#aka medicID
    and polyclinics.polyclinicID=appointments.polyclinicID;	
end; //
delimiter ;

#cu asta se adauga programarea dupa ce s-a ales doctorul si data/ora
drop procedure if exists ADD_APPOINTMENT;
delimiter //
create procedure ADD_APPOINTMENT(PatientFirstName varchar(50), PatientLastName varchar(50), 
								ServiceName varchar(50), PolyclinicName varchar(50), 
                                MedicFirstName varchar(50), MedicLastName varchar(50), 
                                Date date, BeginsAt time)
begin
	declare EndsAt time;
	set EndsAt:=ADDTIME(BeginsAt, (select services.Duration from services where services.ServiceID=(select ServiceID from services where services.Name=ServiceName)));
    insert into appointments(PatientID, ServiceID, PolyclinicID, UserID, Date, BeginsAt, EndsAt)	
    values((select PatientID from patients where patients.FirstName like PatientFirstName and patients.LastName like PatientLastName),
			(select ServiceID from services where services.Name like ServiceName),
            (select PolyclinicID from polyclinics where polyclinics.Name like PolyclinicName), 
            (select UserID from users where users.FirstName like MedicFirstName and users.LastName like MedicLastName),
            Date, BeginsAt, EndsAt);
	select polyclinics.Name, services.Name, services.Price, appointments.appointmentID from polyclinics, services, appointments
    where appointments.polyclinicID=polyclinics.polyclinicID and services.serviceID=appointments.serviceID and appointments.appointmentID=last_insert_id();
end; //
delimiter ;

#cu asta vedem fiecare pacient ce programari are
drop procedure if exists SHOW_PATIENT_APPOINTMENTS;
delimiter //
create procedure SHOW_PATIENT_APPOINTMENTS(PatientFirstName varchar(50), PatientLastName varchar(50))
begin
	select users.FirstName, users.LastName, appointments.Date, appointments.BeginsAt, appointments.EndsAt from users, appointments, patients
    where appointments.PatientID=patients.PatientID and patients.FirstName=PatientFirstName and patients.LastName=PatientLastName
			and appointments.UserID=users.UserID;#poate adaug ceva Date>NOW() pt a vedea doar programarile neefectuate inca
end; //
delimiter ;

#cu asta adauga medicul rapoarte despre un appointment
drop procedure if exists ADD_MEDICAL_REPORT;
delimiter //
create procedure ADD_MEDICAL_REPORT(PatientFirstName varchar(50), PatientLastName varchar(50), AppointmentID int, MedicalReport blob)
begin
	insert into patient_history(PatientID, AppointmentID, MedicalReport) values
    ((select PatientID from patients where patients.FirstName=PatientFirstName and patients.LastName=PatientLastName), AppointmentID, MedicalReport);
end; //
delimiter ;

#cu asta adauga medicul rapoarte despre un appointment
drop procedure if exists SHOW_PATIENT_HISTORY;
delimiter //
create procedure SHOW_PATIENT_HISTORY(PatientFirstName varchar(50), PatientLastName varchar(50))
begin
	select patient_history.AppointmentID, users.FirstName, users.LastName, patient_history.MedicalReport from patient_history, users, appointments, patients
    where appointments.UserID=users.UserID and patients.FirstName=PatientFirstName and patients.LastName=PatientLastName;
end; //
delimiter ;

#cu asta cream bonul
drop trigger if exists CREATE_RECEIPT;
delimiter //
create trigger CREATE_RECEIPT
AFTER INSERT ON appointments FOR EACH ROW
BEGIN
	insert into receipts(AppointmentID) values
    (NEW.AppointmentID);
END //

#cu asta vedem profitul total
drop procedure if exists SHOW_TOTAL_PROFIT;
delimiter //
create procedure SHOW_TOTAL_PROFIT()
begin
	declare profit double default 0.0;
	
    #facem suma tuturor veniturilor
    drop table if exists income;
    create temporary table income(price double);
    insert into income(price) (select services.price from services, appointments where appointments.serviceID=services.serviceID);
	set profit = (select SUM(price) from income) - (select SUM(NegociatedIncome) from employees);
    
	if profit is null then
    begin
        set profit = 0 - (select SUM(NegociatedIncome) from employees);
        select profit;
	end;
    else select profit;
    end if;
    
	drop table income;
end; //
delimiter ;

#cu asta vedem profitul pe fiecare medic
drop procedure if exists SHOW_MEDIC_PROFIT;
delimiter //
create procedure SHOW_MEDIC_PROFIT(MedicFirstName varchar(50), MedicLastName varchar(50))
begin
	declare profit double default 0.0;

	#facem suma tuturor veniturilor
    drop table if exists income;
    create temporary table income(sum double);
    insert into income(sum) (select services.Price from services, appointments where appointments.serviceID=services.serviceID 
			and appointments.userID=(select UserID from users where users.FirstName=MedicFirstName and users.LastName=MedicLastName));
    set profit = (select SUM(sum) from income);
    drop table income;
    
    if profit is null then
    begin
		select 0;
	end;
    else select profit;
    end if;
        
    #select profit;
end; //
delimiter ;

#cu asta adaugam un pacient
drop procedure if exists ADD_PATIENT;
delimiter //
create procedure ADD_PATIENT(CNP varchar(13), FirstName varchar(20), LastName varchar(20), PhoneNumber varchar(10), Email varchar(50))
begin
	insert into patients(CNP, FirstName, LastName, PhoneNumber, Email) values
    (CNP, FirstName, LastName, PhoneNumber, Email);
end; //
delimiter ;

#cu asta vedem toti pacientii
drop procedure if exists SHOW_PATIENTS;
delimiter //
create procedure SHOW_PATIENTS()
begin
	select * from patients;
end; //
delimiter ;

#cu asta vedem toti userii
drop procedure if exists SHOW_USERS;
delimiter //
create procedure SHOW_USERS()
begin
	select * from users;
end; //
delimiter ;

#cu asta vedem toti employeees
drop procedure if exists SHOW_EMPLOYEES;
delimiter //
create procedure SHOW_EMPLOYEES()
begin
	select * from users
	join employees on users.userID=employees.userID;
end; //
delimiter ;

#cu asta adaugam admin nou
drop procedure if exists ADD_NEW_ADMIN;
delimiter //
create procedure ADD_NEW_ADMIN(Username varchar(20), Password varchar(20), CNP varchar(13), FirstName varchar(20), LastName varchar(20),
	PhoneNumber varchar(10), Email varchar(50), IBAN varchar(24), ContractNumber int, HiringDate date)
begin
	declare SQLStatement varchar(500) default null;

	insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
    (Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, 'DB');
    insert into admins(UserID) values (last_insert_id());
    
	set @SQLStatement:=CONCAT('create user ', Username, '@localhost identified by ', '\'',Password,'\'');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant all privileges on polyclinic_chain.* to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
end; //
delimiter ;

#cu asta adaugam inspector nou
drop procedure if exists ADD_NEW_INSPECTOR;
delimiter //
create procedure ADD_NEW_INSPECTOR(Username varchar(20), Password varchar(20), CNP varchar(13), FirstName varchar(20), LastName varchar(20),
	PhoneNumber varchar(10), Email varchar(50), IBAN varchar(24), ContractNumber int, HiringDate date, NegociatedIncome double, WorkHours int)
begin
	declare SQLStatement varchar(500) default null;

	insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
    (Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, 'HR');
    insert into employees(UserID, NegociatedIncome, WorkHours, PositionHeld) values 
    (last_insert_id(), NegociatedIncome, WorkHours, 'Inspector');
    
	set @SQLStatement:=CONCAT('create user ', Username, '@localhost identified by ', '\'',Password,'\'');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MY_DATA to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_TIME_TABLE to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_HOLIDAY to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SET_TIME_TABLE to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SET_HOLIDAY to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_EMPLOYEES to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
end; //
delimiter ;

#cu asta adaugam receptioner nou
drop procedure if exists ADD_NEW_RECEPTIONIST;
delimiter //
create procedure ADD_NEW_RECEPTIONIST(Username varchar(20), Password varchar(20), CNP varchar(13), FirstName varchar(20), LastName varchar(20),
	PhoneNumber varchar(10), Email varchar(50), IBAN varchar(24), ContractNumber int, HiringDate date, NegociatedIncome double, WorkHours int)
begin
	declare SQLStatement varchar(500) default null;

	insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
    (Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, 'PR');
    insert into employees(UserID, NegociatedIncome, WorkHours, PositionHeld) values 
    (last_insert_id(), NegociatedIncome, WorkHours, 'Receptionist');
    
	set @SQLStatement:=CONCAT('create user ', Username, '@localhost identified by ', '\'',Password,'\'');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MY_DATA to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.ADD_PATIENT to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_PATIENTS to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.ADD_APPOINTMENT to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
end; //
delimiter ;

#cu asta adaugam accountant nou
drop procedure if exists ADD_NEW_ACCOUNTANT;
delimiter //
create procedure ADD_NEW_ACCOUNTANT(Username varchar(20), Password varchar(20), CNP varchar(13), FirstName varchar(20), LastName varchar(20),
	PhoneNumber varchar(10), Email varchar(50), IBAN varchar(24), ContractNumber int, HiringDate date, NegociatedIncome double, WorkHours int)
begin
	declare SQLStatement varchar(500) default null;

	insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
    (Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, 'F');
    insert into employees(UserID, NegociatedIncome, WorkHours, PositionHeld) values 
    (last_insert_id(), NegociatedIncome, WorkHours, 'Accountant');
    
	set @SQLStatement:=CONCAT('create user ', Username, '@localhost identified by ', '\'',Password,'\'');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MY_DATA to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_TOTAL_PROFIT to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MEDIC_PROFIT to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
end; //
delimiter ;

#cu asta adaugam nurse nou
drop procedure if exists ADD_NEW_NURSE;
delimiter //
create procedure ADD_NEW_NURSE(Username varchar(20), Password varchar(20), CNP varchar(13), FirstName varchar(20), LastName varchar(20),
	PhoneNumber varchar(10), Email varchar(50), IBAN varchar(24), ContractNumber int, HiringDate date, NegociatedIncome double, WorkHours int,
    Type varchar(50), Grade varchar(1))
begin
	declare SQLStatement varchar(500) default null;

	insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
    (Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, 'M');
    insert into employees(UserID, NegociatedIncome, WorkHours, PositionHeld) values 
    (last_insert_id(), NegociatedIncome, WorkHours, 'Nurse');
    insert into nurses(UserID, Type, Grade) values
    (last_insert_id(), Type, Grade);
    
	set @SQLStatement:=CONCAT('create user ', Username, '@localhost identified by ', '\'',Password,'\'');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MY_DATA to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_PATIENTS to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
end; //
delimiter ;

#cu asta adaugam medic nou
drop procedure if exists ADD_NEW_MEDIC;
delimiter //
create procedure ADD_NEW_MEDIC(Username varchar(20), Password varchar(20), CNP varchar(13), FirstName varchar(20), LastName varchar(20),
	PhoneNumber varchar(10), Email varchar(50), IBAN varchar(24), ContractNumber int, HiringDate date, NegociatedIncome double, WorkHours int,
    StampCode varchar(10), MedicalTitle varchar(50), AcademicPosition varchar(50), Comission smallint, Specialization varchar(50), 
    Competence varchar(50)) 
begin
	declare SQLStatement varchar(500) default null;

	insert into users(Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, UserType) values
    (Username, CNP, FirstName, LastName, PhoneNumber, Email, IBAN, ContractNumber, HiringDate, 'M');
    insert into employees(UserID, NegociatedIncome, WorkHours, PositionHeld) values 
    (last_insert_id(), NegociatedIncome, WorkHours, 'Medic');
    insert into medics(UserID, StampCode, MedicalTitle, AcademicPosition, Comission) values
    (last_insert_id(), StampCode, MedicalTitle, AcademicPosition, Comission);
    
    insert into medics_specializations(UserID, SpecializationID) values
    (last_insert_id(), (select SpecializationID from specializations where Name like Specialization));
    insert into medics_competences(UserID, CompetenceID) values
    (last_insert_id(), (select CompetenceID from competences where Name like Competence));
    
	set @SQLStatement:=CONCAT('create user ', Username, '@localhost identified by ', '\'',Password,'\'');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MY_DATA to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.ADD_MEDICAL_REPORT to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_PATIENT_HISTORY to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_PATIENTS to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    set @SQLStatement:=CONCAT('grant execute on procedure polyclinic_chain.SHOW_MY_MEDIC_SCHEDULE to ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
end; //
delimiter ;

#cu asta stergem un cont (neimpl in java)
drop procedure if exists DELETE_ACCOUNT;
delimiter //
create procedure DELETE_ACCOUNT(Username varchar(20))
begin
	declare SQLStatement varchar(500) default null;
    
	set @SQLStatement:=CONCAT('drop user if exists ', Username, '@localhost');
    prepare stmt from @SQLStatement;
    execute stmt;
    
    delete from users
    where users.Username=Username;
end; //
delimiter ;


