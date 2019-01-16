import { Station } from './station.model';

export class CheckBoxItem{
    value: Station;
    checked: boolean;

    constructor(station: Station, checked:boolean){
        this.value = station;
        this.checked = checked;
    }
}

