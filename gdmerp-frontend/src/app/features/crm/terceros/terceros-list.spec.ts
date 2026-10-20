import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TercerosList } from './terceros-list';

describe('TercerosList', () => {
  let component: TercerosList;
  let fixture: ComponentFixture<TercerosList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TercerosList],
    }).compileComponents();

    fixture = TestBed.createComponent(TercerosList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
