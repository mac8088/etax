/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import OfficeService from '@/entities/office/office.service';
import { Office } from '@/shared/model/office.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('Office Service', () => {
    let service: OfficeService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new OfficeService();
      currentDate = new Date();

      elemDefault = new Office(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            effectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a Office', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            effectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            effectiveDate: currentDate,
            expiryDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a Office', async () => {
        const returnedFromService = Object.assign(
          {
            cstdOfficeType: 'BBBBBB',
            name: 'BBBBBB',
            cstdClassifierCode: 'BBBBBB',
            effectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT),
            phone: 'BBBBBB',
            fax: 'BBBBBB',
            stl: 'BBBBBB',
            mngOffice: 1,
            physicalAdr: 'BBBBBB',
            postalAadr: 'BBBBBB',
            pinCode: 'BBBBBB',
            cstdWeekWorkingDay: 'BBBBBB',
            officeCode: 'BBBBBB',
            cstdOfficeSubType: 'BBBBBB',
            cstdOfficeFuncType: 'BBBBBB',
            ccVersion: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
            expiryDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of Office', async () => {
        const returnedFromService = Object.assign(
          {
            cstdOfficeType: 'BBBBBB',
            name: 'BBBBBB',
            cstdClassifierCode: 'BBBBBB',
            effectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT),
            phone: 'BBBBBB',
            fax: 'BBBBBB',
            stl: 'BBBBBB',
            mngOffice: 1,
            physicalAdr: 'BBBBBB',
            postalAadr: 'BBBBBB',
            pinCode: 'BBBBBB',
            cstdWeekWorkingDay: 'BBBBBB',
            officeCode: 'BBBBBB',
            cstdOfficeSubType: 'BBBBBB',
            cstdOfficeFuncType: 'BBBBBB',
            ccVersion: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            effectiveDate: currentDate,
            expiryDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a Office', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
