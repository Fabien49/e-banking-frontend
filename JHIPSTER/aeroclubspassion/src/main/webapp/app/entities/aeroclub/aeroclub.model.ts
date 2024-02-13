import { ITarif } from 'app/entities/tarif/tarif.model';

export interface IAeroclub {
  id?: number;
  oaci?: string;
  name?: string;
  type?: string;
  phoneNumber?: string;
  mail?: string;
  adresse?: string;
  codePostal?: string;
  commune?: string;
  tarif?: ITarif | null;
}

export class Aeroclub implements IAeroclub {
  constructor(
    public id?: number,
    public oaci?: string,
    public name?: string,
    public type?: string,
    public phoneNumber?: string,
    public mail?: string,
    public adresse?: string,
    public codePostal?: string,
    public commune?: string,
    public tarif?: ITarif | null
  ) {}
}

export function getAeroclubIdentifier(aeroclub: IAeroclub): number | undefined {
  return aeroclub.id;
}
