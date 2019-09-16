/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT, DATE_TIME_FORMAT } from '@/shared/date/filters';
import StdCodesGroupPropService from '@/entities/std-codes-group-prop/std-codes-group-prop.service';
import { StdCodesGroupProp, ValueTypeIndicator, OptionIndicator } from '@/shared/model/std-codes-group-prop.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('StdCodesGroupProp Service', () => {
    let service: StdCodesGroupPropService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new StdCodesGroupPropService();
      currentDate = new Date();

      elemDefault = new StdCodesGroupProp(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        ValueTypeIndicator.D,
        OptionIndicator.Y,
        currentDate,
        'AAAAAAA',
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT),
            dfltValueDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a StdCodesGroupProp', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT),
            dfltValueDate: format(currentDate, DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
            dfltValueDate: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a StdCodesGroupProp', async () => {
        const returnedFromService = Object.assign(
          {
            groupCode: 'BBBBBB',
            propCode: 'BBBBBB',
            propDesc: 'BBBBBB',
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT),
            propType: 'BBBBBB',
            propMdtr: 'BBBBBB',
            dfltValueDate: format(currentDate, DATE_FORMAT),
            dfltValueString: 'BBBBBB',
            dfltValueBool: true,
            dfltValueNumber: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
            dfltValueDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of StdCodesGroupProp', async () => {
        const returnedFromService = Object.assign(
          {
            groupCode: 'BBBBBB',
            propCode: 'BBBBBB',
            propDesc: 'BBBBBB',
            startDate: format(currentDate, DATE_TIME_FORMAT),
            endDate: format(currentDate, DATE_TIME_FORMAT),
            propType: 'BBBBBB',
            propMdtr: 'BBBBBB',
            dfltValueDate: format(currentDate, DATE_FORMAT),
            dfltValueString: 'BBBBBB',
            dfltValueBool: true,
            dfltValueNumber: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
            dfltValueDate: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a StdCodesGroupProp', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
