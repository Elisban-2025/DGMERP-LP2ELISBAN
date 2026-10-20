import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CompraForm } from './compra-form';

describe('CompraForm', () => {
  let component: CompraForm;
  let fixture: ComponentFixture<CompraForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompraForm],
    }).compileComponents();

    fixture = TestBed.createComponent(CompraForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
