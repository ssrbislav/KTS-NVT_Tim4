insert into line(name, line_type,zone,deleted) values('nova_linija', 0,0, false);
insert into line(name, line_type,zone,deleted) values('nova_linija2', 1,0, false);
insert into line(name, line_type,zone,deleted) values('nova_linija3', 2,0, false);

insert into bus(code,line,late,name,time_arrive,deleted) values('nova_linija_bus_7ca',1,false,'7ca',5,false);
insert into bus(code,line,late,name,time_arrive,deleted) values('nova_linija_bus_11ca',1,false,'11ca',6,false);
insert into bus(code,line,late,name,time_arrive,deleted) values('nova_linija_bus_77ca',1,false,'77ca',5,false);

insert into subway(code,line,late,name,time_arrive,deleted) values('nova_linija2_subway_3ka',2,false,'3ka',5,false);
insert into subway(code,line,late,name,time_arrive,deleted) values('nova_linija2_subway_55ca',2,false,'55ca',5,false);
insert into subway(code,line,late,name,time_arrive,deleted) values('nova_linija2_subway_88ca',2,false,'88ca',6,false);

insert into trolley(code,line,late,name,time_arrive,deleted) values('nova_linija3_trolley_4ka',3,false,'4ka',5,false);
insert into trolley(code,line,late,name,time_arrive,deleted) values('nova_linija3_trolley_12ka',3,false,'12ka',7,false);
insert into trolley(code,line,late,name,time_arrive,deleted) values('nova_linija3_trolley_17ca',3,false,'17ca',5,false);


--- V ---

insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica1', 'Vojvode Supljikca 50', 30.40, 30.20, 'station', false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica2', 'Vojvode Supljikca 51', 31.40, 30.50, 'station',false);
insert into location(location_name, address, latitude, longitude, type,deleted) values('stanica3', 'Vojvode Supljikca 52', 39.40, 35.20, 'station',false);

insert into station(location,zone,deleted) values(1,0,false);
insert into station(location,zone,deleted) values(2,0,false);
insert into station(location,zone,deleted) values(2,0, false);
