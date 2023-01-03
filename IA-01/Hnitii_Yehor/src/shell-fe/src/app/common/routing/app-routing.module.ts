import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TerminalComponent } from '../../terminal/terminal.component';
import {HistoryComponent} from "../../history/history.component";
import {ZipComponent} from "../../zip/zip.component";

const routes: Routes = [
  { path: 'zip', component: ZipComponent },
  { path: 'history', component: HistoryComponent },
  { path: '**', component: TerminalComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
