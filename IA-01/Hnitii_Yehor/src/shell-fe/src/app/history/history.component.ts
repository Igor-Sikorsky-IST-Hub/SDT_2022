import {Component, OnInit} from '@angular/core';
import {HistoryDto} from "./dto/history-dto";
import {HistoryService} from './service/history.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  displayedColumns: string[] = ['index', 'history', 'action', 'executed'];
  dataSource: HistoryDto[] = [];

  public constructor(private historyService: HistoryService) {
  }


  ngOnInit(): void {
    this.fetchHistory();
  }


  private fetchHistory() {
    this.historyService
      .getHistory()
      .subscribe(data => this.dataSource = data);
  }
}
