<template>
    <nav class="navbar navbar-expand-lg " style="background: linear-gradient(to right, rgb(49, 11, 76) , rgb(13, 35, 99));">
    <div class="container-fluid">
        <router-link class="navbar-brand text-white" aria-current="page" style="color:rgb(255, 241, 214)" to="/">KLN Crud App</router-link>
        <!-- <a class="navbar-brand" href="#" style="color:rgb(255, 241, 214)">KLN Crud App</a> -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item ">
                    <router-link class="nav-link text-white" aria-current="page" style="color:rgb(255, 241, 214)" to="/warehouses">Warehouse</router-link>
                    <!-- <a class="nav-link text-white" aria-current="page" href="/warehouses" style="color:rgb(255, 241, 214)">Warehouse</a> -->
                </li>
                <li class="nav-item">
                    <router-link class="nav-link text-white" aria-current="page" style="color:rgb(255, 241, 214)" to="/stocks">Stock</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link text-white" aria-current="page" style="color:rgb(255, 241, 214)" to="/offices">Office</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link text-white" aria-current="page" style="color:rgb(255, 241, 214)" to="/invoices">Company</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link text-white" aria-current="page" style="color:rgb(255, 241, 214)" to="/customers">Customer</router-link>
                </li>
            </ul>
        </div>
    </div>
</nav>
</template>

<script>
export default{
    
}
</script>

<style>

</style>