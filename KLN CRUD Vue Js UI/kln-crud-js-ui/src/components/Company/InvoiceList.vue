<template>
    <h3>
        List of Invoices
        <button class="btn" style="background-color:gold; float:right; margin-right: 10px;" @click="showTheFilterModal">
            <span style="font-weight:700; color:white">Filter</span>
        </button>

        <button v-if="filterKey" class="btn btn-danger" style="float:right" @click="cancelFilter">
            <span style="font-weight:700; color:white">Filter x</span>
        </button>
    </h3>
    

    <!--Filtre Yoksa-->
    <p  v-if="invoiceMasters==null"><em>Loading...</em></p>
    <table  v-else-if="invoiceMasters!=null && filterKey==false" class="table" style="margin-top: 25px;">
        <thead class="bg-dark text-white">
            <tr>
                    <th>Id</th>
                    <th>Customer Name</th>
                    <th>Customer Tax Administration</th>
                    <th>Customer Bank Account Number</th>
                    <th>Date</th>
                    <th>String Price</th>
                    <th>Price</th>
                    <th>VAT</th>
                    <th>Total Price</th>
                    <th>Office Id</th>
                    <th>Customer Id</th>
                    <th><!--Detail--></th>
                </tr>
        </thead>
        <tbody>
            <tr v-for="invoiceMaster in invoiceMasters" :key="invoiceMaster.id"> 
                <td>{{invoiceMaster.id}}</td>
                <td>{{invoiceMaster.customerName}}</td>
                <td>{{invoiceMaster.customerTaxAdministration}}</td>
                <td>{{invoiceMaster.customerBankAccountNumber}}</td>
                <td>{{invoiceMaster.date}}</td>
                <td>{{invoiceMaster.stringPrice}}</td>
                <td>{{invoiceMaster.price}}</td>
                <td>{{invoiceMaster.vat}}</td>
                <td>{{invoiceMaster.totalPrice}}</td>
                <td>{{invoiceMaster.officeId}}</td>
                <td>{{invoiceMaster.customerId}}</td>
                <td><button class="btn btn-primary" @click="showDetailModal(invoiceMaster.id)">Detail</button></td>              
            </tr>
        </tbody>
    </table>

    <!--Filtre varsa-->
    <table  v-if="filterKey==true" class="table" style="margin-top: 25px;">
        <thead class="bg-dark text-white">
            <tr>
                    <th>Id</th>
                    <th>Customer Name</th>
                    <th>Customer Tax Administration</th>
                    <th>Customer Bank Account Number</th>
                    <th>Date</th>
                    <th>String Price</th>
                    <th>Price</th>
                    <th>VAT</th>
                    <th>Total Price</th>
                    <th>Office Id</th>
                    <th>Customer Id</th>
                    <th><!--Detail--></th>
                </tr>
        </thead>
        <tbody>
            <tr v-for="invoiceMaster in filteredInvoiceMasters" :key="invoiceMaster.id"> 
                    <td>{{invoiceMaster.id}}</td>
                    <td>{{invoiceMaster.customerName}}</td>
                    <td>{{invoiceMaster.customerTaxAdministration}}</td>
                    <td>{{invoiceMaster.customerBankAccountNumber}}</td>
                    <td>{{invoiceMaster.date}}</td>
                    <td>{{invoiceMaster.stringPrice}}</td>
                    <td>{{invoiceMaster.price}}</td>
                    <td>{{invoiceMaster.vat}}</td>
                    <td>{{invoiceMaster.totalPrice}}</td>
                    <td>{{invoiceMaster.officeId}}</td>
                    <td>{{invoiceMaster.customerId}}</td>
                    <td><button class="btn btn-primary" @click="showDetailModal(invoiceMaster.id)">Detail</button></td>           
            </tr>
        </tbody>
    </table>

    <dialog-box
    :title="'Details'"
    v-model:visible="detailForInvoice"
    :okButton="{text:'close', onclick:()=>{detailForInvoice=false;invoiceMasterId=0;}}"
    :cancelButton="{text:'close', onclick:()=>{detailForInvoice=false;invoiceMasterId=0;}}"
    >
    <!---->
    <table class="table">
        <thead class="bg-dark text-white">
            <tr>
                <th>Id</th>
                <th>Product Name</th>
                <th>Pieces</th>
                <th>Price</th>
                <th>Amount</th>
                <th>InvoiceMaster Id</th>
            </tr>
        </thead>
        <tbody>
                <tr v-for="invoiceDetail in invoiceDetailsForShowing" :key="invoiceDetail.id">
                    <td>{{invoiceDetail.id}}</td>
                    <td>{{invoiceDetail.productName}}</td>
                    <td>{{invoiceDetail.pieces}}</td>
                    <td>{{invoiceDetail.price}} $</td>
                    <td>{{invoiceDetail.amount}}</td>
                    <td>{{invoiceDetail.invoiceMasterId}}</td>
                </tr>
        </tbody>
    </table>

    </dialog-box>

    <dialog-box
    :title="'Filter By Company'"
    v-model:visible="isFilter"
    :okButton="{text:'filter', onclick:setFilteredInvoices}"
    :cancelButton="{text:'cancel', onclick:()=>{isFilter=false;companyIdForFilter=0;}}"
    >
        <div class="form-control">
            <label for="companyIdForFilter">Company: </label>
            <select id="companyIdForFilter" name="companyIdForFilter" v-model="companyIdForFilter" >
            <option v-for="company in companies" :value="company.id" :key="company.id" >{{company.name}}</option>
            </select>
        </div>
    </dialog-box>

</template>
<script>
import axios from 'axios';
export default{
    /*
        BURAYA FATURA DETAYI GÖRÜNTÜLEME VE ŞİRKETE GÖRE FİLTRELEME ÖZELLİĞİ EKLENECEK
    */
    data(){
        return{     
            /*offices:[
                {id:1, name:"Konak Ofisi", address:"İzmir", companyId:"1"},
                {id:2, name:"Melikgazi Ofisi", address:"Kayseri", companyId:"2"},
                {id:3, name:"Alaaddin Keykubad Ofisi", address:"Antalya", companyId:"3"},
            ],
            invoiceMasters:
            [
                {
                    "id": 1,
                    "customerName": "Mert Sığırcı",
                    "customerTaxAdministration": "00000001",
                    "customerBankAccountNumber": "1100000011",
                    "date": "2022-10-08",
                    "stringPrice": "onsekizbin",
                    "price": 9000,
                    "vat": 1,
                    "totalPrice": 18000,
                    "officeId": 1,
                    "customerId": 1
                },
                {
                    "id": 2,
                    "customerName": "İsmail Eren Büyükyabat",
                    "customerTaxAdministration": "00000002",
                    "customerBankAccountNumber": "1100000012",
                    "date": "2022-10-08",
                    "stringPrice": "otuzüçbin",
                    "price": 16500,
                    "vat": 1,
                    "totalPrice": 33000,
                    "officeId": 2,
                    "customerId": 2
                },
                {
                    "id": 3,
                    "customerName": "Serdar Kaplan",
                    "customerTaxAdministration": "00000003",
                    "customerBankAccountNumber": "1100000014",
                    "date": "2022-10-08",
                    "stringPrice": "otuzyedibin",
                    "price": 18500,
                    "vat": 1,
                    "totalPrice": 37000,
                    "officeId": 3,
                    "customerId": 3
                },
            ], 
            invoiceDetails:
            [
                {
                    "id": 1,
                    "productName": "Dishwasher v1.1",
                    "pieces": 1,
                    "price": 4000,
                    "amount": 4000,
                    "invoiceMasterId": 1
                },
                {
                    "id": 2,
                    "productName": "Dishwasher v2.0",
                    "pieces": 1,
                    "price": 5000,
                    "amount": 5000,
                    "invoiceMasterId": 1
                },
                {
                    "id": 3,
                    "productName": "Fridge v2.0",
                    "pieces": 1,
                    "price": 8500,
                    "amount": 8500,
                    "invoiceMasterId": 2
                },
                {
                    "id": 4,
                    "productName": "Fridge v1.1",
                    "pieces": 1,
                    "price": 8000,
                    "amount": 8000,
                    "invoiceMasterId": 2
                },
                {
                    "id": 5,
                    "productName": "Fridge v2.0",
                    "pieces": 1,
                    "price": 8500,
                    "amount": 8500,
                    "invoiceMasterId": 3
                },
                {
                    "id": 6,
                    "productName": "Fridge v2.1",
                    "pieces": 1,
                    "price": 10000,
                    "amount": 10000,
                    "invoiceMasterId": 3
                },
            ],
            companies:[
                {id:1, name:"Beko"},
                {id:2, name:"Arcelik"},
                {id:3, name:"Bosch"},
            ],*/
            offices:[],
            invoiceMasters:[],
            invoiceDetails:[],
            companies:[],

            filteredOffices:[],
            filteredInvoiceMasters:[],
            detailForInvoice:false,
            invoiceMasterId:0,
            invoiceDetailsForShowing:[],
            filterKey:false,
            isFilter:false,
            companyIdForFilter:0,
        }
    },
    methods:{
        showDetailModal(invoiceMasterId){
            this.invoiceMasterId = invoiceMasterId;
            this.detailForInvoice = true;
            this.invoiceDetailsForShowing = this.invoiceDetails.filter(detail=>detail.invoiceMasterId == invoiceMasterId)
        },
        showTheFilterModal(){
            this.isFilter=true;
        },
        cancelFilter(){
            this.filterKey=false;
            this.filteredInvoiceMasters=[];
            this.companyIdForFilter=0;
        },
        setFilteredInvoices(){
            let filteredOffices = this.offices.filter(office=>office.companyId==this.companyIdForFilter);
            
            for(let office of filteredOffices){
                for(let item of this.invoiceMasters){
                    if(office.id == item.officeId){
                        this.filteredInvoiceMasters.push(item);
                    }
                }
            }
            this.filterKey=true;
            this.isFilter=false;
        },
    },
    created(){
        axios.get("http://localhost:8080/api/offices").then(response=>{
            for(let item of response.data){
                this.offices.push(item);
            }
        });

        axios.get("http://localhost:8080/api/invoicemasters").then(response=>{
            for(let item of response.data){
                this.invoiceMasters.push(item);
            }
        });

        axios.get("http://localhost:8080/api/invoicedetails").then(response=>{
            for(let item of response.data){
                this.invoiceDetails.push(item);
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
<style scoped>


</style>