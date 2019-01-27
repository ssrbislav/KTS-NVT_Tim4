import { Passenger } from './passenger.model';

export class MyDocument{

    id: BigInteger;
	date_of_upload: Date;
	image_location:string;
    approved: string; //need approve,approved,declined
    passenger: Passenger;
}