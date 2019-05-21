import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VotingCollectionComponent } from './voting-collection.component';

describe('VotingCollectionComponent', () => {
  let component: VotingCollectionComponent;
  let fixture: ComponentFixture<VotingCollectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VotingCollectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VotingCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
