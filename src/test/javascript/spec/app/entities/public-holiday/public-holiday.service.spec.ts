/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import PublicHolidayService from '@/entities/public-holiday/public-holiday.service';
import { PublicHoliday } from '@/shared/model/public-holiday.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('PublicHoliday Service', () => {
    let service: PublicHolidayService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new PublicHolidayService();
      currentDate = new Date();

      elemDefault = new PublicHoliday(0, 'AAAAAAA', 'AAAAAAA', currentDate, false, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            date: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a PublicHoliday', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a PublicHoliday', async () => {
        const returnedFromService = Object.assign(
          {
            cstdHolidayTypes: 'BBBBBB',
            description: 'BBBBBB',
            date: format(currentDate, DATE_FORMAT),
            workingFlag: true,
            countryWide: true,
            ccVersion: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of PublicHoliday', async () => {
        const returnedFromService = Object.assign(
          {
            cstdHolidayTypes: 'BBBBBB',
            description: 'BBBBBB',
            date: format(currentDate, DATE_FORMAT),
            workingFlag: true,
            countryWide: true,
            ccVersion: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a PublicHoliday', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
