import { MyLocation } from './location.model';
import { Line } from './line.model';
import { Timetable } from './timetable.model';

export class Bus {

  id: BigInteger;
  code: string;
  line: Line;
  late: boolean;
  name: string;
  location: MyLocation;
  timetable: Timetable;
  time_arrive: number;
  }