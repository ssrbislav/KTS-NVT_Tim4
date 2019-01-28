export class changeStationDTO{
    id_station: BigInteger;
    location_name: string;
    zone: Zone;
}

enum Zone{
    first,second
}