export interface IOfficeRelationship {
  id?: number;
  parentId?: number;
  chileId?: number;
  startDate?: Date;
  endDate?: Date;
  ccVersion?: number;
}

export class OfficeRelationship implements IOfficeRelationship {
  constructor(
    public id?: number,
    public parentId?: number,
    public chileId?: number,
    public startDate?: Date,
    public endDate?: Date,
    public ccVersion?: number
  ) {}
}
