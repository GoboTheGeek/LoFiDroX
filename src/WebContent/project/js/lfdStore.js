import { writable } from 'svelte/store';

export let lfdStore = writable( {
    loggedIn: false,
    loading: false
} );
