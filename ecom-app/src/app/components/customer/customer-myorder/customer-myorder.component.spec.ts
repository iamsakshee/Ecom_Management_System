import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerMyorderComponent } from './customer-myorder.component';

describe('CustomerMyorderComponent', () => {
  let component: CustomerMyorderComponent;
  let fixture: ComponentFixture<CustomerMyorderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerMyorderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerMyorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
