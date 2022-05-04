<template>
	<view style="width: 80vw; padding-left: 10vw;">
	    <view class="promote-0">Find your</view>
		<view class="promote-1">Activity</view>
		<u-row>
			<u-col span="6">
				<u-tabs lineColor="#000" :list="sortTabs"></u-tabs>
			</u-col>
			<u-col span="6">
				<u-search placeholder="search" v-model="searchKey" :showAction="false" height="50rpx"></u-search>
			</u-col>
		</u-row>
		<image class="entry"></image>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchKey: '',
				
				sortTabs: [
					{ name: 'Popular' },
					{ name: 'ALL' },
				],
			};
		},
		
		onLoad() {
			// // Check if token present
			// if(uni.getStorageSync("token").length == 0)
			// {
			// 	console.log("token not present");
			// 	uni.$u.route({
			// 		url: "pages/index/index",
			// 		type: "reLaunch"
			// 	})
			// }
		},
		
		methods: {
			click1(e) {
				console.log('click1', e);
			},
			
			gotoLogin() {
				uni.$u.route('pages/auth/login')
			},
			
			gotoRegis() {
				uni.$u.route('pages/auth/regis')
			}
		},
	}
</script>

<style lang="scss" scoped>
	.entry {
		border-radius: 30rpx;
	}
	
	.promote-0 {
		font-size: 70rpx;
		margin-top: 20rpx;
	}
	
	.promote-1 {
		font-weight: bold;
		font-size: 70rpx;
		margin-bottom: 30rpx;
	}
</style>
