import {Component, OnInit} from '@angular/core';
import {User} from "./helpers/model/common-dtos";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "./helpers/auth/auth.service";
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  user: User | undefined = undefined;

  constructor(private toastr: ToastrService, private auth: AuthService, private router: Router) {
    auth.userLoggedIn.subscribe(user => this.user = user);
  }

  ngOnInit(): void {
    this.user = this.auth.getAuthUser();
    console.log(this.user)
  }

  logout() {
    localStorage.clear();
    this.user = undefined;
    this.router.navigate(['/login'])
  }
}
