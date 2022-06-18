<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
    import { Router, Link, Route } from "svelte-routing";
    import GtgUtilsTools from '../../frameworks/gtg-svelte/gtg-utils-tooling.js';
    import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
    import LfdUrls from '../js/lfdUrlManager.js';
    import LfdLocalStore from '../js/lfdLocalStore.js';
    import { lfdStore } from '../js/lfdStore.js';
    import CmpLfdTitle from './cmpLfdTitle.svelte';
    import CmpLfdHome from './cmpLfdHome.svelte';
    import CmpLfdUserLogin from './cmpLfdUserLogin.svelte';
    import CmpLfdUserRegister from './cmpLfdUserRegister.svelte';
    import CmpLfdUserLogout from './cmpLfdUserLogout.svelte';
    import CmpLfdFileUpload from './cmpLfdFileUpload.svelte';
    import CmpLfdFileInbox from './cmpLfdFileInbox.svelte';
    import CmpLfdError from './cmpLfdError.svelte';

    let container = { };
    // fetch query string parameters
    container.urlParams = new URLSearchParams(window.location.search);
    // fetch redirect page
    container.page = container.urlParams.get('page');
    // check if user session is still valid
    container.username = window.localStorage.getItem(LfdLocalStore.field_username);
    // define default route
    container.urlSpa = LfdUrls.spa_user_login;

    // skip user session check if no username found
    if (!GtgUtilsTools.isNullOrEmpty(container.username)) {
        // send check request
        GtgUtilsWeb.postJson(LfdUrls.crud_user_check, { username: container.username }, function(data) {
            if (!GtgUtilsTools.isNull(data) && data.session) {
                // user's session is still valid, goto to homepage
                $lfdStore.loggedIn = true;
                GtgUtilsWeb.route(LfdUrls.spa_home);
            } else {
                // session is now invalid. If requested page is error, show it otherwise show login page
                GtgUtilsWeb.route((LfdUrls.spa_error !== container.page? LfdUrls.spa_user_login: container.page));
            }
        }, function(error) {
            // an error occured, goto to login page
            if (!GtgUtilsTools.isNull(error) && (GtgUtilsWeb.HTTP_CODES.Unauthorized === error)) {
                GtgUtilsWeb.route(LfdUrls.spa_user_login);
            } else {
                GtgUtilsWeb.route(LfdUrls.spa_error);
            }
        } );
    };

    // define default route
    //export let urlSpa = LfdUrls.spa_user_login;
</script>

<main class="hero is-fullheight lfd-scroller-hori-no lfd-scroller-vert-no">
    <div class="hero-head">
        <CmpLfdTitle />
    </div>
    <div class="hero-body">
        <div class="container lfd-container-main">
            <Router url="{container.urlSpa}">
                <Route path="{LfdUrls.spa_error}"><CmpLfdError /></Route>
                <Route path="{LfdUrls.spa_home}"><CmpLfdHome /></Route>
                <Route path="{LfdUrls.spa_user_login}"><CmpLfdUserLogin /></Route>
                <Route path="{LfdUrls.spa_user_register}"><CmpLfdUserRegister /></Route>
                <Route path="{LfdUrls.spa_user_logout}"><CmpLfdUserLogout /></Route>
                <Route path="{LfdUrls.spa_file_upload}"><CmpLfdFileUpload /></Route>
                <Route path="{LfdUrls.spa_file_inbox}"><CmpLfdFileInbox /></Route>
            </Router>
        </div>
    </div>
</main>
