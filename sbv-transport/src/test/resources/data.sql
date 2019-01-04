 
insert into line(name, line_type) values('nova_linija', 0);
insert into line(name, line_type) values('nova_linija2', 1);
insert into line(name, line_type) values('nova_linija3', 2);

insert into bus(code,line,late,name) values('nova_linija_bus_7ca',1,false,'7ca');
insert into bus(code,line,late,name) values('nova_linija_bus_11ca',1,false,'11ca');
insert into bus(code,line,late,name) values('nova_linija_bus_77ca',1,false,'77ca');

insert into subway(code,line,late,name) values('nova_linija2_subway_3ka',2,false,'3ka');
insert into subway(code,line,late,name) values('nova_linija2_subway_55ca',2,false,'55ca');
insert into subway(code,line,late,name) values('nova_linija2_subway_88ca',2,false,'88ca');

insert into trolley(code,line,late,name) values('nova_linija3_trolley_4ka',3,false,'4ka');
insert into trolley(code,line,late,name) values('nova_linija3_trolley_12ka',3,false,'12ka');
insert into trolley(code,line,late,name) values('nova_linija3_trolley_17ca',3,false,'17ca');

--- V ---

insert into location(location_name, address, latitude, longitude, type) values('stanica1', 'Vojvode Supljikca 50', 30.40f, 30.20f, 'station');
insert into location(location_name, address, latitude, longitude, type) values('stanica2', 'Vojvode Supljikca 51', 31.40f, 30.50f, 'station');
insert into location(location_name, address, latitude, longitude, type) values('stanica3', 'Vojvode Supljikca 52', 39.40f, 35.20f, 'station');