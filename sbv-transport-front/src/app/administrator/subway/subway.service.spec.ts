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


describe('SubwayService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [SubwayService]
        });
    });

    it('getSubways() should get all subways', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {
        let s: Subway = new Subway();
        s.code = 'kod';
        s.late = false;
        s.line = new Line();
        s.location = null;
        s.name = 'Lasta';
        s.time_arrive = 5;
        s.timetable = null;
        let dummySubways: Subway[] = [];
        dummySubways.push(s);

        dataService.getSubways().subscribe(data => {
            expect(data).toEqual(dummySubways);
            expect(data.length).toEqual(1);
            expect(data[0].code).toEqual('kod');
            expect(data[0].late).toEqual(false);
            expect(data[0].name).toEqual('Lasta');
            expect(data[0].time_arrive).toEqual(5);

        });

        const mockReq = httpMock.expectOne(dataService.subwayUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummySubways);

        httpMock.verify();
    }
    )
    );


    it('DeleteSubway() should get true', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

        let i : BigInteger = Uint8Array.of(1);
        let b: Subway = new Subway();
        b.id =  i;
        b.code = 'kod';
        b.late = false;
        b.line = new Line();
        b.location = null;
        b.name = 'Lasta';
        b.time_arrive = 5;
        b.timetable = null;

        dataService.deleteSubway(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.subwayUrl+ 'deleteSubway'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: true}));

        httpMock.verify();
    }
    )
    );

    it('DeleteSubway() should get false-does not exist', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

        let i : BigInteger = Uint8Array.of(3);

        dataService.deleteSubway(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.subwayUrl+ 'deleteSubway'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: false}));

        httpMock.verify();
    }
    )
    );

    it('AddSubway() should get created subway', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

        let i : BigInteger = Uint8Array.of(1);

        let newSubway: TransportDTO = new TransportDTO();
        newSubway.id_line = i;
        newSubway.name = 'Novi';
        newSubway.time_arrive = 5;

        let line: Line = new Line();
        line.name = '7ca';
        line.id = i;

        let resultSubway: Subway = new Subway();
        resultSubway.id =  i;
        resultSubway.code = 'kod';
        resultSubway.late = false;
        resultSubway.line = line;
        resultSubway.location = null;
        resultSubway.name = 'Novi';
        resultSubway.time_arrive = 5;
        resultSubway.timetable = null;


        dataService.addSubway(newSubway).subscribe(data => {
            expect(data).toEqual(resultSubway);
            expect(data.code).toEqual('kod');
            expect(data.late).toEqual(false);
            expect(data.name).toEqual('Novi');
            expect(data.time_arrive).toEqual(5);
            expect(data.line.id).toEqual(i);
            expect(data.line.name).toEqual('7ca');

        });

        const mockReq = httpMock.expectOne(dataService.subwayUrl + "addSubway");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(resultSubway);

        httpMock.verify();
    }
    )
    );

    it('AddSubway() line wrong-return null', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

        let i : BigInteger = Uint8Array.of(45645);

        let newSubway: TransportDTO = new TransportDTO();
        newSubway.id_line = i;
        newSubway.name = 'Novi';
        newSubway.time_arrive = 5;

        dataService.addSubway(newSubway).subscribe(data => {
            expect(data).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.subwayUrl + "addSubway");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(null);

        httpMock.verify();
    }
    )
    );

    it('AddLocation() to subway :return updated subway', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);

        let addLocation: AddLocationToTransportDTO = new AddLocationToTransportDTO(i,i2);
        let b: Subway = new Subway();
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

        const mockReq = httpMock.expectOne(dataService.subwayUrl + "addLocation");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(b);

        httpMock.verify();
    }
    )
    );

    it('updateSubway() :return updated subway', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

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

        let b: Subway = new Subway();
        b.id = i;
        b.location = location;
        b.name = 'Izmenjeno ime';
        b.time_arrive = 7;
        b.late = true;
        b.timetable = changeTransport.timetable;

        dataService.updateSubway(changeTransport).subscribe(data => {
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

        const mockReq = httpMock.expectOne(dataService.subwayUrl + "updateSubway");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(b);

        httpMock.verify();
    }
    )
    );

    it('updateSubway()- wrong id :return null', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

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

        dataService.updateSubway(changeTransport).subscribe(data => {
            expect(data).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.subwayUrl + "updateSubway");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(null);

        httpMock.verify();
    }
    )
    );

    it('filterSearch()- return List<Subway>', inject([HttpTestingController, SubwayService], (
        httpMock: HttpTestingController,
        dataService: SubwayService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);
        
        let filterSearch: FilterSearchTransportDTO = new FilterSearchTransportDTO(i,true,i2,'la');

        let b: Subway = new Subway();
        b.code = 'kod';
        b.late = true;
        b.line = new Line();
        b.line.id = i;
        b.location = new MyLocation();
        b.location.id = i2;
        b.name = 'Lasta';

        let b2: Subway = new Subway();
        b2.code = 'kod';
        b2.late = true;
        b2.line = new Line();
        b2.line.id = i;
        b2.location = new MyLocation();
        b2.location.id = i2;
        b2.name = 'Lala';
        let dummySubways: Subway[] = [];
        dummySubways.push(b);
        dummySubways.push(b2);

        dataService.filterSearch(filterSearch).subscribe(data => {
            expect(data).toEqual(dummySubways);
            expect(data.length).toEqual(2);
            expect(data[0].code).toEqual('kod');
            expect(data[0].late).toEqual(true);
            expect(data[0].name).toEqual('Lasta');
            expect(data[0].line.id).toEqual(i);
            expect(data[0].location.id).toEqual(i2);
        });

        const mockReq = httpMock.expectOne(dataService.subwayUrl + "searchFilter");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(dummySubways);

        httpMock.verify();
    }
    )
    );

});

