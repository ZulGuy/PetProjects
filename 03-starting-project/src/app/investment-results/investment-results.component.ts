import {Component, Input} from '@angular/core';
import {CurrencyPipe} from "@angular/common";

interface InvestmentResult {
  year: number,
  interest: number,
  valueEndOfYear: any,
  annualInvestment: any,
  totalInterest: number,
  totalAmountInvested: any,
}

@Component({
  selector: 'app-investment-results',
  standalone: true,
  imports: [
    CurrencyPipe
  ],
  templateUrl: './investment-results.component.html',
  styleUrl: './investment-results.component.css'
})
export class InvestmentResultsComponent {
  @Input() investmentResults: InvestmentResult[] = [];

}
