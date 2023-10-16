import {Component, OnInit} from '@angular/core';
import { User } from '../classes/user';
import { UserService } from '../services/user.service';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-aeroclub',
  templateUrl: './aeroclub.component.html',
  styleUrls: ['./aeroclub.component.scss']
})
export class AeroclubComponent implements OnInit{

  private user = new User();
  private data: any;

  constructor(private userService : UserService) { }

  ngOnInit() {
    this.getData(this.user);
  }

  form = new FormGroup({
    name : new FormControl(),
    email : new FormControl()
  });


  getData(user: User)
  {
    this.userService.getData(user).subscribe(
      response => {
        this.data = response.json();
      },
      error => {
        console.log("error while getting user Details");
      }
    );
  }

  searchForm(searchInfo: HTMLFormElement)
  {
    // @ts-ignore
    this.user.name = this.Name.value;
    // @ts-ignore
    this.user.emailId = this.Email.value;
    this.getData(this.user);
  }

  get Name()
  {
    return this.form.get('name');
  }

  get Email()
  {
    return this.form.get('email');
  }

}
