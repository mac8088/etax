/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import StdCodesDescService from '@/entities/std-codes-desc/std-codes-desc.service';
import { StdCodesDesc } from '@/shared/model/std-codes-desc.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('StdCodesDesc Service', () => {
    let service: StdCodesDescService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new StdCodesDescService();
      currentDate = new Date();

      elemDefault = new StdCodesDesc(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a StdCodesDesc', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT)
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

      it('should update a StdCodesDesc', async () => {
        const returnedFromService = Object.assign(
          {
            groupCode: 'BBBBBB',
            internalCode: 'BBBBBB',
            langCode: 'BBBBBB',
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT),
            externalCode: 'BBBBBB',
            codeDesc: 'BBBBBB'
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

      it('should return a list of StdCodesDesc', async () => {
        const returnedFromService = Object.assign(
          {
            groupCode: 'BBBBBB',
            internalCode: 'BBBBBB',
            langCode: 'BBBBBB',
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT),
            externalCode: 'BBBBBB',
            codeDesc: 'BBBBBB'
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

      it('should delete a StdCodesDesc', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
