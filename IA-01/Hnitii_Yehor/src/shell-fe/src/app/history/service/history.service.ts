import {Injectable, OnInit} from '@angular/core';
import {HistoryDto} from "../dto/history-dto";
import {CommonService} from "../../common/service/common.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HistoryService extends CommonService {


  getHistory(): Observable<HistoryDto[]> {
    return this.http.get<HistoryDto[]>(
      this.PUBLIC_PATH + '/history'
    );
  }

}
