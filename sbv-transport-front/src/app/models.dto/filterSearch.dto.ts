export class FilterSearchDTO{

    id_line: BigInteger;
	late: boolean;
	id_location: BigInteger;
	text_search: string;

	constructor(id_line: BigInteger,late:boolean,id_location:BigInteger,text_search: string){
		this.id_line = id_line;
		this.late = late;
		this.id_location = id_location;
		this.text_search = text_search;
	}

}