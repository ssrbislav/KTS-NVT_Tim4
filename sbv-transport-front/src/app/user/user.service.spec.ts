import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LocationService } from 'src/app/services/location.service';
import { MyLocation } from 'src/app/models/location.model';
import { HttpResponse } from '@angular/common/http';
import { LocationDTO } from 'src/app/models.dto/location.dto';
import { PassengerService } from '../services/passenger.service';
import { Passenger } from '../models/passenger.model';

describe('PassengerService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [PassengerService]
        });
    });

    it('getPassengers() should get all passengers', inject([HttpTestingController, PassengerService], (
        httpMock: HttpTestingController,
        dataService: PassengerService) => {

        let i: BigInteger = Uint8Array.of(1);

        let p: Passenger = new Passenger();
        p.active = true;
        p.address = 'Balzakova 28';
        p.date_birth = new Date();
        p.deleted = false;
        p.email = 'putnik@gmail.com';
        p.first_name = 'Bojana';
        p.id = i;
        p.last_name = 'Corilic';
        p.password = 'putnik';
        p.phone_number = '43246523462';

        let listP: Passenger[] = [];
        listP.push(p);

        dataService.getPassengers().subscribe(data => {
            expect(data).toEqual(listP);
            expect(data.length).toEqual(1);
            expect(data[0].active).toEqual(true);
            expect(data[0].address).toEqual('Balzakova 28');
            expect(data[0].date_birth).toEqual(p.date_birth);
            expect(data[0].deleted).toEqual(false);
            expect(data[0].email).toEqual('putnik@gmail.com');
            expect(data[0].first_name).toEqual('Bojana');
            expect(data[0].id).toEqual(i);
            expect(data[0].last_name).toEqual('Corilic');
            expect(data[0].password).toEqual('putnik');
            expect(data[0].phone_number).toEqual('43246523462');


        });

        const mockReq = httpMock.expectOne(dataService.passengerUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(listP);

        httpMock.verify();
    }
    )
    );

    it('getPassenger() should get one passenger by usrename', inject([HttpTestingController, PassengerService], (
        httpMock: HttpTestingController,
        dataService: PassengerService) => {
        
        let i : BigInteger = Uint8Array.of(1);

        let p: Passenger = new Passenger();
        p.active = true;
        p.address = 'Balzakova 28';
        p.date_birth = new Date();
        p.deleted = false;
        p.email = 'putnik@gmail.com';
        p.first_name = 'Bojana';
        p.id = i;
        p.last_name = 'Corilic';
        p.password = 'putnik';
        p.phone_number = '43246523462';
        p.username = 'putnik';

        dataService.getPassenger('putnik').subscribe(data => {
            expect(data).toEqual(p);
            expect(data.active).toEqual(true);
            expect(data.address).toEqual('Balzakova 28');
            expect(data.date_birth).toEqual(p.date_birth);
            expect(data.deleted).toEqual(false);
            expect(data.email).toEqual('putnik@gmail.com');
            expect(data.first_name).toEqual('Bojana');
            expect(data.id).toEqual(i);
            expect(data.last_name).toEqual('Corilic');
            expect(data.password).toEqual('putnik');
            expect(data.phone_number).toEqual('43246523462');
            expect(data.username).toEqual('putnik');

        });

        const url = `${dataService.passengerUrl + 'getPassenger'}/${p.username}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(p);

        httpMock.verify();
    }
    )
    );

    it('DeletePassenger() should get true', inject([HttpTestingController, PassengerService], (
        httpMock: HttpTestingController,
        dataService: PassengerService) => {

        let i : BigInteger = Uint8Array.of(1);

        dataService.deletePassenger(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.passengerUrl+ 'deletePassenger'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: true}));

        httpMock.verify();
    }
    )
    );

    it('DeletePassenger() should get false-does not exist', inject([HttpTestingController, PassengerService], (
        httpMock: HttpTestingController,
        dataService: PassengerService) => {

        let i : BigInteger = Uint8Array.of(3);

        dataService.deletePassenger(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.passengerUrl+ 'deletePassenger'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: false}));

        httpMock.verify();
    }
    )
    );

    it('addPassenger() should get created passenger', inject([HttpTestingController, PassengerService], (
        httpMock: HttpTestingController,
        dataService: PassengerService) => {
        
        let i : BigInteger = Uint8Array.of(1);

        let p: Passenger = new Passenger();
        p.active = true;
        p.address = 'Balzakova 28';
        p.date_birth = new Date();
        p.deleted = false;
        p.email = 'putnik@gmail.com';
        p.first_name = 'Bojana';
        p.id = i;
        p.last_name = 'Corilic';
        p.password = 'putnik';
        p.phone_number = '43246523462';
        p.username = 'putnik';

        dataService.addPassenger(p).subscribe(data => {
            expect(data).toEqual(p);
            expect(data.active).toEqual(true);
            expect(data.address).toEqual('Balzakova 28');
            expect(data.date_birth).toEqual(p.date_birth);
            expect(data.deleted).toEqual(false);
            expect(data.email).toEqual('putnik@gmail.com');
            expect(data.first_name).toEqual('Bojana');
            expect(data.id).toEqual(i);
            expect(data.last_name).toEqual('Corilic');
            expect(data.password).toEqual('putnik');
            expect(data.phone_number).toEqual('43246523462');
            expect(data.username).toEqual('putnik');

        });

        const mockReq = httpMock.expectOne(dataService.passengerUrl + 'addPassenger');

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(p);

        httpMock.verify();
    }
    )
    );

    it('updatePassenger() should get updated passenger', inject([HttpTestingController, PassengerService], (
        httpMock: HttpTestingController,
        dataService: PassengerService) => {
        
        let i : BigInteger = Uint8Array.of(1);

        let p: Passenger = new Passenger();
        p.active = true;
        p.address = 'Balzakova 28';
        p.date_birth = new Date();
        p.deleted = false;
        p.email = 'putnik@gmail.com';
        p.first_name = 'Bojana';
        p.id = i;
        p.last_name = 'Corilic';
        p.password = 'putnik';
        p.phone_number = '43246523462';
        p.username = 'putnik';

        dataService.updatePassenger(p, i).subscribe(data => {
            expect(data).toEqual(p);
            expect(data.active).toEqual(true);
            expect(data.address).toEqual('Balzakova 28');
            expect(data.date_birth).toEqual(p.date_birth);
            expect(data.deleted).toEqual(false);
            expect(data.email).toEqual('putnik@gmail.com');
            expect(data.first_name).toEqual('Bojana');
            expect(data.id).toEqual(i);
            expect(data.last_name).toEqual('Corilic');
            expect(data.password).toEqual('putnik');
            expect(data.phone_number).toEqual('43246523462');
            expect(data.username).toEqual('putnik');

        });

        const url = `${dataService.passengerUrl + 'updatePassenger'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(p);

        httpMock.verify();
    }
    )
    );


});