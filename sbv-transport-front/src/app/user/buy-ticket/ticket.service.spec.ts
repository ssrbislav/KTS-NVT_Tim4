import { TestBed, inject } from "@angular/core/testing";
import { Line } from 'src/app/models/line.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TransportDTO } from 'src/app/models.dto/transport.dto';
import { AddLocationToTransportDTO } from 'src/app/models.dto/addLocationToTransportDTO.dto';
import { MyLocation } from 'src/app/models/location.model';
import { HttpResponse } from '@angular/common/http';
import { ChangeTransportDTO } from 'src/app/models.dto/changeTransport.dto';
import { Timetable } from 'src/app/models/timetable.model';
import { FilterSearchTransportDTO } from 'src/app/models.dto/filterSearchTransport.dto';
import { SubwayService } from 'src/app/services/subway.service';
import { Subway } from 'src/app/models/subway.model';
import { TicketService } from 'src/app/services/ticket.service';
import { Ticket } from 'src/app/models/ticket.model';
import { Passenger } from 'src/app/models/passenger.model';
import { BuyTicketDTO } from 'src/app/models.dto/buyTicket.dto';

describe('TicketService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [TicketService]
        });
    });

    enum DemographicTicketType{
        student,standard,senior
    }
    enum TicketType{
        oneUse,daily,monthly,year
    }
    enum TypeTransport {
        bus,subway,trolley
    }
    enum Zone{
        first,second
    }

    it('getTickets() should get all tickets', inject([HttpTestingController,TicketService], (
        httpMock: HttpTestingController,
        dataService: TicketService) => {

        let i : BigInteger = Uint8Array.of(1);

        let t : Ticket = new Ticket();
        t.active = true;
        t.block = false;
        t.code_transport = 'kod';
        t.cost = 1000.00;
        t.date = new Date();
        t.demographic_type = DemographicTicketType.student;
        t.expired = false;
        t.id = i;
        t.passenger = new Passenger();
        t.ticket_type = TicketType.year;
        t.type_transport = TypeTransport.bus;
        t.zone = Zone.first;
        let dummyTickets: Ticket[] = [];
        dummyTickets.push(t);

        dataService.getTickets().subscribe(data => {
            expect(data).toEqual(dummyTickets);
            expect(data.length).toEqual(1);
            expect(data[0].zone).toEqual(Zone.first);
            expect(data[0].type_transport).toEqual(TypeTransport.bus);
            expect(data[0].ticket_type).toEqual(TicketType.year);
            expect(data[0].passenger).toEqual(t.passenger);
            expect(data[0].id).toEqual(i);
            expect(data[0].expired).toEqual(false);
            expect(data[0].demographic_type).toEqual(DemographicTicketType.student);
            expect(data[0].date).toEqual(t.date);
            expect(data[0].cost).toEqual(1000.00);
            expect(data[0].code_transport).toEqual('kod');
            expect(data[0].block).toEqual(false);
            expect(data[0].active).toEqual(true);

        });

        const mockReq = httpMock.expectOne(dataService.ticketUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyTickets);

        httpMock.verify();
    }
    )
    );

    it('DeleteTicket() should get true', inject([HttpTestingController, TicketService], (
        httpMock: HttpTestingController,
        dataService: TicketService) => {

        let i : BigInteger = Uint8Array.of(1);
        let b: Ticket = new Ticket();
        b.id =  i;

        dataService.deleteTicket(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.ticketUrl+ 'deleteTicket'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: true}));

        httpMock.verify();
    }
    )
    );

    it('DeleteTicket() should get false-does not exist', inject([HttpTestingController, TicketService], (
        httpMock: HttpTestingController,
        dataService: TicketService) => {

        let i : BigInteger = Uint8Array.of(3);

        dataService.deleteTicket(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.ticketUrl+ 'deleteTicket'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: false}));

        httpMock.verify();
    }
    )
    );

    it('AddTicket() should get created subway', inject([HttpTestingController, TicketService], (
        httpMock: HttpTestingController,
        dataService: TicketService) => {

        let i : BigInteger = Uint8Array.of(1);

        let c: BuyTicketDTO = new BuyTicketDTO();
        c.code_transport = 'kod';
        c.date = new Date();
        c.demographic_type = 'student';
        c.idPassenger = i;
        c.ticket_type = 'year';
        c.type_transport = 'bus';
        c.zone = 'first';
        
        let t : Ticket = new Ticket();
        let p:Passenger = new Passenger();
        p.id = i;
        t.active = true;
        t.block = false;
        t.code_transport = 'kod';
        t.cost = 1000.00;
        t.date = new Date();
        t.demographic_type = DemographicTicketType.student;
        t.expired = false;
        t.id = i;
        t.passenger = p;
        t.ticket_type = TicketType.year;
        t.type_transport = TypeTransport.bus;
        t.zone = Zone.first;

        dataService.addTicket(c).subscribe(data => {
            expect(data).toEqual(t);
            expect(data.zone).toEqual(Zone.first);
            expect(data.type_transport).toEqual(TypeTransport.bus);
            expect(data.ticket_type).toEqual(TicketType.year);
            expect(data.passenger).toEqual(t.passenger);
            expect(data.id).toEqual(i);
            expect(data.expired).toEqual(false);
            expect(data.demographic_type).toEqual(DemographicTicketType.student);
            expect(data.date).toEqual(t.date);
            expect(data.cost).toEqual(1000.00);
            expect(data.code_transport).toEqual('kod');
            expect(data.block).toEqual(false);
            expect(data.active).toEqual(true);

        });

        const mockReq = httpMock.expectOne(dataService.ticketUrl + "addTicket");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(t);

        httpMock.verify();
    }
    )
    );

    it('Find user by id() should get all tickets from that user', inject([HttpTestingController, TicketService], (
        httpMock: HttpTestingController,
        dataService: TicketService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);

        let t : Ticket = new Ticket();
        let p:Passenger = new Passenger();
        p.id = i;
        t.active = true;
        t.block = false;
        t.code_transport = 'kod';
        t.cost = 1000.00;
        t.date = new Date();
        t.demographic_type = DemographicTicketType.student;
        t.expired = false;
        t.id = i;
        t.passenger = p;
        t.ticket_type = TicketType.year;
        t.type_transport = TypeTransport.bus;
        t.zone = Zone.first;

        let t2 : Ticket = new Ticket();
        t2.active = true;
        t2.block = false;
        t2.code_transport = 'kod';
        t2.cost = 1000.00;
        t2.date = new Date();
        t2.demographic_type = DemographicTicketType.student;
        t2.expired = false;
        t2.id = i2;
        t2.passenger = p;
        t2.ticket_type = TicketType.year;
        t2.type_transport = TypeTransport.bus;
        t2.zone = Zone.first;

        let list:Ticket[]=[];
        list.push(t);
        list.push(t2);

        dataService.findByUserID(i).subscribe(data => {
            expect(data).toEqual(list);
            expect(data.length).toEqual(2);
            expect(data[0].zone).toEqual(Zone.first);
            expect(data[0].type_transport).toEqual(TypeTransport.bus);
            expect(data[0].ticket_type).toEqual(TicketType.year);
            expect(data[0].passenger).toEqual(t.passenger);
            expect(data[0].id).toEqual(i);
            expect(data[0].expired).toEqual(false);
            expect(data[0].demographic_type).toEqual(DemographicTicketType.student);
            expect(data[0].date).toEqual(t.date);
            expect(data[0].cost).toEqual(1000.00);
            expect(data[0].code_transport).toEqual('kod');
            expect(data[0].block).toEqual(false);
            expect(data[0].active).toEqual(true);

        });

        const url = `${dataService.ticketUrl + 'findByUserID'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(list);

        httpMock.verify();
    }
    )
    );

    it('Activate ticket() should get all ticket that is active', inject([HttpTestingController, TicketService], (
        httpMock: HttpTestingController,
        dataService: TicketService) => {

        let i : BigInteger = Uint8Array.of(1);

        let t : Ticket = new Ticket();
        let p:Passenger = new Passenger();
        p.id = i;
        t.active = true;
        t.block = false;
        t.code_transport = 'kod';
        t.cost = 1000.00;
        t.date = new Date();
        t.demographic_type = DemographicTicketType.student;
        t.expired = false;
        t.id = i;
        t.passenger = p;
        t.ticket_type = TicketType.year;
        t.type_transport = TypeTransport.bus;
        t.zone = Zone.first;

        dataService.activate(i).subscribe(data => {
            expect(data).toEqual(t);
             expect(data.zone).toEqual(Zone.first);
            expect(data.type_transport).toEqual(TypeTransport.bus);
            expect(data.ticket_type).toEqual(TicketType.year);
            expect(data.passenger).toEqual(t.passenger);
            expect(data.id).toEqual(i);
            expect(data.expired).toEqual(false);
            expect(data.demographic_type).toEqual(DemographicTicketType.student);
            expect(data.date).toEqual(t.date);
            expect(data.cost).toEqual(1000.00);
            expect(data.code_transport).toEqual('kod');
            expect(data.block).toEqual(false);
            expect(data.active).toEqual(true);

        });

        const url = `${dataService.ticketUrl + 'activate'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(t);

        httpMock.verify();
    }
    )
    );
});