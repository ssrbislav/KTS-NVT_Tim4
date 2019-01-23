import { ScheduleDTO } from './schedule.dto';

export class AltTimetableDTO{
	transportType : string; // bus/subway/trolley
	id_transport: BigInteger;
	timetable: String[];
}