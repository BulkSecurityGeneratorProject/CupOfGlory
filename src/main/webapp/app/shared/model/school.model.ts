import { IHouse } from 'app/shared/model/house.model';

export interface ISchool {
  id?: number;
  name?: string;
  directorLogin?: string;
  directorId?: number;
  houses?: IHouse[];
}

export const defaultValue: Readonly<ISchool> = {};
