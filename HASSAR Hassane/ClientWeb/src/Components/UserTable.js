import React, { useState, useEffect, useRef } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import UserService from '../Services/UserService'
import {Button} from 'primereact/button';
import './UserTable.css';
import { Link } from 'react-router-dom';


export default function UserTable(){
    const [users, setUsers] = useState(null);
    const [globalFilter, setGlobalFilter] = useState(null);
    const dt = useRef(null);

    const userService = new UserService();

    useEffect(() => {
        userService.getAllUsers().then(data => setUsers(data.data));
    }, []);


    const actionBodyTemplate = (rowData) => {
        return (
            <Link to={{pathname: "/amis", id:rowData.id}}>
                <Button type="button" className="p-button-success" style={{width:'auto'}}>Amis</Button>
            </Link>
        );
    }

    
    return (
        <div className="datatable-doc-demo">
            <div className="card">
                <DataTable ref={dt} value={users}
                   className="p-datatable-users" dataKey="id" rowHover globalFilter={globalFilter}
                    paginator rows={10} emptyMessage="No users found" currentPageReportTemplate="Showing {first} to {last} of {totalRecords} entries"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" rowsPerPageOptions={[10,25,50]}>
                    <Column field="nom" header="Nom" sortable/>
                    <Column field="prenom" header="Prénom" sortable></Column>
                    <Column field="email" header="Email" sortable></Column>
                    <Column field="telephone" header="Telephone" sortable></Column>
                    <Column field="imei" header="IMEI" sortable></Column>
                    <Column field="sexe" header="Sexe" sortable ></Column>
                    <Column field="dateNaissance" header="date de naissance"sortable/>

                    <Column field="id" body={actionBodyTemplate} headerStyle={{width: '8em', textAlign: 'center'}} bodyStyle={{textAlign: 'center', overflow: 'visible'}} />
                </DataTable>
            </div>
        </div>
    );
}