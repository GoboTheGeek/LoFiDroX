<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
	import GtgUtilsTools from '../../frameworks/gtg-svelte/gtg-utils-tooling.js';
	import GtgUtilsDates from '../../frameworks/gtg-svelte/gtg-utils-dates.js';
	import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
	import LfdUrls from '../js/lfdUrlManager.js';

	let hasFiles = false;
	let files = [ ];
	let selectedFiles = [ ];
	let hasSelectionDown = true;
	let hasSelectionDel = true;

    function processNextFile(index) {
        let dataToSend;
        if (index < selectedFiles.length) {
            dataToSend = { id: selectedFiles[index] };
            GtgUtilsWeb.postJson(LfdUrls.crud_file_down, dataToSend, function(data) {
                if (!GtgUtilsTools.isNull(data)) {
					GtgUtilsWeb.download(data.data, data.filename, data.type);
                    processNextFile(++index);
                }
            }, function(error) {
                GtgUtilsWeb.route(LfdUrls.spa_error);
            } );
        } else {
            // erase selection
            selectedFiles = [ ];
            hasSelectionDown = true;
            // refresh list
            doRefresh();
        }
    };

	function doDownload() {
		hasSelectionDown = ((null != selectedFiles) && (0 < selectedFiles.length));
		if (hasSelectionDown) {
			processNextFile(0);
		}
	};

	function doRefresh() {
		fetchFiles();
	};

	function doDelete() {
		let dataToSend = { files: null };
		hasSelectionDel = ((null != selectedFiles) && (0 < selectedFiles.length));
		if (hasSelectionDel) {
			dataToSend.files = selectedFiles;
			GtgUtilsWeb.deleteJson(LfdUrls.crud_file_del, dataToSend, function(data) {
                if (!GtgUtilsTools.isNull(data)) {
					fetchFiles();
                }
            }, function(error) {
                GtgUtilsWeb.route(LfdUrls.spa_error);
            } );
		}
	};

    function fetchFiles() {
        files = [ ];
        GtgUtilsWeb.postJson(LfdUrls.crud_file_list, null, function(data) {
            if (!GtgUtilsTools.isNull(data)) {
                files = data.files;
                hasFiles = (!GtgUtilsTools.isNull(data.files) && (0 < data.files.length));
                clearSelection();
            }
        }, function(error) {
            GtgUtilsWeb.route(LfdUrls.spa_error);
        } );
    };

	function clearSelection() {
		selectedFiles = [ ];
		hasSelectionDown = true;
		hasSelectionDel = true;
	};

    fetchFiles();
</script>

<main>
	<div class="row">
		<div class="col mx-1 my-2">
			<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
				<div class="container-fluid">
					<span class="navbar-brand text-warning lfd-title">Inbox</span>
					<div>
						<a class="btn btn-sm btn-outline-danger" on:click={doDelete}><i class="mdi mdi-delete-outline"></i></a>
						<span class="text-secondary">&nbsp;</span><a class="btn btn-sm btn-outline-info" on:click={doRefresh}>Refresh&nbsp;<i class="mdi mdi-refresh"></i></a>
						<span class="text-secondary">&nbsp;</span><a class="btn btn-sm btn-outline-primary" on:click={doDownload}>Download&nbsp;<i class="mdi mdi-download"></i></a>
					</div>
				</div>
			</nav>
		</div>
	</div>
	<div class="row">
		<div class="col mx-1 my-2">
			<div class="lfd-files-list">
				{#if !hasFiles }
					<p class="bg-warning text-black px-1 py-1">You have no file to download</p>
				{/if}
				{#if !hasSelectionDown }
					<p class="bg-danger text-white px-1 py-1">You must choose as least one file to download</p>
				{/if}
				{#if !hasSelectionDel }
					<p class="bg-danger text-white px-1 py-1">You must choose as least one file to delete</p>
				{/if}
				<div class="lfd-card-list-content">
					<div class="content">
						<div class="row lfd-files-header">
							<div class="col-sm-8 text-warning">Filename</div>
							<div class="col-sm-4 text-warning">Sent by / on</div>
						</div>
						{#if files}
							<div class="row lfd-files-list-data">
								{#each files as file, idx}
									<div class="col-sm-8 lfd-row-color-{idx % 2} text-white">
										<input type="checkbox" name="user" value="{file.id}" bind:group={selectedFiles} />
										{#if file.isNew}<i class="mdi mdi-alert-decagram text-danger"></i>{/if}
										{file.filename}
									</div>
									<div class="col-sm-4 lfd-row-color-{idx % 2} text-white">{file.sentBy}, {file.sentOn}</div>
								{/each}
							</div>
						{/if}
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
