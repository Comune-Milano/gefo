import { Injectable, OnInit } from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';

@Injectable({ providedIn: 'root' })
export class RolesCheckService {
  endpoints: Array<string>;
  constructor(private roleService: ProfilesService) {
    this.endpoints = [];
  }

  setEndpoints(ep: Array<any>): void {
    this.endpoints = ep;
    localStorage.setItem('listEp', JSON.stringify(this.endpoints));
  }

  isPresent(ep: string): boolean {
    return this.endpoints.includes(ep);
  }
}
