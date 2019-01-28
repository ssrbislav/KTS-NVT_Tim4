import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LocationService } from 'src/app/services/location.service';
import { MyLocation } from 'src/app/models/location.model';
import { HttpResponse } from '@angular/common/http';
import { LocationDTO } from 'src/app/models.dto/location.dto';

describe('LocationService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [LocationService]
        });
    });

    it('getLocations() should get all locations', inject([HttpTestingController, LocationService], (
        httpMock: HttpTestingController,
        dataService: LocationService) => {

        let i: BigInteger = Uint8Array.of(1);

        let l: MyLocation = new MyLocation();
        l.address = 'Balzakova 28';
        l.deleted = false;
        l.id = i;
        l.latitude = 56.67;
        l.location_name = 'Liman 4';
        l.longitude = 67.82;
        l.type = 'station';
        let dummyLocations: MyLocation[] = [];
        dummyLocations.push(l);

        dataService.getLocations().subscribe(data => {
            expect(data).toEqual(dummyLocations);
            expect(data.length).toEqual(1);
            expect(data[0].type).toEqual('station');
            expect(data[0].longitude).toEqual(67.82);
            expect(data[0].location_name).toEqual('Liman 4');
            expect(data[0].latitude).toEqual(56.67);
            expect(data[0].id).toEqual(i);
            expect(data[0].deleted).toEqual(false);
            expect(data[0].address).toEqual('Balzakova 28');

        });

        const mockReq = httpMock.expectOne(dataService.locationUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyLocations);

        httpMock.verify();
    }
    )
    );

    it('DeleteLocation() should get true', inject([HttpTestingController, LocationService], (
        httpMock: HttpTestingController,
        dataService: LocationService) => {

        let i: BigInteger = Uint8Array.of(1);

        let l: MyLocation = new MyLocation();
        l.address = 'Balzakova 28';
        l.deleted = false;
        l.id = i;
        l.latitude = 56.67;
        l.location_name = 'Liman 4';
        l.longitude = 67.82;
        l.type = 'station';

        dataService.deleteLocation(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.locationUrl + 'deleteLocation'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();


        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({ body: true }));

        httpMock.verify();
    }
    )
    );

    it('DeleteLocation() should get false-does not exist', inject([HttpTestingController, LocationService], (
        httpMock: HttpTestingController,
        dataService: LocationService) => {

        let i: BigInteger = Uint8Array.of(3);

        dataService.deleteLocation(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.locationUrl + 'deleteLocation'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();


        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({ body: false }));

        httpMock.verify();
    }
    )
    );

    it('AddLocation() should get created location', inject([HttpTestingController, LocationService], (
        httpMock: HttpTestingController,
        dataService: LocationService) => {

        let i: BigInteger = Uint8Array.of(1);

        let l: LocationDTO = new LocationDTO();
        l.address = 'Nova adresa';
        l.latitude = 54.78;
        l.location_name = 'Nova lokacija';
        l.longitude = 59.56;
        l.type = 'station';

        let l2: MyLocation = new MyLocation();
        l2.address = 'Nova adresa';
        l2.deleted = false;
        l2.id = i;
        l2.latitude = 54.78;
        l2.location_name = 'Nova lokacija';
        l2.longitude = 59.56;
        l2.type = 'station';

        dataService.addLocation(l).subscribe(data => {
            expect(data).toEqual(l2);
            expect(data.type).toEqual('station');
            expect(data.longitude).toEqual(59.56);
            expect(data.location_name).toEqual('Nova lokacija');
            expect(data.latitude).toEqual(54.78);
            expect(data.id).toEqual(i);
            expect(data.deleted).toEqual(false);
            expect(data.address).toEqual('Nova adresa');

        });

        const mockReq = httpMock.expectOne(dataService.locationUrl + 'addLocation');
        expect(mockReq.cancelled).toBeFalsy();


        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(l2);

        httpMock.verify();
    }
    )
    );

});