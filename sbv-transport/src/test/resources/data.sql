
--- B ---

insert into location(location_name, address, latitude, longitude, type,deleted) values('Rotkvarija', '71, Bulevar oslobodjenja, Роткварија, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia',45.2559, 19.8349, 'station', false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('Banatic', '150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia', 45.2652, 19.8159, 'station',false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('Podbara', '1, Filipa Visnjica, Подбара, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia', 45.2618, 19.8516, 'station',false);

insert into station(location,zone,deleted) values(1,0,false);
insert into station(location,zone,deleted) values(2,0,false);
insert into station(location,zone,deleted) values(3,0, false);

insert into line(name, line_type,zone,deleted,first_station) values('7ca', 0,0, false, 1);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 1,0, false, 2);
insert into line(name, line_type,zone,deleted,first_station) values('7ca', 2,0, true, 3);

insert into line_project(id,station_id) values(1,1);
insert into line_project(id,station_id) values(1,2);
insert into line_project(id,station_id) values(1,3);
insert into line_project(id,station_id) values(2,2);
insert into line_project(id,station_id) values(2,3);
insert into line_project(id,station_id) values(3,2);


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





--- V ---

-- insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica1', 'Vojvode Supljikca 50', 30.40, 30.20, 'station', false);
-- insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica2', 'Vojvode Supljikca 51', 31.40, 30.50, 'station',false);
-- insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica3', 'Vojvode Supljikca 52', 39.40, 35.20, 'station',false);
--
-- insert into station(location,zone,deleted) values(1,0,false);
-- insert into station(location,zone,deleted) values(2,0,false);
-- insert into station(location,zone,deleted) values(2,0, false);
