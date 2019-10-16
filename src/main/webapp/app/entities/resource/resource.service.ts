import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IResource } from '@/shared/model/resource.model';

const baseApiUrl = 'api/resources';

export default class ResourceService {
  public find(id: number): Promise<IResource> {
    return new Promise<IResource>(resolve => {
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

  public create(entity: IResource): Promise<IResource> {
    return new Promise<IResource>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IResource): Promise<IResource> {
    return new Promise<IResource>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
