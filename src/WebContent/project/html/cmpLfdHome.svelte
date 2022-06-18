<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
    import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
    import LfdUrls from '../js/lfdUrlManager.js';

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

<main>
    <div class="row">
        <div class="col-lg-12 text-center">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-lg-12 text-center">
            <h3 class="text-white">
                Welcome on&nbsp;<span class="lfd-title text-warning">LoFiDroX</span>
            </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12 text-center">&nbsp;</div>
    </div>
    <div class="row gx-5 text-center">
        <div class="col-lg-4 d-flex align-items-center justify-content-center">
            <div class="card" style="width: 18rem;">
                <img src="/lofidrox/img/home_inbox.png" class="card-img-top" alt="Inbox">
                <div class="card-body">
                    <h5 class="card-title">Inbox</h5>
                    <p class="card-text">To consult files you received</p>
                    <a class="btn btn-sm btn-outline-primary" on:click={goInbox} >Inbox</a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 d-flex align-items-center justify-content-center">
            <div class="card" style="width: 18rem;">
                <img src="/lofidrox/img/home_upload.png" class="card-img-top" alt="Upload">
                <div class="card-body">
                    <h5 class="card-title">Upload</h5>
                    <p class="card-text">To send files to other users</p>
                    <a class="btn btn-sm btn-outline-primary" on:click={goUpload}>Upload</a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 d-flex align-items-center justify-content-center">
            <div class="card" style="width: 18rem;">
                <img src="/lofidrox/img/home_logout.png" class="card-img-top" alt="Logout">
                <div class="card-body">
                    <h5 class="card-title">Logout</h5>
                    <p class="card-text">To quit&nbsp;<span class="lfd-app-name">LoFiDroX</span></p>
                    <a class="text-danger btn btn-sm btn-outline-danger" on:click={goLogout} >Logout</a>
                </div>
            </div>
        </div>
    </div>
</main>
