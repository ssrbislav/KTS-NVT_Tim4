import { ScheduleDTO } from './schedule.dto';

export class TimetableDTO{
    transportType : string; 
	id_transport : BigInteger;
	schedules: ScheduleDTO[];
}