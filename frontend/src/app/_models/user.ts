﻿import { Device } from './index';
export class User {
    id: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    devices: Device[];
}