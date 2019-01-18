import { MyLocation } from './location.model';
import { Line } from './line.model';
import { Timetable } from './timetable.model';

export class Subway{

    id: BigInteger;
    code: string;
    line: Line;
    late: boolean;
    name: string;
    location: MyLocation;
    time_arrive: number;
    timetable: Timetable;
  }