export class ReportTicketDTO{
    start_date:Date;
    finished_date:Date;
    
    constructor(private start:Date,private finish:Date){
        this.start_date = this.start;
        this.finished_date = this.finish;

    }
}