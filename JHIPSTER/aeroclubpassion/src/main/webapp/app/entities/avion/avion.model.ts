import { IAtelier } from 'app/entities/atelier/atelier.model';
import { IRevision } from 'app/entities/revision/revision.model';

export interface IAvion {
  id?: number;
  marque?: string;
  type?: string | null;
  moteur?: string | null;
  puissance?: number | null;
  place?: number;
  autonomie?: string | null;
  usage?: string | null;
  heures?: string;
  imageContentType?: string | null;
  image?: string | null;
  atelier?: IAtelier | null;
  revision?: IRevision | null;
}

export class Avion implements IAvion {
  constructor(
    public id?: number,
    public marque?: string,
    public type?: string | null,
    public moteur?: string | null,
    public puissance?: number | null,
    public place?: number,
    public autonomie?: string | null,
    public usage?: string | null,
    public heures?: string,
    public imageContentType?: string | null,
    public image?: string | null,
    public atelier?: IAtelier | null,
    public revision?: IRevision | null
  ) {}
}

export function getAvionIdentifier(avion: IAvion): number | undefined {
  return avion.id;
}
