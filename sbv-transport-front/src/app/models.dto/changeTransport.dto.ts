import { MyLocation } from '../models/location.model';
import { Timetable } from '../models/timetable.model';

export class ChangeTransportDTO{
    id_transport: BigInteger;
	name: string;
	time_arrive : number;
	current_location: MyLocation;
	timetable: Timetable;
}