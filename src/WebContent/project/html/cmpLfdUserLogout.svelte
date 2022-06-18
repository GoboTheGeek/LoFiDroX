<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
    import { navigate } from "svelte-routing";
    import GtgUtilsTools from '../../frameworks/gtg-svelte/gtg-utils-tooling.js';
    import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
    import LfdUrls from '../js/lfdUrlManager.js';
    import LfdLocalStore from '../js/lfdLocalStore.js';
    import { lfdStore } from '../js/lfdStore.js';

    function logout() {
        GtgUtilsWeb.deleteJson(LfdUrls.crud_user_logout, { 'username': GtgUtilsTools.getLocalValue(LfdLocalStore.field_username) }, function(data) {
            if (!GtgUtilsTools.isNull(data) && !GtgUtilsTools.isNull(data.exited) && data.exited) {
                $lfdStore.loggedIn = false;
                GtgUtilsWeb.route(LfdUrls.spa_user_login);
            }
        }, function(error) {
            GtgUtilsWeb.route(LfdUrls.spa_error);
        } );
    };

    function goBack() {
        GtgUtilsWeb.route(LfdUrls.spa_home);
    };
</script>

<main class="container">
    <section class="h-100 lfd-gradient-form">
        <div class="container py-2 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-xl-10">
                    <div class="card rounded-3 text-black">
                        <div class="row g-0">
                            <div class="col-md-6">
                                <div class="card-body p-md-5 mx-md-4">
                                    <div class="text-center">
                                        <img src="/lofidrox/img/lofidrox.png" alt="LoFiDroX" class="lfd-logo">
                                        <h4 class="mt-1 mb-5 pb-1"></h4>
                                    </div>
                                    <form>
                                        <p class="bg-danger text-white px-3 py-4">Are you sure you want to exit <span class="lfd-app-name">LoFiDroX</span>?</p>
                                        <div class="text-center pt-1 mb-5 pb-1">
                                            <a class="btn btn-danger btn-block fa-lg mb-3" type="button" on:click="{logout}">Logout&nbsp;<i class="mdi mdi-logout"></i></a>
                                            <a class="btn btn-outline-dark fa-lg mb-3" on:click="{goBack}">Cancel&nbsp;<i class="mdi mdi-close-circle-outline"></i></a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="col-md-6 d-flex align-items-center lfd-gradient-colors">
                                <img src="/lofidrox/img/user_logout_logout.png" class="lfd-image-illu" alt="Logout">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

