<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
    import { lfdStore } from '../js/lfdStore.js';
    import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
    import LfdUrls from '../js/lfdUrlManager.js';

    let loggedIn = false;

    $: loggedIn = $lfdStore.loggedIn;

    function goHome() {
        GtgUtilsWeb.route(LfdUrls.spa_home);
    };

    function goInbox() {
        GtgUtilsWeb.route(LfdUrls.spa_file_inbox);
    };

    function goUpload() {
        GtgUtilsWeb.route(LfdUrls.spa_file_upload);
    };

    function goLogout() {
        GtgUtilsWeb.route(LfdUrls.spa_user_logout);
    };
</script>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark lfd-nav-bar">
    <div class="container-fluid">
        {#if loggedIn}
            <a on:click={goHome} ><img src="/lofidrox/img/lofidrox_small.png"/></a>
        {:else}
            <img src="/lofidrox/img/lofidrox_small.png"/>
        {/if}
        <span class="navbar-brand lfd-title">LoFiDroX</span>
        <div>
            <div>
                {#if loggedIn}
                <a class="text-secondary btn btn-sm btn-outline-secondary" on:click={goHome} >Home</a>
                <span class="text-secondary">&nbsp;</span>
                <a class="text-white btn btn-sm btn-outline-light" on:click={goInbox} >Inbox</a>
                <span class="text-secondary">&nbsp;</span>
                <a class="text-white btn btn-sm btn-outline-light" on:click={goUpload} >Upload</a>
                <span class="text-secondary">&nbsp;</span>
                <a class="text-danger btn btn-sm btn-outline-danger" on:click={goLogout} >Logout</a>
                {/if}
            </div>
        </div>
    </div>
</nav>

