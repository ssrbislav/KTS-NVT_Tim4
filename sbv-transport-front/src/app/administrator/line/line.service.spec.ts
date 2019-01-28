import { TestBed, inject } from "@angular/core/testing";
import { Line } from 'src/app/models/line.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LineService } from 'src/app/services/line.service';
import { Station } from 'src/app/models/station.model';
import { HttpResponse } from '@angular/common/http';
import { LineDTO } from 'src/app/models.dto/line.dto';
import { AddStationToList } from 'src/app/models.dto/addStationToList.dto';
import { FilterSearchLineDTO } from 'src/app/models.dto/filterSearchLine.dto';

describe('LineService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [LineService]
        });
    });

    enum Zone {
        first, second
    }

    enum TypeTransport {
        bus, subway, trolley
    }

    it('getLines() should get all lines', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i: BigInteger = Uint8Array.of(1);

        let i2: BigInteger = Uint8Array.of(2);

        let l: Line = new Line();
        l.first_station = i2;
        l.id = i;
        l.line_type = TypeTransport.bus;
        l.name = 'lasta';
        l.station_list = [];
        l.timetable = null;
        l.zone = Zone.first;

        let dummyLines: Line[] = [];
        dummyLines.push(l);

        dataService.getLines().subscribe(data => {
            expect(data).toEqual(dummyLines);
            expect(data.length).toEqual(1);
            expect(data[0].zone).toEqual(Zone.first);
            expect(data[0].timetable).toEqual(null);
            expect(data[0].station_list).toEqual(l.station_list);
            expect(data[0].name).toEqual('lasta');
            expect(data[0].line_type).toEqual(TypeTransport.bus);
            expect(data[0].id).toEqual(i);
            expect(data[0].first_station).toEqual(i2);

        });

        const mockReq = httpMock.expectOne(dataService.lineUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyLines);

        httpMock.verify();
    }
    )
    );

    it('DeleteLine() should get true', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i: BigInteger = Uint8Array.of(1);
        let b: Line = new Line();
        b.id = i;

        dataService.deleteLine(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.lineUrl + 'deleteLine'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();


        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({ body: true }));

        httpMock.verify();
    }
    )
    );

    it('DeleteLine() should get false-does not exist', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i: BigInteger = Uint8Array.of(3);

        dataService.deleteLine(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.lineUrl + 'deleteLine'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();


        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({ body: false }));

        httpMock.verify();
    }
    )
    );

    it('getLine() should get one line by id', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i: BigInteger = Uint8Array.of(1);

        let i2: BigInteger = Uint8Array.of(2);

        let l: Line = new Line();
        l.first_station = i2;
        l.id = i;
        l.line_type = TypeTransport.bus;
        l.name = 'lasta';
        l.station_list = [];
        l.timetable = null;
        l.zone = Zone.first;

        dataService.getLine(i).subscribe(data => {
            expect(data).toEqual(l);
            expect(data.zone).toEqual(Zone.first);
            expect(data.timetable).toEqual(null);
            expect(data.station_list).toEqual(l.station_list);
            expect(data.name).toEqual('lasta');
            expect(data.line_type).toEqual(TypeTransport.bus);
            expect(data.id).toEqual(i);
            expect(data.first_station).toEqual(i2);
        });

        const url = `${dataService.lineUrl + 'getLine'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(l);

        httpMock.verify();
    }
    )
    );

    it('AddLine() should get created line', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i : BigInteger = Uint8Array.of(1);

        let newLine : LineDTO = new LineDTO();
        newLine.line_type = 'bus';
        newLine.name = '8ca';
        newLine.zone = Zone.first;

        let l : Line = new Line();
        l.first_station = null;
        l.id = i;
        l.line_type = TypeTransport.bus;
        l.name = '8ca';
        l.station_list = [];
        l.timetable = null;
        l.zone = Zone.first;


        dataService.addLine(newLine).subscribe(data => {
            expect(data).toEqual(l);
            expect(data.zone).toEqual(Zone.first);
            expect(data.timetable).toEqual(null);
            expect(data.station_list).toEqual(l.station_list);
            expect(data.name).toEqual('8ca');
            expect(data.line_type).toEqual(TypeTransport.bus);
            expect(data.id).toEqual(i);
            expect(data.first_station).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.lineUrl + "addLine");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(l);

        httpMock.verify();
    }
    )
    );

    it('AddLine() wrong type transport-return null', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i : BigInteger = Uint8Array.of(1);

        let newLine : LineDTO = new LineDTO();
        newLine.line_type = '';
        newLine.name = '8ca';
        newLine.zone = Zone.first;

        dataService.addLine(newLine).subscribe(data => {
            expect(data).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.lineUrl + "addLine");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(null);

        httpMock.verify();
    }
    )
    );

    it('update Line() return update line', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let idStation1 : BigInteger = Uint8Array.of(1);
        let idStation2 : BigInteger = Uint8Array.of(2);
        let idLine : BigInteger = Uint8Array.of(2);

        let adStation1: AddStationToList = new AddStationToList(idStation1,idLine);
        let adStation2: AddStationToList = new AddStationToList(idStation2,idLine);
        let listStation: AddStationToList[] = [];
        listStation.push(adStation1);
        listStation.push(adStation2);

        let s1: Station = new Station();
        s1.id= idStation1;
        let s2: Station = new Station();
        s2.id= idStation2;
        let allList: Station[] = [];
        allList.push(s1);
        allList.push(s2);

        let l : Line = new Line();
        l.first_station = null;
        l.id = idLine;
        l.line_type = TypeTransport.bus;
        l.name = '8ca';
        l.station_list = allList;
        l.timetable = null;
        l.zone = Zone.first;

        dataService.updateLine(listStation).subscribe(data => {
            expect(data).toEqual(l);
            expect(data.zone).toEqual(Zone.first);
            expect(data.timetable).toEqual(null);
            expect(data.station_list).toEqual(l.station_list);
            expect(data.name).toEqual('8ca');
            expect(data.line_type).toEqual(TypeTransport.bus);
            expect(data.id).toEqual(idLine);
            expect(data.first_station).toEqual(null);

        });

        const mockReq = httpMock.expectOne(dataService.lineUrl + "updateLine");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(l);

        httpMock.verify();
    }
    )
    );

    it('filterSearch()- return List<Line>', inject([HttpTestingController, LineService], (
        httpMock: HttpTestingController,
        dataService: LineService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);
        
        let filterSearch: FilterSearchLineDTO = new FilterSearchLineDTO('first','bus',null,'l');

        let l: Line = new Line();
        l.first_station = null;
        l.id = i;
        l.line_type = TypeTransport.bus;
        l.name = 'lasta';
        l.station_list = [];
        l.timetable = null;
        l.zone = Zone.first;

        let l2: Line = new Line();
        l2.first_station = null;
        l2.id = i2;
        l2.line_type = TypeTransport.bus;
        l2.name = 'lala';
        l2.station_list = [];
        l2.timetable = null;
        l2.zone = Zone.first;

        let dummyLines: Line[] = [];
        dummyLines.push(l);
        dummyLines.push(l2);

        dataService.filterSearch(filterSearch).subscribe(data => {
            expect(data).toEqual(dummyLines);
            expect(data.length).toEqual(2);
            expect(data[0].zone).toEqual(Zone.first);
            expect(data[0].timetable).toEqual(null);
            expect(data[0].station_list).toEqual(l.station_list);
            expect(data[0].name).toEqual('lasta');
            expect(data[0].line_type).toEqual(TypeTransport.bus);
            expect(data[0].id).toEqual(i);
            expect(data[0].first_station).toEqual(null);
        });

        const mockReq = httpMock.expectOne(dataService.lineUrl + "searchFilter");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(dummyLines);

        httpMock.verify();
    }
    )
    );



});