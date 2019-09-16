export interface IExchangeRate {
  id?: number;
  cstdCurrencyA?: string;
  cstdCurrencyB?: string;
  rate?: number;
  startDate?: Date;
  endDate?: Date;
  ccVersion?: number;
}

export class ExchangeRate implements IExchangeRate {
  constructor(
    public id?: number,
    public cstdCurrencyA?: string,
    public cstdCurrencyB?: string,
    public rate?: number,
    public startDate?: Date,
    public endDate?: Date,
    public ccVersion?: number
  ) {}
}
