import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService, private router: Router) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const user = this.auth.getAuthUser();
    const isLoggedIn = user && user.email && user.password;
    const isNotPublicEndpoint = !request.url.startsWith(this.auth.PUBLIC_PATH);
    console.log(isLoggedIn && isNotPublicEndpoint)
    if (isLoggedIn && isNotPublicEndpoint) {
      request = request.clone({
        setHeaders: {
          Authorization: `Basic ${btoa(user.email + ':' + user.password)}`
        }
      });
      console.log(request)
    } else if (isNotPublicEndpoint) {
      this.router.navigate(['/login']);
    }

    return next.handle(request);
  }
}
