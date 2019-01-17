export class AddLocationToTransportDTO{
    id_transport: BigInteger;
    id_location: BigInteger;
    
    constructor(id_transport : BigInteger, id_location : BigInteger){
        this.id_transport = id_transport;
        this.id_location = id_location;
    }
}