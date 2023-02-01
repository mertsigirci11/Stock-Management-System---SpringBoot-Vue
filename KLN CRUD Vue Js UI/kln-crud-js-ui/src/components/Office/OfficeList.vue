<template>
    <button class="btn btn-success" style="float:Left; margin-left: 10px;" @click="showAddingBox">
        <span style="font-weight:700; color:white">Create Office</span>
    </button>
    <br>
    <p  v-if="offices==null"><em>Loading...</em></p>
    <table  v-else class="table" style="margin-top: 25px;">
        <thead class="bg-dark text-white">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Address</th>
                <th>Company Id</th>
                <th><!--Edit--></th>
                <th><!--Delete--></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="office in offices" :key="office.id">
                <td>{{office.id}}</td>
                <td>{{office.name}}</td>
                <td>{{office.address}}</td>
                <td>{{office.companyId}}</td>
                <td><button class="btn btn-info" @click="showTheEditModal(office.id)">Edit</button></td>
                <td><button class="btn btn-danger" @click="takeIdForDelete(office.id)">Delete</button></td>
            </tr>
        </tbody>
    </table>

    <!-- DELETE -->
    <dialog-box 
    :title="'WARNING !'" 
    v-model:visible="isVisibleForDelete"
    :okButton="{text:'Yes', onclick:()=>{
        this.offices = this.offices.filter(office=> office.id != officeId);
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

    <!-- UPDATE -->
    <dialog-box
    :title="'Updating the office'"
    v-model:visible="isVisibleForUpdate"
    :okButton="{text:'Save', onclick:btnClickForUpdate}"
    :cancelButton="{text:'Cancel', onclick:resetInformations}">
        <form @submit.prevent="submitForm">
        <div class="form-control">
            <label for="officeName">Office Name: </label>
            <input id="officeName" name="officeName" type="text" v-model="officeName"/>
        </div>
        <div class="form-control">
            <label for="officeAddress">Address</label>
            <input id="officeAddress" name="officeAddress" type="text" v-model="officeAddress"/>
        </div>
        <div class="form-control">
            <label for="officeCompanyId">Company: </label>
            <select id="officeCompanyId" name="officeCompanyId" v-model="officeCompanyId" >
            <option v-for="company in companies" :value="company.id" :key="company.id" >{{company.name}}</option>
            </select>
        </div>
    </form>
    </dialog-box>

    <!-- ADD -->
    <dialog-box 
    :title="'Add New Office'" 
    v-model:visible="isVisibleForAdd"
    :okButton="{text:'Yes', onclick:btnClickForAdd, loading:false}"
    :cancelButton="{text:'No', onclick:()=>{
        this.isVisibleForAdd=false;
    }, loading:false}">
        <div>
            <form @submit.prevent="saveAdding">
                <div class="form-control">
                    <label for="officeName">Office Name</label>
                    <input id="officeName" name="officeName" type="text" v-model="officeName"/>
                </div>

                <div class="form-control">
                    <label for="officeAddress">Office Address</label>
                    <input id="officeAddress" name="officeAddress" type="text" v-model="officeAddress"/>
                </div>

                <div class="form-control">
                    <label for="officeCompanyId">Company: </label>
                    <select id="officeCompanyId" name="officeCompanyId" v-model="officeCompanyId" >
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
            /*offices:[
                {id:1, name:"Konak Ofisi", address:"İzmir", companyId:"1"},
                {id:2, name:"Melikgazi Ofisi", address:"Kayseri", companyId:"2"},
                {id:3, name:"Alaaddin Keykubad Ofisi", address:"Antalya", companyId:"3"},
            ],*/
            offices:[],
            companies:[],
            /*companies:[
                {id:1, name:"Beko"},
                {id:2, name:"Arcelik"},
                {id:3, name:"Bosch"},
            ],*/
            officeId:0,
            officeName:"",
            officeAddress:"",
            officeCompanyId:0,
            isVisibleForDelete:false,
            isVisibleForUpdate:false,
            isVisibleForAdd:false,
        }
    },
    methods:{
        showTheEditModal(officeId){
            this.officeId = officeId;
            let office = this.offices.filter(office=>office.id == officeId)[0];
            this.officeName = office.name;
            this.officeAddress = office.address;
            this.officeCompanyId = office.companyId;
            this.isVisibleForUpdate=true;
        },
        takeIdForDelete(officeId){
            this.officeId = officeId;
            this.isVisibleForDelete=true;
            //this.offices = this.offices.filter(office=> office.id != officeId);
        },
        showAddingBox(){
            this.resetInformations();
            this.isVisibleForAdd=true;
        },
        btnClickForUpdate(){
            for(var item of this.offices){
                if(item.id==this.officeId){
                    item.name=this.officeName;
                    item.address=this.officeAddress;
                    item.companyId=this.officeCompanyId;
                }
            }

            axios.put(`http://localhost:8080/api/offices/${this.officeId}`,
            {
                name:`${this.officeName}`,
                address:`${this.officeAddress}`,
                companyId:`${this.officeCompanyId}`
            });

            this.isVisibleForUpdate=false;
        },
        btnClickForAdd(){
            //this.offices.push({id:this.offices.length+1, name:this.officeName, address:this.officeAddress, companyId:this.officeCompanyId});
           
            axios.post("http://localhost:8080/api/offices",
                {
                    name:`${this.officeName}`,
                    address:`${this.officeAddress}`,
                    companyId:`${this.officeCompanyId}`
                });
            
            this.officeName = "";
            this.officeAddress = "";
            this.officeCompanyId = 0;
            this.isVisibleForAdd = false;
            window.location.reload();
        },
        deleteFromDb(){
            axios.delete(`http://localhost:8080/api/offices/${this.officeId}`);
        },
        resetInformations()
        {
            this.officeName = "";
            this.officeAddress = "";
            this.officeCompanyId = 0;
            this.isVisibleForUpdate = false;
            this.isVisibleForDelete = false;
            this.isVisibleForAdd = false;
        }
    },
    created(){
        axios.get("http://localhost:8080/api/offices").then(response=>{
            for(let item of response.data){
                this.offices.push(item);
            }
        });

        axios.get("http://localhost:8080/api/companies").then(response=>{
            for(let item of response.data){
                this.companies.push(item);
            }
        });
    }
}
</script>

<style>
</style>