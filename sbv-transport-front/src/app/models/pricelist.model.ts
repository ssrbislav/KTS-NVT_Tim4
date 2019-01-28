export class Pricelist {
    id: BigInteger;
    valid_since: Date;
    valid_until: Date;
    active: boolean;
    senior_discount_percentage: number;
    student_discount_percentage: number;
    standard_discount_percentage: number;
    double_zone_premium_percentage: number;
    bus_one_use_price: number;
    bus_daily_use_price: number;
    bus_monthly_use_price: number;
    bus_yearly_use_price: number;
    subway_one_use_price: number;
    subway_daily_use_price: number;
    subway_monthly_use_price: number;
    subway_yearly_use_price: number;
    trolley_one_use_price: number;
    trolley_daily_use_price: number;
    trolley_monthly_use_price: number;
    trolley_yearly_use_price: number;
    deleted: boolean;
}