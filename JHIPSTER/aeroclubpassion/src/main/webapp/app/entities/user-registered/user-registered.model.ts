import { IUser } from 'app/entities/user/user.model';

export interface IUserRegistered {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  mail?: string | null;
  adresse?: string | null;
  codePostal?: string | null;
  commune?: string | null;
  heureVol?: string | null;
  user?: IUser | null;
}

export class UserRegistered implements IUserRegistered {
  constructor(
    public id?: number,
    public nom?: string | null,
    public prenom?: string | null,
    public telephone?: string | null,
    public mail?: string | null,
    public adresse?: string | null,
    public codePostal?: string | null,
    public commune?: string | null,
    public heureVol?: string | null,
    public user?: IUser | null
  ) {}
}

export function getUserRegisteredIdentifier(userRegistered: IUserRegistered): number | undefined {
  return userRegistered.id;
}
