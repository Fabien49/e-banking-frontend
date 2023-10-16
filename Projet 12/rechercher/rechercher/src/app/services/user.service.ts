import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import {User} from "../classes/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = "http://localhost:8080/SearchFieldExample/api/";

  constructor(private http:Http) { }

  getData(user : User)
  {
    let url = this.baseUrl + "filterData";
    return  this.http.post(url , user);
  }
}
