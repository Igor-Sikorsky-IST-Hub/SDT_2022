import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {ToastrService} from "ngx-toastr";
import {User} from "../../model/common-dtos";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
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
      password: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required]
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
    let firstname = this.loginForm.controls['firstname'].value;
    let lastname = this.loginForm.controls['lastname'].value;
    let user = {email: username, password: password, firstname: firstname, lastname: lastname};
console.log(user)
    this.loading = true;
    this.authService
      .register(user)
      .subscribe({
        next: (user: User) => {
          this.router.navigate(['/login']);
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
