<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
    import { navigate } from "svelte-routing";
    import { onMount } from 'svelte';
    import * as yup from 'yup';
    import GtgUtilsTools from '../../frameworks/gtg-svelte/gtg-utils-tooling.js';
    import GtgUtilsPassword from '../../frameworks/gtg-svelte/gtg-utils-password.js';
    import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
    import LfdUrls from '../js/lfdUrlManager.js';
    import LfdEvents from '../js/lfdEventsManager.js';
    import LfdLocalStore from '../js/lfdLocalStore.js';
    import { lfdStore } from '../js/lfdStore.js';

    let fields = { fieldUsr: null, fieldPwd: null };
    let values = { username: null, password: null };
    let validation = { login: true };
    let errorMessages = { username: null, pwd: null };

    let schemaValidation = yup.object().shape( {
        username: yup.string().nullable().required('You must enter a username').min(4, 'Username must contains at least ${min} letters'),
        pwd: yup.string().nullable().required('You must enter a password').
            min(10, 'Password must contains at least ${min} caracters').
            max(256, 'Passqord must contains up to ${max} caracters long').
            test('pwd-strength', 'Password must contains uppercase letter, lowercase letter, digit, special caracter',
            function(value) {
                return GtgUtilsPassword.checkStrongPwd(value);
            } )
    } );

    async function login() {
        try {
            validation.login = true;
            errorMessages = { username: null, pwd: null };
            await schemaValidation.validate( { 'username': values.username, 'pwd': values.password }, { abortEarly: false } );
            GtgUtilsTools.setLocalValue(LfdLocalStore.field_username, values.username);
            GtgUtilsWeb.postJson(LfdUrls.crud_user_login, { 'username': values.username, 'pwd': values.password }, function(data) {
                if (!GtgUtilsTools.isNull(data) && !GtgUtilsTools.isNull(data.logged) && data.logged) {
                    $lfdStore.loggedIn = true;
                    GtgUtilsWeb.route(LfdUrls.spa_home);
                }
            }, function(error) {
                if (!GtgUtilsTools.isNull(error) && (GtgUtilsWeb.HTTP_CODES.Unauthorized === error)) {
                    validation.login = false;
                } else {
                    GtgUtilsWeb.route(LfdUrls.spa_error);
                }
            } );
        } catch (errors) {
            errorMessages = GtgUtilsTools.turnYupErrorsToArray(errors);
        }
    };

    function goRegister() {
        GtgUtilsWeb.route(LfdUrls.spa_user_register);
    };

    values.username = window.localStorage.getItem(LfdLocalStore.field_username);

    onMount(() => {
        if (!GtgUtilsTools.isNull(values.username)) {
            fields.fieldPwd.focus();
        } else {
            fields.fieldUsr.focus();
        }
    } );
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
                                    <form on:submit|preventDefault|stopPropagation={(e)=>{login();}}>
                                        <p>Please login to your account</p>
                                        <div class="form-outline mb-4">
                                            <input class="input form-control form-control-sm" tabindex="1" type="text" name="fieldUsr" placeholder="Enter your username" bind:value={values.username} bind:this={fields.fieldUsr}>
                                            {#if errorMessages.username }
                                            <p class="bg-danger text-white px-1 py-1">{errorMessages.username}</p>
                                            {/if}
                                        </div>
                                        <div class="form-outline mb-4">
                                            <input class="input form-control form-control-sm" tabindex="2" type="password" name="fieldPwd" placeholder="Enter your password" bind:value={values.password} bind:this={fields.fieldPwd}>
                                            {#if errorMessages.pwd }
                                            <p class="bg-danger text-white px-1 py-1">{errorMessages.pwd}</p>
                                            {/if}
                                        </div>
                                        {#if !validation.login }
                                        <div class="form-outline mb-4">
                                            <p class="bg-danger text-white px-1 py-1">
                                                You cannot login. Please check your login and password.
                                            </p>
                                        </div>
                                        {/if}
                                        <div class="text-center pt-1 mb-5 pb-1">
                                            <button class="btn btn-primary btn-block fa-lg lfd-gradient-colors mb-3" tabindex="3" type="submit">Log in&nbsp;<i class="mdi mdi-login" aria-hidden="true"></i></button>
                                        </div>
                                        <div class="d-flex align-items-center justify-content-center pb-4">
                                            <p class="mb-0 me-2">Don't have an account?</p>
                                            <a type="button" class="btn btn-outline-primary" on:click="{goRegister}" tabindex="4">Create&nbsp;<i class="mdi mdi-account-plus"></i></a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="col-md-6 d-flex align-items-center lfd-gradient-colors">
                                {#if validation.login }
                                    <div class="text-white px-3 py-4 p-md-5 mx-md-4">
                                        <p class="small mb-0"><span class="lfd-app-name">LoFiDroX</span>&nbsp;let you share files over a Local Area Network.&nbsp;
                                            <span class="lfd-app-name">LoFiDroX</span>&nbsp;don't use cloud to synchronize content: files are stored on you local&nbsp;
                                            <span class="lfd-app-name">LoFiDroX</span>&nbsp;instance.
                                        </p>
                                    </div>
                                {:else}
                                    <img src="/lofidrox/img/user_login_error.png" class="lfd-image-illu" alt="Error">
                                {/if}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
