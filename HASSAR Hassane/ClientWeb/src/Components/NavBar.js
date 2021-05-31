import React from 'react';
import { Menubar } from 'primereact/menubar';

export default function NavBar() {
    const items = [
        {
            label: 'Map',
            icon: 'pi pi-fw pi-map',
            command: () => {window.location = "/map"}
        },
        {
            label: 'La liste des utilisateurs',
            icon: 'pi pi-fw pi-users',
            command: () => {window.location = "/"}
        }
    ];

    return (
        <div>
            <div className="card">
                <Menubar model={items}/>
            </div>
        </div>
    );
}