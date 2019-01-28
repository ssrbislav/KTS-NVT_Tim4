import { Station } from './station.model';
import { Timetable } from './timetable.model';

export class Line {
    id: BigInteger;
    name:string;
    station_list: Station[];
    line_type: TypeTransport;
    zone: Zone;
    timetable: Timetable[];
    first_station: BigInteger;
}

enum TypeTransport {
    bus,subway,trolley
}

enum Zone{
    first,second
}