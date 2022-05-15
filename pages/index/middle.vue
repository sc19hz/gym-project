<template>
	<view>
		Middle page
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		
		onLoad(params) {
			uni.$u.route({
				url: params.url,
				type: params.type ?? 'redirect',
			})
		},
		
		methods: {
			
		}
	}
</script>

<style lang="scss" scoped>

</style>
