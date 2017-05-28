import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptionsArgs } from '@angular/http';
import { Device } from '../_models/device';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {labConConfig} from 'labCon-config';

@Injectable()
export class DeviceService {
    private devicesUrl = '/assets/devices.json';
    private restServerApiUrl = labConConfig.restServerApiUrl + '/devices/';
    private dev: Device;
    constructor(private http: Http) {
    }
    
    getAllDevices(): Observable<Device[]> {
        return this.http.get(this.devicesUrl).map(this.extractAllDevices).catch(this.handleError);
    }

    private extractAllDevices(res: Response) {
        let body = res.json();
        return body.Device || { };
    }

    private handleError(error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Promise.reject(errMsg);
    }

    getDevice(id: number, typeName: String): Promise<Device> {
        return this.getAllDevices().toPromise().then(Device => Device.find(element => element.id === id && element.name===typeName))      
    }

}
