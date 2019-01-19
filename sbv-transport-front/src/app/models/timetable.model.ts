import { Schedule } from './schedule.model';

export class Timetable{
    id:BigInteger;
	code: string;
    schedule : Set<Schedule>;
    
}