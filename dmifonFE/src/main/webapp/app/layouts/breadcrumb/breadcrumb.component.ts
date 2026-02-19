import { Component, OnInit, ChangeDetectionStrategy, HostListener, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd, PRIMARY_OUTLET, NavigationStart, NavigationCancel } from '@angular/router';
import { filter, tap } from 'rxjs/operators';
import { Location } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { Breadcrumb } from 'app/models/breadcrumb.model';
import { JoyrideService } from 'ngx-joyride';

@Component({
  selector: 'jhi-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BreadcrumbComponent implements OnInit, AfterViewInit {
  public breadcrumbs: Breadcrumb[];
  showSpinnerSubject$ = new BehaviorSubject<boolean>(false);
  showSpinnerAction$ = this.showSpinnerSubject$.asObservable();
  navigationEvent = this.router.events;
  innerWidth!: any;
  navigationEventEnd = this.navigationEvent.pipe(
    filter(event => event instanceof NavigationEnd || event instanceof NavigationCancel),
    tap(() => this.showSpinnerSubject$.next(false)),
    tap(() => {
      this.breadcrumbs = this.getBreadcrumbs(this.activatedRoute.root);
    })
  );
  navigationEventStart = this.navigationEvent.pipe(
    filter(event => event instanceof NavigationStart),
    tap(() => this.showSpinnerSubject$.next(true))
  );
  constructor(
    private activatedRoute: ActivatedRoute,
    protected router: Router,
    private location: Location,
    private joyrideService: JoyrideService
  ) {
    this.breadcrumbs = [];
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): any {
    this.innerWidth = window.innerWidth;
  }

  ngOnInit(): void {
    // this.breadcrumbs = this.getBreadcrumbs(this.router.routerState.root);
    this.innerWidth = window.innerWidth;
    this.navigationEventStart.subscribe();
    this.navigationEventEnd.subscribe();
  }

  ngAfterViewInit(): void {
    console.log(this.innerWidth);
  }

  goFullScreen(): void {
    (document.querySelector('#main') as HTMLElement).requestFullscreen().then();
  }

  startTour(): void {
    if (this.joyrideService.isTourInProgress()) {
      this.joyrideService.closeTour();
    }
    switch (this.router.url) {
      case '/gest-progetti': {
        this.joyrideService.startTour({
          steps: ['firstStepProg', 'secondStepProg', 'thirdStepProg', 'fourthStepProg', 'fifthStepProg', 'sixthStepProg'],
          waitingTime: 100,
        });
        break;
      }
      case '/gest-previsioni': {
        this.joyrideService.startTour({ steps: ['firstStepPrev'] });
        break;
      }
      default: {
        if (
          this.router.url.includes('/gest-avanzamenti') &&
          this.router.url.includes('viewDetail') &&
          !this.router.url.includes('allegati')
        ) {
          this.joyrideService.startTour({
            steps: [
              'firstStepAvaDet',
              'secondStepAvaDet',
              'thirdStepAvaDet',
              'fourthStepAvaDet',
              'fifthStepAvaDet',
              'sixthStepAvaDet',
              'seventhStepAvaDet',
            ],
          });
        } else {
          this.joyrideService.startTour({ steps: ['noStepsAv'] });
        }
        break;
      }
    }
  }

  private getBreadcrumbs(route: ActivatedRoute, url = '', breadcrumbs: Breadcrumb[] = []): Breadcrumb[] {
    const ROUTE_DATA_BREADCRUMB = 'breadcrumb';

    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {
      if (child.outlet !== PRIMARY_OUTLET) {
        continue;
      }

      if (!child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
        return this.getBreadcrumbs(child, url, breadcrumbs);
      }

      const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');

      url += `/${routeURL}`;

      const breadcrumb: Breadcrumb = {
        label: child.snapshot.data[ROUTE_DATA_BREADCRUMB],
        params: child.snapshot.params,
        url,
      };
      if (breadcrumb.label) {
        breadcrumbs.push(breadcrumb);
      }
      return this.getBreadcrumbs(child, url, breadcrumbs);
    }
    return [];
  }
  /* backClicked() {
    this.location.back();
  } */
}
