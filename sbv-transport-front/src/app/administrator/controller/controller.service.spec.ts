import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ControllerService } from 'src/app/services/controller.service';
import { Controller } from 'src/app/models/controller.model';
import { HttpResponse } from '@angular/common/http';
import { FilterSearchControllerDTO } from 'src/app/models.dto/filterSearchController.dto';

describe('ControllerService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [ControllerService]
        });
    });

    it('getControllers() should get all controllers', inject([HttpTestingController, ControllerService], (
        httpMock: HttpTestingController,
        dataService: ControllerService) => {
        
        let i : BigInteger = Uint8Array.of(1);

        let s: Controller = new Controller();
        s.address = 'Balzakova 28';
        s.date_birth = new Date();
        s.deleted = false; 
        s.email = 'bojanacrlc@gmail.com';
        s.first_name = 'Bojana';
        s.id = i;
        s.last_name = 'Corilic';
        s.password = 'lozinka';
        s.phone_number = '43353254253';
        s.username = 'boka'
        let dummyControllers : Controller[] = [];
        dummyControllers.push(s);

        dataService.getControllers().subscribe(data => {
            expect(data).toEqual(dummyControllers);
            expect(data[0].address).toEqual(s.address);
            expect(data[0].date_birth).toEqual(s.date_birth);
            expect(data[0].deleted).toEqual(s.deleted);
            expect(data[0].email).toEqual(s.email);
            expect(data[0].first_name).toEqual(s.first_name);
            expect(data[0].id).toEqual(s.id);
            expect(data[0].phone_number).toEqual(s.phone_number);
            expect(data[0].username).toEqual(s.username);
            expect(data[0].last_name).toEqual(s.last_name);
            expect(data[0].password).toEqual(s.password);

        });

        const mockReq = httpMock.expectOne(dataService.controllerUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyControllers);

        httpMock.verify();
    }
    )
    );

    it('getController() should get one controllers by usrename', inject([HttpTestingController, ControllerService], (
        httpMock: HttpTestingController,
        dataService: ControllerService) => {
        
        let i : BigInteger = Uint8Array.of(1);

        let s: Controller = new Controller();
        s.address = 'Balzakova 28';
        s.date_birth = new Date();
        s.deleted = false; 
        s.email = 'bojanacrlc@gmail.com';
        s.first_name = 'Bojana';
        s.id = i;
        s.last_name = 'Corilic';
        s.password = 'lozinka';
        s.phone_number = '43353254253';
        s.username = 'boka'

        dataService.getController('boka').subscribe(data => {
            expect(data).toEqual(s);
            expect(data.address).toEqual(s.address);
            expect(data.date_birth).toEqual(s.date_birth);
            expect(data.deleted).toEqual(s.deleted);
            expect(data.email).toEqual(s.email);
            expect(data.first_name).toEqual(s.first_name);
            expect(data.id).toEqual(s.id);
            expect(data.phone_number).toEqual(s.phone_number);
            expect(data.username).toEqual(s.username);
            expect(data.last_name).toEqual(s.last_name);
            expect(data.password).toEqual(s.password);

        });

        const url = `${dataService.controllerUrl + 'getController'}/${s.username}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(s);

        httpMock.verify();
    }
    )
    );

    it('DeleteController() should get true', inject([HttpTestingController, ControllerService], (
        httpMock: HttpTestingController,
        dataService: ControllerService) => {

        let i : BigInteger = Uint8Array.of(1);
        let b: Controller = new Controller();
        b.id =  i;

        dataService.deleteController(i).subscribe(data => {
            expect(data).toEqual(true);

        });

        const url = `${dataService.controllerUrl+ 'deleteController'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: true}));

        httpMock.verify();
    }
    )
    );

    it('DeleteController() should get false-does not exist', inject([HttpTestingController, ControllerService], (
        httpMock: HttpTestingController,
        dataService: ControllerService) => {

        let i : BigInteger = Uint8Array.of(3);

        dataService.deleteController(i).subscribe(data => {
            expect(data).toEqual(false);

        });

        const url = `${dataService.controllerUrl+ 'deleteController'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.event(new HttpResponse<boolean>({body: false}));

        httpMock.verify();
    }
    )
    );

    it('AddController() should get created controller', inject([HttpTestingController, ControllerService], (
        httpMock: HttpTestingController,
        dataService: ControllerService) => {

        let i : BigInteger = Uint8Array.of(1);

        let s: Controller = new Controller();
        s.address = 'Balzakova 28';
        s.date_birth = new Date();
        s.deleted = false; 
        s.email = 'bojanacrlc@gmail.com';
        s.first_name = 'Bojana';
        s.id = i;
        s.last_name = 'Corilic';
        s.password = 'lozinka';
        s.phone_number = '43353254253';
        s.username = 'boka'
        
        dataService.addController(s).subscribe(data => {
            expect(data).toEqual(s);
            expect(data.address).toEqual(s.address);
            expect(data.date_birth).toEqual(s.date_birth);
            expect(data.deleted).toEqual(s.deleted);
            expect(data.email).toEqual(s.email);
            expect(data.first_name).toEqual(s.first_name);
            expect(data.id).toEqual(s.id);
            expect(data.phone_number).toEqual(s.phone_number);
            expect(data.username).toEqual(s.username);
            expect(data.last_name).toEqual(s.last_name);
            expect(data.password).toEqual(s.password);


        });

        const mockReq = httpMock.expectOne(dataService.controllerUrl + "addController");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(s);

        httpMock.verify();
    }
    )
    );

    it('filterSearch()- return List<Controller>', inject([HttpTestingController, ControllerService], (
        httpMock: HttpTestingController,
        dataService: ControllerService) => {

        let i : BigInteger = Uint8Array.of(1);
        let i2 : BigInteger = Uint8Array.of(2);
        
        let filterSearch: FilterSearchControllerDTO = new FilterSearchControllerDTO("username", "b");
  
        let s: Controller = new Controller();
        s.address = 'Balzakova 28';
        s.date_birth = new Date();
        s.deleted = false; 
        s.email = 'bojanacrlc@gmail.com';
        s.first_name = 'Bojana';
        s.id = i;
        s.last_name = 'Corilic';
        s.password = 'lozinka';
        s.phone_number = '43353254253';
        s.username = 'boka'

        let s2: Controller = new Controller();
        s2.address = 'Balzakova 28';
        s2.date_birth = new Date();
        s2.deleted = false; 
        s2.email = 'banee@gmail.com';
        s2.first_name = 'Bane';
        s2.id = i2;
        s2.last_name = 'Corilic';
        s2.password = 'lozinka2';
        s2.phone_number = '43353254253';
        s2.username = 'bane'
        let dummyControllers : Controller[] = [];
        dummyControllers.push(s);
        dummyControllers.push(s2);


        dataService.filterSearch(filterSearch).subscribe(data => {
            expect(data).toEqual(dummyControllers);
            expect(data[0].address).toEqual(s.address);
            expect(data[0].date_birth).toEqual(s.date_birth);
            expect(data[0].deleted).toEqual(s.deleted);
            expect(data[0].email).toEqual(s.email);
            expect(data[0].first_name).toEqual(s.first_name);
            expect(data[0].id).toEqual(s.id);
            expect(data[0].phone_number).toEqual(s.phone_number);
            expect(data[0].username).toEqual(s.username);
            expect(data[0].last_name).toEqual(s.last_name);
            expect(data[0].password).toEqual(s.password);
        });

        const mockReq = httpMock.expectOne(dataService.controllerUrl + "searchFilter");

        expect(mockReq.cancelled).toBeFalsy();

        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(dummyControllers);

        httpMock.verify();
    }
    )
    );




});