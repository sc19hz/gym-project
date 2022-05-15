<template>
	<view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		
		onLoad() {
			uni.$u.route({
				url: uni.getStorageSync("token") ? "pages/index/manage" : "pages/auth/login",
				type: "redirect"
			})
		},
		
		methods: {
			
		}
	}
</script>

<style>

</style>
