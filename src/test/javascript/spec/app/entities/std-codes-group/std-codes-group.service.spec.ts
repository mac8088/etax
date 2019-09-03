/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import StdCodesGroupService from '@/entities/std-codes-group/std-codes-group.service';
import { StdCodesGroup, OptionIndicator, ValueTypeIndicator } from '@/shared/model/std-codes-group.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('StdCodesGroup Service', () => {
    let service: StdCodesGroupService;
    let elemDefault;
    beforeEach(() => {
      service = new StdCodesGroupService();

      elemDefault = new StdCodesGroup(
        0,
        'AAAAAAA',
        'AAAAAAA',
        OptionIndicator.Y,
        OptionIndicator.Y,
        OptionIndicator.Y,
        ValueTypeIndicator.D,
        OptionIndicator.Y,
        OptionIndicator.Y,
        0,
        OptionIndicator.Y,
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a StdCodesGroup', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a StdCodesGroup', async () => {
        const returnedFromService = Object.assign(
          {
            groupCode: 'BBBBBB',
            groupDesc: 'BBBBBB',
            systemInd: 'BBBBBB',
            secLevelRequired: 'BBBBBB',
            valueRequired: 'BBBBBB',
            valueType: 'BBBBBB',
            descriptionRequired: 'BBBBBB',
            externalCodeRequired: 'BBBBBB',
            externalCodeLength: 1,
            parentGroupRequired: 'BBBBBB',
            parentGroupCode: 'BBBBBB',
            touppercase: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of StdCodesGroup', async () => {
        const returnedFromService = Object.assign(
          {
            groupCode: 'BBBBBB',
            groupDesc: 'BBBBBB',
            systemInd: 'BBBBBB',
            secLevelRequired: 'BBBBBB',
            valueRequired: 'BBBBBB',
            valueType: 'BBBBBB',
            descriptionRequired: 'BBBBBB',
            externalCodeRequired: 'BBBBBB',
            externalCodeLength: 1,
            parentGroupRequired: 'BBBBBB',
            parentGroupCode: 'BBBBBB',
            touppercase: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a StdCodesGroup', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
