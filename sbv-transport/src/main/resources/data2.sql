
insert into location(location_name, address, latitude, longitude, type,deleted) values('Rotkvarija', '71, Bulevar oslobodjenja, Роткварија, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia',45.2559, 19.8349, 'station', false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('Banatic', '150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia', 45.2652, 19.8159, 'station',false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('Podbara', '1, Filipa Visnjica, Подбара, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia', 45.2618, 19.8516, 'station',false);

insert into station(location,zone,deleted) values(1,0,false);
insert into station(location,zone,deleted) values(2,0,false);
insert into station(location,zone,deleted) values(3,0, false);

insert into line(name, line_type,zone,deleted,first_station) values('7ca', 0,0, false, 1);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 1,0, false,2);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 2,0, false, 3);

insert into line_project(id,station_id) values(1,1);
insert into line_project(id,station_id) values(1,2);
insert into line_project(id,station_id) values(1,3);
insert into line_project(id,station_id) values(2,2);
insert into line_project(id,station_id) values(2,3);
insert into line_project(id,station_id) values(3,3);


insert into bus(code,line,late,name,time_arrive,deleted,location) values('7ca_bus_lasta',1,false,'lasta',5,false,2);
insert into bus(code,line,late,name,time_arrive,deleted,location) values('7ca_bus_sara',1,true,'sara',6,false,1);
insert into bus(code,line,late,name,time_arrive,deleted,location) values('7ca_bus_Nis_expres',1,false,'Nis_expres',5,false,2);

insert into subway(code,line,late,name,time_arrive,deleted,location) values('7ca_subway_Nis_expres',2,false,'Nis_expres',5,false,2);
insert into subway(code,line,late,name,time_arrive,deleted,location) values('7ca_subway_Zastava',2,false,'Zastava',5,false,2);
insert into subway(code,line,late,name,time_arrive,deleted,location) values('7ca_subway_Zrenjanin putnik',2,true,'Zrenjanin putnik',6,false,3);

insert into trolley(code,line,late,name,time_arrive,deleted,location) values('7ca_trolley_lasta',3,false,'lasta',5,false,3);
insert into trolley(code,line,late,name,time_arrive,deleted) values('7ca_trolley_Dunav prevoz',3,true,'Dunav prevoz',7,false);
insert into trolley(code,line,late,name,time_arrive,deleted,location) values('7ca_trolley_Sara',3,false,'Sara',5,false,3);

-- ne valja, nesto nije dobro jer ne moze da se uloguj sa ovim podacima kao da ne vidi role?
/*
insert into role(name) values('ROLE_PASSENGER');
insert into role(name) values('ROLE_CONTROLLER');

insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('bokaa@gmail.com','bokaKontroler','lozinka','Bojana','Corilic','Balzakova 28','0897346576','1995-03-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('bane@gmail.com','baneKontroler','lozinka2','Bane','Corilic','Balzakova 28','456436463','1985-05-30');

insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik@gmail.com','putnik','lozinka','Bane','Corilic','Balzakova 28','453534532','1985-05-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik2@gmail.com','putnik2','lozinka2','ime2','prezime2','Balzakova 28','462747242','1985-05-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik3@gmail.com','putnik3','lozinka3','ime3','prezime3','Balzakova 28','462747242','1985-05-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik4@gmail.com','putnik4','lozinka4','ime4','prezime4','Balzakova 28','46256464642','1985-05-30');

insert into user_roles(user_id,role_id) values(1,2);
insert into user_roles(user_id,role_id) values(2,2);
insert into user_roles(user_id,role_id) values(3,3);
insert into user_roles(user_id,role_id) values(4,3);
insert into user_roles(user_id,role_id) values(5,3);
insert into user_roles(user_id,role_id) values(6,3);

insert into controller(id,deleted)values(1,false);
insert into controller(id,deleted)values(2,false);

insert into passenger(id,active,user_type,validate_document)values(3,true,1,false);
insert into passenger(id,active,user_type,validate_document)values(4,true,1,false);
insert into passenger(id,active,user_type,validate_document)values(5,true,1,false);
insert into passenger(id,active,user_type,validate_document)values(6,true,1,false);

insert into document(date_of_upload,image_location,passenger_id,approved)values('2019-01-10','lokacija',4,'need approve');
insert into document(date_of_upload,image_location,passenger_id,approved)values('2018-05-30','lokacija',5,'need approve');
*/
