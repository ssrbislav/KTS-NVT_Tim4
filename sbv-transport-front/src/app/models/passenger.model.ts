import { MyDocument } from './document.model';
import { Ticket } from './ticket.model';

export class Passenger {
    id: BigInteger;
    
    email: string;
	username:string;
	password: string;
	first_name:string;
	last_name:string;
	address:string;
	phone_number:string;
	date_birth:Date;
	deleted: boolean;
    roles: string[];
    active: boolean;
    userType: UserType;
    document: MyDocument;
    document_validated: boolean;
    tickets: Ticket[];
}

enum UserType{
    standard, student, senior
}