import { Passenger } from './passenger.model';

export class Ticket {
    id: BigInteger;
    type_transport: TypeTransport;
    cost: Number;
    zone: Zone;
    date: Date;
    ticket_type: TicketType;
    active: boolean;
    expired: boolean;
    demographic_type: DemographicTicketType;
    time_expired: Date;
    block: boolean;
    code_transport: String;
    date_purchase: Date;
    passenger: Passenger;
}

enum TypeTransport {
    bus,subway,trolley
}

enum TicketType{
    oneUse,daily,monthly,year
}

enum DemographicTicketType{
    student,standard,senior
}

enum Zone{
    first,second
}