export class FilterSearchControllerDTO{
    type: string;
    text_search: string;
    
    constructor(type:string, text:string){
        this.type = type;
        this.text_search = text;
    }
}