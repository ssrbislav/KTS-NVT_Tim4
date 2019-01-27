import { TestBed } from "@angular/core/testing";
import {BaseRequestOptions, ConnectionBackend, Http, RequestOptions} from '@angular/http';
import {Response, ResponseOptions, RequestMethod} from '@angular/http';
import {async, fakeAsync, tick} from '@angular/core/testing';
import {MockBackend} from '@angular/http/testing';
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/models/bus.model';
import { Line } from 'src/app/models/line.model';
import { MyLocation } from 'src/app/models/location.model';
import { Timetable } from 'src/app/models/timetable.model';


describe('BusService', () => {

    beforeEach(() => {

        TestBed.configureTestingModule({
           providers:    [ 
              {provide: ConnectionBackend, useClass: MockBackend},
              {provide: RequestOptions, useClass: BaseRequestOptions},
              Http,
              BusService]
        });
        
        this.busService = TestBed.get(BusService);
        this.backend = TestBed.get(ConnectionBackend);
        this.backend.connections.subscribe((connection: any) => 
           this.lastConnection = connection);
    });

    it('should pass simple test', () => {
	    expect(true).toBe(true);
    });
    
    it('getBuses() should query current service url', () => {
        this.busService.getBuses();
        expect(this.lastConnection).toBeDefined('no http service connection at all?');
        expect(this.lastConnection.request.url).toMatch(/api\/bus$/, 'url invalid');
    });
    
    it('getBuses() should return some buses', fakeAsync(() => {
        let buses: Bus[];
        let line:Line = new Line();
        let myLocation: MyLocation = new MyLocation();
        let timetable: Timetable = new Timetable();
        this.busService.getBuses().then((data: Bus[]) => buses = data);
        this.lastConnection.mockRespond(new Response(new ResponseOptions({
            body: JSON.stringify([
                {
                    id: 1,
                    code: 'neki kod',
                    line: line,
                    late: false,
                    name: 'Lasta',
                    location: myLocation,
                    timetable: timetable,
                    time_arrive: 5,
                    deleted: false            
                },
             {
                id: 2,
                code:'neki kod2',
                line: line,
                late: false,
                name: 'Lasta',
                location: myLocation,
                timetable: timetable,
                time_arrive: 5,
                deleted: false                
             }])
          })));
        tick();
        expect(buses.length).toEqual(2, 'should contain given amount of buses');
        //expect(buses[0].id).toEqual(1);
        expect(buses[0].code).toEqual('neki kod');
        expect(buses[0].name).toEqual('Lasta');
          
     }));

    });
   
