import { Line } from '../models/line.model';

export class ReportLineDTO{

    line:Line;
    numb_vehicles: number;
    numb_on_time: number;
    numb_late: number;
}