import { TestBed, inject } from "@angular/core/testing";
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { Line } from 'src/app/models/line.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TimetableService } from 'src/app/services/timetable.service';
import { AltTimetableDTO } from 'src/app/models.dto/timetable.dto';
import { Timetable } from 'src/app/models/timetable.model';
import { Schedule } from 'src/app/models/schedule.model';
import { Station } from 'src/app/models/station.model';

describe('TimetableService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [TimetableService]
        });
    });

    it('addTimetable() should get created timetable', inject([HttpTestingController, TimetableService], (
        httpMock: HttpTestingController,
        dataService: TimetableService) => {
        
        let i : BigInteger = Uint8Array.of(1);

        let t: AltTimetableDTO = new AltTimetableDTO();
        t.id_transport =i;
        t.timetable = [];
        t.transportType = 'bus';

        let s: Set<Schedule> = new Set<Schedule>();
        let timetable:Timetable = new Timetable();
        timetable.code = 'kod';
        timetable.id = i;
        timetable.schedule = s;

        dataService.addTimetable(t).subscribe(data => {
            expect(data).toEqual(timetable);
            expect(data.code).toEqual('kod');
            expect(data.id).toEqual(i);
            expect(data.schedule).toEqual(s);
            

        });

        const mockReq = httpMock.expectOne(dataService.timetableUrl + 'addAltTimetable');

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(timetable);

        httpMock.verify();
    }
    )
    );
});