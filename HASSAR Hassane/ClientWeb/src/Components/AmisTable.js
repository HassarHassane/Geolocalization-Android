import React, { useState, useEffect, useRef } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import UserService from '../Services/UserService'
import './UserTable.css';


export default function AmiTable(props){
    const [users, setUsers] = useState(null);
    const [globalFilter, setGlobalFilter] = useState(null);
    const dt = useRef(null);

    const userService = new UserService();

    useEffect(() => {
        userService.getAllAmis(props.location.id).then(data => setUsers(data.data));
    }, []);

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
                </DataTable>
            </div>
        </div>
    );
}