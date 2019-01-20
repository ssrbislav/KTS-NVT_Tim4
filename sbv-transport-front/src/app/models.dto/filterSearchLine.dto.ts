export class FilterSearchLineDTO{

    zone: string;
	type: string;
	id_station: BigInteger;
    search_text: string;
    
    constructor(zone:string, type:string, id_station:BigInteger, search_text:string){
        this.zone = zone;
        this.type = type;
        this.id_station = id_station;
        this.search_text = search_text;
    }

}