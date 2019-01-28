import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PricelistService } from 'src/app/services/pricelist.service';
import { ReportTicketDTO } from 'src/app/models.dto/reportTicket.dto';
import { ReportResultTicketDTO } from 'src/app/models.dto/reportResultTicket.dto';

describe('PriceListService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [PricelistService]
        });
    });

    it('get Pricelists for ticket() should return pricelist and number of tickets', inject([HttpTestingController, PricelistService], (
        httpMock: HttpTestingController,
        dataService: PricelistService) => {

        let i: BigInteger = Uint8Array.of(1);

        let t: ReportTicketDTO = new ReportTicketDTO(new Date(), new Date());
        let r: ReportResultTicketDTO = new ReportResultTicketDTO();
        r.end = new Date();
        r.number_ticket = 150;
        r.start = new Date();

        let result: ReportResultTicketDTO[] = [];
        result.push(r);

        dataService.reportTicket(t).subscribe(data => {
            expect(data).toEqual(result);
            expect(data.length).toEqual(1);
            expect(data[0].end).toEqual(r.end);
            expect(data[0].number_ticket).toEqual(150);
            expect(data[0].start).toEqual(r.start);
        });

        const mockReq = httpMock.expectOne(dataService.pricelistUrl + 'reportTicket');

        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');

        mockReq.flush(result);

        httpMock.verify();
    }
    )
    );

});