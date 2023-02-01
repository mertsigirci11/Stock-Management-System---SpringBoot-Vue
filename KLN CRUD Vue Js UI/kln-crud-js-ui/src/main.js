import { createApp } from 'vue';
import App from './App.vue';
import { Modal } from 'usemodal-vue3';

import { createRouter, createWebHistory } from 'vue-router';

import "bootstrap/dist/css/bootstrap.min.css";

import NavigationBar from './components/NavBar/NavigationBar.vue';
import WarehouseList from './components/Warehouse/WarehouseList.vue';
import StockList from './components/Stock/StockList.vue';
import OfficeList from './components/Office/OfficeList.vue';
import IndexPage from './components/Index/IndexPage.vue';
import CustomerList from './components/Customer/CustomerList.vue';
import Company from './components/Company/InvoiceList.vue'
import ManageCustomer from './components/Customer/ManageCustomer.vue';

const router = createRouter({
    history:createWebHistory(),
    routes:[
        {
            name:"warehouses",
            path:"/warehouses",
            component: WarehouseList,
        },
        {
            name:"stocks",
            path:"/stocks",
            component: StockList,
        },
        {
            name:"offices",
            path:"/offices",
            component: OfficeList,
        },
        {
            name:"index",
            path:"/",
            component: IndexPage,
        },
        {
            name:"customers",
            path:"/customers",
            component: CustomerList,
        },
        {
            name:"invoices",
            path:"/invoices",
            component:Company,
        },
        {
            name:"manage-customer",
            path:"/manage-customer",
            component:ManageCustomer,
        },
    ],
});



const app = createApp(App);
app.component("navigation-bar", NavigationBar);
app.component("dialog-box", Modal);
app.use(router);
app.mount('#app');
