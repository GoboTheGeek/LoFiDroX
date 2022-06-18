<style>
    @import "../../frameworks/bootstrap/css/bootstrap.min.css";
    @import "../css/lofidrox.css";
</style>

<script>
	import GtgUtilsWeb from '../../frameworks/gtg-svelte/gtg-utils-web.js';
	import LfdUrls from '../js/lfdUrlManager.js';

	function goLogin() {
        GtgUtilsWeb.route(LfdUrls.spa_user_login);
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
										<p class="bg-danger text-white px-3 py-4">
											An error occured. You cannot access this page. Please continue to <a class="text-white" on:click="{goLogin}">login page</a>.
										</p>
									</form>
								</div>
							</div>
							<div class="col-md-6 d-flex align-items-center lfd-gradient-colors">
								<img src="/lofidrox/img/error_error.png" class="lfd-image-illu" alt="Error">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>
