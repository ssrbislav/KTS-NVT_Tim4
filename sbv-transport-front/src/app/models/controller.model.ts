
export class Controller{
    
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
	
	constructor(email:string, username:string, password:string, fname:string, 
		lname:string, address:string, phone:string, date:Date){
        this.email= email;
		this.username = username;
		this.password = password;
		this.first_name = fname;
		this.last_name = lname;
		this.address = address;
		this.phone_number = phone;
		this.date_birth = date;
		
    }
}