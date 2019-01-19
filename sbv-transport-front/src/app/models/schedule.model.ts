import { Station } from './station.model';

export class Schedule{
    id: BigInteger;
    station: Station;
    times: Set<Date>;
}