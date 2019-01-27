import { TestBed, inject } from "@angular/core/testing";
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { Line } from 'src/app/models/line.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TransportDTO } from 'src/app/models.dto/transport.dto';
import { AddLocationToTransportDTO } from 'src/app/models.dto/addLocationToTransportDTO.dto';
import { MyLocation } from 'src/app/models/location.model';
import { HttpResponse } from '@angular/common/http';
import { ChangeTransportDTO } from 'src/app/models.dto/changeTransport.dto';
import { Timetable } from 'src/app/models/timetable.model';
import { FilterSearchTransportDTO } from 'src/app/models.dto/filterSearchTransport.dto';


describe('BusService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [BusService]
        });
    });

    it('getBus() should get all buses', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {
        let b: Bus = new Bus();
        b.code = 'kod';
        b.deleted = false;
        b.late = false;
        b.line = new Line();
        b.location = null;
        b.name = 'Lasta';
        b.time_arrive = 5;
        b.timetable = null;
        let dummyBuses: Bus[] = [];
        dummyBuses.push(b);

        dataService.getBuses().subscribe(data => {
            expect(data).toEqual(dummyBuses);
            expect(data.length).toEqual(1);
            expect(data[0].code).toEqual('kod');
            expect(data[0].deleted).toEqual(false);
            expect(data[0].late).toEqual(false);
            expect(data[0].name).toEqual('Lasta');
            expect(data[0].time_arrive).toEqual(5);

        });

        const mockReq = httpMock.expectOne(dataService.busUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyBuses);

        httpMock.verify();
    }
    )
    );


    it('DeleteBus() should get true', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(1);
        let b: Bus = new Bus();
        b.id =  i;
        b.code = 'kod';
        b.deleted = false;
        b.late = false;
        b.line = new Line();
        b.location = null;
        b.name = 'Lasta';
        b.time_arrive = 5;
        b.timetable = null;

        dataService.deleteBus(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.busUrl+ 'deleteBus'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: true}));

        httpMock.verify();
    }
    )
    );

    it('DeleteBus() should get false-does not exist', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(3);

        dataService.deleteBus(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.busUrl+ 'deleteBus'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: false}));

        httpMock.verify();
    }
    )
    );

    it('AddBus() should get created bus', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(1);

        let newBus: TransportDTO = new TransportDTO();
        newBus.id_line = i;
        newBus.name = 'Novi bus';
        newBus.time_arrive = 5;

        let line: Line = new Line();
        line.name = '7ca';
        line.id = i;

        let resultBus: Bus = new Bus();
        resultBus.id =  i;
        resultBus.code = 'kod';
        resultBus.deleted = false;
        resultBus.late = false;
        resultBus.line = line;
        resultBus.location = null;
        resultBus.name = 'Novi bus';
        resultBus.time_arrive = 5;
        resultBus.timetable = null;


        dataService.addBus(newBus).subscribe(data => {
            expect(data).toEqual(resultBus);
            expect(data.code).toEqual('kod');
            expect(data.deleted).toEqual(false);
            expect(data.late).toEqual(false);
            expect(data.name).toEqual('Novi bus');
            expect(data.time_arrive).toEqual(5);
            expect(data.line.id).toEqual(i);
            expect(data.line.name).toEqual('7ca');

        });

        const mockReq = httpMock.expectOne(dataService.busUrl + "addBus");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(resultBus);

        httpMock.verify();
    }
    )
    );

    it('AddBus() line wrong-return null', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(45645);

        let newBus: TransportDTO = new TransportDTO();
        newBus.id_line = i;
        newBus.name = 'Novi bus';
        newBus.time_arrive = 5;

        dataService.addBus(newBus).subscribe(data => {
            expect(data).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.busUrl + "addBus");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(null);

        httpMock.verify();
    }
    )
    );

    it('AddLocation() to bus :return updated bus', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);

        let addLocation: AddLocationToTransportDTO = new AddLocationToTransportDTO(i,i2);
        let b: Bus = new Bus();
        b.location = new MyLocation();
        b.location.id = i2;
        b.location.address =  'Stari grad';
        b.location.deleted = false;
        b.location.latitude = 45.6;
        b.location.location_name = 'Stari grad,Centar';
        b.location.longitude = 44.6;
        b.location.type = 'transport';

        dataService.addLocation(addLocation).subscribe(data => {
            expect(data).toEqual(b);
            expect(data.location.address).toEqual(b.location.address);
            expect(data.location.deleted).toEqual(b.location.deleted);
            expect(data.location.id).toEqual(b.location.id);
            expect(data.location.latitude).toEqual(b.location.latitude);
            expect(data.location.location_name).toEqual(b.location.location_name);
            expect(data.location.longitude).toEqual(b.location.longitude);
            expect(data.location.type).toEqual(b.location.type);

        });

        const mockReq = httpMock.expectOne(dataService.busUrl + "addLocation");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(b);

        httpMock.verify();
    }
    )
    );

    it('updateBus() :return updated bus', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);

        let location : MyLocation = new MyLocation();
        location.id = i2;
        location.address =  'Stari grad';
        location.deleted = false;
        location.latitude = 45.6;
        location.location_name = 'Stari grad,Centar';
        location.longitude = 44.6;
        location.type = 'transport';

        let changeTransport : ChangeTransportDTO = new ChangeTransportDTO();
        changeTransport.id_transport = i;
        changeTransport.current_location = location;
        changeTransport.name = 'Izmenjeno ime';
        changeTransport.time_arrive = 7;
        changeTransport.timetable = new Timetable();

        let b: Bus = new Bus();
        b.id = i;
        b.location = location;
        b.name = 'Izmenjeno ime';
        b.time_arrive = 7;
        b.late = true;
        b.timetable = changeTransport.timetable;

        dataService.updateBus(changeTransport).subscribe(data => {
            expect(data).toEqual(b);
            expect(data.id).toEqual(changeTransport.id_transport);
            expect(data.name).toEqual(changeTransport.name);
            expect(data.time_arrive).toEqual(changeTransport.time_arrive);
            expect(data.timetable).toEqual(changeTransport.timetable);
            expect(data.location.address).toEqual(b.location.address);
            expect(data.location.deleted).toEqual(b.location.deleted);
            expect(data.location.id).toEqual(b.location.id);
            expect(data.location.latitude).toEqual(b.location.latitude);
            expect(data.location.location_name).toEqual(b.location.location_name);
            expect(data.location.longitude).toEqual(b.location.longitude);
            expect(data.location.type).toEqual(b.location.type);

        });

        const mockReq = httpMock.expectOne(dataService.busUrl + "updateBus");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(b);

        httpMock.verify();
    }
    )
    );

    it('updateBus()- wrong id :return null', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(1465363634);
        let i2 : BigInteger = Uint8Array.of(2);

        let location : MyLocation = new MyLocation();
        location.id = i2;
        location.address =  'Stari grad';
        location.deleted = false;
        location.latitude = 45.6;
        location.location_name = 'Stari grad,Centar';
        location.longitude = 44.6;
        location.type = 'transport';

        let changeTransport : ChangeTransportDTO = new ChangeTransportDTO();
        changeTransport.id_transport = i;
        changeTransport.current_location = location;
        changeTransport.name = 'Izmenjeno ime';
        changeTransport.time_arrive = 7;
        changeTransport.timetable = new Timetable();

        dataService.updateBus(changeTransport).subscribe(data => {
            expect(data).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.busUrl + "updateBus");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(null);

        httpMock.verify();
    }
    )
    );

    it('filterSearch()- return List<Bus>', inject([HttpTestingController, BusService], (
        httpMock: HttpTestingController,
        dataService: BusService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);
        
        let filterSearch: FilterSearchTransportDTO = new FilterSearchTransportDTO(i,true,i2,'la');

        let b: Bus = new Bus();
        b.code = 'kod';
        b.deleted = false;
        b.late = true;
        b.line = new Line();
        b.line.id = i;
        b.location = new MyLocation();
        b.location.id = i2;
        b.name = 'Lasta';

        let b2: Bus = new Bus();
        b2.code = 'kod';
        b2.deleted = false;
        b2.late = true;
        b2.line = new Line();
        b2.line.id = i;
        b2.location = new MyLocation();
        b2.location.id = i2;
        b2.name = 'Lala';
        let dummyBuses: Bus[] = [];
        dummyBuses.push(b);
        dummyBuses.push(b2);

        dataService.filterSearch(filterSearch).subscribe(data => {
            expect(data).toEqual(dummyBuses);
            expect(data.length).toEqual(2);
            expect(data[0].code).toEqual('kod');
            expect(data[0].deleted).toEqual(false);
            expect(data[0].late).toEqual(true);
            expect(data[0].name).toEqual('Lasta');
            expect(data[0].line.id).toEqual(i);
            expect(data[0].location.id).toEqual(i2);
        });

        const mockReq = httpMock.expectOne(dataService.busUrl + "searchFilter");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(dummyBuses);

        httpMock.verify();
    }
    )
    );

});

