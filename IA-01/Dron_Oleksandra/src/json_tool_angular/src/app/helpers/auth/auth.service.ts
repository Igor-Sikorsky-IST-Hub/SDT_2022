import {Injectable} from '@angular/core';
import {User} from "../model/common-dtos";
import {AbstractService} from "../service/abstract.service";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService extends AbstractService {

  emitUserLogin = new Subject<User>();
  userLoggedIn = this.emitUserLogin.asObservable();

  authenticate(username: string, password: string): Observable<User> {
    return this.httpClient
      .post<User>(`${this.PUBLIC_PATH}/auth`, {
        email: username,
        password: password
      });
  }

  register(user: { password: any; firstname: any; email: any; lastname: any }): Observable<User> {
    return this.httpClient
      .post<User>(`${this.PUBLIC_PATH}/users`, user);
  }

  getAuthUser(): User | undefined {
    let user = localStorage.getItem('user');

    if (!user) {
      return undefined;
    }

    return JSON.parse(user);
  }

}
