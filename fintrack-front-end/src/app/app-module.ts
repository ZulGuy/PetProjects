import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Users } from './components/users/users';
import { UsersComponent } from './components/users.component/users.component';
import { TransactionsComponent } from './components/transactions.component/transactions.component';
import { AccountsComponent } from './components/accounts.component/accounts.component';

@NgModule({
  declarations: [
    App,
    Users,
    UsersComponent,
    TransactionsComponent,
    AccountsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
  ],
  bootstrap: [App]
})
export class AppModule { }
