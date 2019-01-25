export class BuyTicketDTO {
    idPassenger: BigInteger;
    type_transport: TypeTransport;
    zone: Zone;
    ticket_type: TicketType;
    demographic_type: DemographicTicketType;
    code_transport: String;
    date: Date;
}