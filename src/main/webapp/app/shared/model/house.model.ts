export interface IHouse {
  id?: number;
  name?: string;
  color?: string;
  score?: number;
  schoolName?: string;
  schoolId?: number;
}

export const defaultValue: Readonly<IHouse> = {};
