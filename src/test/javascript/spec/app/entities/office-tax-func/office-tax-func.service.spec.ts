/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import OfficeTaxFuncService from '@/entities/office-tax-func/office-tax-func.service';
import { OfficeTaxFunc } from '@/shared/model/office-tax-func.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('OfficeTaxFunc Service', () => {
    let service: OfficeTaxFuncService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new OfficeTaxFuncService();
      currentDate = new Date();

      elemDefault = new OfficeTaxFunc(0, 0, 0, currentDate, currentDate);
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

      it('should create a OfficeTaxFunc', async () => {
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

      it('should update a OfficeTaxFunc', async () => {
        const returnedFromService = Object.assign(
          {
            taxOfficeId: 1,
            funcOfficeId: 1,
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
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of OfficeTaxFunc', async () => {
        const returnedFromService = Object.assign(
          {
            taxOfficeId: 1,
            funcOfficeId: 1,
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
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a OfficeTaxFunc', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
