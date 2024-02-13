import * as dayjs from 'dayjs';
import { IAvion } from 'app/entities/avion/avion.model';
import { IUserRegistered } from 'app/entities/user-registered/user-registered.model';

export interface IReservation {
  id?: number;
  dateEmprunt?: dayjs.Dayjs | null;
  dateRetour?: dayjs.Dayjs | null;
  avions?: IAvion | null;
  userRegistereds?: IUserRegistered | null;
}

export class Reservation implements IReservation {
  constructor(
    public id?: number,
    public dateEmprunt?: dayjs.Dayjs | null,
    public dateRetour?: dayjs.Dayjs | null,
    public avions?: IAvion | null,
    public userRegistereds?: IUserRegistered | null
  ) {}
}

export function getReservationIdentifier(reservation: IReservation): number | undefined {
  return reservation.id;
}
