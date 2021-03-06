
--- B ---

insert into location(location_name, address, latitude, longitude, type,deleted) values('Rotkvarija', '71, Bulevar oslobodjenja, Роткварија, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia',45.2559, 19.8349, 'station', false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('Banatic', '150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia', 45.2652, 19.8159, 'station',false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('Podbara', '1, Filipa Visnjica, Подбара, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia', 45.2618, 19.8516, 'station',false);

insert into station(location,zone,deleted) values(1,0,false);
insert into station(location,zone,deleted) values(2,0,false);
insert into station(location,zone,deleted) values(3,0, false);

insert into line(name, line_type,zone,deleted,first_station) values('7ca', 0,0, false, 1);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 1,0, false, 2);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 2,0, false, 3);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 2,0, true, 3);

insert into line_project(id,station_id) values(1,1);
insert into line_project(id,station_id) values(1,2);
insert into line_project(id,station_id) values(1,3);
insert into line_project(id,station_id) values(2,2);
insert into line_project(id,station_id) values(2,3);
insert into line_project(id,station_id) values(3,2);
insert into line_project(id,station_id) values(3,3);

insert into bus(code,line,late,name,time_arrive,deleted,location) values('7ca_bus_lasta',1,false,'lasta',5,false,2);
insert into bus(code,line,late,name,time_arrive,deleted,location) values('7ca_bus_sara',1,true,'sara',6,false,1);
insert into bus(code,line,late,name,time_arrive,deleted,location) values('7ca_bus_Nis_expres',1,false,'Nis_expres',5,false,2);
insert into bus(code,line,late,name,time_arrive,deleted) values('7ca_bus_novi',1,false,'novi',5,false);

insert into subway(code,line,late,name,time_arrive,deleted,location) values('7ca_subway_Nis_expres',2,false,'Nis_expres',5,false,2);
insert into subway(code,line,late,name,time_arrive,deleted,location) values('7ca_subway_Zastava',2,false,'Zastava',5,false,2);
insert into subway(code,line,late,name,time_arrive,deleted,location) values('7ca_subway_Zrenjanin putnik',2,true,'Zrenjanin putnik',6,false,3);
insert into subway(code,line,late,name,time_arrive,deleted) values('7ca_subway_novi',2,false,'novi',5,false);

insert into trolley(code,line,late,name,time_arrive,deleted,location) values('7ca_trolley_lasta',3,false,'lasta',5,false,2);
insert into trolley(code,line,late,name,time_arrive,deleted,location) values('7ca_trolley_Dunav prevoz',3,true,'Dunav prevoz',7,false,2);
insert into trolley(code,line,late,name,time_arrive,deleted,location) values('7ca_trolley_Sara',3,false,'Sara',5,false,3);
insert into trolley(code,line,late,name,time_arrive,deleted) values('7ca_trolley_novi',3,false,'novi',5,false);

insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('bokaa@gmail.com','bokaKontroler','lozinka','Bojana','Corilic','Balzakova 28','0897346576','1995-03-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('bane@gmail.com','baneKontroler','lozinka2','Bane','Corilic','Balzakova 28','456436463','1985-05-30');

insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik@gmail.com','putnik','lozinka','Bane','Corilic','Balzakova 28','453534532','1985-05-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik2@gmail.com','putnik2','lozinka2','ime','prezime','Balzakova 28','462747242','1985-05-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik3@gmail.com','putnik3','lozinka3','ime','prezime','Balzakova 28','462747242','1985-05-30');
insert into user(email, username, password, first_name, last_name, address, phone_number, date_birth)values('putnik4@gmail.com','putnik4','lozinka4','ime','prezime','Balzakova 28','46256464642','1985-05-30');

insert into controller(id,deleted)values(1,false);
insert into controller(id,deleted)values(2,false);

insert into passenger(id,active,user_type,validate_document)values(3,true,1,false);
insert into passenger(id,active,user_type,validate_document)values(4,true,1,false);
insert into passenger(id,active,user_type,validate_document)values(5,true,1,false);
insert into passenger(id,active,user_type,validate_document)values(6,true,1,false);

insert into document(id,date_of_upload,image_location,passenger_id,approved)values(1,'2019-01-10','lokacija',3,'need approve');
insert into document(id,date_of_upload,image_location,passenger_id,approved)values(2,'2018-05-30','lokacija',4,'need approve');



--- V ---

INSERT INTO pricelist (id, active, bus_daily_use_price, bus_monthly_use_price, bus_one_use_price, bus_yearly_use_price, double_zone_premium_percentage, senior_discount_percentage, standard_discount_percentage, student_discount_percentage, subway_daily_use_price, subway_monthly_use_price, subway_one_use_price, subway_yearly_use_price, trolley_daily_use_price, trolley_monthly_use_price, trolley_one_use_price, trolley_yearly_use_price, valid_since, valid_until, deleted) VALUES (1, false, 1175, 11750, 65, 117500, 1180, 40, 11, 45, 1195, 11950, 185, 119500, 1185, 11850, 715, 118500, '2019-01-01 00:00:00', '2019-12-31 00:00:00', false);
INSERT INTO pricelist (id, active, bus_daily_use_price, bus_monthly_use_price, bus_one_use_price, bus_yearly_use_price, double_zone_premium_percentage, senior_discount_percentage, standard_discount_percentage, student_discount_percentage, subway_daily_use_price, subway_monthly_use_price, subway_one_use_price, subway_yearly_use_price, trolley_daily_use_price, trolley_monthly_use_price, trolley_one_use_price, trolley_yearly_use_price, valid_since, valid_until, deleted) VALUES (2, true, 175, 1750, 65, 17500, 180, 10, 1, 15, 195, 1950, 85, 19500, 185, 1850, 75, 18500, '2019-01-01 00:00:00', '2019-12-31 00:00:00', false);
INSERT INTO pricelist (id, active, bus_daily_use_price, bus_monthly_use_price, bus_one_use_price, bus_yearly_use_price, double_zone_premium_percentage, senior_discount_percentage, standard_discount_percentage, student_discount_percentage, subway_daily_use_price, subway_monthly_use_price, subway_one_use_price, subway_yearly_use_price, trolley_daily_use_price, trolley_monthly_use_price, trolley_one_use_price, trolley_yearly_use_price, valid_since, valid_until, deleted) VALUES (3, false, 2175, 12750, 265, 172500, 1280, 5, 5, 5, 1295, 19250, 8225, 129500, 1825, 18250, 725, 128500, '2019-01-01 00:00:00', '2019-12-31 00:00:00', true);

INSERT INTO ticket (id, active, block, code_transport, cost, date, date_purchase, demographic_type, expired, ticket_type, time_expired, type_transport, zone, passenger) VALUES (1, true, false, 'kec_bus_devetka', 1732.5, '2019-02-02 00:00:00', '2019-01-27 03:27:32', 1, false, 2, '2019-03-02 00:00:00', 0, 0, 4);
INSERT INTO ticket (id, active, block, code_transport, cost, date, date_purchase, demographic_type, expired, ticket_type, time_expired, type_transport, zone, passenger) VALUES (2, true, false, 'kec_bus_devetka', 3118.5, '2019-02-02 00:00:00', '2019-01-27 04:47:46', 1, false, 2, '2019-03-02 00:00:00', 0, 1, 4);
INSERT INTO ticket (id, active, block, code_transport, cost, date, date_purchase, demographic_type, expired, ticket_type, time_expired, type_transport, zone, passenger) VALUES (3, true, false, 'kec_bus_devetka', 64.35, '2019-01-27 00:00:00', '2019-01-27 07:12:04', 1, false, 0, '2020-01-27 00:00:00', 0, 0, 4);

INSERT INTO timetable (id, code, deleted, timetable_line_id) VALUES (15, 'kec_bus_devetka', false, 1);
INSERT INTO timetable (id, code, deleted, timetable_line_id) VALUES (16, 'kec_bus_desetka', false, 1);
INSERT INTO timetable (id, code, deleted, timetable_line_id) VALUES (17, 'dvojka_bus_buscin', false, 2);
INSERT INTO timetable (id, code, deleted, timetable_line_id) VALUES (18, 'dvojka_bus_lolo', false, 2);

-- insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica1', 'Vojvode Supljikca 50', 30.40, 30.20, 'station', false);
-- insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica2', 'Vojvode Supljikca 51', 31.40, 30.50, 'station',false);
-- insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica3', 'Vojvode Supljikca 52', 39.40, 35.20, 'station',false);
--
-- insert into station(location,zone,deleted) values(1,0,false);
-- insert into station(location,zone,deleted) values(2,0,false);
-- insert into station(location,zone,deleted) values(2,0, false);
