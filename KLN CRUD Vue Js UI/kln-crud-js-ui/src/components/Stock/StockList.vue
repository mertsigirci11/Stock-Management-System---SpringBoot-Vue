<template>
    <button class="btn btn-success" style="float:Left; margin-left: 10px;" @click="showAddingBox">
        <span style="font-weight:700; color:white">Create Stock</span>
    </button>
    <br>
    <p  v-if="stocks==null"><em>Loading...</em></p>
    <table  v-else class="table" style="margin-top: 25px;">
        <thead class="bg-dark text-white">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Pieces</th>
                <th>Warehouse Id</th>
                <th><!--Edit--></th>
                <th><!--Delete--></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="stock in stocks" :key="stock.id">
                <td>{{stock.id}}</td>
                <td>{{stock.name}}</td>
                <td>{{stock.price}}</td>
                <td>{{stock.piece}}</td>
                <td>{{stock.warehouseId}}</td>
                <td><button class="btn btn-info" @click="showTheEditModal(stock.id)">Edit</button></td>
                <td><button class="btn btn-danger" @click="takeIdForDelete(stock.id)">Delete</button></td>
            </tr>
        </tbody>
    </table>

    <!-- DELETE -->
    <dialog-box 
    :title="'WARNING !'" 
    v-model:visible="isVisibleForDelete"
    :okButton="{text:'Yes', onclick:()=>{
        this.stocks = this.stocks.filter(stock=> stock.id != stockId);
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
    :title="'Updating the stock'"
    v-model:visible="isVisibleForUpdate"
    :okButton="{text:'Save', onclick:btnClickForUpdate}"
    :cancelButton="{text:'Cancel', onclick:resetInformations}">
        <form @submit.prevent="submitForm">
        <div class="form-control" :class="{invalid:stockNameValidity=='invalid'}">
            <label for="stockName">Stock Name</label>
            <input id="stockName" name="stockName" type="text" v-model="stockName" @blur="validateInput"/>
            <label v-if="stockNameValidity=='invalid'">Please enter a valid name!</label>
        </div>
        <div class="form-control">
            <label for="stockPrice">Price</label>
            <input id="stockPrice" name="stockPrice" type="number" v-model="stockPrice"/>
        </div>
        <div class="form-control">
            <label for="stockPiece">Piece</label>
            <input id="stockPiece" name="stockPiece" type="number" v-model="stockPiece"/>
        </div>
        <div class="form-control">
            <label for="stockWarehouseId">Warehouse: </label>
            <select id="stockWarehouseId" name="stockWarehouseId" v-model="stockWarehouseId" >
            <option v-for="warehouse in warehouses" :value="warehouse.id" :key="warehouse.id" >{{warehouse.name}}</option>
            </select>
        </div>
    </form>
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
                <div class="form-control" :class="{invalid:stockNameValidity=='invalid'}">
                    <label for="stockName">Stock Name</label>
                    <input id="stockName" name="stockName" type="text" v-model="stockName" @blur="validateInput"/>
                    <label v-if="stockNameValidity=='invalid'">Please enter a valid name!</label>
                </div>
                <div class="form-control">
                    <label for="stockPrice">Stock Price</label>
                    <input id="stockPrice" name="stockPrice" type="number" v-model="stockPrice"/>
                </div>
                <div class="form-control">
                    <label for="stockPiece">Stock Piece</label>
                    <input id="stockPiece" name="stockPiece" type="number" v-model="stockPiece"/>
                </div>

                <div class="form-control">
                    <label for="stockWarehouseId">Warehouse: </label>
                    <select id="stockWarehouseId" name="stockWarehouseId" v-model="stockWarehouseId" >
                    <option v-for="warehouse in warehouses" :value="warehouse.id" :key="warehouse.id" >{{warehouse.name}}</option>
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

            /*stocks:[
                {id:1, name:"Phone", price:50, piece:540, warehouseId:"1"},
                {id:2, name:"Notebook", price:40, piece:250, warehouseId:"2"},
                {id:3, name:"Fridge", price:110, piece:30, warehouseId:"3"},
            ],*/
            stocks:[],
            /*warehouses:[
                {id:1, name:"Sakarya Deposu", companyId:"1"},
                {id:2, name:"İstanbul Deposu", companyId:"2"},
                {id:3, name:"Kocaeli Deposu", companyId:"3"},
            ],*/
            warehouses:[],
            isVisibleForDelete:false,
            isVisibleForUpdate:false,
            isVisibleForAdd:false,
            stockNameValidity:"pending",
            stockId:0,
            stockName:"",
            stockPrice:0,
            stockPiece:0,
            stockWarehouseId:0,
        }
    },
    methods:{
        showTheEditModal(stockId){
            this.stockId = stockId;
            let stock = this.stocks.filter(stock=>stock.id==stockId);
            this.stockName = stock[0].name;
            this.stockPrice = stock[0].price;
            this.stockPiece = stock[0].piece;
            this.stockWarehouseId = stock[0].warehouseId;
            this.isVisibleForUpdate=true;
        },
        takeIdForDelete(stockId){

            this.stockId = stockId;
            this.isVisibleForDelete = true;
            //this.stocks = this.stocks.filter(stock=> stock.id != stockId);
        },
        btnClickForUpdate(){
            for(var item of this.stocks){
                if(item.id==this.stockId){
                    item.name=this.stockName;
                    item.warehouseId=this.stockWarehouseId;
                    item.price=this.stockPrice;
                    item.piece=this.stockPiece;
                }
            }

            axios.put(`http://localhost:8080/api/stocks/${this.stockId}`,
            {
                name:`${this.stockName}`,
                price:`${this.stockPrice}`,
                piece:`${this.stockPiece}`,
                warehouseId:`${this.stockWarehouseId}`,
            });

            this.isVisibleForUpdate=false;
        },
        submitForm(){

        },
        showAddingBox(){
            this.resetInformations();
            this.isVisibleForAdd=true;
        },
        btnClickForAdd(){
            //this.stocks.push({id:this.stocks.length+1, name:this.stockName, price:this.stockPrice, piece:this.stockPiece, warehouseId:this.stockWarehouseId})
            axios.post("http://localhost:8080/api/stocks",
                {
                    name:`${this.stockName}`,
                    price:`${this.stockPrice}`,
                    piece:`${this.stockPiece}`,
                    warehouseId:`${this.stockWarehouseId}`,
                });

            this.stockId=0;
            this.stockName="";
            this.stockPiece=0;
            this.stockPrice=0;
            this.stockWarehouseId=0;
            this.isVisibleForAdd=false;
            window.location.reload();
        },
        deleteFromDb(){
            axios.delete(`http://localhost:8080/api/stocks/${this.stockId}`);
        },
        resetInformations()
        {
            this.stockId = 0;
            this.stockName = "";
            this.stockPiece = 0;
            this.stockPrice = 0;
            this.stockWarehouseId =0;
            this.isVisibleForUpdate = false;
            this.isVisibleForAdd = false;
            this.isVisibleForDelete = false;
        }

    },
    created(){
        axios.get("http://localhost:8080/api/warehouses").then(response=>{
            for(let item of response.data){
                this.warehouses.push(item);
            }
        });

        axios.get("http://localhost:8080/api/stocks").then(response=>{
            for(let item of response.data){
                this.stocks.push(item);
            }
        });
    },
}
</script>

<style>
</style>