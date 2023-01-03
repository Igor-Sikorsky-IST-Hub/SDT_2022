import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-terminal',
  templateUrl: './terminal.component.html',
  styleUrls: ['./terminal.component.css']
})
export class TerminalComponent implements OnInit {

  title = 'shell-fe';
  action: string = '';
  // '/Users/user/Desktop/files'
  path1: string = '/';
  path2: string = '/';

  swapItems() {
    const temp = this.path2;
    this.path2 = this.path1;
    this.path1 = temp;
    this.updatePathsAtLocalStorage();
  }

  ngOnInit(): void {
    let path1 = localStorage.getItem('path1');
    let path2 = localStorage.getItem('path2');
    if (path1 !== null) {
      this.path1 = path1;
    }
    if (path2 !== null) {
      this.path2 = path2;
    }
  }

  executeAction(action: string) {
    console.log(action + ' action')
    //copy | transfer | delete | reload
    this.action = action;
  }

  setPath(event: { primary: boolean; path: string }) {
    if (event.primary) {
      this.path1 = event.path;
    } else this.path2 = event.path;
    this.updatePathsAtLocalStorage()
  }

  private updatePathsAtLocalStorage() {
    localStorage.setItem('path1', this.path1);
    localStorage.setItem('path2', this.path2);
  }
}
