/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import UserInfoService from '@/entities/user-info/user-info.service';
import { UserInfo } from '@/shared/model/user-info.model';

const mockedAxios: any = axios;
jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn()
}));

describe('Service Tests', () => {
  describe('UserInfo Service', () => {
    let service: UserInfoService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new UserInfoService();
      currentDate = new Date();

      elemDefault = new UserInfo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        false,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        false,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            effiectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT),
            loginTime: format(currentDate, DATE_TIME_FORMAT),
            logoutTime: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should create a UserInfo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            effiectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT),
            loginTime: format(currentDate, DATE_TIME_FORMAT),
            logoutTime: format(currentDate, DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            effiectiveDate: currentDate,
            expiryDate: currentDate,
            loginTime: currentDate,
            logoutTime: currentDate
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should update a UserInfo', async () => {
        const returnedFromService = Object.assign(
          {
            cstdAdmSection: 'BBBBBB',
            cstdSecurityLevel: 'BBBBBB',
            cstdUserType: 'BBBBBB',
            description: 'BBBBBB',
            middleName: 'BBBBBB',
            gender: 'BBBBBB',
            phoneNum: 'BBBBBB',
            faxNum: 'BBBBBB',
            effiectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT),
            blocked: true,
            blockedReason: 'BBBBBB',
            forcedPwdChange: true,
            cstdTitles: 'BBBBBB',
            cstdStatus: 'BBBBBB',
            cstdAdmDivsison: 'BBBBBB',
            loginStatus: 'BBBBBB',
            loginTime: format(currentDate, DATE_TIME_FORMAT),
            attempt: 1,
            needApprove: true,
            logoutTime: format(currentDate, DATE_TIME_FORMAT),
            nationalId: 'BBBBBB',
            cstdOrganizationGrade: 'BBBBBB',
            cstdEmploymentType: 'BBBBBB',
            manuScript: 'BBBBBB',
            ccVersion: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effiectiveDate: currentDate,
            expiryDate: currentDate,
            loginTime: currentDate,
            logoutTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should return a list of UserInfo', async () => {
        const returnedFromService = Object.assign(
          {
            cstdAdmSection: 'BBBBBB',
            cstdSecurityLevel: 'BBBBBB',
            cstdUserType: 'BBBBBB',
            description: 'BBBBBB',
            middleName: 'BBBBBB',
            gender: 'BBBBBB',
            phoneNum: 'BBBBBB',
            faxNum: 'BBBBBB',
            effiectiveDate: format(currentDate, DATE_TIME_FORMAT),
            expiryDate: format(currentDate, DATE_TIME_FORMAT),
            blocked: true,
            blockedReason: 'BBBBBB',
            forcedPwdChange: true,
            cstdTitles: 'BBBBBB',
            cstdStatus: 'BBBBBB',
            cstdAdmDivsison: 'BBBBBB',
            loginStatus: 'BBBBBB',
            loginTime: format(currentDate, DATE_TIME_FORMAT),
            attempt: 1,
            needApprove: true,
            logoutTime: format(currentDate, DATE_TIME_FORMAT),
            nationalId: 'BBBBBB',
            cstdOrganizationGrade: 'BBBBBB',
            cstdEmploymentType: 'BBBBBB',
            manuScript: 'BBBBBB',
            ccVersion: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            effiectiveDate: currentDate,
            expiryDate: currentDate,
            loginTime: currentDate,
            logoutTime: currentDate
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should delete a UserInfo', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });
    });
  });
});
