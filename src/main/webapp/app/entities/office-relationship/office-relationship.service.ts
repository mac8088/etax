import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IOfficeRelationship } from '@/shared/model/office-relationship.model';

const baseApiUrl = 'api/office-relationships';

export default class OfficeRelationshipService {
  public find(id: number): Promise<IOfficeRelationship> {
    return new Promise<IOfficeRelationship>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IOfficeRelationship): Promise<IOfficeRelationship> {
    return new Promise<IOfficeRelationship>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IOfficeRelationship): Promise<IOfficeRelationship> {
    return new Promise<IOfficeRelationship>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
