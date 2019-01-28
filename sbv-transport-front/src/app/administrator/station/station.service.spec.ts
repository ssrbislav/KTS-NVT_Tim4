import { TestBed, inject } from "@angular/core/testing";
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { Line } from 'src/app/models/line.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';
import { MyLocation } from 'src/app/models/location.model';
import { HttpResponse } from '@angular/common/http';
import { StationDTO } from 'src/app/models.dto/station.dto';
import { changeStationDTO } from 'src/app/models.dto/changeStation.dto';
import { FilterSearchStationDTO } from 'src/app/models.dto/filterSearchStation.dto';

describe('StationService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [StationService]
        });
    });

    enum Zone{
        first,second
    }

    it('getStations() should get all stations', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {
        
        let i: BigInteger = Uint8Array.of(1);

        let s: Station = new Station();
        s.deleted = false;
        s.id = i;
        s.location = new MyLocation();
        s.zone = Zone.first;
        let dummyStations: Station[] = [];
        dummyStations.push(s);

        dataService.getStations().subscribe(data => {
            expect(data).toEqual(dummyStations);
            expect(data.length).toEqual(1);
            expect(data[0].id).toEqual(i);
            expect(data[0].deleted).toEqual(false);
            expect(data[0].location).toEqual(s.location);
            expect(data[0].zone).toEqual(Zone.first);

        });

        const mockReq = httpMock.expectOne(dataService.stationUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyStations);

        httpMock.verify();
    }
    )
    );

    it('DeleteStation() should get true', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(1);
        let b: Bus = new Bus();
        b.id =  i;
     
        dataService.deleteStation(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.stationUrl+ 'deleteStation'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: true}));

        httpMock.verify();
    }
    )
    );

    it('DeleteStation() should get false-does not exist', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(3);

        dataService.deleteStation(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.stationUrl+ 'deleteStation'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: false}));

        httpMock.verify();
    }
    )
    );

    it('getStation() should get station by id', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(3);
        let s: Station = new Station();
        s.deleted = false;
        s.id = i;
        s.location = new MyLocation();
        s.zone = Zone.first;

        dataService.getStation(i).subscribe(data => {
            expect(data).toEqual(s);
            expect(data.id).toEqual(i);
            expect(data.deleted).toEqual(false);
            expect(data.location).toEqual(s.location);
            expect(data.zone).toEqual(Zone.first);

        });

        const url = `${dataService.stationUrl+ 'getStation'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(s);

        httpMock.verify();
    }
    )
    );

    it('AddStation() should get created station', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(1);

        let createStation: StationDTO = new StationDTO();
        createStation.zone = 'first';
        createStation.location_id = i;

        let l: MyLocation = new MyLocation();
        l.id = i;
        let s: Station = new Station();
        s.deleted = false;
        s.id = i;
        s.location = l;
        s.zone = Zone.first;


        dataService.addStation(createStation).subscribe(data => {
            expect(data).toEqual(s);
            expect(data.id).toEqual(i);
            expect(data.deleted).toEqual(false);
            expect(data.location).toEqual(s.location);
            expect(data.zone).toEqual(Zone.first);

        });

        const mockReq = httpMock.expectOne(dataService.stationUrl + "addStation");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(s);

        httpMock.verify();
    }
    )
    );

    it('AddStation() location wrong-return null', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(45645);

        let createStation: StationDTO = new StationDTO();
        createStation.zone = 'first';
        createStation.location_id = null;

        dataService.addStation(createStation).subscribe(data => {
            expect(data).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.stationUrl + "addStation");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(null);

        httpMock.verify();
    }
    )
    );

    it('updateStation() should get updated station', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(1);

        let updateS: changeStationDTO = new changeStationDTO();
        updateS.id_station = i;
        updateS.location_name = 'novo';
        updateS.zone = Zone.second;

        let l: MyLocation = new MyLocation();
        l.id = i;
        l.location_name = 'novo';
        let s: Station = new Station();
        s.deleted = false;
        s.id = i;
        s.location = l;
        s.zone = Zone.first;


        dataService.updateStation(updateS).subscribe(data => {
            expect(data).toEqual(s);
            expect(data.id).toEqual(i);
            expect(data.deleted).toEqual(false);
            expect(data.location.id).toEqual(i);
            expect(data.location.location_name).toEqual('novo');
            expect(data.zone).toEqual(Zone.first);

        });

        const mockReq = httpMock.expectOne(dataService.stationUrl + "updateStation");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(s);

        httpMock.verify();
    }
    )
    );

    it('filterSearch()- return List<Station>', inject([HttpTestingController, StationService], (
        httpMock: HttpTestingController,
        dataService: StationService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);
        
        let filterSearch: FilterSearchStationDTO = new FilterSearchStationDTO('first','n');
       
        let l: MyLocation = new MyLocation();
        l.id = i;
        l.location_name = 'novo';
        let s: Station = new Station();
        s.deleted = false;
        s.id = i;
        s.location = l;
        s.zone = Zone.first;
        let dummyStations: Station[] = [];
        dummyStations.push(s);

        dataService.filterSearch(filterSearch).subscribe(data => {
            expect(data).toEqual(dummyStations);
            expect(data.length).toEqual(1);
            expect(data[0].id).toEqual(i);
            expect(data[0].deleted).toEqual(false);
            expect(data[0].location.id).toEqual(i);
            expect(data[0].location.location_name).toEqual('novo');
            expect(data[0].zone).toEqual(Zone.first);
        });

        const mockReq = httpMock.expectOne(dataService.stationUrl + "searchFilter");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(dummyStations);

        httpMock.verify();
    }
    )
    );



});