import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DocumentService } from 'src/app/services/document.service';
import { MyDocument } from 'src/app/models/document.model';



describe('DocumentService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [DocumentService],
        });
    });

    it('getDocuments() should get all documents', inject([HttpTestingController, DocumentService], (
        httpMock: HttpTestingController,
        dataService: DocumentService) => {
      
        let i : BigInteger = Uint8Array.of(1);
        let i2: BigInteger = Uint8Array.of(2);

        let d: MyDocument = new MyDocument();
        d.id = i;
        d.approved = 'need approve';
        d.date_of_upload = new Date();
        d.image_location = 'lokacija';

        let d2: MyDocument = new MyDocument();
        d2.id = i2;
        d2.approved = 'need approve';
        d2.date_of_upload = new Date();
        d2.image_location = 'lokacija';

        let dummyDocuments : MyDocument[] = [];
        dummyDocuments.push(d);
        dummyDocuments.push(d2);

        dataService.getDocuments().subscribe(data => {
            expect(data).toEqual(dummyDocuments);
            expect(data.length).toEqual(2);
            expect(data[0].image_location).toEqual('lokacija');
            expect(data[0].id).toEqual(i);
            expect(data[0].date_of_upload).toEqual(d.date_of_upload);
            expect(data[0].approved).toEqual('need approve');

        });

        const mockReq = httpMock.expectOne(dataService.documentUrl);

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(dummyDocuments);

        httpMock.verify();
    }
    )
    );

    it('approve Document() should get updated document', inject([HttpTestingController, DocumentService], (
        httpMock: HttpTestingController,
        dataService: DocumentService) => {
      
        let i : BigInteger = Uint8Array.of(1);

        let d: MyDocument = new MyDocument();
        d.id = i;
        d.approved = 'approved';
        d.date_of_upload = new Date();
        d.image_location = 'lokacija';

        dataService.achangeApproved(d).subscribe(data => {
            expect(data).toEqual(d);
            expect(data.image_location).toEqual('lokacija');
            expect(data.id).toEqual(i);
            expect(data.date_of_upload).toEqual(d.date_of_upload);
            expect(data.approved).toEqual('approved');

        });

        const mockReq = httpMock.expectOne(dataService.documentUrl + 'changeApproved');

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(d);

        httpMock.verify();
    }
    )
    );


    

});