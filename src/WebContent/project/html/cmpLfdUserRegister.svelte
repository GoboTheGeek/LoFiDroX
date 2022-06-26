<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
    import { onMount } from 'svelte';
    import * as yup from 'yup';
    import LfdUrls from '../js/lfdUrlManager.js';
    import LfdStore from '../js/lfdLocalStore.js';
    import GtgUtilsTools from '../../frameworks/gtg-svelte/gtg-utils-tooling.js';
    import GtgUtilsPassword from '../../frameworks/gtg-svelte/gtg-utils-password.js';
    import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';

    let fields = { fUsername: null };
    let values = { username: null, password: null, passwordConf: null };
    let validation = { registered: true };
    let errorMessages = { username: null, pwd: null, pwdCnf: null };

    let schemaValidation = yup.object().shape( {
        username: yup.string().nullable().required('You must enter a username').min(4, 'Username must contains at least ${min} letters'),
        pwd: yup.string().nullable().required('You must enter a password').
            min(10, 'Password must contains at least ${min} caracters').
            max(256, 'Passqord must contains up to ${max} caracters long').
            test('pwd-strength', 'Password must contains uppercase letter, lowercase letter, digit, special caracter',
            function(value) {
                return GtgUtilsPassword.checkStrongPwd(value);
            } ),
        pwdCnf: yup.string().nullable().required('You must confirm password').
            min(10, 'Password must contains at least ${min} caracters').
            max(256, 'Passqord must contains up to ${max} caracters long').
            test('pwd-egality', 'Passwords are not identical',
            function(value) {
                return GtgUtilsPassword.checkEquality(values.password, value);
            } )
    } );

    function goLogin() {
        GtgUtilsWeb.route(LfdUrls.spa_user_login);
    };

    async function register() {
        try {
            validation.registered = true;
            errorMessages = { username: null, pwd: null, pwdCnf: null };
            await schemaValidation.validate( { 'username': values.username, 'pwd': values.password, 'pwdCnf': values.passwordConf }, { abortEarly: false } );
            GtgUtilsWeb.postJson(LfdUrls.crud_user_register, { username: values.username, pwd: values.password }, function(data) {
                if ((null != data) && data.registered) {
                    window.localStorage.setItem(LfdStore.field_username, data.username);
                    GtgUtilsWeb.route(LfdUrls.spa_user_login);
                }
            }, function(error) {
                if (!GtgUtilsTools.isNull(error) && (GtgUtilsWeb.HTTP_CODES.NotAcceptable === error)) {
                    validation.registered = false;
                } else {
                    GtgUtilsWeb.route(LfdUrls.spa_error);
                }
            } );
        } catch (errors) {
            errorMessages = GtgUtilsTools.turnYupErrorsToArray(errors);
        }
    };

    onMount(() => {
        fields.fUsername.focus();
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
                                    <form on:submit|preventDefault|stopPropagation={(e)=>{register();}}>
                                        <p>Please create your account</p>
                                        <div class="form-outline mb-4">
                                            <input class="input form-control form-control-sm" tabindex="1" type="text" placeholder="Enter your name" id="fUsername" bind:value={values.username} bind:this={fields.fUsername}>
                                            {#if errorMessages.username }
                                                <p class="bg-danger text-white px-1 py-1">{errorMessages.username}</p>
                                            {/if}
                                        </div>
                                        <div class="form-outline mb-4">
                                            <input class="input form-control form-control-sm" tabindex="2" type="password" placeholder="Enter your password" id="fPassword" bind:value="{values.password}">
                                            {#if errorMessages.pwd}
                                                <p class="bg-danger text-white px-1 py-1">{errorMessages.pwd}</p>
                                            {/if}
                                        </div>
                                        <div class="form-outline mb-4">
                                            <input class="input form-control form-control-sm" tabindex="3" type="password" placeholder="Confirm your password" id="fPasswordCnf" bind:value="{values.passwordConf}">
                                            {#if errorMessages.pwdCnf}
                                                <p class="bg-danger text-white px-1 py-1">{errorMessages.pwdCnf}</p>
                                            {/if}
                                        </div>
                                        {#if !validation.registered }
                                            <div class="form-outline mb-4">
                                                <p class="bg-danger text-white px-1 py-1">
                                                    You cannot register this account. Please use another username.
                                                </p>
                                            </div>
                                        {/if}
                                        <div class="text-center pt-1 mb-5 pb-1">
                                            <button class="btn btn-primary btn-block fa-lg mb-3" tabindex="4" type="submit">Register&nbsp;<i class="mdi mdi-account-plus"></i></button>
                                            <a class="btn btn-outline-dark fa-lg mb-3" tabindex="5" type="button" on:click="{goLogin}">Cancel&nbsp;<i class="mdi mdi-close"></i></a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="col-md-6 d-flex align-items-center lfd-gradient-colors">
                                {#if validation.registered }
                                    <img src="/lofidrox/img/user_register_register.png" class="lfd-image-illu" alt="Register">
                                {:else}
                                    <img src="/lofidrox/img/user_register_error.png" class="lfd-image-illu" alt="Error">
                                {/if}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
