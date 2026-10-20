import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TerceroForm } from './tercero-form';

describe('TerceroForm', () => {
  let component: TerceroForm;
  let fixture: ComponentFixture<TerceroForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TerceroForm],
    }).compileComponents();

    fixture = TestBed.createComponent(TerceroForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
