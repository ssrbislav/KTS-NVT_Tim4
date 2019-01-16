export class AddStationToList{
    id_station: BigInteger;
    id_line: BigInteger;
    
    constructor(id_s:BigInteger, id_l :BigInteger){
        this.id_station = id_s;
        this.id_line = id_l;
    }
}