<template>
    <button class="btn btn-success" style="float:Left; margin-left: 10px;" @click="showAddingBox">
        <span style="font-weight:700; color:white">Create Warehouse</span>
    </button>
    <br>
    <p  v-if="warehouses==null && warehouses1==null"><em>Loading...</em></p>
    <table  v-else class="table" style="margin-top: 25px;">
        <thead class="bg-dark text-white">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Company Id</th>
                <th><!--Edit--></th>
                <th><!--Delete--></th>
            </tr>
        </thead>
        <tbody >
            <tr v-for="warehouse in warehouses" :key="String(warehouse.id)">
                <td>{{warehouse.id}}</td>
                <td>{{warehouse.name}}</td>
                <td>{{warehouse.companyId}}</td>
                <td><button class="btn btn-info" @click="showTheEditModal(warehouse.id)">Edit</button></td>
                <td><button class="btn btn-danger" @click="takeIdForDelete(warehouse.id)">Delete</button></td>
            </tr>
        </tbody>  
    </table>

    <!-- UPDATE -->
    <dialog-box
    :title="'Updating the warehouse'"
    v-model:visible="isVisibleForUpdate"
    :okButton="{text:'Save', onclick:btnClickForUpdate}"
    :cancelButton="{text:'Cancel', onclick:resetInformations}">
        <form @submit.prevent="submitForm">
        <div class="form-control" :class="{invalid:warehouseNameValidity=='invalid'}">
            <label for="warehouseName">Warehouse Name</label>
            <input id="warehouseName" name="warehouseName" type="text" v-model="warehouseName" @blur="validateInput"/>
            <label v-if="warehouseNameValidity=='invalid'">Please enter a valid name!</label>
        </div>
        <div class="form-control">
            <label for="warehouseCompanyId">Company: </label>
            <select id="warehouseCompanyId" name="warehouseCompanyId" v-model="warehouseCompanyId" >
            <option v-for="company in companies" :value="company.id" :key="company.id" >{{company.name}}</option>
            </select>
        </div>

    </form>
    </dialog-box>

    <!-- DELETE -->
    <dialog-box 
    :title="'WARNING !'" 
    v-model:visible="isVisibleForDelete"
    :okButton="{text:'Yes', onclick:()=>{
        this.warehouses = this.warehouses.filter(warehouse=> warehouse.id != warehouseId);
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

    <!-- ADD -->
    <dialog-box 
    :title="'Add New Warehouse'" 
    v-model:visible="isVisibleForAdd"
    :okButton="{text:'Yes', onclick:btnClickForAdd, loading:false}"
    :cancelButton="{text:'No', onclick:()=>{
        this.isVisibleForAdd=false;
    }, loading:false}">
        <div>
            <form @submit.prevent="saveAdding">
                <div class="form-control" :class="{invalid:warehouseNameValidity=='invalid'}">
                <label for="warehouseName">Warehouse Name</label>
                <input id="warehouseName" name="warehouseName" type="text" v-model="warehouseName" @blur="validateInput"/>
                <label v-if="warehouseNameValidity=='invalid'">Please enter a valid name!</label>
            </div>
            <div class="form-control">
                <label for="warehouseCompanyId">Company: </label>
                <select id="warehouseCompanyId" name="warehouseCompanyId" v-model="warehouseCompanyId" >
                <option v-for="company in companies" :value="company.id" :key="company.id" >{{company.name}}</option>
                </select>
            </div>
            </form>
        </div>
    </dialog-box>
</template>

<script>
import axios from 'axios';
export default{
    data(){
        return{
            warehouseId:null,
            /*warehouses:[
                {id:1, name:"Sakarya Deposu", companyId:"1"},
                {id:2, name:"İstanbul Deposu", companyId:"2"},
                {id:3, name:"Kocaeli Deposu", companyId:"3"},
            ],*/
            warehouses:[],
            warehouses1:[],
            /*companies:[
                {id:1, name:"Beko"},
                {id:2, name:"Arcelik"},
                {id:3, name:"Bosch"},
            ],*/
            companies:[],
            isVisibleForDelete:false,
            isVisibleForAdd:false,
            isVisibleForUpdate:false,
            isChange:false,

            warehouseName:"",
            warehouseNameValidity:"pending",
            warehouseCompanyId:0,
            warehouseCompanyName:"",
            deneme:null,


        }
    },
    methods:{
        
        takeIdForDelete(warehouseId){
            this.warehouseId = warehouseId;
            this.isVisibleForDelete=true
        },
        submitForm(){
            console.log(this.deneme);
            console.log("Warehouse name: "+this.warehouseName);
            this.warehouseName="";
            console.log("Warehouse company id: "+this.warehouseCompanyId);
            this.warehouseCompanyId=0;
        },
        btnClickForUpdate(){
            //var objIndex = this.warehouses.findIndex(obj=>obj.id=this.warehouseId);
            
            for(var item of this.warehouses){
                if(item.id==this.warehouseId){
                    item.name=this.warehouseName;
                    item.companyId=this.warehouseCompanyId;
                }
            }
            axios.put(`http://localhost:8080/api/warehouses/${this.warehouseId}`,
            {
                name:`${this.warehouseName}`, 
                companyId:`${this.warehouseCompanyId}`
            });
            //warehouses[objIndex].name=warehouseName;
            //warehouses[objIndex].companyId=warehouseCompanyId;           
            this.isVisibleForUpdate=false;
        },
        showAddingBox(){
            this.isVisibleForAdd=true;
        },
        btnClickForAdd(){
            //this.warehouses.push({id:this.warehouses.length+1, name:`${this.warehouseName}`, companyId:`${this.warehouseCompanyId}`});
            axios.post("http://localhost:8080/api/warehouses",{name:`${this.warehouseName}`, companyId:`${this.warehouseCompanyId}`})
            
            this.warehouseName='';
            this.warehouseCompanyId=0;
            this.isVisibleForAdd=false;
            window.location.reload();
        },
        deleteFromDb(){
            axios.delete(`http://localhost:8080/api/warehouses/${this.warehouseId}`);
        },
        resetInformations()
        {
            this.warehouseCompanyId = 0;
            this.warehouseName = "";
            this.warehouseId = 0;
            this.isVisibleForUpdate = false;
        },
        showTheEditModal(warehouseId){
            console.log("id: " +warehouseId);

            this.warehouseId = warehouseId;
            let warehouse = this.warehouses.filter(warehouse=>warehouse.id==warehouseId);
            this.warehouseName=warehouse[0].name; 
            this.warehouseCompanyName=this.companies.filter(company=>company.id==warehouse[0].companyId)[0].name;
            this.warehouseCompanyId = warehouse[0].companyId;
            this.isVisibleForUpdate = true;
        },
    },
    created(){
        axios.get("http://localhost:8080/api/warehouses").then(response=>{
            for(let item of response.data){
                this.warehouses.push(item);
            }
        });

        axios.get("http://localhost:8080/api/companies").then(response=>{
            for(let item of response.data){
                this.companies.push(item);
            }
        });
    },
}
</script>
<style>
</style>