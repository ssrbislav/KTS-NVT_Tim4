import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdministratorService } from '../services/administrator.service';
import { Administrator } from '../models/administrator.model';

describe('AdministratorService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [AdministratorService]
        });
    });

    
    it('findAdministrator() should get administrator', inject([HttpTestingController, AdministratorService], (
        httpMock: HttpTestingController,
        dataService: AdministratorService) => {

        let i : BigInteger = Uint8Array.of(1);

        let a : Administrator = new Administrator();
        a.username = 'admin';
        a.password = 'admin';

        dataService.findAdmin(i).subscribe(data => {
            expect(data).toEqual(a);
            expect(data.username).toEqual('admin');
            expect(data.password).toEqual('admin');

        });

        const url = `${dataService.administratorUrl+ 'findAdministrator'}/${i}`;
        const mockReq = httpMock.expectOne(url);

        expect(mockReq.cancelled).toBeFalsy();

       
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(a);

        httpMock.verify();
    }
    )
    );
});