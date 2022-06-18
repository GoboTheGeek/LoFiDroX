<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
	import GtgUtilsTools from '../../frameworks/gtg-svelte/gtg-utils-tooling.js';
	import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
	import LfdUrls from '../js/lfdUrlManager.js';

	let files = null;
	let filesDsp = null;
	let users = null;
	let selectedUsers = [ ];
	let hasUsers = true;
	let hasFiles = true;

    function doProcess() {
        hasUsers = (0 !== selectedUsers.length);
		hasFiles = (GtgUtilsTools.isArray(filesDsp) && (0 !== filesDsp.length));

        if (hasUsers && hasFiles) {
            processNextFile(0);
        }
    };

	function handleFileUploadChange() {
		filesDsp = [];
		if (0 < files.length) {
			for (let pos = 0; pos < files.length; pos++) {
				filesDsp = [...filesDsp, { name: files[pos].name, uploaded: false, uploading: false } ];
			}
		}
	};

    function processNextFile(index) {
        let fileRead;

        if (index < files.length) {
            filesDsp[index] = { name: filesDsp[index].name, uploaded: false, uploading: true };
            fileRead = new FileReader();
            fileRead.addEventListener("load", function(evt) {
				let dataToSend = {
					filename: files[index].name,
					users: selectedUsers,
					//data: GtgUtilsWeb.extractBase64(fileRead.result)
					data: fileRead.result
				};
                GtgUtilsWeb.postJson(LfdUrls.crud_file_send, dataToSend, function(data) {
	                if (!GtgUtilsTools.isNull(data) && data.written) {
	                    filesDsp[index] = { name: filesDsp[index].name, uploaded: true, uploading: false };
	                    processNextFile(++index);
	                }
	            }, function(error) {
	                GtgUtilsWeb.route(LfdUrls.spa_error);
	            } );
            }, false);
			fileRead.readAsDataURL(files[index]);
        }
    };

    function fetchUsers() {
        GtgUtilsWeb.postJson(LfdUrls.crud_user_list, null, function(data) {
            if (!GtgUtilsTools.isNull(data)) {
                users = data.users;
            }
        }, function(error) {
            GtgUtilsWeb.route(LfdUrls.spa_error);
        } );
    };

    function doClearFiles() {
        filesDsp = [ ];
        hasFiles = true;
    };

	function doClearUsers() {
        selectedUsers = [ ];
        hasUsers = true;
    };

    fetchUsers();
</script>

<main>
	<div class="row">
		<div class="col mx-1 my-2">
			<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
				<div class="container-fluid">
					<span class="navbar-brand text-warning lfd-title">Upload</span>
				</div>
			</nav>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-4">
			<div class="row">
				<div class="col mx-1 my-2">
					<nav class="navbar navbar-dark bg-dark lfd-files-header">
						<div class="container-fluid">
							<span class="navbar-brand text-warning">Users available</span>
							<div>
								<div>
									<a class="btn btn-sm btn-outline-secondary float-end" on:click={doClearUsers}>Clear</a>
								</div>
							</div>
						</div>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col mx-1 my-2">
					<div class="lfd-files-list">
						{#if !hasUsers }
							<p class="bg-danger text-white px-1 py-1">You must choose as least one user</p>
						{/if}
						<div class="lfd-files-list-content">
							<div class="content">
								{#if users}
									{#each users as name}
										<div class="is-size-7">
											<input type="checkbox" name="user" value="{name}" bind:group={selectedUsers} />
											<span class="text-white">{name}</span>
										</div>
									{/each}
								{/if}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-8">
			<div class="row">
				<div class="col mx-1 my-2">
					<nav class="navbar navbar-dark bg-dark lfd-files-header">
						<div class="container-fluid">
							<span class="navbar-brand text-warning">Files to send</span>
							<div>
								<div>
									<a class="btn btn-sm btn-outline-secondary" on:click={doClearFiles}>Clear</a>
									<span class="text-secondary">&nbsp;</span>
									<a class="btn btn-sm btn-outline-primary float-end" on:click={doProcess}>Send&nbsp;<i class="mdi mdi-file-send"></i></a>
								</div>
							</div>
						</div>
					</nav>
				</div>
			</div>
			<div class="row">
				<div class="col mx-1 my-2">
					<div class="lfd-files-list">
						{#if !hasFiles }
							<p class="bg-danger text-white px-1 py-1">You must choose as least one file</p>
						{/if}
						<div class="lfd-files-list-content">
							<div class="content">
								<input id="fileUpload" type="file" multiple bind:files on:change={handleFileUploadChange} />
								{#if filesDsp}
									{#each filesDsp as send}
									<div class="is-size-7">
										{#if !send.uploading && !send.uploaded}
											<span class="icon text-danger"><i class="mdi mdi-timer-sand"></i></span>
										{/if}
										{#if send.uploading}
										<span class="icon text-primary"><i class="mdi mdi-progress-clock"></i></span>
										{/if}
										{#if send.uploaded}
										<span class="icon text-success"><i class="mdi mdi-check-circle-outline"></i></span>
										{/if}
										&nbsp;<span class="text-white">{send.name}</span>
									</div>
									{/each}
								{/if}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
