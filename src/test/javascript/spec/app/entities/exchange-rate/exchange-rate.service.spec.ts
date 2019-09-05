/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import ExchangeRateService from '@/entities/exchange-rate/exchange-rate.service';
import { ExchangeRate } from '@/shared/model/exchange-rate.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('ExchangeRate Service', () => {
    let service: ExchangeRateService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new ExchangeRateService();
      currentDate = new Date();

      elemDefault = new ExchangeRate(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: format(currentDate, DATE_FORMAT),
            endDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a ExchangeRate', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: format(currentDate, DATE_FORMAT),
            endDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a ExchangeRate', async () => {
        const returnedFromService = Object.assign(
          {
            cstdCurrencyA: 'BBBBBB',
            cstdCurrencyB: 'BBBBBB',
            rate: 1,
            startDate: format(currentDate, DATE_FORMAT),
            endDate: format(currentDate, DATE_FORMAT),
            ccVersion: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of ExchangeRate', async () => {
        const returnedFromService = Object.assign(
          {
            cstdCurrencyA: 'BBBBBB',
            cstdCurrencyB: 'BBBBBB',
            rate: 1,
            startDate: format(currentDate, DATE_FORMAT),
            endDate: format(currentDate, DATE_FORMAT),
            ccVersion: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a ExchangeRate', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
