import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {ToastrService} from "ngx-toastr";
import {User} from "../../model/common-dtos";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService
  ) {

  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }
    let username = this.loginForm.controls['username'].value;
    let password = this.loginForm.controls['password'].value;

    this.loading = true;
    this.authService
      .authenticate(username, password)
      .subscribe({
        next: (user: User) => {
          user.password = password;
          localStorage.setItem('user', JSON.stringify(user));
          this.authService.emitUserLogin.next(user);
          this.router.navigate(['/']);
        },
        error: (err) => {
          console.log(err)
          err.error.errors.forEach((error: { message: string | undefined; }) => this.toastr.error());
          this.loading = false;
        }, complete: () => {
          this.loading = false;
        }
      })
  }
}
