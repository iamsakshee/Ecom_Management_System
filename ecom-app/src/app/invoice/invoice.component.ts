import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.css'
})
export class InvoiceComponent implements OnInit {
  productDetails: any;
  customerName: string = '';
  shippingAddress: string = '';

  @ViewChild('invoiceContent', { static: false }) invoiceContent!: ElementRef;

  ngOnInit(): void {
    // Retrieve order details from local storage
    const data = localStorage.getItem('invoiceData');
    if (data) {
      this.productDetails = JSON.parse(data);
      this.customerName = this.productDetails?.shippingAddress?.customer?.name || 'Unknown';
      this.shippingAddress = `${this.productDetails.shippingAddress.addressLine1}, ${this.productDetails.shippingAddress.city}`;
    }
  }

  generatePDF(): void {
    const invoiceElement = this.invoiceContent.nativeElement;
    
    html2canvas(invoiceElement, { scale: 2 }).then((canvas) => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4');
      
      const imgWidth = 190;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;
      
      pdf.addImage(imgData, 'PNG', 10, 10, imgWidth, imgHeight);
      pdf.save("invoice.pdf");
    });
  }
}
