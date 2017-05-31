import { Component, OnInit } from '@angular/core';
import { User } from '../_models/index';
import { UserService } from '../_services/index';
import { Light } from '../_models/light';
import { Workstation } from '../_models/workstation';
import { PlotComponent } from '../Plot/plot.component';
import { SidenavComponent } from "../sidenav/sidenav.component";

@Component({
    moduleId: module.id,
    templateUrl: 'homeSystAdmin.component.html'
})

export class HomeComponentSysAdmin implements OnInit{
    title = 'Home';
    currentUser: User;
    selectedWs: Workstation;
    constructor(private userService: UserService) {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    ngOnInit() {
    }
}
