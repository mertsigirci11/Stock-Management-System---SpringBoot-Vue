<template>
    <h3>
        Manage Customers
        <router-link aria-current="page" to="/customers">
            <button class="btn" style="background-color:orange">
                <span style="font-weight:700; color:white">Back To Invoices</span>
            </button>
        </router-link>
        <button class="btn" style="background-color:limegreen; float:right; margin-right: 10px;" @click="showTheAddModal">
            <span style="font-weight:700; color:white">Create</span>
        </button>
    </h3>

    <table class="table">
        <thead class="bg-dark text-white">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Surname</th>
                <th>username</th>
                <th>Password</th>
                <th>Tax Administration</th>
                <th><!--Edit--></th>
                <th><!--Delete--></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="customer in customers" :key="customer.id">
                <td>{{customer.id}}</td>
                <td>{{customer.name}}</td>
                <td>{{customer.surname}}</td>
                <td>{{customer.username}}</td>
                <td>{{customer.password}}</td>
                <td>{{customer.taxAdministration}}</td>
                <td><button class="btn btn-info" @click="showTheEditModal(customer.id)">Edit</button></td>
                <td><button class="btn btn-danger" @click="takeIdForDelete(customer.id)">Delete</button></td>
            </tr>
        </tbody>
    </table>

    <!--Delete-->
    <dialog-box
    :title="'WARNING !'" 
    v-model:visible="isVisibleForDelete"
    :okButton="{text:'Yes', onclick:()=>{
        this.customers = this.customers.filter(customer=> customer.id != customerId);
        deleteFromDb();
        this.isVisibleForDelete=false;
    }, loading:false}"
    :cancelButton="{text:'No', onclick:()=>{
        this.isVisibleForDelete=false;
    }, loading:false}">
        <div>
            <p>Are you sure about delete operation?</p>
        </div>
    </dialog-box>

    <!--Update-->
    <dialog-box
    :title="'Updating the customer'" 
    v-model:visible="isVisibleForUpdate"
    :okButton="{text:'Save', onclick:btnClickForUpdate, loading:false}"
    :cancelButton="{text:'Cancel', onclick:resetInformations}">

    <form @submit.prevent="submitForm">
        <div class="form-control">
            <label for="customerName">Customer Name</label>
            <input id="customerName" name="customerName" type="text" v-model="customerName"/>
        </div>
        <div class="form-control">
            <label for="customerSurname">Customer Surname</label>
            <input id="customerSurname" name="customerSurname" type="text" v-model="customerSurname"/>
        </div>
        <div class="form-control">
            <label for="customerUsername">Customer Username</label>
            <input id="customerUsername" name="customerUsername" type="text" v-model="customerUsername"/>
        </div>
        <div class="form-control">
            <label for="customerPassword">Customer Password</label>
            <input id="customerPassword" name="customerPassword" type="text" v-model="customerPassword"/>
        </div>
        <div class="form-control">
            <label for="customerTaxAdm">Customer Tax Administration</label>
            <input id="customerTaxAdm" name="customerTaxAdm" type="text" v-model="customerTaxAdm"/>
        </div>
    </form>

    </dialog-box>

    <!--Create-->
    <dialog-box
    :title="'Add new customer'" 
    v-model:visible="isVisibleForAdd"
    :okButton="{text:'Save', onclick:btnClickForAdd, loading:false}"
    :cancelButton="{text:'Cancel', onclick:resetInformations}">

    <form @submit.prevent="submitForm">
        <div class="form-control">
            <label for="customerName">Customer Name</label>
            <input id="customerName" name="customerName" type="text" v-model="customerName"/>
        </div>
        <div class="form-control">
            <label for="customerSurname">Customer Surname</label>
            <input id="customerSurname" name="customerSurname" type="text" v-model="customerSurname"/>
        </div>
        <div class="form-control">
            <label for="customerUsername">Customer Username</label>
            <input id="customerUsername" name="customerUsername" type="text" v-model="customerUsername"/>
        </div>
        <div class="form-control">
            <label for="customerPassword">Customer Password</label>
            <input id="customerPassword" name="customerPassword" type="text" v-model="customerPassword"/>
        </div>
        <div class="form-control">
            <label for="customerTaxAdm">Customer Tax Administration</label>
            <input id="customerTaxAdm" name="customerTaxAdm" type="text" v-model="customerTaxAdm"/>
        </div>
    </form>

    </dialog-box>

</template>

<script>
import axios from 'axios';
export default{
    data(){
        return{
            /*customers:[
            {
                "id": 1,
                "name": "Mert",
                "surname": "Sığırcı",
                "username": "msigirci",
                "password": "123",
                "taxAdministration": "00000001"
            },
            {
                "id": 4,
                "name": "Serdar",
                "surname": "Kaplan",
                "username": "skaplan",
                "password": "123",
                "taxAdministration": "00000003"
            },
            {
                "id": 2,
                "name": "İsmail Eren",
                "surname": "Büyükyabat",
                "username": "ierenb",
                "password": "123",
                "taxAdministration": "00000002"
            },
            ],*/
            customers:[],
            isVisibleForDelete:false,
            isVisibleForUpdate:false,
            isVisibleForAdd:false,
            customerId:0,
            customerName:"",
            customerSurname:"",
            customerUsername:"",
            customerPassword:"",
            customerTaxAdm:"",


        }
    },
    methods:{
        takeIdForDelete(customerId){
            this.customerId = customerId;
            this.isVisibleForDelete = true;
        },
        showTheEditModal(customerId){
            this.customerId = customerId;

            let y = this.customers.filter(item=> item.id == this.customerId)[0];
            this.customerName = y.name;
            this.customerSurname = y.surname;
            this.customerPassword = y.password;
            this.customerTaxAdm = y.taxAdministration;
            this.customerUsername = y.username;

            this.isVisibleForUpdate = true;
        },
        showTheAddModal(){
            this.resetInformations();
            this.isVisibleForAdd = true;
        },
        btnClickForUpdate(){
            let y = this.customers.filter(item=> item.id == this.customerId)[0];
            y.name = this.customerName;
            y.surname = this.customerSurname;
            y.password = this.customerPassword;
            y.taxAdministration = this.customerTaxAdm;
            y.username = this.customerUsername;

            axios.put(`http://localhost:8080/api/customers/${this.customerId}`,
            {
                name:`${this.customerName}`,
                surname:`${this.customerSurname}`,
                username:`${this.customerUsername}`,
                password:`${this.customerPassword}`,
                taxAdministration:`${this.customerTaxAdm}`
            });

            this.resetInformations();
        },
        btnClickForAdd(){
            axios.post("http://localhost:8080/api/customers",
                {
                    name:`${this.customerName}`,
                    surname:`${this.customerSurname}`,
                    username:`${this.customerUsername}`,
                    password:`${this.customerPassword}`,
                    taxAdministration:`${this.customerTaxAdm}`
                });

            /*this.customers.push(
                {
                    id: this.customers.length+1,
                    name: this.customerName,
                    surname: this.customerSurname,
                    username: this.customerUsername,
                    password: this.customerPassword,
                    taxAdministration: this.customerTaxAdm
                })*/

            this.resetInformations();
            window.location.reload();
        },
        deleteFromDb(){
            axios.delete(`http://localhost:8080/api/customers/${this.customerId}`);
        },
        resetInformations(){
            this.customerId = 0;
            this.customerName = "";
            this.customerSurname = "";
            this.customerPassword = "";
            this.customerTaxAdm = "";
            this.customerUsername = "";
            this.isVisibleForUpdate = false;
            this.isVisibleForAdd = false;
        }
    },
    created(){
        axios.get("http://localhost:8080/api/customers").then(response=>{
            for(let item of response.data){
                this.customers.push(item);
            }
        });
    },

}
</script>

<style>

</style>