export class FilterSearchStationDTO{
    zone: string;
    search_text: string;
    
    constructor(zone:string, search_text:string){
        this.zone = zone;
        this.search_text = search_text;
    }
}