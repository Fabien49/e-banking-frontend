export interface ITarif {
  id?: number;
  taxeAtterrissage?: number | null;
  taxeParking?: number | null;
  carburant?: number | null;
}

export class Tarif implements ITarif {
  constructor(
    public id?: number,
    public taxeAtterrissage?: number | null,
    public taxeParking?: number | null,
    public carburant?: number | null
  ) {}
}

export function getTarifIdentifier(tarif: ITarif): number | undefined {
  return tarif.id;
}
