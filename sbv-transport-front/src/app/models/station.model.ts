import { MyLocation } from './location.model';

export class Station{
    id: BigInteger;
    location: MyLocation;
    zone: Zone;
    deleted: boolean;
}